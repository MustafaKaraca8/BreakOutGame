import javax.swing.*;
import java.awt.*;

import static utility.Helper.generateRandomDir;

public class CollisionControl {

    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    Timer timer;
    Component comp;

    public CollisionControl(Ball ball, Paddle paddle, Brick[] bricks, boolean inGame, Timer timer, Component comp) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.inGame = inGame;
        this.timer = timer;
        this.comp = comp;
    }

    public void updateGame() {
        checkPaddleCollision();
        checkBrickCollision();
        checkGameSituation();
    }

    // Topun tokaçla ilişkisi
    private void checkPaddleCollision() {

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1);
            }
        }
    }

    // Topun tuğlayla ilişkisi
    private void checkBrickCollision(){
        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {
            if ((ball.getRect()).intersects(bricks[i].getRect())) {
                int ballLeft = (int) ball.getRect().getMinX();
                int ballTop = (int) ball.getRect().getMinY();
                int ballRight = (int) ball.getRect().getMaxX();
                int ballBottom = (int) ball.getRect().getMaxY();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballHeight = (int) ball.getRect().getHeight();

                var pointRight = new Point(ballRight + 1, ballTop + ballHeight / 2);
                var pointLeft = new Point(ballLeft - 1, ballTop + ballHeight / 2);
                var pointBottom = new Point(ballLeft + ballWidth / 2, ballBottom + 1);
                var pointTop = new Point(ballLeft + ballWidth / 2, ballTop - 1);


                if (!bricks[i].isDestroyed()) {
                    bricks[i].setHealth(ball.getDamage());

                    if (bricks[i].getRect().contains(pointLeft)) {
                        if (ball.getXdir() == -1) {
                            ball.setXDir(1);
                        } else if (ball.getXdir() == 1) {
                            ball.setXDir(-1);
                        }

                    } else if (bricks[i].getRect().contains(pointRight)) {
                        if (ball.getXdir() == -1) {
                            ball.setXDir(1);
                        } else if (ball.getXdir() == 1) {
                            ball.setXDir(-1);
                        }
                    }

                    if (bricks[i].getRect().contains(pointTop)) {
                        if (ball.getYDir() == -1) {
                            ball.setYDir(1);
                        } else if (ball.getYDir() == 1) {
                            ball.setYDir(-1);
                        }
                    } else if (bricks[i].getRect().contains(pointBottom)) {
                        if (ball.getYDir() == -1) {
                            ball.setYDir(1);
                        } else if (ball.getYDir() == 1) {
                            ball.setYDir(-1);
                        }
                    }

                    if (bricks[i].getRect().contains(pointRight) && bricks[i].getRect().contains(pointTop)) {
                        System.out.println("sol alt çapraz");
                        ball.setXDir(-1);
                        ball.setYDir(+1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointTop)) {
                        System.out.println("sağ alt çapraz");
                        ball.setXDir(+1);
                        ball.setYDir(+1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointBottom)) {
                        System.out.println("sağ üst çapraz");
                        ball.setXDir(1);
                        ball.setYDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointBottom)) {
                        System.out.println("sol üst çapraz");
                        ball.setXDir(-1);
                        ball.setYDir(-1);
                    }

                    if(bricks[i].getHealth() <= 0){
                        bricks[i].setDestroyed(true);
                    }
                }
            }
        }
    }


    private void checkGameSituation() {
        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {
            endGame();
        }

        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS; i++) {
            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == Commons.N_OF_BRICKS) {
                endGame();
            }
        }
    }
    private void endGame(){
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        inGame = false;
        timer.stop();
        parent.endScreen.openEndScreen(inGame);
    }
}