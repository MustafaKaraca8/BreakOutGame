
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Paddle extends Sprite {

    private int dx;
    private int dy;
    private int speed = 5;
    boolean extraShut;
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
        y = Commons.INIT_PADDLE_Y +dy;
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

        if(key == KeyEvent.VK_DOWN){
            dy = 8;
            System.out.println(extraShut);
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

        if(key == KeyEvent.VK_DOWN){
            ExtraShut();
            System.out.println(extraShut);
            dy = 0;
        }
        image = new ImageIcon("src/resources/images/bricks/redBrick.png").getImage();
    }

    private void resetState() {

        x = Commons.INIT_PADDLE_X;
        y = Commons.INIT_PADDLE_Y;
    }

    private void ExtraShut() {

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Timer'ın süresi dolunca burası çalışır
                System.out.println("Timer süresi doldu, şey true oldu!");
                extraShut = false;

                ((Timer) e.getSource()).stop();
            }
        });

        // Timer'ı başlat
        extraShut = true;
        System.out.println("true değer çalıştı");
        timer.start();
        // Ekstra işlemler (eğer gerekirse)
    }
    int getDy() {
        return dy;
    }

    boolean getExtraShut(){
        return extraShut;
    }
}
