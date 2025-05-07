package org.example.snakegame;

import Models.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        scene.getStylesheets().add(
                getClass().getResource("/Styles.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
    private static final int TILE_SIZE = 20;
    private Snake snake;
    private Direction currentDirection = Direction.Right;

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case UP -> currentDirection = Direction.Up;
            case DOWN -> currentDirection = Direction.Down;
            case LEFT -> currentDirection = Direction.Left;
            case RIGHT -> currentDirection = Direction.Right;
        }
    }
}