package Service;

import Models.Body;
import Models.Direction;
import Models.Food;
import Models.Snake;

public class SnakeBody implements ISnakeBodyService
{
    @Override
    public Snake createSnake(int x, int y, int speed)
    {
        return new Snake(new Body(0, x, y), Direction.Right, speed);
    }

    @Override
    public void addBody(Snake snake, int x, int y)
    {
        snake.addBody(new Body(snake.getBody().size(), x, y));
    }

    @Override
    public void eatFood(Snake snake, Food food)
    {
        snake.eatFood(food);
    }
}
