package levels;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface Level {
    void startLevel();
    void initializeLevel();
    void drawObjects(Graphics2D g2d);
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
    void updateGame();

}