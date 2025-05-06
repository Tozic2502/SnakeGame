package Models;

public class Food extends Placement
{
    private String foodName;
    private int points;
    private String color;
    private int experationTimer;

    public String getFoodName()
    {
        return foodName;
    }

    public void setFoodName(String foodName)
    {
        this.foodName = foodName;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getExperationTimer()
    {
        return experationTimer;
    }

    public void setExperationTimer(int experationTimer)
    {
        this.experationTimer = experationTimer;
    }

    public Food (String foodName, int points, String color, int experationTimer, int x, int y)
    {
        super(x, y);
        this.foodName = foodName;
        this.points = points;
        this.color = color;
        this.experationTimer = experationTimer;
    }
}
