import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

public class PauseGame {

    private boolean isPaused = false;
    Component comp;
    public PauseGame(Component comp) {
        this.comp = comp;
    }

    void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE)
             isPaused = true;
        if (key == KeyEvent.VK_SPACE)
            isPaused = false;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public void drawPauseScreen(Graphics2D g2d) {
        if (isPaused) {
            // Karartma efekti
            g2d.setColor(new Color(0, 0, 0, 52));
            g2d.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
            // Ekranı yenile
            comp.repaint();

            String continueText = "Oyun Durduruldu";
            String pressForContinueText = "Devam etmek için space basın";

            int continueTextWidth = g2d.getFontMetrics().stringWidth(continueText) + 420;
            int pressForContinueTextWidth = g2d.getFontMetrics().stringWidth(pressForContinueText) + 100;

            int xContinue = (Commons.WIDTH - continueTextWidth) / 2;
            int xPresSpace = (Commons.WIDTH - pressForContinueTextWidth) / 2;

            int y = Commons.HEIGHT / 2 ;

            g2d.setColor(Color.BLACK);
            g2d.setFont(loadPixelFont(35f));
            g2d.drawString(continueText, xContinue, y);
            g2d.setFont(loadPixelFont(10f));
            g2d.drawString(pressForContinueText , xPresSpace,y + 60);
        }
    }


    // Yolunu girdiğimiz fonta ulaşılamaz ise Font olarak Arial kullan
    private Font loadPixelFont(float fontSize) {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/fonts/PressStart2P.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Hata durumunda varsayılan fontu kullanabilirsiniz
            return new Font("Arial", Font.PLAIN, 24);
        }
    }
}