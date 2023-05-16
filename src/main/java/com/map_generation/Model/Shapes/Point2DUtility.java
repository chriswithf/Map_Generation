package com.map_generation.Model.Shapes;


public class Point2DUtility {
    public static double averageDepth(double x, double y, Point2D p1, Point2D p2, Point2D p3) {
        final Vector3D v1 = new Vector3D(
                p1.x - p2.x,
                p1.y - p2.y,
                p1.depth - p2.depth);

        final Vector3D v2 = new Vector3D(
                p1.x - p3.x,
                p1.y - p3.y,
                p1.depth - p3.depth);

        final Vector3D cross = Vector3DUtility.cross(v1, v2);

        return (1 / cross.z) * (cross.x * p1.x + cross.y * p1.y + cross.z *
                p1.depth - cross.x * x - cross.y * y);
    }

    public static boolean pointInTriangle(double x, double y, Point2D p1, Point2D p2, Point2D p3) {
        final double d1 = sign(x, y, p1, p2);
        final double d2 = sign(x, y, p2, p3);
        final double d3 = sign(x, y, p3, p1);

        final boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        final boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }

    public static double sign(double x, double y, Point2D p2, Point2D p3) {
        return (x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (y - p3.y);
    }

    public static boolean inRange(double x, double y, int width, int height) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }
}
