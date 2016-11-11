package client.api.oldschool;

import client.api.client.Client;
import client.api.oldschool.interfaces.Positionable;
import client.api.oldschool.wrappers.RSPlayer;
import client.api.oldschool.wrappers.RSTile;

import java.awt.*;

/**
 * Created by Spencer on 11/7/2016.
 */
public class Drawing {

    public static void drawTileOverlay(Graphics graphics, int x, int y, int zOffset, Color outlineColor, float lineThickness) {
        Point p1 = Projection.worldToScreen(x - 64, y - 64, zOffset);
        Point p2 = Projection.worldToScreen(x - 64, y + 64, zOffset);
        Point p3 = Projection.worldToScreen(x + 64, y + 64, zOffset);
        Point p4 = Projection.worldToScreen(x + 64, y - 64, zOffset);

        Graphics2D g2 = (Graphics2D) graphics;
        Stroke oldStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(lineThickness));
        g2.setColor(outlineColor);
        drawPoints(graphics, p1, p2, p3, p4);
        g2.setStroke(oldStroke);
    }

    public static void drawTile(Graphics graphics, Positionable... tiles) {
        Graphics2D g2 = (Graphics2D) graphics;
        for (Positionable tile : tiles) {
            RSTile pos = tile.getPosition();
            Polygon poly = Projection.getTileBoundsPoly(pos, 0);
            if (poly != null) {
                g2.draw(poly);
            }
        }
    }

    public static void drawPoints(Graphics graphics, Point... points) {
        Polygon poly = new Polygon();
        for (Point point : points) {
            poly.addPoint(point.x, point.y);
        }

        graphics.drawPolygon(poly);
    }

    public static void drawToMinimap(Graphics graphics, Positionable... positionables) {
        int angle = Client.getMapScale() + Client.getMapAngle() & 0x7FF;
        int mapOffset = Client.getMapOffset();
        int appletWidth = Client.getAppletWidth();
        boolean resizable = Client.isResizable();

        RSPlayer player = Players.getLocalPlayer();
        if (player != null) {
            RSTile playerTile = player.getPosition();
            if (playerTile != null) {
                for (Positionable tile : positionables) {
                    Point tilePoint = Projection.worldToMiniMap(tile.getPosition(), playerTile, angle, mapOffset, appletWidth, resizable);
                    graphics.drawOval(tilePoint.x, tilePoint.y, 4, 4);
                }
            }
        }
    }
}
