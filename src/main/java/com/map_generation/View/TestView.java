package com.map_generation.View;

import com.map_generation.Model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.HBox;

//Just for testing pls dont touch


public class TestView extends HBox {

    private static GenerateSimplexTiles terrain = new GenerateSimplexTiles(1000,1000);


    private int mouseRadius = 100;

    private int mouseEditDirection = -1;




    Tile[][] tiles = terrain.getTiles();

    public TestView() {
        Canvas canvas = new Canvas(1000, 1000);
        Button button = new Button("Random");
        canvas.onMouseClickedProperty().set(mouseEvent -> {
            //increase the double value of the tile at the mouse position and surounding 10 tiles it should be a circle
            //the further away the less it increases
            for (int i = 0; i < terrain.getX(); i++) {
                for (int j = 0; j < terrain.getY(); j++) {
                    double distance = Math.sqrt(Math.pow(mouseEvent.getX() - i, 2) + Math.pow(mouseEvent.getY() - j, 2));
                    if (distance < mouseRadius) {
                        tiles[j][i].value += (0.1 - distance / (mouseRadius*10))* mouseEditDirection;
                    }
                    tiles[i][j].type = terrain.getType(tiles[i][j].value);

                }
            }

        /*    for (int i = 0; i < terrain.getX(); i++) {
                for (int j = 0; j < terrain.getY(); j++) {

                }
            }*/
            draw(canvas);
        });

        button.setOnAction(actionEvent -> {
            terrain.rerender();
            draw(canvas);
        });
        draw(canvas);
        getChildren().addAll(canvas, button);


    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        for (int i = 0; i < terrain.getX(); i++) {
            for (int j = 0; j < terrain.getY(); j++) {
                // Get color tile should be drawn as from TileUtils
                //gc.setFill(TileUtils.getColorForFx(tiles[i][j]));
                //convert awt color to javafx color
                // Get size of panel for drawing tiles proportionally
                //System.out.println("Drew " + TileUtils.getColor(tiles[i][j]) + " tile!");
                //gc.fillRect(j * (1000 / terrain.x), (i * 1000 / terrain.y), (int) 1000 / terrain.x, (int) 1000 / terrain.y + 1);

                pw.setColor(j , i , TileUtility.getColor(tiles[i][j]));

            }
        }
    }




    }
