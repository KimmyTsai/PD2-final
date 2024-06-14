package comsimple;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private List<Clip> clips = new ArrayList<>();

    public void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clips.add(clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusicOnce(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();
            clips.add(clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        for (Clip clip : clips) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        }
        clips.clear();
    }
}
