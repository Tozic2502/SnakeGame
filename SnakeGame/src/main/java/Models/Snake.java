package Models;

import java.util.ArrayList;
import java.util.List;

public class Snake
{
    private Direction direction;
    private List<Food> foodEaten;
    private List<Body> body;
    private int speed;

    public Snake() {
        body = new ArrayList<>();
        speed = 5;
    }

    public void initializeSnake(int startX, int startY) {
        body.add(new Body(0, startX, startY));
    }

    public void moveSnake(Direction direction) {
        Body head = body.get(0);
        int newX = head.getX();
        int newY = head.getY();

        switch (direction) {
            case Up -> newY -= 20;
            case Down -> newY += 20;
            case Left -> newX -= 20;
            case Right -> newX += 20;
        }

        body.add(0, new Body(0, newX, newY));

        }

    public List<Body> getBody() {
        return body;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public Snake(Body body, Direction direction, int speed)
    {
        this.body = new ArrayList<Body>();
        this.body.add(body);
        this.direction = direction;
        this.speed = speed;
        this.foodEaten = new ArrayList<>();
    }

    public void addBody(Body body)
    {
        this.body.add(body);
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public List<Food> getFoodEaten()
    {
        return foodEaten;
    }

    public void setFoodEaten(List<Food> foodEaten)
    {
        this.foodEaten = foodEaten;
    }
}
