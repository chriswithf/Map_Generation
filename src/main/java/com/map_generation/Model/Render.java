package com.map_generation.Model;


import com.map_generation.Model.Generators.TerrainMaker;
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

public class Render {
    private final int width;
    private final int height;
    private final Window3DTerrain window3DTerrain;
    private final Mouse mouse;
    private final Keyboard keyboard;
    private final Point3D cameraPoint;
    private final Point3D originPoint;
    private final Vector3D lightVector;
    private final double[] depthBuffer;
    private final int[] pixelBuffer;
    int frameCount;
    private Vector3D rotateVector;
    private Vector3D currentRotateVector;
    private List<Polygon3D> polygons;
    private double scale;
    private boolean isRunning;
    private TerrainMaker terrainMaker;
    private Model model;

    public Render(int width, int height, TerrainMaker terrainMaker, Model model) {
        this.width = width;
        this.height = height;
        this.terrainMaker = terrainMaker;
        this.model = model;
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

        depthBuffer = new double[width * height];

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixelBuffer = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

        window3DTerrain = new Window3DTerrain(bufferedImage, mouse, keyboard);
    }

    private void renderPolygon(Polygon3D polygon) {
        Polygon3DUtility.rotate(polygon, rotateVector);


        final Triangle2D triangle = new Triangle2D();

        Polygon3DUtility.convert(triangle, polygon, originPoint, width, height, scale);

        /*
         * Simple occlusion culling (if the polygon is behind
         * the screen, we don't render it).
         * */

        if (!Triangle2DUtility.triangleVisibleOnScreen(triangle, width, height)) {
            return;
        }

        final Color color = Polygon3DUtility.lightingColor(polygon.color, polygon.normalVector(), lightVector);

        final int xMin = (int) Math.ceil(Math.min(triangle.p1.x, Math.min(triangle.p2.x, triangle.p3.x)));
        final int yMin = (int) Math.ceil(Math.min(triangle.p1.y, Math.min(triangle.p2.y, triangle.p3.y)));
        final int xMax = (int) Math.ceil(Math.max(triangle.p1.x, Math.max(triangle.p2.x, triangle.p3.x)));
        final int yMax = (int) Math.ceil(Math.max(triangle.p1.y, Math.max(triangle.p2.y, triangle.p3.y)));

        /*
         * Drawing a scan line pixel by pixel.
         * */

        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                if (Point2DUtility.inRange(x, y, width, height) && Point2DUtility.pointInTriangle(x, y, triangle.p1, triangle.p2, triangle.p3)) {
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


        frameCount++;
        if (frameCount % 1 == 0) {
            setPolygons(terrainMaker.generate(model.getTiles(), currentRotateVector));
        }

        rotateVector.clear();


        switch (keyboard.getCurrentKeyCode()) {
            case KeyEvent.VK_ESCAPE -> stop();

            case KeyEvent.VK_CONTROL -> {
                if (mouse.isDragged()) {
                    originPoint.y += (double) mouse.getDiffX() * 0.01;
                    originPoint.z += (double) -mouse.getDiffY() * 0.01;
                }

                if (mouse.getWheelRotation() == 1) {
                    /*
                     * Zoom out.
                     * */

                    scale /= 1.2;
                } else if (mouse.getWheelRotation() == -1) {
                    /*
                     * Zoom in.
                     * */

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

            /*default -> {
                if (mouse.isDragged()) {

                    rotateVector.y = mouse.getDiffY();
                    rotateVector.z = mouse.getDiffX();

                    System.out.println("rotateVector.y = " + rotateVector.y);
                    System.out.println("rotateVector.z = " + rotateVector.z);

                    currentRotateVector.y += mouse.getDiffY();
                    currentRotateVector.z += mouse.getDiffX();
                }
            }*/
        }

        mouse.reset();
        keyboard.reset();
    }

    private void updateAndDraw() {
        /*
         * Mouse and keyboard control.
         * */

        interactionControl();

        /*
         * Clearing buffers.
         * */

        Arrays.fill(depthBuffer, -1);
        Arrays.fill(pixelBuffer, 0xFF000000);


        /*
         * Rendering.
         * */

        polygons.parallelStream().forEach(this::renderPolygon);

        /*
         * Drawing.
         * */

        window3DTerrain.draw();
    }

    public void start() {
        //create a new thread and start it
        Thread thread = new Thread(() -> {
            isRunning = true;
            while (isRunning) {

                updateAndDraw();
            }
        });
        thread.start();
    }


    public void stop() {
        isRunning = false;
    }


    public void setPolygons(List<Polygon3D> polygons) {
        this.polygons = polygons;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
