package collisions;

import audio.AudioController;
import entites.Ball;
import entites.Brick;
import entites.Paddle;
import ui.Breakout;
import utility.Commons;

import javax.swing.*;
import java.awt.*;

import static utility.Commons.pathOfAudio;
import static utility.Helper.generateRandomDir;
import static utility.Helper.level;

public class CollisionControl {

    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame ;
    Timer timer;
    Component comp;

    private final AudioController hittingTheBrick = new AudioController();
    private final AudioController hittingThePaddle = new AudioController();

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

        //Paddle ve topun etkileşime girdiği yer
        if ((ball.getRect()).intersects(paddle.getRect())) {
            hittingThePaddle.calAsync(pathOfAudio + "hittingSound.wav");
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


                if(paddle.getExtraShut()){

                    ball.setXDir(0);
                    ball.setYDir(-2);
                }


            }

            if (ballLPos >= first && ballLPos < second) {


                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1 * ball.getYDir());

                if(paddle.getExtraShut()){

                    ball.setXDir(0);
                    ball.setYDir(-2);
                }
            }

            if (ballLPos >= second && ballLPos < third) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1);

                if(paddle.getExtraShut()){

                    ball.setXDir(0);
                    ball.setYDir(-2);
                }

            }

            if (ballLPos >= third && ballLPos < fourth) {

                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1 * ball.getYDir());

                if(paddle.getExtraShut()){

                    ball.setXDir(0);
                    ball.setYDir(-2);
                }


            }

            if (ballLPos > fourth) {


                if (ball.getXdir() == -1) ball.setXDir(-1);
                else if (ball.getXdir() == 1) ball.setXDir(1);
                else ball.setXDir(generateRandomDir());
                ball.setYDir(-1);

                if(paddle.getExtraShut()){

                    ball.setXDir(0);
                    ball.setYDir(-2);
                }

            }


        }
    }

    // Topun tuğlayla ilişkisi
    private void checkBrickCollision(){
        for (int i = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                if(!bricks[i].isDestroyed()) hittingTheBrick.calAsync(pathOfAudio + "hittingSound.wav");

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

                        ball.setXDir(-1);
                        ball.setYDir(+1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointTop)) {

                        ball.setXDir(+1);
                        ball.setYDir(+1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointBottom)) {
                        ball.setXDir(1);
                        ball.setYDir(-1);
                    } else if (bricks[i].getRect().contains(pointRight) && bricks[i].getRect().contains(pointBottom)) {

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
            if(timer != null ) endGame();
        }


        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            if(bricks[i].getRect().intersects(paddle.getRect())){
                endGame();
            }
            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == Commons.N_OF_BRICKS_PER_LEVEL[level]) {
                level +=1;
                levelScreen();
                if(level == 5) winScreen();
            }
        }
    }


    private void endGame(){
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        inGame = false;
        if(timer != null)  timer.stop();
        parent.endScreen.openEndScreen(inGame);
    }

    private void levelScreen(){
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        if(timer != null) timer.stop();
        parent.levelScreen.openLevelScreen();
    }

    private void winScreen(){
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        inGame = false;
        if(timer != null) timer.stop();
        parent.winScreen.openWinScreen(inGame);
    }
}