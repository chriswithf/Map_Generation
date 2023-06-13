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
    private Vector3D currentRotateVector;
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
        this.currentRotateVector = new Vector3D(0, 0, 0);
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
        if (window3DTerrain == null) {
            depthBuffer = new double[width * height];

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            pixelBuffer = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
            window3DTerrain = new Window3DTerrain(bufferedImage, mouse, keyboard);
        }

    }

    private void renderPolygon(Polygon3D polygon) {
        Polygon3DUtility.rotate(polygon, rotateVector);

        final Triangle2D triangle = new Triangle2D();

        Polygon3DUtility.convert(triangle, polygon, originPoint, width, height, scale);

        final Color color = Polygon3DUtility.lightingColor(polygon.color, polygon.normalVector(), lightVector);

        final int xMin = (int) Math.ceil(Math.min(triangle.p1.x, Math.min(triangle.p2.x, triangle.p3.x)));
        final int yMin = (int) Math.ceil(Math.min(triangle.p1.y, Math.min(triangle.p2.y, triangle.p3.y)));
        final int xMax = (int) Math.ceil(Math.max(triangle.p1.x, Math.max(triangle.p2.x, triangle.p3.x)));
        final int yMax = (int) Math.ceil(Math.max(triangle.p1.y, Math.max(triangle.p2.y, triangle.p3.y)));

        /*
         * Drawing a scan line pixel by pixel.
         */

        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                if (Point2DUtility.inRange(x, y, width, height)
                        && Point2DUtility.pointInTriangle(x, y, triangle.p1, triangle.p2, triangle.p3)) {
                    double depth = Point2DUtility.averageDepth(x, y, triangle.p1, triangle.p2, triangle.p3);
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
                    currentRotateVector.x += mouse.getDiffX();
                }
            }

            case KeyEvent.VK_LEFT -> {
                rotateVector.z = 3;
                currentRotateVector.z += 3;
            }

            case KeyEvent.VK_RIGHT -> {
                rotateVector.z = -3;
                currentRotateVector.z -= 3;
            }

            case KeyEvent.VK_UP -> {
                rotateVector.y = 3;
                currentRotateVector.y += 3;
                if (KeyEvent.VK_SHIFT == keyboard.getCurrentKeyCode()) {
                    rotateVector.x = 3;
                    currentRotateVector.x += 3;
                }
            }

            case KeyEvent.VK_DOWN -> {
                rotateVector.y = -3;
                currentRotateVector.y -= 3;
                if (KeyEvent.VK_SHIFT == keyboard.getCurrentKeyCode()) {
                    rotateVector.x = -3;
                    currentRotateVector.x -= 3;
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
         * Rendering.
         */

        polygons.parallelStream().forEach(this::renderPolygon);

        /**
         * Drawing.
         */

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
     * Stop.
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
}
