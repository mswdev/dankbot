package client.api.client;

import client.api.oldschool.Game;
import client.api.oldschool.Projection;
import client.api.oldschool.util.Random;
import client.reflection.Reflection;

import java.awt.*;
import java.util.Arrays;
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

    private int[] xPoints, yPoints, zPoints;
    private int[] xTriangles, yTriangles, zTriangles;


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

    public void applyChanges(int[] x, int[] y, int[] z, int[] triX, int[] triY, int[] triZ) {
        this.xPoints = Arrays.copyOf(x, x.length);
        this.yPoints = Arrays.copyOf(y, y.length);
        this.zPoints = Arrays.copyOf(z, z.length);

        this.xTriangles = Arrays.copyOf(triX, triX.length);
        this.yTriangles = Arrays.copyOf(triY, triY.length);
        this.zTriangles = Arrays.copyOf(triZ, triZ.length);

        if (orientation != 0) {
            int theta = orientation & 0x3fff;
            int sin = Projection.SINE[theta];
            int cos = Projection.COSINE[theta];

            int[] orginal_x;
            int[] orginal_z;

            orginal_x = Arrays.copyOfRange(xPoints, 0, xPoints.length);
            orginal_z = Arrays.copyOfRange(zPoints, 0, zPoints.length);

            for (int i = 0; i < xPoints.length; ++i) {
                xPoints[i] = (orginal_x[i] * cos + orginal_z[i] * sin >> 15) >> 1;
                zPoints[i] = (orginal_z[i] * cos - orginal_x[i] * sin >> 15) >> 1;
            }
        }
    }

    public Polygon[] getTriangles() {
        LinkedList<Polygon> polygons = new LinkedList<>();

        applyChanges(getXVertices(), getYVertices(), getZVertices(), getXIndices(), getYIndices(), getZIndices());

        int len = xTriangles.length;

        for (int i = 0; i < len; ++i) {
            Point p1 = Projection.worldToScreen(x + xPoints[xTriangles[i]], y + zPoints[xTriangles[i]], -yPoints[xTriangles[i]] + z);
            Point p2 = Projection.worldToScreen(x + xPoints[yTriangles[i]], y + zPoints[yTriangles[i]], -yPoints[yTriangles[i]] + z);
            Point p3 = Projection.worldToScreen(x + xPoints[zTriangles[i]], y + zPoints[zTriangles[i]], -yPoints[zTriangles[i]] + z);

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
