package com.example.project3fitnessmanagerv3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class StudioManagerController {

    // UI COMPONENTS
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<Member> memberDisplay;

    // BACKEND COMPONENTS
    private MemberList members = new MemberList();

    @FXML
    public void initialize() {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}