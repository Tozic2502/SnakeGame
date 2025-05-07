package org.example.snakegame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.*;

public class Controller {

    @FXML private Label title;
    @FXML private Button insaneMod, normaleMod;
    @FXML private AnchorPane board;

    public void removeStartObj(){
        normaleMod.setVisible(false);
        insaneMod.setVisible(false);
        title.setVisible(false);

    }
    public void setBoardReady(){
        board.setStyle("-fx-background-color: #1e1e1e;");
        drawCheckerBoard(20, 28,25);
    }


    public void onActionNormaleMode(javafx.event.ActionEvent actionEvent) {
        removeStartObj();
        setBoardReady();
    }

    public void onActionInsaneMode(javafx.event.ActionEvent actionEvent) {
        removeStartObj();
        setBoardReady();
    }
    public void drawCheckerBoard( int rows, int cols, int tileSize) {
        board.getChildren().clear();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Pane tile = new Pane();
                tile.setPrefSize(tileSize, tileSize);
                tile.setLayoutX(col * tileSize);
                tile.setLayoutY(row * tileSize);

                // Assign CSS class
                String styleClass = (row + col) % 2 == 0 ? "tile-light" : "tile-dark";
                tile.getStyleClass().add(styleClass);

                board.getChildren().add(tile);
            }
        }
    }


}