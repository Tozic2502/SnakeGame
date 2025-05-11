package Service;

import Models.Food;
import Models.PowerUp;
import Models.Snake;

public interface IFoodService {
    Food createApple(Snake snake);
    Food createBanana(Snake snake);
    Food createOrange(Snake snake);

}
