package Models;

import java.util.List;

public class Snake
{
    private List<Body> body;
    private Direction direction;
    private int speed;
    private List<Food> foodEaten;

    public List<Body> getBody()
    {
        return body;
    }

    public void setBody(List<Body> body)
    {
        this.body = body;
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
