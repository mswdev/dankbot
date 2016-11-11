package client.api.client;

import client.api.oldschool.Game;
import client.api.oldschool.Projection;
import client.api.oldschool.util.Random;
import client.reflection.Reflection;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Spencer on 11/9/2016.
 */
public class Model {

    private Object raw;
    private int x;
    private int y;
    private int z;
    private int orientation;


    public Model(Object raw) {
        this.raw = raw;
        this.orientation = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Model(Object raw, int orientation, int x, int y, int z) {
        this.raw = raw;
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] getXIndices() {
        return (int[]) Reflection.value("Model#getXIndices", raw);
    }

    public int[] getYIndices() {
        return (int[]) Reflection.value("Model#getYIndices", raw);
    }

    public int[] getZIndices() {
        return (int[]) Reflection.value("Model#getZIndices", raw);
    }

    public int[] getXVertices() {
        return (int[]) Reflection.value("Model#getXVertices", raw);
    }

    public int[] getYVertices() {
        return (int[]) Reflection.value("Model#getYVertices", raw);
    }

    public int[] getZVertices() {
        return (int[]) Reflection.value("Model#getZVertices", raw);
    }

    public Polygon[] getTriangles() {
        LinkedList<Polygon> polygons = new LinkedList<>();

        int[] indices1 = getXIndices();
        int[] indices2 = getYIndices();
        int[] indices3 = getZIndices();

        int[] xPoints = getXVertices();
        int[] yPoints = getYVertices();
        int[] zPoints = getZVertices();

        int len = indices1.length;

        for (int i = 0; i < len; ++i) {
            Point p1 = Projection.worldToScreen(x + xPoints[indices1[i]], y + zPoints[indices1[i]], -yPoints[indices1[i]] + z);
            Point p2 = Projection.worldToScreen(x + xPoints[indices2[i]], y + zPoints[indices2[i]], -yPoints[indices2[i]] + z);
            Point p3 = Projection.worldToScreen(x + xPoints[indices3[i]], y + zPoints[indices3[i]], -yPoints[indices3[i]] + z);

            if (p1.x >= 0 && p2.x >= 0 && p3.x >= 0) {
                polygons.add(new Polygon(new int[]{p1.x, p2.x, p3.x}, new int[]{p1.y, p2.y, p3.y}, 3));
            }
        }

        return polygons.toArray(new Polygon[polygons.size()]);
    }

    public boolean isOnScreen() {
        return Projection.VIEWPORT.contains(getRandomPoint());
    }

    public Point getRandomPoint() {
        Polygon[] triangles = getTriangles();
        if (triangles.length > 0) {
            for (int i = 0; i < 100; i++) {
                Polygon p = triangles[Random.nextInt(0, triangles.length)];
                Point point = new Point(p.xpoints[Random.nextInt(0, p.xpoints.length)], p.ypoints[Random.nextInt(0, p.ypoints.length)]);
                if (Projection.VIEWPORT.contains(point))
                    return point;
            }
        }

        return new Point(-1, -1);
    }

    public void draw(Graphics2D graphics, Color color) {
        if (Game.isLoggedIn() && isOnScreen()) {
            graphics.setColor(color);
            Polygon[] tangles = getTriangles();
            for (Polygon triangle : tangles) {
                graphics.draw(triangle);
            }
        }
    }

    public boolean isValid() {
        return raw != null;
    }

    public void updatePosition(int x, int y, int z, int orientation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = orientation;
    }
}
