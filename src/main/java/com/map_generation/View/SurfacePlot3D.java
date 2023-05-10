package com.map_generation.View;

import com.map_generation.Model.GenerateSimplexTiles;
import com.map_generation.Model.Tile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
;

public class SurfacePlot3D extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int SIZE = 800;
    private static final double SCALE = 1;

    @Override
    public void start(Stage primaryStage) {

        Group group = new Group();
        Tile[][] tiles = new GenerateSimplexTiles(SIZE, SIZE, 8, .5).getTiles();

        TriangleMesh terrainMesh = new TriangleMesh();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                float x = (float) (i * SCALE);

                //System.out.println(tiles[i][j].value * SCALE * 5);
                if (tiles[i][j].value < 0){
                    tiles[i][j].value = 0;
                }
                //*-60 to invert
                float y = (float) (tiles[i][j].value * SCALE * -60);

                float z = (float) (j * SCALE);
                terrainMesh.getPoints().addAll(x, y, z);

                float color = 255; // calculate color based on height
                terrainMesh.getTexCoords().addAll(color, color);
            }
        }

        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                int p0 = i * SIZE + j;
                int p1 = i * SIZE + j + 1;
                int p2 = (i + 1) * SIZE + j + 1;
                int p3 = (i + 1) * SIZE + j;

                terrainMesh.getFaces().addAll(p0, 0, p3, 0, p1, 0);

                terrainMesh.getFaces().addAll(p1, 0, p3, 0, p2, 0);
            }
        }


        MeshView terrain = new MeshView(terrainMesh);
        terrain.setCullFace(CullFace.BACK);
        terrain.setDrawMode(DrawMode.FILL);
        //terrain.setMaterial(new PhongMaterial(Color.RED));


        group.getChildren().add(terrain);
        Scene scene = new Scene(group, WIDTH, HEIGHT, true);

        // Camera

        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setNearClip(0);
        camera.setTranslateZ(0);
        camera.setTranslateY(-500);
        camera.setTranslateX(0);
        camera.setFieldOfView(60);

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            double moveAmount = 15.0;
            if (keyCode == KeyCode.A) {
                camera.setTranslateX(camera.getTranslateX() - moveAmount);
            } else if (keyCode == KeyCode.D) {
                camera.setTranslateX(camera.getTranslateX() + moveAmount);
            } else if (keyCode == KeyCode.W) {
                camera.setTranslateZ(camera.getTranslateZ() + moveAmount);
            } else if (keyCode == KeyCode.S) {
                camera.setTranslateZ(camera.getTranslateZ() - moveAmount);
            } else if (keyCode == KeyCode.Q) {
                camera.setTranslateY(camera.getTranslateY() - moveAmount);
            } else if (keyCode == KeyCode.Z) {
                camera.setTranslateY(camera.getTranslateY() + moveAmount);
            } else if (keyCode == KeyCode.LEFT) {
                Rotate rotateY = new Rotate(-3, Rotate.Y_AXIS);
                camera.getTransforms().addAll(rotateY);
            } else if (keyCode == KeyCode.RIGHT) {
                Rotate rotateY = new Rotate(3, Rotate.Y_AXIS);
                camera.getTransforms().addAll(rotateY);
            } else if (keyCode == KeyCode.UP) {
                Rotate rotateY = new Rotate(-3, Rotate.X_AXIS);
                camera.getTransforms().addAll(rotateY);
            } else if (keyCode == KeyCode.DOWN) {
                Rotate rotateY = new Rotate(3, Rotate.X_AXIS);
                camera.getTransforms().addAll(rotateY);
            } else if (keyCode == KeyCode.R) {
                camera.setTranslateZ(0);
                camera.setTranslateY(-500);
                camera.setTranslateX(0);
            }
        });

        scene.setCamera(camera);


        /*

        Animation

        Timeline timeline = new Timeline();

        // Define the key frames for the animation
        KeyValue xValue = new KeyValue(camera.translateXProperty(), 500);
        KeyValue yValue = new KeyValue(camera.translateYProperty(), -700);
        KeyValue zValue = new KeyValue(camera.translateZProperty(), -1000);
        KeyFrame startFrame = new KeyFrame(Duration.ZERO, xValue, yValue, zValue);

        KeyValue xValue2 = new KeyValue(camera.translateXProperty(), 500);
        KeyValue yValue2 = new KeyValue(camera.translateYProperty(), -700);
        KeyValue zValue2 = new KeyValue(camera.translateZProperty(), 10000);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(60), xValue2, yValue2, zValue2);

        // Add the key frames to the timeline and configure the animation settings
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        // Start the animation
        timeline.play();
        */


        primaryStage.setScene(scene);
        primaryStage.setTitle("3D Visualize");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

class ColorGradient {
    private final double minValue;
    private final double maxValue;
    private final Color[] colors;

    public ColorGradient(double minValue, double maxValue, Color... colors) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.colors = colors;
    }

    public Color getColor(double value) {
        if (value <= minValue) {
            return colors[0];
        } else if (value >= maxValue) {
            return colors[colors.length - 1];
        } else {
            double ratio = (value - minValue) / (maxValue - minValue);
            int index = (int) Math.floor(ratio * (colors.length - 1));
            Color color1 = colors[index];
            Color color2 = colors[index + 1];
            double fraction = (ratio * (colors.length - 1)) - index;
            return color1.interpolate(color2, fraction);
        }
    }
}