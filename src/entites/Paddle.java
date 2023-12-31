package entites;

import utility.Commons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Paddle extends Sprite {

    private double dx;
    private int dy;
    private int speed = 5;
    boolean extraShut;
    boolean canSpinRight;
    boolean canSpinLeft;
    Timer timer;

    public Paddle() {
        initPaddle();
    }

    private void initPaddle() {
        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {
        var ii = new ImageIcon("src/resources/images/paddle03.png");
        image = ii.getImage();
    }

    public void move() {
        x += dx * speed;
        y = Commons.INIT_PADDLE_Y + dy;
        if (x <= 0) {
            x = 0;
        }
        if (x >= Commons.WIDTH - imageWidth) {
            x = Commons.WIDTH - imageWidth;
        }
    }

    // Basıldığında
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            if (canSpinLeft) {
                timer = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dx = -2.3;
                        ((Timer) e.getSource()).stop();
                    }
                });
                canSpinLeft = false;
                timer.start();
            }
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            if (canSpinRight) {
                timer = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dx = 2.3;
                        ((Timer) e.getSource()).stop();
                    }
                });
                canSpinRight = false;
                timer.start();
            }
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 8;
        }
    }

    // Basılan tuş bırakıldığında
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            spinHorizontalLeft();
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            spinHorizontalRight();
            dx = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            ExtraShut();

            dy = 0;
        }
    }

    private void resetState() {
        x = Commons.INIT_PADDLE_X;
        y = Commons.INIT_PADDLE_Y;
    }

    private void ExtraShut() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extraShut = false;
                ((Timer) e.getSource()).stop();
            }
        });

        extraShut = true;
        timer.start();
    }

    private void spinHorizontalLeft() {
        timer = new Timer(75, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canSpinLeft = false;
                ((Timer) e.getSource()).stop();
            }
        });

        canSpinLeft = true;
        timer.start();
    }

    private void spinHorizontalRight() {
        timer = new Timer(75, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canSpinRight = false;
                ((Timer) e.getSource()).stop();
            }
        });

        canSpinRight = true;
        timer.start();
    }

    public boolean getExtraShut() {
        return extraShut;
    }
}
