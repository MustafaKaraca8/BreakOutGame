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

public class LevelFife implements Level{
    Timer timer ;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    CollisionControl collisionControl;
    private boolean inGame = true;
    private Component comp ;
    private int brickMovementSpeedX = 5;
    private int brickMovementSpeedY = 4;
    private int brickMovementDirection = 1;
    public LevelFife(Timer timer , Component comp) {
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
        ball.setDamage(20);
        ball.setSpeed(8);
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
                int newX = currentX + (brickMovementSpeedX * brickMovementDirection);
                int newY = currentY;

                if (newX < 0 || newX + bricks[i].getImageWidth() > comp.getWidth()) {
                    brickMovementDirection *= -1;
                    newY += 158;
                }

                // Eğer ki y de max yüksekliğe gelirse başlangıç konumuna dön
                if (newY + bricks[i].getImageHeight() > comp.getHeight()) {
                    newY = 20;
                }

                // Canı iki altına düşen brick aşşağı düşsün en alt sınıra değince yok olsun
                if (bricks[i].getHealth() <= 2) {
                    newY += brickMovementSpeedY;
                    if (newY + bricks[i].getImageHeight() > comp.getHeight()) {
                        bricks[i].setDestroyed(true);
                    }
                }

                bricks[i].setX(newX);
                bricks[i].setY(newY);
            }
        }
    }
}
