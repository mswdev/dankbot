package client.api.oldschool;

import client.api.client.Client;
import client.api.oldschool.wrappers.RSPlayer;
import client.api.oldschool.wrappers.RSTile;

import java.awt.*;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Projection {

    public static final Rectangle VIEWPORT = new Rectangle(5, 5, 509, 332);

    /**
     * The SIN values of a given set of angles.
     */
    public static int[] SINE = new int[2048];
    /**
     * The COS values of a given set of angles.
     */
    public static int[] COSINE = new int[2048];

    static {
        for (int i = 0; i < SINE.length; i++) {
            SINE[i] = (int) (65536.0D * Math.sin((double) i * 0.0030679615D));
            COSINE[i] = (int) (65536.0D * Math.cos((double) i * 0.0030679615D));
        }
    }

    public static Polygon getTileBoundsPoly(RSTile tile, int height) {
        Point[] bounds = getTileBounds(tile, height);
        if (bounds != null) {
            Polygon poly = new Polygon();
            for (Point point : bounds) {
                poly.addPoint(point.x, point.y);
            }
            return poly;
        }

        return null;
    }

    public static Point[] getTileBounds(RSTile tile, int height) {
        if (tile.getType() != RSTile.TYPES.WORLD) {
            tile = tile.getWorldTile();
        }

        Point p1 = worldToScreen(tile.getX(), tile.getY() + 128, height);
        Point p2 = worldToScreen(tile.getX() + 128, tile.getY() + 128, height);
        Point p3 = worldToScreen(tile.getX() + 128, tile.getY(), height);
        Point p4 = worldToScreen(tile.getX(), tile.getY(), height);
        Point invalid = new Point(-1, -1);

        if (p1.equals(invalid) || p2.equals(invalid) || p3.equals(invalid) || p4.equals(invalid)) {
            return null;
        }

        return new Point[] {p1, p2, p3, p4};
    }

    public static int getTileHeight(int plane, int x, int y) {
        int xx = x >> 7;
        int yy = y >> 7;
        if (xx < 0 || yy < 0 || xx > 103 || yy > 103) {
            return 0;
        }
        int[][][] tileHeights = Client.getTileHeights();
        if (plane > tileHeights.length - 1) return 0;

        int aa = tileHeights[plane][xx][yy] * (128 - (x & 0x7F)) + tileHeights[plane][xx + 1][yy] * (x & 0x7F) >> 7;
        int ab = tileHeights[plane][xx][yy + 1] * (128 - (x & 0x7F)) + tileHeights[plane][xx + 1][yy + 1] * (x & 0x7F) >> 7;

        return aa * (128 - (y & 0x7F)) + ab * (y & 0x7F) >> 7;
    }

    public static Point worldToScreen(int x, int y, int height) {
        if (x < 128 || y < 128 || x > 13056 || y > 13056) {
            return new Point(-1, -1);
        }

        int z = getTileHeight(Client.getPlane(), x, y) - height;
        x -= Camera.getCameraX();
        z -= Camera.getCameraZ();
        y -= Camera.getCameraY();

        int yaw = Camera.getCameraYaw();
        int pitch = Camera.getCameraPitch();

        int pitch_sin = SINE[pitch];
        int pitch_cos = COSINE[pitch];
        int yaw_sin = SINE[yaw];
        int yaw_cos = COSINE[yaw];

        int _angle = y * yaw_sin + x * yaw_cos >> 16;

        y = y * yaw_cos - x * yaw_sin >> 16;
        x = _angle;
        _angle = z * pitch_cos - y * pitch_sin >> 16;
        y = z * pitch_sin + y * pitch_cos >> 16;


        if (y >= 50) {
            int scale = Client.getViewPortScale();
            x = Client.getViewPortWidth() / 2 + x * scale / y;
            y = Client.getViewPortHeight() / 2 + _angle * scale / y;
            return new Point(x, y);
            //return new Point(258 + (regionX << 9) / regionY, (_angle << 9) / regionY + 170);
        }

        return new Point(-1, -1);
    }

    public static Point worldToMiniMap(RSTile tile) {
        int x = tile.getX();
        int y = tile.getY();

        int angle = Client.getMapScale() + Client.getMapAngle() & 0x7FF;
        RSPlayer player = Players.getLocalPlayer();
        if (player != null) {
            RSTile playerTile = player.getPosition();
            if (playerTile != null) {
                x = x / 32 - playerTile.getX() / 32;
                y = y / 32 - playerTile.getY() / 32;

                int dist = x * x + y * y;
                if (dist < 6400) {
                    int sin = SINE[angle];
                    int cos = COSINE[angle];

                    int mapOffset = Client.getMapOffset();
                    sin = sin * 256 / ( + 256);
                    cos = cos * 256 / (mapOffset + 256);

                    int xx = y * sin + cos * x >> 16;
                    int yy = sin * x - y * cos >> 16;

                    int miniMapX = Client.getAppletWidth() - (!Client.isResizable() ? 208 : 167);

                    x = (miniMapX + 167 / 2) + xx;
                    y = (167 / 2 - 1) + yy;
                    return new Point(x, y);
                }
            }
        }

        return new Point(-1, -1);
    }

    public static Point worldToMiniMap(RSTile tile, RSTile playerTile, int angle, int mapOffset, int appletWidth, boolean resizable) {
        int x = tile.getX();
        int y = tile.getY();

        x = x / 32 - playerTile.getX() / 32;
        y = y / 32 - playerTile.getY() / 32;

        int dist = x * x + y * y;
        if (dist < 6400) {
            int sin = SINE[angle];
            int cos = COSINE[angle];

            sin = sin * 256 / ( + 256);
            cos = cos * 256 / (mapOffset + 256);

            int xx = y * sin + cos * x >> 16;
            int yy = sin * x - y * cos >> 16;

            int miniMapX = appletWidth - (!resizable ? 208 : 167);

            x = (miniMapX + 167 / 2) + xx;
            y = (167 / 2 - 1) + yy;
            return new Point(x, y);
        }

        return new Point(-1, -1);
    }

}
