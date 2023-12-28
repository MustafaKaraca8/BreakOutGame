package utility;

public final class Commons {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 750;
    public static final int BOTTOM_EDGE = 760;
    // BUrayı değiştri amcık samet her levele özel brick sayısı olacak
    // İlk başa 0 koyma sebebimiz 0. level olmaması
    public static final int[] N_OF_BRICKS_PER_LEVEL = {0, 20, 20, 12, 8 , 8};
    public static final int INIT_PADDLE_X = 600;
    public static final int INIT_PADDLE_Y = 720;
    public static final int INIT_BALL_X = 600;
    public static final int INIT_BALL_Y = 670;
    public static final int PERIOD = 10;

    public static final String pathOfButton = "src/resources/images/buttons/";
    public static final String pathOfBricks = "src/resources/images/bricks/";
    public static final String pathOfAudio = "src/resources/audio/";

    private Commons() {
        // Bu sınıfın örneğini oluşturmayı engellemek için private bir kurucu metod
    }
}