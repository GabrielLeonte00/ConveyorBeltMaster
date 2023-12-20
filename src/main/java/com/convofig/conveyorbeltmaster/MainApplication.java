package com.convofig.conveyorbeltmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Conveyor Belt Master");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    protected void maximizeStage(){
        Screen primaryScreen = Screen.getPrimary();
        Rectangle2D visualBounds = primaryScreen.getVisualBounds();

        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());
        stage.setWidth(visualBounds.getWidth());
        stage.setHeight(visualBounds.getHeight());
    }

    public Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}