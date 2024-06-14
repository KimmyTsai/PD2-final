package comsimple;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

public class MusicPlayer {
    private Clip clip;

    public void playBackgroundMusic(String filePath) {
        try {
            System.out.println("Trying to load file: " + filePath);
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Could not find the file: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Background music started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusicOnce(String filePath) {
        try {
            System.out.println("Trying to load file: " + filePath);
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Could not find the file: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Music started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
