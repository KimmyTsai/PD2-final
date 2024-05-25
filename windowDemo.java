import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class windowDemo extends JFrame {
    
    public windowDemo() {
        init();
    }

    @SuppressWarnings("removal")
    private void init() {
        
        JButton btn1 = new JButton("How To Play");
        JButton btn2 = new JButton("Start");

        // 要設定的背景圖片
        ImageIcon img = new ImageIcon("wallpaper.jpg");
        
        // 將背景圖放在標籤裡。
        JLabel imgLabel = new JLabel(img);
        
        // 將背景標籤新增到jfram的LayeredPane面板裡。
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));

        // 設定背景標籤的位置
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        
        // 將內容面板設為透明。將LayeredPane面板中的背景顯示出來。
        Container contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false); 
        
        // 按鈕顯示規則
        contain.setLayout(new FlowLayout());
        contain.add(btn1);
        contain.add(btn2);
        
        // 創建圖片面板並初始設置為不可見
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon popupImg = new ImageIcon("test.png");
        JLabel popupLabel = new JLabel(popupImg);
        JButton closeButton = new JButton("Close");
        
        closeButton.addActionListener(e -> imagePanel.setVisible(false));

        imagePanel.add(popupLabel, BorderLayout.CENTER);
        imagePanel.add(closeButton, BorderLayout.SOUTH);
        imagePanel.setVisible(false);
        
        // 將圖片面板添加到視窗中
        this.getLayeredPane().add(imagePanel, new Integer(Integer.MIN_VALUE + 1));
        imagePanel.setBounds(100, 100, popupImg.getIconWidth(), popupImg.getIconHeight());
        
        // 按鈕的點擊事件
        btn1.addActionListener(e -> imagePanel.setVisible(true));
        
        this.setTitle("背景圖設定");
        this.setSize(1920, 1080); // 設定窗體大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new windowDemo();
    }
}
