package com.map_generation.Model.Shapes;


public class Line2DUtility {
    public static boolean intersects(Point2D start1, Point2D end1, Point2D start2, Point2D end2) {
        final Point2D p1 = new Point2D(end1.x - start1.x, end1.y - start1.y);
        final Point2D p2 = new Point2D(end2.x - start2.x, end2.y - start2.y);

        final double multiplicationResult = p1.x * p2.y - p1.y * p2.x;

        /*
         * Lines are parallel.
         * */

        if (multiplicationResult == 0) return false;

        final Point2D p3 = new Point2D(start2.x - start1.x, start2.y - start1.y);

        final double t = (p3.x * p2.y - p3.y * p2.x) / multiplicationResult;
        if (t < 0 || t > 1) return false;

        final double d = (p3.x * p1.y - p3.y * p1.x) / multiplicationResult;

        return !(d < 0) && !(d > 1);
    }
}
