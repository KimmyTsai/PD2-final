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
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // 开始播放并循环
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clips.add(clip);
            System.out.println("Background music started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusicOnce(String filePath) {
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
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // 开始播放
            clip.start();
            clips.add(clip);
            System.out.println("Music started successfully.");
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
