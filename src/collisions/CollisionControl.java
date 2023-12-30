package collisions;

import audio.AudioController;
import entites.Ball;
import entites.Brick;
import entites.Paddle;
import ui.Breakout;
import utility.Commons;

import javax.swing.*;
import java.awt.*;

import static utility.Commons.pathOfAudio;
import static utility.Helper.generateRandomDir;
import static utility.Helper.level;

public class CollisionControl {

    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    // Oyun durumu kontrolü için Game Screenden geliyor anlatırken Game Screene gidip göster
    // timer ve component'da aynı şekilde Game Screenden geliyor
    // Component'ı burada pencere atamsı yapmak için kullanıyoruz
    private boolean inGame;
    Timer timer;
    Component comp;
    private boolean alreadyHitPaddle = false;
    private final AudioController hittingTheBrick = new AudioController();
    private final AudioController hittingThePaddle = new AudioController();

    public CollisionControl(Ball ball, Paddle paddle, Brick[] bricks, boolean inGame, Timer timer, Component comp) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.inGame = inGame;
        this.timer = timer;
        this.comp = comp;
    }

    public void updateGame() {
        checkPaddleCollision();
        checkBrickCollision();
        checkGameSituation();
    }

    // Topun tokaçla ilişkisi
    private void checkPaddleCollision() {
        // Eğer ki paddle ile top ilişkiye girmemişse ve iki nesne bir birine değişmişse ses dosyasını çal
        // handleBallCollision() metodunu çağır ve alreadyHitPaddle true yap kontrol güvencesini arttırmak için
        if (!alreadyHitPaddle && (ball.getRect()).intersects(paddle.getRect())) {
            hittingThePaddle.calAsync(Commons.pathOfAudio + "hittingSound.wav");
            handleBallCollision();
            alreadyHitPaddle = true;
        } else {
            alreadyHitPaddle = false;
        }
    }

    // Gerekli yönelme işlemlerini yap ve alreadyHitPaddle değişkenini yeniden false yap
    // alreadyHitPaddle bunun amacı iki kere çarpmayı engellemek çarptın true oldu top yön değişti false oldu
    // çarptığın da true olduğundan yularıda ki ilk if kontrolüne giremio ve etkileşime geçemiyorlar
    private void handleBallCollision() {
        int newDirectionX = determineNewDirectionX();
        int newDirectionY = -1;

        ball.setXDir(newDirectionX);
        ball.setYDir(newDirectionY);

        if (paddle.getExtraShut()) {
            ball.setXDir(0);
            ball.setYDir(-2);
        }
        alreadyHitPaddle = false;
    }

    // Topun geldiği yöne gör gideceği yönü ayarlıyoz sağa gidiosa sağa gitmeye devam edicek aynı şekilde sol için de geçerli
    // generateRandomDir() bu ise güçlü vuruş yaptıktan sonra eğer ki top paddle'a dik gelirse rastgele bir yönelim belirle demek. sağ ya da sol
    // Bunu yapmazsan sadece yukarı doğru gider top
    private int determineNewDirectionX() {
        int currentDirectionX = ball.getXdir();

        if (currentDirectionX == -1) {
            return -1;
        } else if (currentDirectionX == 1) {
            return 1;
        } else {
            return generateRandomDir();
        }
    }


    // Topun tuğlayla ilişkisi
    //Commons.N_OF_BRICKS_PER_LEVEL[level] bunu açıkla burada Commons sınıfına git ve tuğlaların miktarını levellere göre belirliyor de
    private void checkBrickCollision() {

        for (int i = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            // Top ve Tuğla etkileşime geçtiğinde iilk olarak topun konumunu hesaplamamız gerekiyor o yüzden aşşağıda
            // ball left zart zurt width height değerlirini alıyoruz
            // daha sonra Point() sınıfı ile belirlediğimiz konumları işaretlioz sağ konumu sol konumu üst alt konumu buranın olayı o
            // Paint açıp anlat burayı sana attığım videolarda var
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballTop = (int) ball.getRect().getMinY();
                int ballRight = (int) ball.getRect().getMaxX();
                int ballBottom = (int) ball.getRect().getMaxY();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballHeight = (int) ball.getRect().getHeight();

                var pointRight = new Point(ballRight + 1, ballTop + ballHeight / 2);
                var pointLeft = new Point(ballLeft + 1, ballTop + ballHeight / 2);
                var pointTop = new Point(ballLeft + ballWidth / 2, ballBottom - 1);
                var pointBottom = new Point(ballLeft + ballWidth / 2, ballTop + 1);

                // Eğer ki etkileşime geçtiği tuğla yok edilmemişse yönelimleri ayarla demek
                if (!bricks[i].isDestroyed()) {
                    // Çarpışmanın işlenip işlenmeyeceğini kontrol etmek için kullanıoz paddle ile aynı mantık
                    boolean handleCollision = false;

                    // Üstten alta hangi Point() noktayla işte etkileşime geçtiğini ve hangi yönelimle gelidğini kontrol ediyor bura
                    // eğer ki sen  ball.getXdir() == -1 buraada ki -1'i 1 yaparsan yunda farklılığı görürsün
                    if (bricks[i].getRect().contains(pointLeft) && ball.getXdir() == -1) {
                        ball.setXDir(1);
                        handleCollision = true;
                    }

                    if (bricks[i].getRect().contains(pointRight) && ball.getXdir() == 1) {
                        ball.setXDir(-1);
                        handleCollision = true;
                    }

                    if (bricks[i].getRect().contains(pointBottom) && ball.getYDir() == -1) {
                        ball.setYDir(1);
                        handleCollision = true;
                    }

                    if (bricks[i].getRect().contains(pointTop) && ball.getYDir() == 1) {
                        ball.setYDir(-1);
                        handleCollision = true;
                    }

                    // Burasıysa biz güçlü vuruş yaptıktan sonra x yönelimi 0 oluyor
                    // Tuğlanın altına xDir = 0 ile çarparsan rastgele bir dir veriyor sana burası rastgele vermezsen
                    // Tümdüz yoluna devam eder
                    if (bricks[i].getRect().contains(pointBottom) && ball.getXdir() == 0) {
                        ball.setXDir(generateRandomDir());
                        handleCollision = true;
                    }

                    if (bricks[i].getRect().contains(pointTop) && ball.getXdir() == 0) {
                        ball.setXDir(generateRandomDir());
                        handleCollision = true;
                    }
                    //System.out.println("Collision at " + i + ": " + handleCollision);

                    // Eğer sol sağ alt üsttle etkileşime girilmişse burada ki kontrol bloğu çalışacak
                    // İlk olarak tuğlaya hasar veriliyor sonra ise ses çalışıyor
                    // ALtında ki kontroller ise çapraz çarpıp çarpmadığını kontrol ediyor eğer ki
                    // Hem alta hem sağa çarpmışsa mesela geldiği yöne gidecek o kontroller var
                    if (handleCollision) {
                        bricks[i].setHealth(ball.getDamage());
                        hittingTheBrick.calAsync(pathOfAudio + "hittingSound.wav");

                        if (bricks[i].getRect().contains(pointTop) && bricks[i].getRect().contains(pointRight)) {
                            ball.setXDir(-1);
                            ball.setYDir(+1);
                        } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointTop)) {
                            ball.setXDir(+1);
                            ball.setYDir(+1);
                        } else if (bricks[i].getRect().contains(pointLeft) && bricks[i].getRect().contains(pointBottom)) {
                            ball.setXDir(1);
                            ball.setYDir(-1);
                        } else if (bricks[i].getRect().contains(pointRight) && bricks[i].getRect().contains(pointBottom)) {
                            ball.setXDir(-1);
                            ball.setYDir(-1);
                        }
                        // En son ise tüm çarpışmlar işlendikten sonra tuğlanın canı 0'a eşit ya da altındaysa yok et
                        if (bricks[i].getHealth() <= 0) {
                            bricks[i].setDestroyed(true);
                        }
                    }
                }
            }
        }
    }

    // Burası oyun durumunu kontrol ediyor
    private void checkGameSituation() {
        // Eğer top altta ki sınıra gelmişse endGame'i çalıştır
        // Hepsinde timer!= null var bu da bug olmaması için bir kontrol
        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {
            if (timer != null) endGame();
        }

        // Burası tüm tuğlalara bakıyor eğer ki bir tuğla tokaçla etkileşime girmişse yani tuğla tokaça çarparsa
        // endGame() çalışacak ancak yok edilmemiş bir tuğla burası önemli !bricks[i].isDestroyed()
        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS_PER_LEVEL[level]; i++) {
            if (bricks[i].getRect().intersects(paddle.getRect()) && !bricks[i].isDestroyed()) {
                endGame();
            }
            // Her tuğla yok edildiğinde j yi arttırır
            if (bricks[i].isDestroyed()) {
                j++;
            }

            // Eğer ki j eşitlenmişse mons.N_OF_BRICKS_PER_LEVEL[level] burada ki belirlene tuğla sayısına level biter
            // level değeri bir artar eğer level 6 olmuşsa oyun biter
            if (j == Commons.N_OF_BRICKS_PER_LEVEL[level]) {
                level += 1;
                if (level == 6) winScreen();
                else levelScreen();
            }
        }
    }


    private void endGame() {
        //SwingUtilities.getWindowAncestor() bu metod bizden comp istiyor o yüzden comp veriyoruz. yukarıda ki sebepten
        // burada ki ebeveyn bizim breakout sınıfında ki tanımlı nesnelere ulaşmamızı sağlıyor ancak bir de hangi
        // sınıftan geldiğimizi soruyor ona compu verioz yani Game Screen
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        inGame = false;
        if (timer != null) timer.stop();
        // ebeveyen'de bulunan endScreen'in openEndScreen metodunu çağırıypor aşşağıdakilerde buna benzer
        parent.endScreen.openEndScreen(inGame);
    }

    private void levelScreen() {
        System.out.println("Level çalıştı: " + level);
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        if (timer != null) timer.stop();
        parent.levelScreen.openLevelScreen();
    }

    private void winScreen() {
        var parent = (Breakout) SwingUtilities.getWindowAncestor(comp);
        inGame = false;
        if (timer != null) timer.stop();
        parent.winScreen.openWinScreen(inGame);
        parent.winScreen.startTimer();
    }
}