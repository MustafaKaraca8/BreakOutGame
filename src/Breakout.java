import javax.swing.*;
import java.awt.*;


public class Breakout extends JFrame {
    public JPanel cardPanel;
    public CardLayout cardLayout;
    private StartScreen startScreen;
    public GameScreen gameScreen;

    public EndScreen endScreen;

    public LevelScreen levelScreen;

    public Breakout() {
        initUI();
    }

    private void initUI() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        startScreen = new StartScreen();
        gameScreen = new GameScreen();
        endScreen = new EndScreen();
        levelScreen = new LevelScreen();

        cardPanel.add(startScreen, "start_screen");
        cardPanel.add(gameScreen, "game_screen");
        cardPanel.add(endScreen, "end_screen");
        cardPanel.add(levelScreen, "level_screen");

        add(cardPanel);

        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setLocationRelativeTo(null);

        startGame();
    }

    public void startGame() {
        startScreen.showStartScreen();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new Breakout();
            game.setVisible(true);
        });
    }
}