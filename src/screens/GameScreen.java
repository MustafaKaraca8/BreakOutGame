package screens;
import levels.*;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static utility.Helper.level;


public class GameScreen extends JPanel {

    private Timer timer;
    private LevelOne levelOne;
    private LevelTwo levelTwo;
    private LevelThree levelThree;
    private LevelFour levelFour;
    private LevelFife levelFife;
    private Level currentLevel;
    private boolean inGame ;
    private PauseGame pauseGame;



    public GameScreen() {
        startGame();
    }

    public void startGame() {
        inGame = true;
        initBoard();

    }

    public void initBoard() {
        setBackground(new Color(40, 40, 40));
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        gameInit();
    }

    private void gameInit() {

        // Timer daha önce çalışmamışsa başlat
        if (timer == null) {
            timer = new Timer(Commons.PERIOD, new GameCycle());
            startTimer();
        }

        if(level == 1) levelOne = new LevelOne(timer,GameScreen.this);
        else if(level == 2) levelTwo = new LevelTwo(timer , GameScreen.this);
        else if(level == 3) levelThree = new LevelThree(timer , GameScreen.this);
        else if(level == 4) levelFour = new LevelFour(timer , GameScreen.this);
        else if(level == 5) levelFife = new LevelFife(timer , GameScreen.this);
        pauseGame = new PauseGame(GameScreen.this);
        currentLevel = levelOne;
    }

    public void startTimer(){
        if(inGame) timer.start();

    }

    public void stopGameScreen(){
        timer.stop();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        ImageIcon background = new ImageIcon("src/resources/images/gameBG.png");
        Image backgroundImage = background.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (inGame) {
            if(level == 1) currentLevel.drawObjects(g2d);
            else if (level == 2) {
                currentLevel = levelTwo;
                currentLevel.drawObjects(g2d);
            } else if (level == 3) {
                currentLevel = levelThree;
                currentLevel.drawObjects(g2d);
            } else if (level == 4) {
                currentLevel = levelFour;
                currentLevel.drawObjects(g2d);
            }else if (level == 5){
                currentLevel = levelFife;
                currentLevel.drawObjects(g2d);
            }
        }

        gamePaused(g2d);
        Toolkit.getDefaultToolkit().sync();
    }

    private void gamePaused(Graphics2D g2d) {
        pauseGame.drawPauseScreen(g2d);

    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            currentLevel.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            currentLevel.keyPressed(e);
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
            currentLevel.updateGame();

        }
        repaint();
    }

}

