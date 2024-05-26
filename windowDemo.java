import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class windowDemo extends JFrame {

    private JLabel imgLabel;
    private JPanel imagePanel;
    private JButton btn1;
    private JButton btn2;
    private ImageIcon img;
    private ImageIcon newImg;
    
    public windowDemo() {
        init();
    }

    @SuppressWarnings("removal")
    private void init() {
        btn1 = new JButton("How To Play");
        btn2 = new JButton("Start");

        img = new ImageIcon("wallpaper.jpg");
        
        // 將背景放在標籤裡
        imgLabel = new JLabel();
        imgLabel.setIcon(scaleImageIcon(img, getScreenWidth(), getScreenHeight()));
        
        // 將背景標籤新增到JFrame的LayeredPane面板裡。
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));

        // 背景標籤位置
        imgLabel.setBounds(0, 0, getScreenWidth(), getScreenHeight());
        
        // 將內容面板設為透明。將LayeredPane面板中的背景顯示出來。
        Container contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false); 
        
        // 按鈕
        contain.setLayout(new FlowLayout());
        contain.add(btn1);
        contain.add(btn2);
        
        imagePanel = new JPanel(new BorderLayout());
        ImageIcon popupImg = new ImageIcon("test.png");
        JLabel popupLabel = new JLabel(popupImg);
        JButton closeButton = new JButton("Close");
        
        closeButton.addActionListener(e -> imagePanel.setVisible(false));

        imagePanel.add(popupLabel, BorderLayout.CENTER);
        imagePanel.add(closeButton, BorderLayout.SOUTH);
        imagePanel.setVisible(false);

        this.getLayeredPane().add(imagePanel, new Integer(Integer.MIN_VALUE + 1));
        imagePanel.setBounds(100, 100, popupImg.getIconWidth(), popupImg.getIconHeight());
        
        btn1.addActionListener(e -> imagePanel.setVisible(true));

        // Start
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 隱藏按鈕
                btn1.setVisible(false);
                btn2.setVisible(false);
                
                // 隱藏說明
                if (imagePanel.isVisible()) {
                    imagePanel.setVisible(false);
                }
                
                // 變暗並換圖片
                Timer timer = new Timer(50, null);
                timer.addActionListener(new ActionListener() {
                    private float opacity = 1.0f;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        opacity -= 0.05f;
                        newImg = new ImageIcon("new_wallpaper.jpg");
                        imgLabel.setIcon(scaleImageIcon(newImg, getScreenWidth(), getScreenHeight()));
                        imgLabel.repaint();
                        timer.stop();
                    }
                });
                timer.start();
            }
        });
        
        this.setTitle("FINAL");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // 全螢幕
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            System.err.println("Full screen not supported");
            this.setSize(1920, 1080); 
        }
    }

    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new windowDemo());
    }
}
