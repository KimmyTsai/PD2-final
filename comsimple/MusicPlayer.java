package comsimple;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

public class MusicPlayer {
    private Clip clip;

    public void playBackgroundMusic(String filePath) {
        try {
            // 打印文件路径
            System.out.println("Trying to load file: " + filePath);
            
            // 从文件加载音频
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Could not find the file: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // 获取音频剪辑并打开
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // 开始播放并循环
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Background music started successfully.");
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
