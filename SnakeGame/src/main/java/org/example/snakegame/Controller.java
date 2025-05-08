package org.example.snakegame;

import Models.Body;
import Models.Direction;
import Models.Food;
import Models.Snake;
import Service.FoodService;
import Service.IFoodService;
import Service.ISnakeBodyService;
import Service.SnakeBody;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class Controller
{
    private final int BOARD_WIDTH = 720, BOARD_HEIGHT = 520, UNIT_SIZE = 20, ROWS = 24, COLS = 36;
    private Snake snake;
    private List<Food> spawnedFood;
    private ISnakeBodyService snakeBodyService;
    private IFoodService foodService;
    private Timeline tl;
    @FXML private Label title, score, foodL;
    @FXML private Button insaneMod, normaleMod;
    @FXML private AnchorPane board;

    public void initialize()
    {

    }

    public void removeMenuChoices()
    {
        board.getChildren().removeAll(insaneMod,normaleMod,title);
        score.setVisible(true);
        foodL.setVisible(true);

    }

    public void setBoardReady()
    {
        board.setStyle("-fx-background-color: #1e1e1e;");
        drawCheckeredBoard(ROWS, COLS, UNIT_SIZE);
        makeBody(snake.getBody().getFirst());

        board.requestFocus();
        board.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent keyEvent)
            {
                System.out.println("Hello");
                switch (keyEvent.getCode())
                {
                    case UP ->
                    {
                        if (!snake.getDirection().equals(Direction.Down))
                        {
                            snake.setDirection(Direction.Up);
                        }
                    }
                    case DOWN ->
                    {
                        if (!snake.getDirection().equals(Direction.Up))
                        {
                            snake.setDirection(Direction.Down);
                        }
                    }
                    case LEFT ->
                    {
                        if (!snake.getDirection().equals(Direction.Right))
                        {
                            snake.setDirection(Direction.Left);
                        }
                    }
                    case RIGHT ->
                    {
                        if (!snake.getDirection().equals(Direction.Left))
                        {
                            snake.setDirection(Direction.Right);
                        }
                    }
                }
            }
        });
        createFood();
    }

    public void onActionNormaleMode(javafx.event.ActionEvent actionEvent)
    {
        removeMenuChoices();
        startGame(20);
        setBoardReady();
    }

    private void startGame(int speed)
    {
        tl = new Timeline();
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.setAutoReverse(false);

        this.snakeBodyService = new SnakeBody();
        this.snake = snakeBodyService.createSnake(BOARD_WIDTH/2, BOARD_HEIGHT/2, speed);
        this.foodService = new FoodService(BOARD_WIDTH, BOARD_HEIGHT, UNIT_SIZE);
        this.spawnedFood = new ArrayList<>();
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                snakeBodyService.moveSnake(snake, snake.getDirection());
                clearSnake();
                for (Body body : snake.getBody())
                {
                    makeBody(body);
                }
                for (Food food : spawnedFood)
                {
                    if (food.getExperationTimer() <= 0)
                    {
                        board.getChildren().remove(food.getCircle());
                        if (food.getFoodName().equals("Apple"))
                        {
                            createFood();
                        }
                        spawnedFood.remove(food);
                    }
                    else
                    {
                        food.setExperationTimer(food.getExperationTimer() - 1);
                    }
                }
            }
        }));

        tl.play();
    }

    public void onActionInsaneMode(javafx.event.ActionEvent actionEvent)
    {
        removeMenuChoices();
        setBoardReady();
    }

    public void drawCheckeredBoard( int rows, int cols, int tileSize)
    {
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
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

    public void setScore()
    {
        int totalScore = 0;
        for (Food f : snake.getFoodEaten())
        {
            totalScore += f.getPoints();
        }

        this.score.setText("Score: " + totalScore);
    }

    public Label getScore()
    {
        return score;
    }

    public void setFoodL()
    {
        int totalFoodEaten = 0;
        for (Food f : snake.getFoodEaten())
        {
            totalFoodEaten += f.getPoints();
        }
        this.foodL.setText("Food Eaten: " + totalFoodEaten);
    }

    public Label getFoodL()
    {
        return foodL;
    }

    public void makeBody(Body body)
    {
        int x = body.getX();
        int y = body.getY();

        Rectangle rect = new Rectangle(UNIT_SIZE, UNIT_SIZE); // size of one tile
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        rect.setStyle("-fx-fill: #FFFFFF;"); // green head

        board.getChildren().add(rect);
    }

    private void clearSnake()
    {
        for (int i = 0; i < board.getChildren().size(); i++)
        {
            if (board.getChildren().get(i) instanceof Rectangle)
            {
                board.getChildren().remove(i);
            }
        }
    }

    private void createFood()
    {
        Food food = foodService.createApple(snake);
        Circle circle = new Circle(UNIT_SIZE/2); // size of one tile
        circle.setLayoutX(food.getX());
        circle.setLayoutY(food.getY());
        circle.setStyle("-fx-fill: " + food.getColor() + ";"); // green head
        food.setCircle(circle);
        spawnedFood.add(food);
        board.getChildren().add(food.getCircle());
    }
}