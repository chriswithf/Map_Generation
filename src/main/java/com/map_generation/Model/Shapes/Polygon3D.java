package com.map_generation.Model.Shapes;


import java.awt.*;

public class Polygon3D {
    public Color color;
    public Point3D v1;
    public Point3D v2;
    public Point3D v3;

    public Polygon3D(Polygon3D otherPolygon) {


        this.color = new Color(color.getRGB());
        this.v1 = new Point3D(otherPolygon.v1);
        this.v2 = new Point3D(otherPolygon.v2);
        this.v3 = new Point3D(otherPolygon.v3);
    }

    public Polygon3D(Color color, Point3D v1, Point3D v2, Point3D v3) {
        this.color = color;
        this.v1 = new Point3D(v1);
        this.v2 = new Point3D(v2);
        this.v3 = new Point3D(v3);
    }

    public Vector3D normalVector() {
        return Vector3DUtility.normalize(Vector3DUtility.cross(
                new Vector3D(v2, v1),
                new Vector3D(v3, v1)));
    }
}