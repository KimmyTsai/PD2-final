import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class windowDemo extends JFrame {
    
    private JLabel imgLabel;
    private JPanel imagePanel;
    private JButton btn1;
    private JButton btn2;
    private ImageIcon homeImg;
    private ImageIcon mapImg;
    
    public windowDemo() {
        init();
    }

    @SuppressWarnings("removal")
    private void init() {
        btn1 = new JButton("How To Play");
        btn2 = new JButton("Start");

        // 主畫面
        homeImg = new ImageIcon("wallpaper.jpg");

        imgLabel = new JLabel();
        imgLabel.setIcon(scaleImageIcon(homeImg, getScreenWidth(), getScreenHeight()));

        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE)); //最底層
        imgLabel.setBounds(0, 0, getScreenWidth(), getScreenHeight());
 
        Container contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false); 

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
        
        // 關卡選擇頁
        this.getLayeredPane().add(imagePanel, new Integer(Integer.MIN_VALUE + 1));
        imagePanel.setBounds(100, 100, popupImg.getIconWidth(), popupImg.getIconHeight());
        
        btn1.addActionListener(e -> imagePanel.setVisible(true));

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levelChoose();
        }});
        
        this.setTitle("FINAL");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // 設置全屏
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            System.err.println("Full screen not supported");
            this.setSize(1920, 1080); //視窗大小，不能全屏就1920*1080
        }
    }
    
    private void levelChoose() { //關卡選擇頁面
        // 隱藏按鈕
        btn1.setVisible(false);
        btn2.setVisible(false);
        
        // 隱藏首頁
        if (imagePanel.isVisible()) {
            imagePanel.setVisible(false);
        }

        Timer timer = new Timer(50, null);
        timer.addActionListener(new ActionListener() {
            private float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                mapImg = new ImageIcon("new_wallpaper.jpg");
                imgLabel.setIcon(scaleImageIcon(mapImg, getScreenWidth(), getScreenHeight()));
                imgLabel.repaint();
                timer.stop();
                addCustomIcons(); //關卡圖示
            }
        });
        timer.start();
    }

    @SuppressWarnings("removal")
    private void addCustomIcons() {
        // 第一關
        JLabel iconLabel1 = new JLabel(new ImageIcon("icon1.png"));
        iconLabel1.setBounds(450, 200, 64, 64);
        iconLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Icon 1 clicked!");
            }
        });
        this.getLayeredPane().add(iconLabel1, new Integer(Integer.MIN_VALUE + 2));

        // 第二關
        JLabel iconLabel2 = new JLabel(new ImageIcon("icon1.png"));
        iconLabel2.setBounds(600, 500, 64, 64);
        iconLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Icon 2 clicked!");
            }
        });
        this.getLayeredPane().add(iconLabel2, new Integer(Integer.MIN_VALUE + 2));

        // 回血點
        JLabel iconLabel3 = new JLabel(new ImageIcon("icon2.png"));
        iconLabel3.setBounds(800, 300, 64, 64);
        iconLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Icon 3 clicked!");
            }
        });
        this.getLayeredPane().add(iconLabel3, new Integer(Integer.MIN_VALUE + 2));

        // 王關
        JLabel iconLabel4 = new JLabel(new ImageIcon("icon3.png"));
        iconLabel4.setBounds(1000, 400, 100, 100);
        iconLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Icon 4 clicked!");
            }
        });
        this.getLayeredPane().add(iconLabel4, new Integer(Integer.MIN_VALUE + 2));
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
