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
    private ImageIcon passImg;
    private JLabel iconLabel1;
    private JLabel iconLabel2;
    private JLabel iconLabel3;
    private JLabel iconLabel4;
    private int[] pass =  new int[4];
    
    public windowDemo() {
        init();
    }

    @SuppressWarnings("removal")
    private void init() {

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
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        // 第一關
        
        if(pass[0] != 1){
            iconLabel1 = new JLabel(new ImageIcon("icon1.png"));
            iconLabel1.setBounds(420, 200,200, 200);
        }
        else{
            iconLabel1.setVisible(true);
        }

        iconLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[0] != 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel1.setIcon(new ImageIcon("icon1_new.png"));
                    //關卡內容
                    //
                    pass[0] = 1;
                    levelChoose();
                }
                // else{
                //     JOptionPane.showMessageDialog(null, "Level 1 passed!");
                // }
                
            }
        });
        this.getLayeredPane().add(iconLabel1, new Integer(Integer.MIN_VALUE + 2));

        // 第二關
        if(pass[1] != 1){
            iconLabel2 = new JLabel(new ImageIcon("icon1.png"));
            iconLabel2.setBounds(550, 500, 200, 200);
        }
        else{
            iconLabel2.setVisible(true);
        }
        iconLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[1] != 1 && pass[0] == 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel2.setIcon(new ImageIcon("icon1_new.png"));
                    //關卡內容
                    //
                    pass[1] = 1;
                    levelChoose();
                }
                else if(pass[1] != 1){
                    JOptionPane.showMessageDialog(null, "Please pass the previous level!");
                }

            }
        });
        this.getLayeredPane().add(iconLabel2, new Integer(Integer.MIN_VALUE + 2));

        // 回血點
        if(pass[2] != 1){
            iconLabel3 = new JLabel(new ImageIcon("icon2.png"));
            iconLabel3.setBounds(720, 300, 200, 200);
        }
        else {
            iconLabel3.setVisible(true);
        }
        iconLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[2] != 1 && pass[0] == 1 && pass[1] == 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel3.setIcon(new ImageIcon("icon2_new.png"));
                    //關卡內容
                    //
                    pass[2] = 1;
                    levelChoose();
                }
                else if(pass[2] != 1){
                    JOptionPane.showMessageDialog(null, "Please pass the previous level!");
                }

            }
        });
        this.getLayeredPane().add(iconLabel3, new Integer(Integer.MIN_VALUE + 2));

        // 王關
        if(pass[3] != 1){
            iconLabel4 = new JLabel(new ImageIcon("icon3.png"));
            iconLabel4.setBounds(950, 400, 200, 200);
        }
        else{
            iconLabel4.setVisible(true);
        }
        iconLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[3] != 1 && pass[0] == 1 && pass[1] == 1 && pass[2] == 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel4.setIcon(new ImageIcon("icon3_new.png"));
                    //關卡內容
                    //
                    pass[3] = 1;
                    passImg = new ImageIcon("pass.jpg");
                    imgLabel.setIcon(scaleImageIcon(passImg, getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                }
                else if(pass[3] != 1){
                    JOptionPane.showMessageDialog(null, "Please pass the previous level!");
                }
            }
        });
        this.getLayeredPane().add(iconLabel4, new Integer(Integer.MIN_VALUE + 2));
    }
    private void hideIcon(){
        iconLabel1.setVisible(false);
        iconLabel2.setVisible(false);
        iconLabel3.setVisible(false);
        iconLabel4.setVisible(false);
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
