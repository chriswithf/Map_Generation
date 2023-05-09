package com.map_generation.View;

import com.map_generation.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
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

public class WelcomeWindow extends VBox {

    private final int MIN = 300;

    public WelcomeWindow() {

        // TODO: we may want to use the screen width and height as maximum values?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int MAX_WIDTH = 2000;
        final int MAX_HEIGHT = 2000;

        // header label with styling
        Label title = new Label("Map Generation");
        title.setStyle("-fx-font-size: 24pt; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #333333; " +
                "-fx-padding: 10px;");
        title.setMaxWidth(Double.MAX_VALUE);

        // create the label for the text fields
        Label sizeLabel = new Label("Enter screen-size: ");
        sizeLabel.setStyle("-fx-font-size: 10pt; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #333333; " +
                "-fx-padding: 10px;");

        // create the text fields for width and height
        TextField widthTextField = new TextField();
        widthTextField.setPromptText("width");
        widthTextField.setTooltip(new Tooltip("Must be between " + MIN + " and " + MAX_WIDTH));
        widthTextField.setStyle("-fx-font-size: 10pt; " +
                "-fx-background-color: #f0f0f0; " +
                "-fx-padding: 10px;");

        TextField heightTextField = new TextField();
        heightTextField.setPromptText("height");
        heightTextField.setTooltip(new Tooltip("Must be between " + MIN + " and " + MAX_HEIGHT));
        heightTextField.setStyle("-fx-font-size: 10pt; " +
                "-fx-background-color: #f0f0f0; " +
                "-fx-padding: 10px;");

        // create the text fields for octaves
        Label octaveLabel = new Label("Enter Octaves: ");
        octaveLabel.setStyle("-fx-font-size: 10pt; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #333333; " +
                "-fx-padding: 10px;");

        TextField octaveTextField = new TextField("8");
        octaveTextField.setPromptText("(1-10)");
        octaveTextField.setTooltip(new Tooltip("Must be between 0 and 10"));
        octaveTextField.setStyle("-fx-font-size: 10pt; " +
                "-fx-background-color: #f0f0f0; " +
                "-fx-padding: 10px;");
        octaveTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                return;
            }
            try {
                Integer value = Integer.parseInt(newValue);
                if (value > 10 || value < 1) {
                    octaveTextField.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                octaveTextField.setText(oldValue);
            }
        });


        // create the text fields for octaves
        Label persistanceLabel = new Label("Enter Persistence: ");
        persistanceLabel.setStyle("-fx-font-size: 10pt; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #333333; " +
                "-fx-padding: 10px;");

        TextField persistanceTextField = new TextField("0.5");
        persistanceTextField.setPromptText("(0 - 1)");
        persistanceTextField.setTooltip(new Tooltip("Must be between 0 and 1"));
        persistanceTextField.setStyle("-fx-font-size: 10pt; " +
                "-fx-background-color: #f0f0f0; " +
                "-fx-padding: 10px;");
        persistanceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                return;
            }
            try {
                Double value = Double.parseDouble(newValue);
                if (value > 1.0) {
                    persistanceTextField.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                persistanceTextField.setText(oldValue);
            }
        });


        HBox size = new HBox();
        size.setSpacing(10);
        size.setAlignment(Pos.CENTER_LEFT);
        size.getChildren().addAll(sizeLabel, widthTextField, heightTextField);

        HBox octaves = new HBox();
        octaves.setSpacing(10);
        octaves.setAlignment(Pos.CENTER_LEFT);
        octaves.getChildren().addAll(octaveLabel, octaveTextField);

        HBox persistence = new HBox();
        persistence.setSpacing(10);
        persistence.setAlignment(Pos.CENTER_LEFT);
        persistence.getChildren().addAll(persistanceLabel, persistanceTextField);

        Button helpButton = new Button("?");
        helpButton.setStyle("-fx-background-color: #F5F5F5; -fx-text-fill: black; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-radius: 50%; -fx-border-color: black; -fx-border-width: 2px;");
        helpButton.setPrefSize(20, 20);
        helpButton.setOnAction(e -> {
            showHelpDialog();
        });

        Button start = new Button("Create Window");
        start.setStyle("-fx-font-size: 13pt; " +
                "-fx-background-color: #333333; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px;");

        start.setOnAction(actionEvent -> {
            try {
                int height = Integer.parseInt(heightTextField.getText());
                int width = Integer.parseInt(widthTextField.getText());
                int oc = Integer.parseInt(octaveTextField.getText());
                double per = Double.parseDouble(persistanceTextField.getText());

                if (height >= MIN && width >= MIN) {
                    if (oc <= 10 && oc > 0) {
                        if (per >= 0 && per <= 1) {
                            Main.myLaunch(TerraformApplication.class, width, height, oc, per);
                        } else {
                            throwWarning("Persistence must be between 0 and 1");
                        }
                    } else {
                        throwWarning("Octaves must be between 1 and 10");
                    }
                } else {
                    throwWarning("Height and width must be equal or greater than " + MIN);
                }
            } catch (NumberFormatException e) {
                throwWarning("All values must be set");
            }
        });

        // Set the text formatter to accept only integer values smaller than 2000
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
        buttonBox.getChildren().addAll(start, helpButton);

        this.setSpacing(20);
        getChildren().addAll(title, size, octaves, persistence, separator, buttonBox);
    }

    private void throwWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showHelpDialog2() {
        String message = "The terrain generator program uses the concept of simplex noise generation to create visually stunning and realistic landscapes. \n" +
                "\n" +
                "The user is presented with a simple graphical interface where they can input the following values:\n" +
                "\n" +
                "- Size of the window: must be greater than 300 pixels\n" +
                "- Number of octaves: between 1 and 10\n" +
                "- Persistence values: between 0.1 and 1\n\n" +
                "Octaves refer to the number of iterations of the noise algorithm that are used to create the terrain. \n" +
                "The higher the number of octaves, the more detailed and complex the terrain will be.\n" +
                "\n" +
                "Persistence is a value that controls the relative weight of each octave in the final terrain generation. \n" +
                "A higher persistence value will result in a smoother and more gradual transition between different terrain heights, \n" +
                "while a lower value will create more abrupt changes in elevation.\n" +
                "\n" +
                "After inputting the desired values, the user can click on \"Start Terraforming\" to generate the terrain, \n" +
                "which is then displayed in the window. \n" +
                "The resulting terrain is a visually striking and realistic representation of a landscape,\n" +
                "with different shades of color representing different elevations.\n" +
                "\n" +
                "Once the terrain is generated, the user can modify it by using the trackpad and/or mouse. \n" +
                "Right-clicking will lower the elevation, while left-clicking will raise it. \n" +
                "\n" +
                "By pressing the keys + and -, we can modify the radius of the cursor. \n" +
                "The cursor is used to determine which part of the terrain is modified by the trackpad or mouse clicks.";

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 10px;");

        Label test = new Label("Map Generator Help");
        test.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 10px;");

        VBox vbox = new VBox(test, label);
        vbox.setSpacing(10);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Help");
        alert.getDialogPane().setContent(vbox);
        alert.showAndWait();
    }

    private void showHelpDialog() {

// Introduction
        String intro = "The terrain generator uses the concept of simplex noise to create visually stunning and realistic landscapes.\n";

// Input section
        String inputHeader = "Input:";
        String windowSize = "- Size of the window: must be greater than 300 pixels";
        String numOctaves = "- Number of octaves: between 1 and 10";
        String persistence = "- Persistence values: between 0.1 and 1\n";
        String inputDescription = "Octaves refer to the number of iterations of the noise algorithm that are used to create the terrain. \n" +
                "The higher the number of octaves, the more detailed and complex the terrain will be.\n\n" +
                "Persistence is a value that controls the relative weight of each octave in the final terrain generation. \n" +
                "A higher persistence value will result in a smoother and more gradual transition between different terrain heights, \n" +
                "while a lower value will create more abrupt changes in elevation.\n";

// Terraforming section
        String terraformHeader = "Terraforming:";
        String terraformDescription = "After inputting the desired values, the user can click on \"Start Terraforming\" to generate the terrain, \n" +
                "which is then displayed in the window. \n" +
                "The resulting terrain is a visually striking and realistic representation of a landscape, \n" +
                "with different shades of color representing different elevations.\n";

// Modification section
        String modifyHeader = "Modification:";
        String modificationDescription = "Once the terrain is generated, the user can modify it by using the trackpad and/or mouse. \n" +
                "Right-clicking will lower the elevation, while left-clicking will raise it. \n\n" +
                "By pressing the keys + and -, we can modify the radius of the cursor. \n" +
                "The cursor is used to determine which part of the terrain is modified by the trackpad or mouse clicks.\n";

        // Introduction
        Label introLabel = new Label(intro);
        introLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 10px 0;");

        // Input section
        Label inputHeaderLabel = new Label(inputHeader);
        inputHeaderLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 0 0 0 10px");

        Label windowSizeLabel = new Label(windowSize);
        windowSizeLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 0 0 0 20px;");

        Label numOctavesLabel = new Label(numOctaves);
        numOctavesLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 0 0 0 20px;");

        Label persistenceLabel = new Label(persistence);
        persistenceLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 0 0 10px 20px;");

        Label inputDescriptionLabel = new Label(inputDescription);
        inputDescriptionLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: black; -fx-padding: 0 0 10px 20px;");

        // Terraforming section
        Label terraformHeaderLabel = new Label(terraformHeader);
        terraformHeaderLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 10px 0;");

        Label terraformDescriptionLabel = new Label(terraformDescription);
        terraformDescriptionLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: black; -fx-padding: 0 0 10px 20px;");

        // Modification section
        Label modifyHeaderLabel = new Label(modifyHeader);
        modifyHeaderLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 10px 0;");

        Label modificationDescriptionLabel = new Label(modificationDescription);
        modificationDescriptionLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: black; -fx-padding: 0 0 10px 20px;");

        // Add all labels to a VBox
        VBox vbox = new VBox(introLabel, inputHeaderLabel, windowSizeLabel, numOctavesLabel, persistenceLabel, inputDescriptionLabel,
                terraformHeaderLabel, terraformDescriptionLabel, modifyHeaderLabel, modificationDescriptionLabel);
        vbox.setSpacing(5);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Map Generation Help");
        alert.getDialogPane().setContent(vbox);
        alert.showAndWait();
    }
}

