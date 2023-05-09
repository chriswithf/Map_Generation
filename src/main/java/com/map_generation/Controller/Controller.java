package com.map_generation.Controller;

import com.map_generation.Model.FileExport;
import com.map_generation.Model.Model;
import com.map_generation.Model.Tile;
import com.map_generation.View.MainWindow;
import javafx.scene.input.MouseButton;

/**
 * This is the controller class, it is responsible for the logic of the application.
 * It is responsible for handling events and updating the model and view accordingly.
 *
 * @author Chris
 */

public class Controller {
    private Model model;
    private MainWindow mainWindow;

    private FileExport fileExport;

    Tile[][] tiles;

    public Controller(Model model, MainWindow mainWindow) {
        this.model = model;
        this.mainWindow = mainWindow;
        this.fileExport = new FileExport();

        tiles = model.getTiles();
        model.drawMap(mainWindow.getCanvas(), tiles);

        // Listener for editing the map
        mainWindow.getCanvas().onMouseClickedProperty().set(e -> {
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                tiles = model.editMap(mainWindow.getMouseRadius(), -1, e.getX(), e.getY(), tiles);
                ;


                model.drawMap(mainWindow.getCanvas(), tiles);

            } else if (e.getButton().equals(MouseButton.PRIMARY)) {
                tiles = model.editMap(mainWindow.getMouseRadius(), 1, e.getX(), e.getY(), tiles);
                ;
                model.drawMap(mainWindow.getCanvas(), tiles);


            }

        });

      /*  mainWindow.getButtonForExport().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileExport.exportMapToImage(tiles);
            }
        });*/

        // Reset cursor when entering canvas
        mainWindow.getCanvas().onMouseEnteredProperty().set(e -> {
            mainWindow.setCursor();
        });

        // Change radius of mouse with '+' and '-'
        mainWindow.onKeyTypedProperty().set(e -> {
            int incDec = 5;
            if (e.getCharacter().equals("+")) {
                mainWindow.incMouseRadius(incDec);
                mainWindow.setCursor();
            } else if (e.getCharacter().equals("-") && mainWindow.getMouseRadius() > incDec) {
                mainWindow.decMouseRadius((incDec));
                mainWindow.setCursor();
            }
        });


    }


}
