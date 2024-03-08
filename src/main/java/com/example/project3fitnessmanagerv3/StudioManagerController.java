package com.example.project3fitnessmanagerv3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudioManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}