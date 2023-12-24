package audio;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class AudioController {

    private Clip clip;
    private SwingWorker<Void, Void> worker;

    public void calAsync(String path) {
        if (worker != null && !worker.isDone()) {
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
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                if (clip != null) {
                    clip.start();
                }
            }
        };

        worker.execute();
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }

        if (worker != null) {
            worker.cancel(true);
        }
    }
}
