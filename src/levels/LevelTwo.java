package levels;

import collisions.CollisionControl;
import entites.Ball;
import entites.Brick;
import entites.Paddle;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static utility.Helper.level;

public class LevelTwo implements Level {
    Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    CollisionControl collisionControl;
    private boolean inGame = true;
    private int radius = 150;


    private Component comp;

    public LevelTwo(Timer timer, Component comp) {
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

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                double angle = 2 * Math.PI * j / 4.0;
                int x = (int) (Math.cos(angle) * radius) + 550;
                int y = (int) (Math.sin(angle) * radius) + i * 40 + 200;
                bricks[k] = new Brick(x, y, level);
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
        // Ekstra güncelleme işlemleri burada yapılabilir
    }

    @Override
    public boolean isLevelComplete() {
        // Seviye tamamlanma koşulları burada kontrol edilir
        return false;
    }
}
