package com.map_generation.Model.Shapes;

public final class Vector3DUtility {
    public static double dotProduct(Vector3D v1, Vector3D v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static Vector3D cross(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }

    public static Vector3D normalize(Vector3D v) {
        double magnitude = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);

        v.x /= magnitude;
        v.y /= magnitude;
        v.z /= magnitude;

        return v;
    }
}
