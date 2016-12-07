package client.api.debuggers;

import client.api.client.Model;
import client.api.oldschool.Drawing;
import client.api.oldschool.Game;
import client.api.oldschool.Players;
import client.api.oldschool.interfaces.Painting;
import client.api.oldschool.wrappers.RSCharacter;
import client.api.oldschool.wrappers.RSPlayer;
import client.api.oldschool.wrappers.RSTile;

import java.awt.*;

/**
 * Created by Spencer on 11/7/2016.
 */
public class PlayerDebugger implements Painting {

    RSPlayer me;

    @Override
    public int onPaint(Graphics graphics, int x, int y) {
        Graphics2D g2 = (Graphics2D) graphics;

        if (Game.getGameState() == 30) {
            graphics.setColor(Color.GREEN);

            if (me == null)
                me = Players.getLocalPlayer();

            if (me.isValid()) {
                RSCharacter character = me;

                /*graphics.drawString("getAnmiation: " + character.getAnmiation(), 5, y += 15);
                graphics.drawString("getCombatTime: " + character.getCombatTime(), 5, y += 15);
                graphics.drawString("getFrameOne: " + character.getFrameOne(), 5, y += 15);
                graphics.drawString("getFrameTwo: " + character.getFrameTwo(), 5, y += 15);
                graphics.drawString("getInteractingIndex: " + character.getInteractingIndex(), 5, y += 15);
                graphics.drawString("getLocalX: " + character.getLocalX(), 5, y += 15);
                graphics.drawString("getLocalY: " + character.getLocalY(), 5, y += 15);
                graphics.drawString("getMessage: " + character.getMessage(), 5, y += 15);
                graphics.drawString("getOrientation: " + character.getOrientation(), 5, y += 15);
                graphics.drawString("getQueueSize: " + character.getQueueSize(), 5, y += 15);
                graphics.drawString("getQueueX: " + character.getQueueX(), 5, y += 15);
                graphics.drawString("getQueueY: " + character.getQueueY(), 5, y += 15);
                graphics.drawString("getRuntimeAnimation: " + character.getRuntimeAnimation(), 5, y += 15);
                graphics.drawString("getStandAnimation: " + character.getStandAnimation(), 5, y += 15);*/

                RSTile position = me.getPosition();
                Drawing.drawTile(graphics, position);
                Drawing.drawToMinimap(graphics, position);

                Model model = me.getModel();
                if (model != null && model.isValid()) {
                    model.draw(g2, Color.white);
                }
            }
        }

        return y;
    }

}
