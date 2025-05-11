package Models;

public class Body extends Placement {
    private int indexOfBody;

    public Body(int indexOfBody, int x, int y) {
        super(x, y);
        this.indexOfBody = indexOfBody;
    }

    public int getIndexOfBody() {
        return indexOfBody;
    }

    public void setIndexOfBody(int indexOfBody) {
        this.indexOfBody = indexOfBody;
    }
}