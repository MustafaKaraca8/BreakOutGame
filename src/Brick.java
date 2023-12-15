
import javax.swing.ImageIcon;
import java.awt.*;

public class Brick extends Sprite {

    private boolean destroyed;
    private int health = 4;

    public Brick(int x, int y) {

        initBrick(x, y);
    }

    private void initBrick(int x, int y) {

        this.x = x;
        this.y = y;

        destroyed = false;

        loadImage();
        getImageDimensions();
    }

    private void loadImage() {
        int randomHealth = (int) (Math.random() * 8);
        health = randomHealth;
        if(health <= 2){
            image = new ImageIcon("src/resources/images/brick.png").getImage();
        }
        else if (health > 2 && health <= 4){
            image = new ImageIcon("src/resources/images/brick17.png").getImage();
        }
        else {
            image = new ImageIcon("src/resources/images/brick5.png").getImage();
        }

    }

    boolean isDestroyed() {

        return destroyed;
    }

    void setDestroyed(boolean val) {

        destroyed = val;
    }

    int getHealth(){
        return health;
    }

    void setHealth(int damage){
        health -= damage;
        if(health <= 2){
            image = new ImageIcon("src/resources/images/brick.png").getImage();
        }
        else if (health > 2 && health <= 4){
            image = new ImageIcon("src/resources/images/brick17.png").getImage();
        }
        else {
            image = new ImageIcon("src/resources/images/brick5.png").getImage();
        }
    }
}
