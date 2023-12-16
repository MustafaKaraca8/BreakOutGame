import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class StartScreen extends JPanel {
    public StartScreen() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        // Konum belirlemek için
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-350, 0, 0, 50); // 100 x üst boşluk

        // Load and set the image
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/images/BreakOut.png"));
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, gbc);

        // Adjust the grid bag constraints for the button
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0); // 20 dp üst boşluk

        JButton startButton = new JButton("Başlat");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var parent = (Breakout) SwingUtilities.getWindowAncestor(StartScreen.this);
                parent.cardLayout.show(parent.cardPanel, "game_screen");
                parent.gameScreen.startGame();
                parent.gameScreen.requestFocusInWindow();
            }
        });

        add(startButton, gbc);
    }

    // Yolunu girdiğimiz fonta ulaşılmaz ise Font olarak Arial kullan
    private Font loadPixelFont() {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/fonts/PressStart2P.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Hata durumunda varsayılan fontu kullanabilirsiniz
            return new Font("Arial", Font.PLAIN, 24);
        }
    }
}