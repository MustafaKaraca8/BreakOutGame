import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class StartScreen extends JPanel {
    public StartScreen() {
        //Ekranda ki Imageların yerini değiştirmek için
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        setBackground(Color.BLACK);

        // Konum belirlemek için
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-350, 0, 0, 50); // 100 x üst boşluk

        // Load and set the image
        ImageIcon imageIcon = new ImageIcon("src/resources/images/BreakOut.png");
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, gbc);

        // Adjust the grid bag constraints for the button
        gbc.gridy = 1;
        gbc.insets = new Insets(-250, 0, 0, 0); // 20 dp üst boşluk

        JLabel startButtonLabel = getStartButton();
        add(startButtonLabel, gbc);

    }

    private JLabel getStartButton() {
        ImageIcon startButtonImage = new ImageIcon("src/resources/images/startButton.png");
        ImageIcon onStartButtonImage = new ImageIcon("src/resources/images/onButton.png");
        JLabel startButtonLabel = new JLabel(startButtonImage);
        startButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButtonLabel.setIcon(onStartButtonImage); // Fare içeri girdiğinde hover görüntüsünü ayarla
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButtonLabel.setIcon(startButtonImage); // Fare çıkış yaptığında normal görüntüyü ayarla
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                var parent = (Breakout) SwingUtilities.getWindowAncestor(StartScreen.this);
                parent.cardLayout.show(parent.cardPanel, "game_screen");
                parent.gameScreen.startGame();
                parent.gameScreen.requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startButtonLabel.setIcon(startButtonImage); // Fare çıkış yaptığında normal görüntüyü ayarla
            }
        });
        return startButtonLabel;
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