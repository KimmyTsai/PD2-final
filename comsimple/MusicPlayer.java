package comsimple;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicPlayer {
    private Clip clip;

    public void playBackgroundMusic(String resourcePath) {
        try {
            // 從資源加載音頻文件
            InputStream audioSrc = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                System.err.println("Could not find the resource: " + resourcePath);
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            // 獲取音頻剪輯並打開
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // 開始播放並循環
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
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
