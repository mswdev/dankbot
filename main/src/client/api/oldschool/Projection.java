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

    /**
     * Gets the polygon from the RSTile.
     *
     * @param tile   The RSTile to get the polygon.
     * @param height The height of the RSTile.
     * @return The polygon of the RSTile.
     */
    public static Polygon getTileBoundsPoly(RSTile tile, int height) {
        final Point[] BOUNDS = getTileBounds(tile, height);
        if (BOUNDS == null)
            return null;

        final Polygon POLYGON = new Polygon();
        for (Point point : BOUNDS)
            POLYGON.addPoint(point.x, point.y);

        return POLYGON;
    }

    /**
     * Gets the tile bounds of the RSTile.
     *
     * @param tile   The RSTile to get the tile bounds.
     * @param height The height of the RSTile.
     * @return The tile bounds of the RSTile.
     */
    public static Point[] getTileBounds(RSTile tile, int height) {
        if (tile.getType() != RSTile.TYPES.WORLD)
            tile = tile.getWorldTile();

        final Point POINT_1 = worldToScreen(tile.getX(), tile.getY() + 128, height);
        final Point POINT_2 = worldToScreen(tile.getX() + 128, tile.getY() + 128, height);
        final Point POINT_3 = worldToScreen(tile.getX() + 128, tile.getY(), height);
        final Point POINT_4 = worldToScreen(tile.getX(), tile.getY(), height);
        final Point invalid = new Point(-1, -1);

        if (POINT_1.equals(invalid) || POINT_2.equals(invalid) || POINT_3.equals(invalid) || POINT_4.equals(invalid))
            return null;

        return new Point[]{POINT_1, POINT_2, POINT_3, POINT_4};
    }

    /**
     * Gets the height of the RSTile
     *
     * @param x     The X position of the RSTile.
     * @param y     The Y position of the RSTile.
     * @param plane The plane of the RSTile.
     * @return The height of the RSTile.
     */
    public static int getTileHeight(int x, int y, int plane) {
        final int XX = x >> 7;
        final int YY = y >> 7;

        if (XX < 0 || YY < 0 || XX > 103 || YY > 103)
            return 0;

        final int[][][] TILE_HEIGHTS = Client.getTileHeights();
        if (plane > TILE_HEIGHTS.length - 1)
            return 0;

        final int AA = TILE_HEIGHTS[plane][XX][YY] * (128 - (x & 0x7F)) + TILE_HEIGHTS[plane][XX + 1][YY] * (x & 0x7F) >> 7;
        final int AB = TILE_HEIGHTS[plane][XX][YY + 1] * (128 - (x & 0x7F)) + TILE_HEIGHTS[plane][XX + 1][YY + 1] * (x & 0x7F) >> 7;

        return AA * (128 - (y & 0x7F)) + AB * (y & 0x7F) >> 7;
    }

    /**
     * Converts a world point to a screen point.
     *
     * @param x      The X position of the world point.
     * @param y      The Y position of the world point.
     * @param height The height of the world point.
     * @return The screen point from the world point.
     */
    public static Point worldToScreen(int x, int y, int height) {
        if (x < 128 || y < 128 || x > 13056 || y > 13056)
            return new Point(-1, -1);

        int z = getTileHeight(x, y, Client.getPlane()) - height;
        x -= Camera.getCameraX();
        z -= Camera.getCameraZ();
        y -= Camera.getCameraY();

        final int YAW = Camera.getCameraYaw();
        final int PITCH = Camera.getCameraPitch();

        final int PITCH_SIN = SINE[PITCH];
        final int PITCH_COS = COSINE[PITCH];
        final int YAW_SIN = SINE[YAW];
        final int YAW_COS = COSINE[YAW];

        int _angle = y * YAW_SIN + x * YAW_COS >> 16;

        y = y * YAW_COS - x * YAW_SIN >> 16;
        x = _angle;
        _angle = z * PITCH_COS - y * PITCH_SIN >> 16;
        y = z * PITCH_SIN + y * PITCH_COS >> 16;

        if (y >= 50) {
            final int SCALE = Client.getViewPortScale();
            x = Client.getViewPortWidth() / 2 + x * SCALE / y;
            y = Client.getViewPortHeight() / 2 + _angle * SCALE / y;
            return new Point(x, y);
        }

        return new Point(-1, -1);
    }

    /**
     * Coverts an RSTile to a point on the minimap.
     *
     * @param tile The RSTile to convert.
     * @return The point on the minimap from the RSTile.
     */
    public static Point worldToMiniMap(RSTile tile) {
        final int ANGLE = Client.getMapScale() + Client.getMapAngle() & 0x7FF;

        final RSPlayer PLAYER = Players.getLocalPlayer();
        if (!PLAYER.isValid())
            return new Point(-1, -1);

        final RSTile PLAYER_TILE = PLAYER.getPosition();
        if (PLAYER_TILE == null)
            return new Point(-1, -1);

        return worldToMiniMap(tile, PLAYER_TILE, ANGLE, Client.getMapOffset(), Client.getAppletWidth(), Client.isResizable());
    }

    /**
     * Coverts an RSTile to a point on the minimap.
     *
     * @param tile         The RSTile to convert.
     * @param player_tile  The RSPlayer's position.
     * @param angle        The angle of the minimap.
     * @param map_offset   The map offset.
     * @param applet_width The width of the applet.
     * @param resizable    True if RuneScape is set to resizable; false otherwise.
     * @return The point on the minimap from the RSTile.
     */
    public static Point worldToMiniMap(RSTile tile, RSTile player_tile, int angle, int map_offset, int applet_width, boolean resizable) {
        int x = tile.getX();
        int y = tile.getY();

        x = x / 32 - player_tile.getX() / 32;
        y = y / 32 - player_tile.getY() / 32;

        int dist = x * x + y * y;
        if (dist < 6400) {
            int sin = SINE[angle];
            int cos = COSINE[angle];

            sin = sin * 256 / (+256);
            cos = cos * 256 / (map_offset + 256);

            final int XX = y * sin + cos * x >> 16;
            final int YY = sin * x - y * cos >> 16;

            final int MINIMAP_X = applet_width - (!resizable ? 208 : 167);

            x = (MINIMAP_X + 167 / 2) + XX;
            y = (167 / 2 - 1) + YY;
            return new Point(x, y);
        }

        return new Point(-1, -1);
    }

}
