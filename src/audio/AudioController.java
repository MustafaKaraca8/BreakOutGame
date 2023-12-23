package audio;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioController {

    private AudioController instance;
    private Clip clip;
    private SwingWorker<Void, Void> worker;

    public AudioController getInstance() {
        if (instance == null) {
            instance = new AudioController();
        }
        return instance;
    }

    public void calAsync(String path) {
        if (worker != null && !worker.isDone()) {
            // Eğer daha önce başlatılmış bir işlem varsa iptal et
            worker.cancel(true);
        }

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    File sesDosyasi = new File(path);
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sesDosyasi);

                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);

                    clip.start();

                    // Bekleme süresini kaldırabilirsiniz
                    // Thread.sleep(clip.getMicrosecondLength() / 1000);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println("Oluşan Hata: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                // Ses çalma işlemi tamamlandığında yapılacak işlemler
                clip.start();
            }
        };

        worker.execute();
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
        }

        if (worker != null ) {
            worker.cancel(true);
        }
    }
}