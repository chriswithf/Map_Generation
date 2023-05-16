package com.map_generation.Model.Shapes;

public final class Point3DUtility {
    public static double dist(Point3D p1, Point3D p2) {
        final double x = Math.pow(p2.x - p1.x, 2);
        final double y = Math.pow(p2.y - p1.y, 2);
        final double z = Math.pow(p2.z - p1.z, 2);

        return Math.sqrt(x + y + z);
    }

    public static void convert(Point2D p2d, Point3D p3d, Point3D originPoint, int width, int height, double scale) {
        final double x = (originPoint.y + p3d.y) * scale;
        final double y = (originPoint.z + p3d.z) * scale;
        final double depth = (originPoint.x + p3d.x) * scale;

        final double theta = Math.atan2(y, x);
        final double localScale = Math.abs(1400 / (-depth + 1400));
        final double dist = Math.sqrt(x * x + y * y) * localScale;

        p2d.x = (double) (width / 2) + dist * Math.cos(theta);
        p2d.y = (double) (height / 2) - dist * Math.sin(theta);
        p2d.depth = localScale;
    }

    public static void rotate(Point3D p, Vector3D rotateVector) {
        rotateX(p, rotateVector.x);
        rotateY(p, rotateVector.y);
        rotateZ(p, rotateVector.z);
    }

    private static void rotateX(Point3D p, double degrees) {
        if (degrees == 0) {
            return;
        }

        double radius = Math.sqrt(p.y * p.y + p.z * p.z);
        double theta = Math.atan2(p.z, p.y) + Math.toRadians(degrees);

        p.y = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }

    private static void rotateY(Point3D p, double degrees) {
        if (degrees == 0) {
            return;
        }

        double radius = Math.sqrt(p.x * p.x + p.z * p.z);
        double theta = Math.atan2(p.x, p.z) + Math.toRadians(degrees);

        p.z = radius * Math.cos(theta);
        p.x = radius * Math.sin(theta);
    }

    private static void rotateZ(Point3D p, double degrees) {
        if (degrees == 0) {
            return;
        }

        double radius = Math.sqrt(p.x * p.x + p.y * p.y);
        double theta = Math.atan2(p.y, p.x) + Math.toRadians(degrees);

        p.x = radius * Math.cos(theta);
        p.y = radius * Math.sin(theta);
    }
}
