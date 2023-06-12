package com.map_generation.Model.Shapes;


import java.util.Objects;

public class Point3D {
    public double x;
    public double y;
    public double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Point3D otherPoint) {
        x = otherPoint.x;
        y = otherPoint.y;
        z = otherPoint.z;
    }

    @Override
    public boolean equals(Object o) {
        Point3D point3D = (Point3D) o;
        return Double.compare(point3D.y, y) == 0 &&
                Double.compare(point3D.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
