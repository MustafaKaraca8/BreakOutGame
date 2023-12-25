package entites;

import utility.Commons;

import javax.swing.ImageIcon;

public class Ball extends Sprite {

    private int xdir;
    private int ydir;
    private int damage;
    private int speed = 5;
    public Ball() {

        initBall();
    }

    private void initBall() {

        xdir = 1;
        ydir = -1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("src/resources/images/ball.png");
        image = ii.getImage();
    }

    public void move() {
        x += xdir * speed;
        y += ydir * speed;

        if (x <= 0 || x >= Commons.WIDTH - imageWidth) {
            setXDir(xdir * -1);
        }

        if (y <= 0) {
            setYDir(1);
        }
    }


    private void resetState() {

        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    public void setXDir(int x) {

        xdir = x;
    }

    public void setYDir(int y) {

        ydir = y;
    }

    public int getYDir() {

        return ydir;
    }

    public int getXdir(){
        return xdir;
    }
    public void setDamage(int dmg){
        this.damage = dmg;
    }

    public int getDamage(){
        return damage;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

}
