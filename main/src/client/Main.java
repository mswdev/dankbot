package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Spencer on 11/5/2016.
 */
public class Main {

    private RSFrame BOT_FRAME;
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 600;
    private Canvas canvas;
    private static Main instance;

    public Main() {
        SwingUtilities.invokeLater(() -> BOT_FRAME = new RSFrame());
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public RSFrame getRSFrame() {
        return BOT_FRAME;
    }

    public static Main getInstance() {
        return instance = (instance == null ? new Main() : instance);
    }

    public static void main(String[] args) {
        Main.getInstance();
    }

    public int getWidth() {
        return CANVAS_WIDTH;
    }

    public int getHeight() {
        return CANVAS_HEIGHT;
    }

    public Dimension getDimensions() {
        return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

}