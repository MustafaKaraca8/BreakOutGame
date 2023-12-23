package entites;

import javax.swing.*;

import static utility.Commons.pathOfBricks;
import static utility.Helper.*;

public class Brick extends Sprite {



    private boolean destroyed;
    private int  mainHealth = 8;
    private int health;
    private ImageIcon[] blackBrickImages = {new ImageIcon(pathOfBricks + "blackBrick.png") };
    private ImageIcon[] redBrickImages = {new ImageIcon(pathOfBricks + "redBrick.png") , new ImageIcon(pathOfBricks + "brokenRedBrick.png") };
    private ImageIcon[] blueBrickImages = {new ImageIcon(pathOfBricks + "blueBrick.png") , new ImageIcon(pathOfBricks + "brokenBlueBrick.png") , new ImageIcon(pathOfBricks + "brokenBlueBrick2.png")};
    private ImageIcon[] purpleBrickImages = {new ImageIcon(pathOfBricks + "purpleBrick.png") , new ImageIcon( pathOfBricks + "brokenPurpleBrick.png") , new ImageIcon(pathOfBricks + "brokenPurpleBrick2.png")};
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

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean val) {
        destroyed = val;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int damage) {
        health -= damage;
        // 2 veya 4'e eşit veya küçükse bu metot çağırıldığında loadImage çalıştır
        if (health <= 2) {
            loadImage();
        }else if(health > 2 && health <= 4) loadImage();
    }
    public void setX(int x){
        this.x = x;
    }
}