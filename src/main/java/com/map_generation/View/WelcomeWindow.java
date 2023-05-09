package com.map_generation.View;

import com.map_generation.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

import java.awt.*;
import java.util.function.UnaryOperator;


/**
 * This is the Welcome Window class. Here the user is introduced to the Application
 * with all it's functions - how it works.
 * Here the user will also set up the parameters necessary for the Map Generation
 *
 * @author danieldibella
 */

//TODO
public class WelcomeWindow extends VBox {

    public WelcomeWindow(){

        // TODO: we may want to use the screen width and height as maximum values?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        System.out.println("Screen width: " + screenWidth);
        System.out.println("Screen height: " + screenHeight);

        // header label with styling
        Label title = new Label("Map Generation");
        title.setStyle("-fx-font-size: 24pt; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #333333; " +
                "-fx-padding: 10px;");
        title.setMaxWidth(Double.MAX_VALUE);

        // create the label for the text fields
        Label sizeLabel = new Label("Enter size: ");
        sizeLabel.setStyle("-fx-font-size: 10pt; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #333333; " +
                "-fx-padding: 10px;");

        // create the text fields for width and height
        TextField widthTextField = new TextField();
        widthTextField.setPromptText("Width");
        widthTextField.setStyle("-fx-font-size: 10pt; " +
                "-fx-background-color: #f0f0f0; " +
                "-fx-padding: 10px;");

        TextField heightTextField = new TextField();
        heightTextField.setPromptText("Height");
        heightTextField.setStyle("-fx-font-size: 10pt; " +
                "-fx-background-color: #f0f0f0; " +
                "-fx-padding: 10px;");

        HBox size = new HBox();
        size.setSpacing(10);
        size.setAlignment(Pos.CENTER_LEFT);
        size.getChildren().addAll(sizeLabel,widthTextField,heightTextField);

        Button start = new Button("Create Window");
        start.setStyle("-fx-font-size: 13pt; " +
                "-fx-background-color: #333333; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px;");

        start.setOnAction(actionEvent -> {
            int height = 0;
            int width = 0;

            try {
                height = Integer.parseInt(heightTextField.getText());
                width = Integer.parseInt(widthTextField.getText());
                if (height >= 200 && width >= 200) {
                    Main.myLaunch(TerraformApplication.class, width, height);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Height and width must be equal or greater than 200.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Height and width must be set.");
                alert.showAndWait();
            }
        });

        // Set the text formatter to accept only integer values
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String inputText = change.getText();
            if (inputText.matches("\\d*")) {
                int newValue = change.getControlNewText().isEmpty() ? 0 : Integer.parseInt(change.getControlNewText());
                if (newValue <= 2000) {
                    return change;
                }
            }
            return null;
        };

        TextFormatter<Integer> widthFormatter = new TextFormatter<>(
                new IntegerStringConverter(), // Use IntegerStringConverter to convert to/from Integer
                null, // Set initial value to null
                filter // Use the unary operator filter to accept only integer values
        );
        TextFormatter<Integer> heightFormatter = new TextFormatter<>(
                new IntegerStringConverter(),
                null,
                filter
        );

        widthTextField.setTextFormatter(widthFormatter);
        heightTextField.setTextFormatter(heightFormatter);

        Separator separator = new Separator();

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(start);

        this.setSpacing(20);
        getChildren().addAll(title, size, separator, buttonBox);
    }
}
