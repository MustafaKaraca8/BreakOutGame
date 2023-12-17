package utility;

import java.util.Random;

public class Helper {

    public static int generateRandomNumber() {
        // Random sınıfını kullanarak rastgele bir sayı üretme
        Random random = new Random();
        // -1 veya 1 arasında rastgele bir sayı üretme
        int randomNumber = random.nextInt(2) * 2 - 1;
        return randomNumber;
    }

    // Diğer yardımcı fonksiyonları da buraya ekleyebilirsiniz.

}