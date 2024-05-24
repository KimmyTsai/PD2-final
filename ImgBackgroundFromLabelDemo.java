import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Elsa
 */
public class ImgBackgroundFromLabelDemo extends JFrame {
    JPanel contentPanel = null, imagePanel = null;
    JLabel wordLabel = null, bgLabel = null;
    ImageIcon background = null;
    
    public ImgBackgroundFromLabelDemo(){
        // 1.設置frame title及Layout之類型
        this.setTitle("利用JLabel在JFrame中加入背景圖");
        this.setLayout(new BorderLayout());
        
        // 2.設置要顯示之資訊與元件
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);              // 將JPanel設置為具透明化
        wordLabel = new JLabel("Hello World!!");
        wordLabel.setBounds(10, 10, 100, 100);        // 設置位置跟寬高
        contentPanel.add(wordLabel);
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        // 3.於JFrame中設置背景圖片 - 圖片無法縮放大小
        background = new ImageIcon(getClass().getResource("wallpaperflare.com_wallpaper.jpg"));       // 背景圖片
        bgLabel = new JLabel(background);      // 把背景圖顯示在Label中
        bgLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());    // 把含有背景圖之Label位置設置為圖片剛好填充整個版面
        // 把内容視窗轉為JPanel，否則不能使用setOpaque()來使視窗變成透明
        imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));     // 把背景圖添加到分層窗格的最底層以作為背景
        
        // 4.設置frame之基本設定
        this.setMinimumSize(new java.awt.Dimension(900, 675));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new ImgBackgroundFromLabelDemo();
    }
}