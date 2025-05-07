package Models;

import java.util.ArrayList;
import java.util.List;

public class Snake
{
    private List<Body> body;
    private Direction direction;
    private int speed;
    private List<Food> foodEaten;

    public Snake(Body body, Direction direction, int speed)
    {
        this.body = new ArrayList<Body>();
        this.body.add(body);
        this.direction = direction;
        this.speed = speed;
        this.foodEaten = new ArrayList<>();
    }

    public List<Body> getBody()
    {
        return body;
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

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
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
