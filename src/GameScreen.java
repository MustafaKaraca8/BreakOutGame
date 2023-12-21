

import utility.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class GameScreen extends JPanel {


    private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    private int time = 0;
    private PauseGame pauseGame;
    CollisionControl collisionControl;
    private final AudioSingleton audioSingleton = AudioSingleton.getInstance();

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
        pauseGame = new PauseGame(GameScreen.this);
        gameInit();
        audioSingleton.calAsync("src/resources/audio/game_start.wav");
    }

    private void gameInit() {

        bricks = new Brick[Commons.N_OF_BRICKS];

        ball = new Ball();
        ball.setDamage(2);
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 1; i++) {

            for (int j = 0; j < 1; j++) {

                // 80 ve 21
                bricks[k] = new Brick(j * 100 + 270, i * 40 + 50 , 1);
                k++;
            }

        }

        collisionControl = new CollisionControl(ball, paddle, bricks, inGame, timer, GameScreen.this);
        //Daha önce timer çalışmamış ise çalıştır
        if (timer == null) {
            timer = new Timer(Commons.PERIOD, new GameCycle());
            timer.start();
        }
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
        }
        gamePaused(g2d);
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

    private void gamePaused(Graphics2D g2d) {
        pauseGame.drawPauseScreen(g2d);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);

        }

        @Override
        public void keyPressed(KeyEvent e) {

            paddle.keyPressed(e);
            pauseGame.keyPressed(e);

        }
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private void doGameCycle() {

        if (!pauseGame.getIsPaused()) {
            // Oyun devam ederken normal oyun ekranını çiz
            ball.move();
            paddle.move();
            collisionControl.updateGame();
        }
        repaint();
    }
}
