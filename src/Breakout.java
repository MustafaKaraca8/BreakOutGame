
import javax.swing.*;
import java.awt.*;


public class Breakout extends JFrame {
    public JPanel cardPanel;
    public CardLayout cardLayout;
    private EntryScreen entryScreen;
    public Board gameScreen;

    public Breakout() {
        initUI();
    }

    private void initUI() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        entryScreen = new EntryScreen();
        gameScreen = new Board();

        cardPanel.add(entryScreen, "entry_screen");
        cardPanel.add(gameScreen, "game_screen");

        add(cardPanel);

        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new Breakout();
            game.setVisible(true);
        });
    }
}

