

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameScreen extends JPanel {


    private Timer timer;
    private String message = "Game Over";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    private int time = 0;

    public GameScreen() {

        startGame();
    }

    public void startGame() {
        inGame = true;
        initBoard();
    }

    public void initBoard() {

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

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 68 + 190, i * 25 + 50);
                k++;
            }
        }

        timer = new Timer(Commons.PERIOD, new GameCycle());
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
            if(time >= 5){
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

        inGame = false;
        timer.stop();
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
            //Tokaçın x yönünde gideceği yeri haraket yönüne göre belirledik
            ball.setXDir(paddle.getDx());
            ball.setYDir(-1);
        }

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                if (!bricks[i].isDestroyed()) {

                    bricks[i].setHealth(ball.getDamage());
                    Random random = new Random();
                    // -1, 0 veya 1 değerlerini rastgele seçme
                    int randomValue = random.nextInt(3) - 1;
                    // Topun tuğlalara çarptıktan sonra rastgele yönlerde gitmesini belirledik
                    ball.setXDir(randomValue);
                    ball.setYDir(1);

                    // Topun değdiği tuğlayı seçip canını kontrol ediyor
                    if(bricks[i].getHealth() <= 0){
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
