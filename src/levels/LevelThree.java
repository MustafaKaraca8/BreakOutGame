package levels;

import collisions.CollisionControl;
import entites.Ball;
import entites.Brick;
import entites.Paddle;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static utility.Helper.generateRandomDir;
import static utility.Helper.level;

public class LevelThree implements Level {
    Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private int[] brickMovementDirections;
    CollisionControl collisionControl;
    private boolean inGame = true;
    private Component comp;

    private int brickMovementSpeed = 2;


    public LevelThree(Timer timer, Component comp) {
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
        brickMovementDirections = new int[Commons.N_OF_BRICKS_PER_LEVEL[level]];

        ball = new Ball();
        ball.setDamage(2);
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                bricks[k] = new Brick(j * 100 + 270, i * 40 + 50, level);
                brickMovementDirections[k] = generateRandomDir(); // Assign random initial direction
                k++;
            }
        }

        System.out.println(comp);
        System.out.println(timer);
        collisionControl = new CollisionControl(ball, paddle, bricks, inGame, timer, comp);
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
    }

    @Override
    public boolean isLevelComplete() {
        // Seviye tamamlanma koşulları burada kontrol edilir
        return false;
    }

    private void moveBricks() {
        for (int i = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            if (!bricks[i].isDestroyed()) {
                int currentX = bricks[i].getX();
                int newX = currentX + (brickMovementSpeed * brickMovementDirections[i]);

                // Her bir biriğin kendi özelinde gittikleri yönü alıyor ve belirli kontrollerle değiştirmesini sağlıyor
                if (newX < 0 || newX + bricks[i].getImageWidth() > comp.getWidth()) {
                    brickMovementDirections[i] *= -1; // Change direction when reaching the boundaries
                }

                // Eğer ki iki brick birbirine değerse yön değiştirmesini sağlıyor
                for (int j = 0; j < Commons.N_OF_BRICKS_PER_LEVEL[level]; j++) {
                    if (i != j && !bricks[j].isDestroyed() && bricks[i].getRect().intersects(bricks[j].getRect())) {
                        brickMovementDirections[i] *= generateRandomDir(); // Change direction on collision
                        break; // No need to check further collisions for this brick
                    }
                }
                // Update the position
                bricks[i].setX(newX);
            }
        }
    }
}
