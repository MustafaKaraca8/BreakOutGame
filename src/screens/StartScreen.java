package screens;

import audio.AudioController;
import ui.Breakout;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static utility.Commons.pathOfAudio;
import static utility.Helper.where;


public class StartScreen extends JPanel {

    // Aşağıda ki gibi iki farklı örnek oluşturmazsak sesler asla durmuyor
    private final AudioController backgroundMusic = new AudioController();
    private final AudioController buttonSound = new AudioController();
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
        JLabel infoButtonLabel = getInfoButton();
        add(infoButtonLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel exitButtonLabel = getExitButton();
        add(exitButtonLabel, gbc);

        backgroundMusic.calAsync("src/resources/audio/background_music.wav");
    }

    // Başlatma butonun fare dinleyicileri ve resim yükleme
    private JLabel getStartButton() {
        ImageIcon startButtonImage = new ImageIcon(Commons.pathOfButton + "startButton.png");
        ImageIcon onStartButtonImage = new ImageIcon(Commons.pathOfButton + "onStartButton.png");
        JLabel startButtonLabel = new JLabel(startButtonImage);
        startButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSound.calAsync(pathOfAudio + "onButton.wav");
                startButtonLabel.setIcon(onStartButtonImage);
                where = 1;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButtonLabel.setIcon(startButtonImage);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                backgroundMusic.stop();
                var parent = (Breakout) SwingUtilities.getWindowAncestor(StartScreen.this);
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

    private JLabel getInfoButton(){
        ImageIcon infoButtonImage = new ImageIcon(Commons.pathOfButton + "controller.png");
        ImageIcon oninfoButtonImage = new ImageIcon(Commons.pathOfButton + "onController.png");
        JLabel infoButtonLabel = new JLabel(infoButtonImage);
        infoButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSound.calAsync(pathOfAudio + "onButton.wav");
                infoButtonLabel.setIcon(oninfoButtonImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                infoButtonLabel.setIcon(infoButtonImage);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                var parent = (Breakout) SwingUtilities.getWindowAncestor(StartScreen.this);
                parent.cardLayout.show(parent.cardPanel, "info_screen");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                infoButtonLabel.setIcon(infoButtonImage);
            }
        });
        return infoButtonLabel;

    }
    public void showStartScreen() {
        var parent = (Breakout) SwingUtilities.getWindowAncestor(StartScreen.this);
        parent.cardLayout.show(parent.cardPanel, "start_screen");
        parent.gameScreen.stopGameScreen();
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
