package Models;

import java.util.ArrayList;
import java.util.List;

public class Snake
{
    private Direction direction;
    private List<Food> foodEaten;
    private List<Body> body;
    private int speed;



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

    public void eatFood(Food foodEaten)
    {
        this.foodEaten.add(foodEaten);
    }
}
