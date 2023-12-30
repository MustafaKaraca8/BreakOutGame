package entites;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    int x;
    int y;
    Image image;
    int imageWidth;
    int imageHeight;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}
