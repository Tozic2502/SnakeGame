package Service;

import Models.*;

import java.util.List;
import java.util.Random;

public class FoodService implements IFoodService
{
    private final String[] foodNames = {"Apple", "Banana", "Orange"};
    private final String[] foodColor = {"Red", "Yellow", "Orange"};
    private final int ROWS, COLS, unitSize;
    private Random random = new Random();

    public FoodService(int cols, int rows, int unitSize)
    {
        this.COLS = cols;
        this.ROWS = rows;
        this.unitSize = unitSize;
    }

    @Override
    public Food createApple(Snake snake)
    {
        int[] xy = createSpawnPoint(snake.getBody());

        return new Food(foodNames[0], 10, foodColor[0], random.nextInt(40,80), xy[0], xy[1]);
    }

    @Override
    public Food createBanana(Snake snake)
    {
        int[] xy = createSpawnPoint(snake.getBody());

        return new Food(foodNames[1], 20, foodColor[1], random.nextInt(4,8), xy[0], xy[1]);
    }

    @Override
    public Food createOrange(Snake snake)
    {
        int[] xy = createSpawnPoint(snake.getBody());

        return new Food(foodNames[2], 50, foodColor[2], random.nextInt(4,8), xy[0], xy[1]);
    }

    /***
     * creates foods spawnpoint from random ints and returns int array for X Y
     * @return
     */
    private int[] createSpawnPoint(List<Body> body)
    {
        int x = 0, y = 0;
        boolean isValid = false;
        while (!isValid)
        {
            isValid = true;
            x = random.nextInt(1, COLS) * unitSize -10;
            y = random.nextInt(1, ROWS) * unitSize -10;
            for (Placement p : body)
            {
                if (x == p.getX()+10 && y == p.getY()+10)
                {
                    isValid = false;
                    break;
                }
            }
        }
        return new int[]{x, y};
    }
}
