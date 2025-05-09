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
import java.util.Random;


public class Controller
{
    private final int BOARD_WIDTH = 720, BOARD_HEIGHT = 520, UNIT_SIZE = 20, ROWS = 24, COLS = 36;
    private Snake snake;
    private List<Food> spawnedFood;
    private ISnakeBodyService snakeBodyService;
    private IFoodService foodService;
    private Timeline tl;
    private Random random;
    @FXML private Label title, score, foodL;
    @FXML private Button insaneMod, normaleMod;
    @FXML private AnchorPane board;

    public void initialize()
    {
        this.random = new Random();
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
        makeBody();

        board.requestFocus();
        board.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent keyEvent)
            {
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
        createFood(foodService.createApple(snake));
    }

    public void onActionNormaleMode(javafx.event.ActionEvent actionEvent)
    {
        removeMenuChoices();
        startGame(2);
        setBoardReady();
    }

    private void startGame(int speed)
    {
        tl = new Timeline();
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.setAutoReverse(false);

        this.snakeBodyService = new SnakeBody();
        this.snake = snakeBodyService.createSnake(BOARD_WIDTH/2, BOARD_HEIGHT/2, speed);
        this.foodService = new FoodService(COLS, ROWS, UNIT_SIZE);
        this.spawnedFood = new ArrayList<>();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(200 * snake.getSpeed()), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if (!moveBody())
                {
                    tl.stop();
                    return;
                }

                if (snake.getBody().getFirst().getX() >= BOARD_WIDTH || snake.getBody().getFirst().getX() < 0 || snake.getBody().getFirst().getY() > 440 || snake.getBody().getFirst().getY() < 0)
                {
                    tl.stop();
                    return;
                }

                List<Food> removedFood = new ArrayList<>();
                for (Food food : spawnedFood)
                {
                    if (snake.getBody().getFirst().getX() +10 == food.getX() && snake.getBody().getFirst().getY() +10 == food.getY())
                    {
                        snakeBodyService.eatFood(snake, food);
                        removedFood.add(food);
                        board.getChildren().remove(food.getCircle());
                        setScore();
                    }

                    if (food.getExperationTimer() <= 0)
                    {
                        board.getChildren().remove(food.getCircle());
                        removedFood.add(food);
                    }
                    else
                    {
                        food.setExperationTimer(food.getExperationTimer() - 1);
                    }
                }

                for (Food food : removedFood)
                {
                    if (food.getFoodName().equals("Apple"))
                    {
                        createFood(foodService.createApple(snake));
                    }
                }

                if (snake.getSpeed() == 1)
                {
                    if (random.nextInt(10) == random.nextInt(10))
                    {
                        board.setRotate(90);
                    }
                }

                if (spawnedFood.size() == 1)
                {
                    if (random.nextInt(10) == random.nextInt(10))
                    {
                        switch (random.nextInt(1,3))
                        {
                            case 1-> createFood(foodService.createBanana(snake));
                            case 2-> createFood(foodService.createOrange(snake));
                        }
                    }
                }
                spawnedFood.removeAll(removedFood);
            }
        }));

        tl.play();
    }

    public void onActionInsaneMode(javafx.event.ActionEvent actionEvent)
    {
        removeMenuChoices();
        startGame(1);
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
        setFoodL();
    }

    public void setFoodL()
    {
        int applesEaten = 0;
        int bananasEaten = 0;
        int orangesEaten = 0;
        for (Food f : snake.getFoodEaten())
        {
            switch (f.getFoodName())
            {
                case "Apple" -> applesEaten++;
                case "Banana" -> bananasEaten++;
                case "Orange" -> orangesEaten++;
            }
        }
        this.foodL.setText("Food Eaten: Apples: " + applesEaten + " Bananas: " + bananasEaten + " Oranges: " + orangesEaten );
    }

    public boolean moveBody()
    {
        if (!snakeBodyService.moveSnake(snake, snake.getDirection()))
        {
            return false;
        }

        makeBody();

        return true;
    }

    private void makeBody()
    {
        clearSnake();
        for (Body body : snake.getBody())
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

    private void createFood(Food food)
    {
        Circle circle = new Circle(UNIT_SIZE/2); // size of one tile
        circle.setLayoutX(food.getX());
        circle.setLayoutY(food.getY());
        circle.setStyle("-fx-fill: " + food.getColor() + ";"); // green head
        food.setCircle(circle);
        spawnedFood.add(food);
        System.out.println("Food spawned = " + food.getFoodName());
        board.getChildren().add(food.getCircle());
    }
}