package screens;

import audio.AudioController;
import ui.Breakout;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static utility.Commons.pathOfAudio;

public class WinScreen extends JPanel {

    private Timer timer;
    private ImageIcon currentBackground;
    private int currentBackgroundIndex = 0;
    private ImageIcon[] backgrounds = {
            new ImageIcon("src/resources/images/winningScreenBackground.png"),
            new ImageIcon("src/resources/images/breakoutGame.png"),
            // Diğer görselleri buraya ekleyebilirsiniz
    };

    GridBagConstraints gbc;
    JLabel exitButtonLabel;

    private boolean isCanVisible = false;
    private final AudioController winScreen = new AudioController();
    private final AudioController buttonSound = new AudioController();

    public WinScreen() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));

    }

    // Timer'ı başlatan metod
    public void startTimer() {
        // Timer oluştur ve her 10 saniyede bir actionPerformed metodu çağrılsın
        timer = new Timer(15200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // İndexi bir arttırır
                currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;

                // Image'i ekrana verir
                currentBackground = backgrounds[currentBackgroundIndex];

                // Panelin yeniden çizilmesini sağla
                isCanVisible = true;
                repaint();
                timer.stop();
            }
        });
        timer.start(); // Timer'ı başlat
        System.out.println("başladı");
    }


    // Timer'ı durduran metod
    private void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    // Çıkış butonun fare dinleyicileri ve resim yükleme
    private JLabel getExitButton() {
        ImageIcon exitButtonImage = new ImageIcon(Commons.pathOfButton + "exitButton.png");
        ImageIcon onExitButtonImage = new ImageIcon(Commons.pathOfButton + "onExitButton.png");
        JLabel exitButtonLabel = new JLabel(exitButtonImage);
        exitButtonLabel.setVisible(false);
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
                // Timer'ı durdur ve oyunu kapat
                stopTimer();
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                exitButtonLabel.setIcon(exitButtonImage);
            }
        });
        return exitButtonLabel;
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

        // İlk arka plan görselini ayarla
        currentBackground = backgrounds[currentBackgroundIndex];
        Image backgroundImage = currentBackground.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (isCanVisible) {
            if (exitButtonLabel == null) {
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(650, 0, 0, 0);
                exitButtonLabel = getExitButton();
                add(exitButtonLabel, gbc);
            }
            exitButtonLabel.setVisible(true);
        }
    }

    public void openWinScreen(boolean inGame) {
        // Bitiş ekranını aç
        var parent = (Breakout) SwingUtilities.getWindowAncestor(WinScreen.this);
        winScreen.calAsync(pathOfAudio + "winScreenMusic.wav");
        if (!inGame) {
            parent.cardLayout.show(parent.cardPanel, "win_screen");
        }
    }
}