package com.map_generation.View;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import javafx.scene.layout.VBox;

public class MainView extends VBox {
    Controller controller;
    Model model;

    public MainView(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }
}
