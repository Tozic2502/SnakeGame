package Service;

import Models.Snake;

public interface SnakeBodyService
{
    Snake createSnake(int x, int y, int speed);
    void addBody(Snake snake, int x, int y);
}
