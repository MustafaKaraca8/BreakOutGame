package levels;

import entites.Ball;
import entites.Brick;
import entites.Paddle;
import utility.Commons;
import collisions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static utility.Helper.level;

public class LevelFour implements Level{
    Timer timer ;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    CollisionControl collisionControl;
    private boolean inGame = true;
    private Component comp ;

    private int brickMovementSpeed = 2; // Adjust the speed as needed
    private int brickMovementDirection = 1; // 1 for right, -1 for left
    public LevelFour(Timer timer , Component comp) {
        this.comp = comp;
        this.timer = timer;
        startLevel();

    }


    @Override
    public void startLevel() {
        initializeLevel();
    }

    @Override
    public void initializeLevel() {
        bricks = new Brick[Commons.N_OF_BRICKS_PER_LEVEL[level]];

        ball = new Ball();
        ball.setDamage(2);
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                bricks[k] = new Brick(j * 100 + 270, i * 40 + 50, level);
                k++;
            }
        }

        collisionControl = new CollisionControl(ball,paddle,bricks,inGame,timer,comp);
    }

    @Override
    public void drawObjects(Graphics2D g2d) {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), null);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), null);

        for (int i = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), null);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        paddle.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        paddle.keyReleased(e);
    }

    @Override
    public void updateGame() {
        ball.move();
        paddle.move();
        collisionControl.updateGame();
        moveBricks();
        // Ekstra güncelleme işlemleri burada yapılabilir
    }

    private void moveBricks() {
        for (int i = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            if (!bricks[i].isDestroyed()) {
                int currentX = bricks[i].getX();
                int currentY = bricks[i].getY();
                int newX = currentX + (brickMovementSpeed * brickMovementDirection);
                int newY = currentY;


                if (newX < 0 || newX + bricks[i].getImageWidth() > comp.getWidth()) {
                    brickMovementDirection *= -1;
                    newY += 158;
                }

                //Eğer ki y de max yüksekliğe gelirse başlangıç konumuna dön
                if (newY + bricks[i].getImageHeight() > comp.getHeight()) {
                    newY = 20;
                }

                bricks[i].setX(newX);
                bricks[i].setY(newY);
            }
        }
    }
}
