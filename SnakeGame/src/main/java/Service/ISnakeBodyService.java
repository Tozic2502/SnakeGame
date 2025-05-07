package Service;

import Models.*;

public interface ISnakeBodyService
{
    Snake createSnake(int x, int y, int speed);
    void addBody(Snake snake, int x, int y);
    void eatFood(Snake snake, Food food);
    void moveSnake(Snake snake, Direction direction);
}
