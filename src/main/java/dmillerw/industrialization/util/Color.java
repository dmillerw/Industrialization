package dmillerw.industrialization.util;

import org.lwjgl.opengl.GL11;

/**
 * Created by Dylan Miller on 1/4/14
 */
public class Color {

    public static boolean similarColor(Color target, Color pixel, int tolerance) {
        return (pixel.r - tolerance <= target.r) && (target.r <= pixel.r + tolerance) &&
               (pixel.g - tolerance <= target.g) && (target.g <= pixel.g + tolerance) &&
               (pixel.b - tolerance <= target.b) && (target.b <= pixel.b + tolerance);
    }

    public static Color average(Color ... colors) {
        int rBucket = 0;
        int gBucket = 0;
        int bBucket = 0;

        for (Color color : colors) {
            rBucket += color.r;
            gBucket += color.g;
            bBucket += color.b;
        }

        rBucket /= colors.length;
        gBucket /= colors.length;
        bBucket /= colors.length;

        return new Color(Math.min(255, rBucket), Math.min(255, gBucket), Math.min(255, bBucket));
    }

    public int r;
    public int g;
    public int b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int c) {
        r = c >> 16 & 255;
        g = c >> 8 & 255;
        b = c & 255;
    }

    public void add(Color color) {
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
    }

    public void subtract(Color color) {
        this.r -= color.r;
        this.g -= color.g;
        this.b -= color.b;
    }

    public int toInt() {
        int color = r;
        color = (color << 8 & 255) + g;
        color = (color << 8 & 255) + b;
        return color;
    }

    public void apply() {
        GL11.glColor3f(1 / r, 1 / g, 1 / b);
    }

    public Color copy() {
        return new Color(r, g, b);
    }

    @Override
    public String toString() {
        return "R: " + r + " G: " + g + " B: " + b;
    }

}
