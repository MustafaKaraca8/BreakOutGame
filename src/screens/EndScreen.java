package screens;

import audio.AudioController;
import ui.Breakout;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static utility.Commons.pathOfAudio;


public class EndScreen  extends  JPanel{

    private final AudioController loseSound = new AudioController();
    private final AudioController buttonSound = new AudioController();
    public EndScreen(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));


        // Konum belirlemek için
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(350, 0, 0, 0); // 100 x üst boşluk
        JLabel restartButtonLabel = getRestartButton();
        add(restartButtonLabel,gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel exitButtonLabel = getExitButton();
        add(exitButtonLabel,gbc);


    }

    private JLabel getRestartButton() {
        ImageIcon startButtonImage = new ImageIcon(Commons.pathOfButton + "restart.png");
        ImageIcon onStartButtonImage = new ImageIcon(Commons.pathOfButton + "onRestart.png");
        JLabel startButtonLabel = new JLabel(startButtonImage);
        startButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSound.calAsync(pathOfAudio + "onButton.wav");
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
                var parent = (Breakout) SwingUtilities.getWindowAncestor(EndScreen.this);
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

    // Çıkış butonun fare dinleyicileri ve resim yükleme
    private JLabel getExitButton() {
        ImageIcon exitButtonImage = new ImageIcon(Commons.pathOfButton + "exitButton.png");
        ImageIcon onExitButtonImage = new ImageIcon(Commons.pathOfButton + "onExitButton.png");
        JLabel exitButtonLabel = new JLabel(exitButtonImage);
        exitButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSound.calAsync(pathOfAudio + "onButton.wav");
                exitButtonLabel.setIcon(onExitButtonImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButtonLabel.setIcon(exitButtonImage);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Oyunu Kapatır
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                exitButtonLabel.setIcon(exitButtonImage);
            }
        });
        return exitButtonLabel;
    }

    public void openEndScreen(boolean inGame){
        // Bitiş ekranını aç
        var parent = (Breakout) SwingUtilities.getWindowAncestor(EndScreen.this);
        loseSound.calAsync(pathOfAudio + "gameOver.wav");
        if(!inGame){
            parent.cardLayout.show(parent.cardPanel, "end_screen");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Arka plan resmini çiz
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        ImageIcon background = new ImageIcon("src/resources/images/loseImage.png");
        Image backgroundImage = background.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
