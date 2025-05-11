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
    private final int BOARD_WIDTH = 720, BOARD_HEIGHT = 440, UNIT_SIZE = 20, ROWS = 23, COLS = 36;
    private Snake snake;
    private List<Food> spawnedFood;
    private ISnakeBodyService snakeBodyService;
    private IFoodService foodService;
    private Timeline tl;
    private Random random;
    private boolean isSpeedBoost = false;
    private boolean isBigHead = false;
    private int powerupTimer = 0;
    private int currentInterval = 200;
    private boolean bigHead = false;
    @FXML private Label title, score, foodL, restartL;
    @FXML private Button insaneMod, normaleMod;
    @FXML private AnchorPane board;

    public void initialize()
    {
        this.random = new Random();
    }

    public void removeMenuChoices()
    {
        insaneMod.setVisible(false);
        normaleMod.setVisible(false);
        title.setVisible(false);
        score.setVisible(true);
        foodL.setVisible(true);
        restartL.setVisible(false);
    }
    /**
     * Show restart screen: clear board tiles, change background to white, show menu
     */
    public void restartScreen() {
        // Stop and clear board contents except UI controls
        clearCheckerboard();
        clearSnake();
        clearFood();
        // Set background to white for game over screen
        board.setStyle("-fx-background-color: white;");

        // Show menu controls and game over title
        insaneMod.setVisible(true);
        normaleMod.setVisible(true);
        title.setVisible(true);
        title.setText("Game Over! " + score.getText());
        score.setVisible(false);
        foodL.setVisible(false);
        restartL.setVisible(true);
    }

    public void setBoardReady()
    {
        board.setStyle("-fx-background-color: #1e1e1e;");
        drawCheckeredBoard(ROWS, COLS, UNIT_SIZE);
        makeBody();
        setScore();

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
        start(2);
        setBoardReady();
    }
    private void startTimeline() {
        if (tl != null) {
            tl.stop();
        }
        tl = new Timeline(new KeyFrame(Duration.millis(currentInterval), e -> gameTick()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
    private void start(int speed) {
        this.snakeBodyService = new SnakeBody();
        this.snake = snakeBodyService.createSnake(BOARD_WIDTH / 2, BOARD_HEIGHT / 2, speed);
        this.foodService = new FoodService(COLS, ROWS, UNIT_SIZE);
        this.spawnedFood = new ArrayList<>();
        this.currentInterval = 200 * snake.getSpeed();
        startTimeline();
    }
    private void gameTick()
    {
        if (!moveBody()) {
            tl.stop();
            restartScreen();
            return;
        }

        if (snake.getBody().getFirst().getX() >= BOARD_WIDTH || snake.getBody().getFirst().getX() < 0
                || snake.getBody().getFirst().getY() > BOARD_HEIGHT || snake.getBody().getFirst().getY() < 0) {
            tl.stop();
            restartScreen();
            return;
        }

        List<Food> removedFood = new ArrayList<>();
        for (Food food : spawnedFood) {
            int headX = snake.getBody().getFirst().getX();
            int headY = snake.getBody().getFirst().getY();
            int foodX = (int) food.getX();
            int foodY = (int) food.getY();

// Define collision range depending on power-up
            double collisionRange = bigHead ? UNIT_SIZE * 1.5 : UNIT_SIZE;

// Check distance from top-left corner (rough enough for food collisions)
            if (Math.abs((headX + UNIT_SIZE / 2) - foodX) < collisionRange &&
                    Math.abs((headY + UNIT_SIZE / 2) - foodY) < collisionRange) {

                snakeBodyService.eatFood(snake, food);
                removedFood.add(food);
                board.getChildren().remove(food.getCircle());
                setScore();

                if (food.getFoodName().equals("Banana")) {
                    isSpeedBoost = true;
                    powerupTimer = 50;
                    currentInterval = 100;
                    startTimeline();
                }

                if (food.getFoodName().equals("Orange")) {
                    bigHead = true;
                    isBigHead = true;
                    powerupTimer = 50;
                }
            }


            if (food.getExperationTimer() <= 0) {
                board.getChildren().remove(food.getCircle());
                removedFood.add(food);
            } else {
                food.setExperationTimer(food.getExperationTimer() - 1);
            }
        }

        for (Food food : removedFood) {
            if (food.getFoodName().equals("Apple")) {
                createFood(foodService.createApple(snake));
            }
        }

        if (snake.getSpeed() == 1 && random.nextInt(10) == random.nextInt(10)) {
            board.setRotate(90);
        }

        if (spawnedFood.size() == 1 && random.nextInt(10) == random.nextInt(10)) {
            switch (random.nextInt(1, 3)) {
                case 1 -> createFood(foodService.createBanana(snake));
                case 2 -> createFood(foodService.createOrange(snake));
            }
        }

        spawnedFood.removeAll(removedFood);

        if (powerupTimer > 0) {
            powerupTimer--;

            if (powerupTimer == 0) {
                if (isSpeedBoost) {
                    currentInterval = 200 * snake.getSpeed();
                    startTimeline();
                }

                if (isBigHead) {
                    bigHead = false;
                }

                isSpeedBoost = false;
                isBigHead = false;
            }
        }

    }

    public void onActionInsaneMode(javafx.event.ActionEvent actionEvent)
    {
        removeMenuChoices();
        start(1);
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

    private void makeBody() {
        clearSnake();
        List<Body> snakeParts = snake.getBody();

        for (int i = 0; i < snakeParts.size(); i++) {
            Body body = snakeParts.get(i);
            int x = body.getX();
            int y = body.getY();

            Rectangle rect;

            if (i == 0 && bigHead) {
                // Head: 3x3 tiles (3 * UNIT_SIZE)
                int size = UNIT_SIZE * 3;
                rect = new Rectangle(size, size);
                // Center it so it visually matches original tile grid
                rect.setLayoutX(x - UNIT_SIZE); // Move 1 tile left
                rect.setLayoutY(y - UNIT_SIZE); // Move 1 tile up
                rect.setStyle("-fx-fill: #FFA500;"); // Optional orange for distinction
            } else {
                // Normal body tile
                rect = new Rectangle(UNIT_SIZE, UNIT_SIZE);
                rect.setLayoutX(x);
                rect.setLayoutY(y);
                rect.setStyle("-fx-fill: #FFFFFF;");
            }

            rect.setArcWidth(4);
            rect.setArcHeight(4);
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
    private void clearCheckerboard() {
        board.getChildren().removeIf(node -> node instanceof Pane &&
                (node.getStyleClass().contains("tile-light") || node.getStyleClass().contains("tile-dark")));
    }

    private void clearFood() {
        board.getChildren().removeIf(node -> node instanceof Circle);
    }
}