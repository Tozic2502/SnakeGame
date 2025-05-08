package Service;

import Models.*;

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
        addBody(snake, food.getX() -10, food.getY() -10);
    }

    @Override
    public void moveSnake(Snake snake, Direction direction)
    {
        Body head = snake.getBody().getFirst();

        switch (direction)
        {
            case Up -> head.setY(head.getY() - 20);
            case Down -> head.setY(head.getY() + 20);
            case Left -> head.setX(head.getX() - 20);
            case Right -> head.setX(head.getX() + 20);
        }

        for (int i = snake.getBody().size() -1; i >= 1; i--)
        {
            snake.getBody().get(i).setX(snake.getBody().get(i-1).getX());
            snake.getBody().get(i).setY(snake.getBody().get(i-1).getY());
        }
    }
}
