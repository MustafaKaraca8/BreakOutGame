package utility;

import java.util.Random;

public class Helper {

    // Random sınıfını kullanarak rastgele bir sayı üretme
    static Random random = new Random();

    public static int generateRandomDir() {

        // -1 veya 1 arasında rastgele bir sayı üretme
        return random.nextInt(2) * 2 - 1;
    }
    public static int generateRandomValue(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid range");
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static int level = 5;

}