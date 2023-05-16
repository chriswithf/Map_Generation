package com.map_generation.Model.Shapes;

public class Vector3D extends Point3D {
    public Vector3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3D(Point3D p1, Point3D p2) {
        super(
                p2.x - p1.x,
                p2.y - p1.y,
                p2.z - p1.z);
    }

    public void clear() {
        x = 0;
        y = 0;
        z = 0;
    }
}
