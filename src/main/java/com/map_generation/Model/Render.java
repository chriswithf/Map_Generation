package com.map_generation.Model;

import com.map_generation.Model.Input.Keyboard;
import com.map_generation.Model.Input.Mouse;
import com.map_generation.Model.Shapes.*;
import com.map_generation.View.Window3DTerrain;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for rendering the map in 3D
 * The Render class is responsible for rendering a 3D map window. It manages the
 * display and interaction controls,
 * such as mouse and keyboard input. The class utilizes buffers for depth and
 * pixel information to efficiently render polygons in the window. It supports
 * zooming, panning, and rotation of the rendered map. The class also provides
 * methods to start and stop the rendering process and to set the polygons to be
 * rendered.
 * 
 * @author Chris
 */

public class Render {
    private final int width;
    private final int height;
    private Window3DTerrain window3DTerrain;
    private final Mouse mouse;
    private final Keyboard keyboard;
    private final Point3D cameraPoint;
    private final Point3D originPoint;
    private final Vector3D lightVector;
    private double[] depthBuffer;
    private int[] pixelBuffer;
    private Vector3D rotateVector;
    private List<Polygon3D> polygons;
    private double scale;
    private boolean isRunning;

    /**
     * @param width  the width of the map
     * @param height the height of the map
     */
    public Render(int width, int height) {
        this.width = width;
        this.height = height;
        this.mouse = new Mouse();
        this.keyboard = new Keyboard();
        this.cameraPoint = new Point3D(200, 0, 0);
        this.originPoint = new Point3D(0, 0, 0);
        this.lightVector = new Vector3D(1, 1, 1);
        this.rotateVector = new Vector3D(0, 0, 0);
        this.polygons = new ArrayList<>();
        this.scale = 150;
        this.isRunning = false;
    }

    /**
     * method to start the window for the 3D map
     * ensures that the window is not already running
     */
    public void start3DMapWindow() {

        // only if the window3DTerrain is null
        // to ensurre while rerendering no second window is created
        if (window3DTerrain == null) {
            depthBuffer = new double[width * height];
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            pixelBuffer = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
            window3DTerrain = new Window3DTerrain(bufferedImage, mouse, keyboard);
        }

    }

    private void renderPolygon(Polygon3D polygon) {

        /*
         * The rotate method in the Polygon3DUtility class is responsible for rotating a
         * Polygon3D object around the x, y, and z axes by a specified angle.
         * 
         * The method takes two parameters: the Polygon3D object to be rotated, and a
         * Vector3D object that represents the rotation angle around each axis. The
         * rotation angle is specified in degrees.
         * 
         * The method first converts the rotation angle from degrees to radians, since
         * the Math.sin and Math.cos methods expect angles in radians. It then applies
         * the rotation matrix to each vertex of the polygon to rotate it around the x,
         * y, and z axes.
         * 
         * The rotation matrix is a 3x3 matrix that represents the rotation around a
         * single axis. To rotate a vertex around multiple axes, the rotation matrices
         * for each axis are multiplied together to form a single 3x3 matrix that
         * represents the combined rotation.
         * 
         * The rotation matrix for the x-axis is:
         * [1 0 0 ]
         * [0 cos(theta) -sin(theta)]
         * [0 sin(theta) cos(theta)]
         * 
         * where theta is the rotation angle around the x-axis.
         * 
         * The rotation matrix for the y-axis is:
         * 
         * [cos(theta) 0 sin(theta)]
         * [0 1 0 ]
         * [-sin(theta) 0 cos(theta)]
         * 
         * where theta is the rotation angle around the y-axis.
         * 
         * The rotation matrix for the z-axis is:
         * 
         * [cos(theta) -sin(theta) 0 ]
         * [sin(theta) cos(theta) 0 ]
         * [0 0 1 ]
         * 
         * where theta is the rotation angle around the z-axis.
         * 
         * The rotated vertices are then stored back into the Polygon3D object, which is
         * then used to render the polygon onto the screen.
         * 
         */
        Polygon3DUtility.rotate(polygon, rotateVector);

        final Triangle2D triangle = new Triangle2D();

        /*
         * The convert method in the Polygon3DUtility class is responsible for
         * converting a Polygon3D object into a 2D triangle that can be rendered onto
         * the screen.
         * 
         * The method takes six parameters: a Triangle2D object to store the resulting
         * triangle, the Polygon3D object to be converted, the origin point of the
         * screen, the width and height of the screen, and a scaling factor.
         * 
         * The method first calculates the center point of the polygon by averaging the
         * x, y, and z coordinates of its vertices. This center point is used as the
         * reference point for the conversion.
         * 
         * Next, the method iterates over each vertex of the polygon and calculates its
         * 2D coordinates on the screen using the following formula:
         * 
         * x2d = (x3d - centerX) * scale + originX
         * y2d = (y3d - centerY) * scale + originY
         * 
         * where x3d and y3d are the x and y coordinates of the vertex in 3D space,
         * centerX and centerY are the x and y coordinates of the center point of the
         * polygon, scale is the scaling factor, and originX and originY are the x and y
         * coordinates of the origin point of the screen.
         * 
         * The resulting 2D coordinates are stored in the Triangle2D object, which is
         * used to render the polygon onto the screen using the scan line algorithm.
         * 
         * This process is repeated for each polygon that needs to be rendered,
         * resulting in a complete 3D scene being displayed on the screen.
         */

        Polygon3DUtility.convert(triangle, polygon, originPoint, width, height, scale);

        /*
         * The lighting color is calculated using the following formula:
         * 
         * lightingColor = baseColor * (ambientLight + diffuseLight * max(0,
         * dot(normalVector, lightVector)))
         * 
         * where:
         * 
         * baseColor is the original color of the polygon
         * ambientLight is the ambient light level in the scene (a constant value)
         * diffuseLight is the intensity of the light source (a constant value)
         * normalVector is the normal vector of the polygon
         * lightVector is the direction of the light source
         * 
         * The dot product of the normal vector and the light vector is used to
         * determine the angle between the two vectors. If the angle is less than 90
         * degrees, the polygon is facing towards the light source and the diffuse
         * lighting component is applied. If the angle is greater than 90 degrees, the
         * polygon is facing away from the light source and no diffuse lighting is
         * applied.
         */

        final Color color = Polygon3DUtility.lightingColor(polygon.color, polygon.normalVector(), lightVector);

        /*
         * The variables xMin, yMin, xMax, and yMax are used to determine the bounds of
         * the triangle that is being rendered. They represent the minimum and maximum x
         * and y coordinates of the triangle's vertices.
         * 
         * The Math.min and Math.max methods are used to find the minimum and maximum x
         * and y values of the triangle's vertices. These values are then rounded up to
         * the nearest integer using the Math.ceil method, since pixels are discrete
         * units and cannot be drawn at fractional coordinates.
         * 
         * The xMin and yMin values represent the top-left corner of the bounding box
         * that encloses the triangle, while the xMax and yMax values represent the
         * bottom-right corner of the bounding box.
         */
        final int xMin = (int) Math.ceil(Math.min(triangle.p1.x, Math.min(triangle.p2.x, triangle.p3.x)));
        final int yMin = (int) Math.ceil(Math.min(triangle.p1.y, Math.min(triangle.p2.y, triangle.p3.y)));
        final int xMax = (int) Math.ceil(Math.max(triangle.p1.x, Math.max(triangle.p2.x, triangle.p3.x)));
        final int yMax = (int) Math.ceil(Math.max(triangle.p1.y, Math.max(triangle.p2.y, triangle.p3.y)));

        /*
         * Drawing a scan line pixel by pixel.
         * The scan line algorithm iterates over each pixel within the bounds of the
         * triangle and checks if it is within the triangle using the
         * Point2DUtility.pointInTriangle method. If the pixel is within the triangle,
         * the method calculates the depth of the pixel using the
         * Point2DUtility.averageDepth method and the distance from the camera using the
         * Point3DUtility.dist method.
         */

        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                if (Point2DUtility.inRange(x, y, width, height)
                        && Point2DUtility.pointInTriangle(x, y, triangle.p1, triangle.p2, triangle.p3)) {

                    /*
                     * The averageDepth method in the Point2DUtility class is responsible for
                     * calculating the average depth of a pixel within a triangle.
                     * 
                     * The method takes five parameters: the x and y coordinates of the pixel, and
                     * the Point2D objects that represent the vertices of the triangle. It returns a
                     * double value that represents the average depth of the pixel within the
                     * triangle.
                     * 
                     * The method first calculates the cross product of two vectors formed by the
                     * triangle's vertices and the pixel's coordinates. The resulting vector is used
                     * to calculate the area of the triangle.
                     * 
                     * The average depth is calculated by taking the weighted average of the depth
                     * values of the triangle's vertices at the pixel's location. The weights are
                     * the barycentric coordinates of the pixel within the triangle.
                     * 
                     * The formula for calculating the average depth is:
                     * 
                     * averageDepth = (1 / cross.z) * (cross.x * p1.depth + cross.y * p2.depth + cross.z * p3.depth - cross.x * x - cross.y * y)
                     * 
                     * where cross is the cross product of two vectors formed by the triangle's
                     * vertices and the pixel's coordinates, p1.depth, p2.depth, and p3.depth are
                     * the depth values of the triangle's vertices, and x and y are the coordinates
                     * of the pixel.
                     * 
                     * The resulting average depth is used in the scan line algorithm to determine
                     * the depth of each pixel when rendering the triangle onto the screen.
                     */
                    double depth = Point2DUtility.averageDepth(x, y, triangle.p1, triangle.p2, triangle.p3);

                    /*
                     * The distance from the camera is calculated using the following formula:
                     * 
                     * dist = sqrt((x - cameraX)^2 + (y - cameraY)^2 + (z - cameraZ)^2)
                     * 
                     */
                    double dist = Point3DUtility.dist(new Point3D(depth, x, y), cameraPoint);

                    if (dist <= 0) {
                        continue;
                    }

                    int index = y * width + x;
                    if (depthBuffer[index] == -1 || dist < depthBuffer[index]) {
                        depthBuffer[index] = dist;
                        pixelBuffer[index] = color.getRGB();
                    }
                }
            }
        }
    }

    private void interactionControl() {

        rotateVector.clear();

        switch (keyboard.getCurrentKeyCode()) {

            case KeyEvent.VK_CONTROL -> {
                if (mouse.isDragged()) {
                    originPoint.y += (double) mouse.getDiffX() * 0.01;
                    originPoint.z += (double) -mouse.getDiffY() * 0.01;
                }

                if (mouse.getWheelRotation() == 1) {
                    /*
                     * Zoom out.
                     */

                    scale /= 1.2;
                } else if (mouse.getWheelRotation() == -1) {
                    /*
                     * Zoom in.
                     */

                    scale *= 1.2;
                }
            }

            case KeyEvent.VK_SHIFT -> {
                if (mouse.isDragged()) {
                    rotateVector.x = mouse.getDiffX();
                }
            }

            case KeyEvent.VK_LEFT -> {
                rotateVector.z = 3;
            }

            case KeyEvent.VK_RIGHT -> {
                rotateVector.z = -3;
            }

            case KeyEvent.VK_UP -> {
                rotateVector.y = 3;
                if (KeyEvent.VK_SHIFT == keyboard.getCurrentKeyCode()) {
                    rotateVector.x = 3;
                }
            }

            case KeyEvent.VK_DOWN -> {
                rotateVector.y = -3;
                if (KeyEvent.VK_SHIFT == keyboard.getCurrentKeyCode()) {
                    rotateVector.x = -3;
                }
            }
        }

        mouse.reset();
        keyboard.reset();
    }

    /**
     * Update and draw.
     * 
     */
    private void updateAndDraw() {
        /**
         * Mouse and keyboard control.
         */
        interactionControl();

        /**
         * Clearing buffers.
         */
        Arrays.fill(depthBuffer, -1);
        Arrays.fill(pixelBuffer, 0xFF000000);

        /**
         * Rendering with multithreading
         */

        final int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available processors

        // Split the polygons into smaller chunks
        List<List<Polygon3D>> polygonChunks = new ArrayList<>();
        int chunkSize = polygons.size() / numThreads;
        int startIndex = 0;
        int endIndex = chunkSize;

        for (int i = 0; i < numThreads - 1; i++) {
            polygonChunks.add(polygons.subList(startIndex, endIndex));
            startIndex = endIndex;
            endIndex += chunkSize;
        }
        // Add the remaining polygons to the last chunk
        polygonChunks.add(polygons.subList(startIndex, polygons.size()));

        // Create and start threads to render the polygon chunks
        List<Thread> threads = new ArrayList<>();
        for (List<Polygon3D> chunk : polygonChunks) {
            Thread thread = new Thread(() -> {
                for (Polygon3D polygon : chunk) {
                    renderPolygon(polygon);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        window3DTerrain.draw();
    }

    /**
     * Start.
     */
    public void start() {
        // create a new thread and start it
        isRunning = true;
        while (isRunning) {
            updateAndDraw();
        }
    }

    /**
     * Stop and close the window individually.
     */
    public void stop() {
        // close the window
        window3DTerrain.close();
        isRunning = false;

    }

    /**
     * Sets the polygons.
     * 
     * @param polygons the polygons to set
     */
    public void setPolygons(List<Polygon3D> polygons) {
        this.polygons = polygons;
    }

    /**
     * @return the isRunning
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Stop all.
     */
    public static void stopAll() {
        System.exit(0);
    }
}
