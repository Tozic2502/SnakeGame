package org.example.snakegame;

import Models.Food;
import Models.Snake;
import Service.ISnakeBodyService;
import Service.SnakeBody;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class Controller {
    private Snake snake;
    private ISnakeBodyService snakeBodyService;
    @FXML private Label title, score, foodL;
    @FXML private Button insaneMod, normaleMod;
    @FXML private AnchorPane board;

    public void initialize() {
        this.snakeBodyService = new SnakeBody();
        this.snake = snakeBodyService.createSnake(320, 260, 20);

    }

    public void removeStartObj(){
        board.getChildren().removeAll(insaneMod,normaleMod,title);
        score.setVisible(true);
        foodL.setVisible(true);

    }
    public void setBoardReady(){
        board.setStyle("-fx-background-color: #1e1e1e;");
        drawCheckeredBoard(24, 35,20);
    }


    public void onActionNormaleMode(javafx.event.ActionEvent actionEvent) {
        removeStartObj();
        setBoardReady();
        makeHead();
    }

    public void onActionInsaneMode(javafx.event.ActionEvent actionEvent) {
        removeStartObj();
        setBoardReady();
        makeHead();
    }
    public void drawCheckeredBoard( int rows, int cols, int tileSize) {


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
    public void setScore() {
        int totalScore = 0;
        for (Food f : snake.getFoodEaten())
        {
            totalScore += f.getPoints();
        }

        this.score.setText("Score: " + totalScore);
    }
    public Label getScore() {
        return score;
    }
    public void setFoodL() {
        int totalFoodEaten = 0;
        for (Food f : snake.getFoodEaten()){
            totalFoodEaten += f.getPoints();
        }
        this.foodL.setText("Food Eaten: " + totalFoodEaten);
    }
    public Label getFoodL() {
        return foodL;
    }
    public void makeHead() {
        int x = snake.getBody().getFirst().getX();
        int y = snake.getBody().getFirst().getY();

        Rectangle rect = new Rectangle(20, 20); // size of one tile
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        rect.setStyle("-fx-fill: #FFFFFF;"); // green head

        board.getChildren().add(rect);
    }


}