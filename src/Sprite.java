

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    int x;
    int y;
    int imageWidth;
    int imageHeight;
    Image image;



    int getX() {

        return x;
    }


    int getY() {

        return y;
    }

    int getImageWidth() {

        return imageWidth;
    }

    int getImageHeight() {

        return imageHeight;
    }

    Image getImage() {

        return image;
    }

    Rectangle getRect() {

        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions() {

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}
