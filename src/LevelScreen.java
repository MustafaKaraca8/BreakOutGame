import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelScreen extends JPanel {

    public LevelScreen() {
        System.out.println("Level Screen Çalıştı");
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 100, 0); // 100 x üst boşluk


        // Oyun İsmi İçin Konum Ayarlaması
        ImageIcon imageIcon = new ImageIcon("src/resources/images/level2.png");
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, gbc);


        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel restartButtonLabel = getRestartButton();
        add(restartButtonLabel, gbc);


    }

    private JLabel getRestartButton() {
        ImageIcon startButtonImage = new ImageIcon(Commons.pathOfButton + "levelStart.png");
        ImageIcon onStartButtonImage = new ImageIcon(Commons.pathOfButton + "onLevelStart.png");
        JLabel startButtonLabel = new JLabel(startButtonImage);
        startButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                startButtonLabel.setIcon(onStartButtonImage);
                // Restart butonun üzerine gelince diğer resim öğeleri haraket ediyordu onu düzeltmek için
                // set preferred size kullanıldı
                startButtonLabel.setPreferredSize(new Dimension(startButtonImage.getIconWidth(), startButtonImage.getIconHeight() + 10));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButtonLabel.setIcon(startButtonImage);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                var parent = (Breakout) SwingUtilities.getWindowAncestor(LevelScreen.this);
                parent.cardLayout.show(parent.cardPanel, "game_screen");
                parent.gameScreen.startGame();
                parent.gameScreen.startTimer();
                parent.gameScreen.requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startButtonLabel.setIcon(startButtonImage);
            }
        });
        return startButtonLabel;
    }

    public void openLevelScreen() {
        // Bitiş ekranını aç
        var parent = (Breakout) SwingUtilities.getWindowAncestor(LevelScreen.this);
        parent.cardLayout.show(parent.cardPanel, "level_screen");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Arka plan resmini çiz
        ImageIcon background = new ImageIcon("src/resources/images/background.png");
        Image backgroundImage = background.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}