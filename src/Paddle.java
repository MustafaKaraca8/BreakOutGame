
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Sprite {

    private int dx;
    private int speed = 5;

    public Paddle() {

        initPaddle();
    }

    private void initPaddle() {

        loadImage();
        getImageDimensions();

        resetState();
    }

    private void loadImage() {
        var ii = new ImageIcon("src/resources/images/bricks/redBrick.png");
        image = ii.getImage();
    }

    void move() {

        x += dx * speed;

        if (x <= 0) {

            x = 0;
        }

        if (x >= Commons.WIDTH - imageWidth) {

            x = Commons.WIDTH - imageWidth;
        }
    }

    // Basıldığında
    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            image = new ImageIcon("src/resources/images/bricks/brokenRedBrick.png").getImage();
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            image = new ImageIcon("src/resources/images/bricks/brokenRedBrick.png").getImage();
            dx = 1;
        }
    }

    // Basılan tuş bırakıldığında
    void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        image = new ImageIcon("src/resources/images/bricks/redBrick.png").getImage();
    }

    private void resetState() {

        x = Commons.INIT_PADDLE_X;
        y = Commons.INIT_PADDLE_Y;
    }

    int getDx() {
        return dx;
    }
}
