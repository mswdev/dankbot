package client;

import client.api.client.Client;
import client.api.debuggers.ClassDebugger;
import client.api.debuggers.InstanceDebugger;
import client.api.debuggers.PositionDebugger;
import client.api.oldschool.Camera;
import client.api.oldschool.Game;
import client.api.oldschool.Players;
import client.api.oldschool.interfaces.Painting;
import client.api.oldschool.wrappers.RSPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Spencer on 11/5/2016.
 */
public class RSCanvas extends java.awt.Canvas {

    private final java.awt.Canvas canvas;

    private final BufferedImage BUFFER_1 = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);
    private final BufferedImage BUFFER_2 = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);

    private ArrayList<Painting> paints = new ArrayList<>();

    private long time_taken = 0;
    private long begin_time = 0;
    private boolean draw_canvas = true;
    private int paint_delay = 10;

    public RSCanvas(Canvas canvas) {
        this.canvas = canvas;
        canvas.setSize(new Dimension(Main.getInstance().getWidth(), Main.getInstance().getHeight()));

        Collections.addAll(paints, new PositionDebugger(), new ClassDebugger(new Class[] {Game.class, Camera.class}), new InstanceDebugger<>(() -> {
            RSPlayer player = Client.getGameState() == 30 ? Players.getLocalPlayer() : null;
            return player != null && player.isValid() ? player : null;
        }));
    }

    @Override
    public Graphics getGraphics() {
        begin_time = System.currentTimeMillis();

        final Graphics graphics = BUFFER_1.getGraphics();
        if (draw_canvas) {
            graphics.drawImage(BUFFER_2, 0, 0, null);

            int y = 5;
            for (Painting paint : getPaints()) {
                if (paint instanceof InstanceDebugger) {
                    InstanceDebugger debugger = (InstanceDebugger) paint;
                    if (!debugger.isValid()) {
                        getPaints().remove(paint);
                        continue;
                    }
                }

                y = paint.onPaint(graphics, 10, y);
                paint.onPaint(graphics);
            }
        }

        graphics.dispose();

        final Graphics2D rend = (Graphics2D) canvas.getGraphics();
        rend.drawImage(BUFFER_1, 0, 0, null);

        time_taken = System.currentTimeMillis() - begin_time;

        try {
            Thread.sleep((int) ((1000 / 30) - time_taken) + paint_delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return BUFFER_2.getGraphics();
    }

    private synchronized ArrayList<Painting> getPaints() {
        return paints;
    }

    @Override
    public int hashCode() {
        return canvas.hashCode();
    }

    public java.awt.Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {

    }

    @Override
    public void setSize(int width, int height) {

    }

    @Override
    public void setLocation(int x, int y) {
        canvas.setLocation(0, 0);
        canvas.setPreferredSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));
    }

}
