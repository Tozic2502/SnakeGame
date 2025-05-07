package Service;

import Models.Food;
import Models.Snake;

public interface ISnakeBodyService
{
    Snake createSnake(int x, int y, int speed);
    void addBody(Snake snake, int x, int y);
    void eatFood(Snake snake, Food food);
}
