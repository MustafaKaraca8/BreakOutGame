
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.InputStream;

public class StartScreen extends JPanel {
    public StartScreen() {
        setLayout(new GridBagLayout());

        // Konum belirlemek için
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-400, 0, 0, 0); // 100 x üst boşluk

        // Press Start 2P fontunu yükle
        Font pixelFont = loadPixelFont();

        // Text oluşturmak için
        JLabel welcomeLabel = new JLabel("Welcome to Breakout");
        welcomeLabel.setFont(pixelFont);

        add(welcomeLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(50, 0, 0, 0); // 50 dp üst boşluk

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

    // Yolunu girdğimiz fonta ulaşılmaz ise Font olarak Arial kullan
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