package ga.matthewtgm.skyblockmod.features;

public class FeaturePosition {

    private int x, y;

    public FeaturePosition(int x, int y) {
        this.setPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

}