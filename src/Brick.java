import javax.swing.*;

import static utility.Helper.*;

public class Brick extends Sprite {



    private boolean destroyed;
    private int  mainHealth = 8;
    private int health;
    private ImageIcon[] blackBrickImages = {new ImageIcon("src/resources/images/bricks/blackBrick.png") , new ImageIcon("src/resources/images/bricks/brokenBlackBrick.png") , new ImageIcon("src/resources/images/bricks/brokenBlackBrick2.png")};
    private ImageIcon[] redBrickImages = {new ImageIcon("src/resources/images/bricks/redBrick.png") , new ImageIcon("src/resources/images/bricks/brokenRedBrick.png") , new ImageIcon("src/resources/images/bricks/brokenRedBrick2.png")};
    private ImageIcon[] blueBrickImages = {new ImageIcon("src/resources/images/bricks/blueBrick.png") , new ImageIcon("src/resources/images/bricks/brokenBlueBrick.png") , new ImageIcon("src/resources/images/bricks/brokenBlueBrick2.png")};
    private ImageIcon[] purpleBrickImages = {new ImageIcon("src/resources/images/bricks/purpleBrick.png") , new ImageIcon("src/resources/images/bricks/brokenPurpleBrick.png") , new ImageIcon("src/resources/images/bricks/brokenPurpleBrick2.png")};
    private ImageIcon [] brickImages;
    public Brick(int x, int y, int level) {
        initBrick(x, y , level);
    }

    private void initBrick(int x, int y , int level) {
        this.x = x;
        this.y = y;
        destroyed = false;
        if(level == 1){
            mainHealth = 2;
        } else if (level == 2) {
            mainHealth = generateRandomValue(2 , 4);
        } else if (level == 3) {
            mainHealth = generateRandomValue(4 , 6);
        }else {
            mainHealth = 8;
        }
        health = mainHealth;
        loadImage();
        getImageDimensions();
    }

    private void loadImage() {
        if (mainHealth == 2) {
                brickImages = blackBrickImages;
            if(health <= 2){
                image = brickImages[0].getImage();
            }
        } else if (mainHealth == 4 || mainHealth == 3) {
                brickImages = redBrickImages;
            if(health <= 2)image = brickImages[1].getImage();
            else image = brickImages[0].getImage();
        } else if (mainHealth == 6 || mainHealth == 5) {
            System.out.println(health);
            brickImages = blueBrickImages;
            if(health<=2) image = brickImages[2].getImage();
            else if(health<=4) image = brickImages[1].getImage();
            else  image = brickImages[0].getImage();
        } else if(mainHealth >= 8 ){
            brickImages = purpleBrickImages;
            if(health<=2) image = brickImages[2].getImage();
            else if(health<=4) image = brickImages[1].getImage();
            else  image = brickImages[0].getImage();
        }
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