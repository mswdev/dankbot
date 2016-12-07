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

    /**
     * Draws the tile(s) polygons to the screen.
     *
     * @param graphics Graphics.
     * @param tiles    The tiles to draw.
     */
    public static void drawTile(Graphics graphics, Positionable... tiles) {
        final Graphics2D G2 = (Graphics2D) graphics;
        for (Positionable tile : tiles) {
            final RSTile POSITION = tile.getPosition();
            final Polygon POLYGON = Projection.getTileBoundsPoly(POSITION, 0);
            if (POLYGON == null)
                continue;

            G2.draw(POLYGON);
        }
    }

    /**
     * Draws the tiles(s) polygons to the minimap.
     *
     * @param graphics      Graphics.
     * @param positionables The tiles to draw.
     */
    public static void drawToMinimap(Graphics graphics, Positionable... positionables) {
        final int ANGLE = Client.getMapScale() + Client.getMapAngle() & 0x7FF;
        final int MAP_OFFSET = Client.getMapOffset();
        final int APPLET_WIDTH = Client.getAppletWidth();
        final boolean IS_RESIZABLE = Client.isResizable();

        final RSPlayer PLAYER = Players.getLocalPlayer();
        if (PLAYER == null)
            return;

        final RSTile PLAYER_TILE = PLAYER.getPosition();
        if (PLAYER_TILE == null)
            return;

        for (Positionable tile : positionables) {
            final Point TILE_POINT = Projection.worldToMiniMap(tile.getPosition(), PLAYER_TILE, ANGLE, MAP_OFFSET, APPLET_WIDTH, IS_RESIZABLE);
            graphics.drawOval(TILE_POINT.x, TILE_POINT.y, 4, 4);
        }
    }
}
