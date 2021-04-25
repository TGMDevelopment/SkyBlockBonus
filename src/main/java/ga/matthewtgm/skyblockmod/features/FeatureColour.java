package ga.matthewtgm.skyblockmod.features;

import java.awt.*;

public class FeatureColour {

    private int r, g, b, a;
    private int rgba;

    public FeatureColour(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getA() {
        return a;
    }

    public int getRGBA() {
        return new Color(r, g, b, a).getRGB();
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setA(int a) {
        this.a = a;
    }

}