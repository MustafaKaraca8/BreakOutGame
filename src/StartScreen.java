import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class StartScreen extends JPanel {

    public StartScreen() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));

        // Konum belirlemek için
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 100, 0); // 100 x üst boşluk

        // Oyun İsmi İçin Konum Ayarlaması
        ImageIcon imageIcon = new ImageIcon("src/resources/images/gameName.png");
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, gbc);

        // Başlat Butonu için konum ayarlaması
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel startButtonLabel = getStartButton();
        add(startButtonLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel exitButtonLabel = getExitButton();
        add(exitButtonLabel, gbc);


    }

    // Başlatma butonun fare dinleyicileri ve resim yükleme
    private JLabel getStartButton() {
        ImageIcon startButtonImage = new ImageIcon("src/resources/images/buttons/startButton.png");
        ImageIcon onStartButtonImage = new ImageIcon("src/resources/images/buttons/onStartButton.png");
        JLabel startButtonLabel = new JLabel(startButtonImage);
        startButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButtonLabel.setIcon(onStartButtonImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButtonLabel.setIcon(startButtonImage);
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
                startButtonLabel.setIcon(startButtonImage);
            }
        });
        return startButtonLabel;
    }

    // Çıkış butonun fare dinleyicileri ve resim yükleme
    private JLabel getExitButton() {
        ImageIcon exitButtonImage = new ImageIcon("src/resources/images/buttons/exitButton.png");
        ImageIcon onExitButtonImage = new ImageIcon("src/resources/images/buttons/onExitButton.png");
        JLabel exitButtonLabel = new JLabel(exitButtonImage);
        exitButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButtonLabel.setIcon(onExitButtonImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButtonLabel.setIcon(exitButtonImage);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                exitButtonLabel.setIcon(exitButtonImage);
            }
        });
        return exitButtonLabel;
    }

    // Yolunu girdiğimiz fonta ulaşılamaz ise Font olarak Arial kullan
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Arka plan resmini çiz
        ImageIcon background = new ImageIcon("src/resources/images/background2.png");
        Image backgroundImage = background.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
