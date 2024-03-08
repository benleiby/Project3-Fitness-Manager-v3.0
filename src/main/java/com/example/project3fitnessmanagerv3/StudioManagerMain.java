package com.example.project3fitnessmanagerv3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudioManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StudioManagerMain.class.getResource("studioManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);






        stage.setTitle("Fitness Club Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();








    }

    public static void main(String[] args) {
        launch();
    }
}