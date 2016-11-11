package client.api.debuggers;

import client.api.client.Model;
import client.api.oldschool.Drawing;
import client.api.oldschool.Game;
import client.api.oldschool.Players;
import client.api.oldschool.interfaces.Painting;
import client.api.oldschool.wrappers.RSPlayer;
import client.api.oldschool.wrappers.RSTile;

import java.awt.*;

/**
 * Created by Spencer on 11/7/2016.
 */
public class PositionDebugger implements Painting {

    RSPlayer me ;

    @Override
    public void onPaint(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;

        if (Game.getGameState() == 30) {
            graphics.setColor(Color.GREEN);

            if (me == null)
                me = Players.getLocalPlayer();

            if (me.isValid()) {
                RSTile position = me.getPosition();
                if (position != null) {
                    Drawing.drawTile(graphics, position);
                    Drawing.drawToMinimap(graphics, position);

                    Model model = me.getModel();
                    if (model != null) {
                        model.draw(g2, Color.white);
                    }
                }
            }
        }
    }

}
