package screens;

import audio.AudioController;
import ui.Breakout;
import utility.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static utility.Commons.pathOfAudio;
import static utility.Helper.level;


public class WinScreen  extends  JPanel{

    private final AudioController loseSound = new AudioController();
    private final AudioController buttonSound = new AudioController();
    public WinScreen(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));


        // Konum belirlemek için
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(350, 0, 0, 0);
        JLabel exitButtonLabel = getExitButton();
        add(exitButtonLabel,gbc);


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

    public void openWinScreen(boolean inGame){
        // Bitiş ekranını aç
        var parent = (Breakout) SwingUtilities.getWindowAncestor(WinScreen.this);
        loseSound.calAsync(pathOfAudio + "levelPass.wav");
        if(!inGame){
            parent.cardLayout.show(parent.cardPanel, "win_screen");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Arka plan resmini çiz
        ImageIcon background = new ImageIcon("src/resources/images/winningScreenBackground.png");
        Image backgroundImage = background.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
