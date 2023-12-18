

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static utility.Helper.generateRandomDir;

public class GameScreen extends JPanel {


    private Timer timer;
    private String message = "Game Over";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    private int time = 0;


    // End screeni Game screen içerisinde çağıracağımızdan kaynaklı bir referansa ihitiyaç duyuyouruz
    // bu yüzden refarans bir end screen alıyoruz.
    public GameScreen() {
        startGame();
    }

    public void startGame() {
        inGame = true;
        initBoard();
    }

    public void initBoard() {
        setBackground(new Color(217, 117, 117));
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        gameInit();
    }

    private void gameInit() {

        bricks = new Brick[Commons.N_OF_BRICKS];

        ball = new Ball();
        ball.setDamage(2);
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 4; j++) {

                bricks[k] = new Brick(j * 80 + 270, i * 21 + 50);
                k++;
            }
        }

        timer = new Timer(Commons.PERIOD, new GameCycle());
    }

    public void startTimer() {
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {

            drawObjects(g2d);
        } else {

            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {

        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }

    private void gameFinished(Graphics2D g2d) {

        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message,
                (Commons.WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            paddle.keyPressed(e);
        }
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            time++;
            if (time >= 5) {
                // Do Brick Change
            }
            doGameCycle();
        }
    }

    private void doGameCycle() {

        ball.move();
        paddle.move();
        checkCollision();
        repaint();
    }

    private void stopGame() {
        var parent = (Breakout) SwingUtilities.getWindowAncestor(GameScreen.this);
        inGame = false;
        timer.stop();
        parent.endScreen.openEndScreen(inGame);

    }

    private void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {

            stopGame();
        }

        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS; i++) {

            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == Commons.N_OF_BRICKS) {
                message = "Victory";
                stopGame();
            }
        }

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

                    if(bricks[i].getRect().contains(pointRight) && bricks[i].getRect().contains(pointTop)){
                        System.out.println("sol alt çapraz");
                        ball.setXDir(-1);
                        ball.setYDir(+1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointTop)) {
                        System.out.println("sağ alt çapraz");
                        ball.setXDir(+1);
                        ball.setYDir(+1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointBottom)){
                        System.out.println("sağ üst çapraz");
                        ball.setXDir(1);
                        ball.setYDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointBottom)) {
                        System.out.println("sol üst çapraz");
                        ball.setXDir(-1);
                        ball.setYDir(-1);
                    }

                    if (bricks[i].getHealth() <= 0) {
                        bricks[i].setDestroyed(true);
                    }
                }
            }
        }
    }


   /*private void ultimateMode(){
        for (int i = 0; i < ; i++) {

        }
    }*/
}
