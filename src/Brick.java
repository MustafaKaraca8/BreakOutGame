import javax.swing.*;

import static utility.Helper.selectRandomBrickImage;

public class Brick extends Sprite {



    private boolean destroyed;
    private int health;
    private ImageIcon[] brickImages;

    public Brick(int x, int y) {
        initBrick(x, y);
    }

    private void initBrick(int x, int y) {
        this.x = x;
        this.y = y;
        destroyed = false;
        health = (int) (Math.random() * Commons.MAX_HEALTH);
        loadImage();
        getImageDimensions();
    }

    private void loadImage() {
        if (health <= 2) {
            brickImages = new ImageIcon[]{new ImageIcon("src/resources/images/bricks/lowHealthBrick.png"), new ImageIcon("src/resources/images/bricks/lowHealthBrick2.png")};
        } else if (health <= 4) {
            brickImages = new ImageIcon[]{new ImageIcon("src/resources/images/bricks/brokenBrick.png"), new ImageIcon("src/resources/images/bricks/lowHealthBrick.png")};
        } else {
            brickImages = new ImageIcon[]{new ImageIcon("src/resources/images/bricks/brick.png"), new ImageIcon("src/resources/images/bricks/brick.png")};
        }
        image = brickImages[selectRandomBrickImage()].getImage();
    }

    boolean isDestroyed() {
        return destroyed;
    }

    void setDestroyed(boolean val) {
        destroyed = val;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int damage) {
        health -= damage;
        // 2 veya 4'e eşit veya küçükse bu metot çağırıldığında loadImage çalıştır
        if (health <= 2) {
            loadImage();
        }else if(health > 2 && health <= 4) loadImage();
    }
}