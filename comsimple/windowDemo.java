package comsimple;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private List<JLabel> cardLabels = new ArrayList<>();
    private int attackNumber = 5;
    private int defendNumber = 4;
    private int bashNumber = 2;
    private int muscleNumber = 2;
    private JLabel monsterLabel;
    private JLabel manLabel;
    private int[] pass =  new int[4];
    private Point initialClick;
    private Point initialPosition;
    private JPanel cardPanel;
    private List<String> cardTypes = new ArrayList<>();
    private int discardDeck = 0;
    private int deck;
    private JLabel discardDeckLabel;
    private JLabel deckLabel;
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
        this.setSize(1920, 1080);
        /*
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            System.err.println("Full screen not supported");
            this.setSize(1920, 1080); //視窗大小，不能全屏就1920*1080
        }
        */

        btn1 = new JButton("How To Play");
        btn2 = new JButton("Start");
        
        // 主畫面
        homeImg = new ImageIcon("image/wallpaper.jpg");

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
        ImageIcon popupImg = new ImageIcon("image/test.png");
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

        // 创建显示抽取卡片的面板
        cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new FlowLayout());
        this.getLayeredPane().add(cardPanel, new Integer(Integer.MIN_VALUE + 4));
        cardPanel.setBounds(0, getScreenHeight() - 330, getScreenWidth(), 990);

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

            @Override
            public void actionPerformed(ActionEvent e) {
                mapImg = new ImageIcon("image/new_wallpaper.jpg");
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
            iconLabel1 = new JLabel(new ImageIcon("image/icon1.png"));
            iconLabel1.setBounds(420, 200, 200, 200);
        }
        else{
            iconLabel1.setVisible(true);
        }

        iconLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[0] != 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("image/level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel1.setIcon(new ImageIcon("image/icon1_new.png"));
                    //關卡內容
                    monsterLabel = new JLabel(new ImageIcon("image/monster1.png"));
                    monsterLabel.setBounds(1050, 100, 400, 400);
                    getLayeredPane().add(monsterLabel, new Integer(Integer.MIN_VALUE + 3));

                    manLabel = new JLabel(new ImageIcon("image/fighter.png"));
                    manLabel.setBounds(110, 110, 500, 500);
                    getLayeredPane().add(manLabel, new Integer(Integer.MIN_VALUE + 3));

                    discardDeckLabel = new JLabel(new ImageIcon("image/棄牌堆.png"));
                    discardDeckLabel.setBounds(1300, 700, 156, 133);
                    getLayeredPane().add(discardDeckLabel, new Integer(Integer.MIN_VALUE + 3));

                    deckLabel = new JLabel(new ImageIcon("image/牌堆.png"));
                    deckLabel.setBounds(10, 700, 148, 135);
                    getLayeredPane().add(deckLabel, new Integer(Integer.MIN_VALUE + 3));

                    deck = showRandomCards();
                    //
                    pass[0] = 1;
                    //levelChoose();
                }
                // else{
                //     JOptionPane.showMessageDialog(null, "Level 1 passed!");
                // }
                
            }
        });
        this.getLayeredPane().add(iconLabel1, new Integer(Integer.MIN_VALUE + 2));

        // 第二關
        if(pass[1] != 1){
            iconLabel2 = new JLabel(new ImageIcon("image/icon1.png"));
            iconLabel2.setBounds(550, 500, 200, 200);
        }
        else{
            iconLabel2.setVisible(true);
        }
        iconLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[1] != 1 && pass[0] == 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("image/level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel2.setIcon(new ImageIcon("image/icon1_new.png"));
                    //關卡內容
                    //
                    pass[1] = 1;
                    levelChoose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please pass the previous level!");
                }

            }
        });
        this.getLayeredPane().add(iconLabel2, new Integer(Integer.MIN_VALUE + 2));

        // 回血點
        if(pass[2] != 1){
            iconLabel3 = new JLabel(new ImageIcon("image/icon2.png"));
            iconLabel3.setBounds(720, 300, 200, 200);
        }
        else {
            iconLabel3.setVisible(true);
        }
        iconLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[2] != 1 && pass[0] == 1 && pass[1] == 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("image/level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel3.setIcon(new ImageIcon("image/icon2_new.png"));
                    //關卡內容
                    //
                    pass[2] = 1;
                    levelChoose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please pass the previous level!");
                }

            }
        });
        this.getLayeredPane().add(iconLabel3, new Integer(Integer.MIN_VALUE + 2));

        // 王關
        if(pass[3] != 1){
            iconLabel4 = new JLabel(new ImageIcon("image/icon3.png"));
            iconLabel4.setBounds(950, 400, 200, 200);
        }
        else{
            iconLabel4.setVisible(true);
        }
        iconLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pass[3] != 1 && pass[0] == 1 && pass[1] == 1 && pass[2] == 1){
                    imgLabel.setIcon(scaleImageIcon(new ImageIcon("image/level1.jpg"), getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                    hideIcon();
                    iconLabel4.setIcon(new ImageIcon("image/icon3_new.png"));
                    //關卡內容
                    //
                    pass[3] = 1;
                    passImg = new ImageIcon("image/pass.jpg");
                    imgLabel.setIcon(scaleImageIcon(passImg, getScreenWidth(), getScreenHeight()));
                    imgLabel.repaint();
                }
                else{
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

    private void moveObject(JLabel label, String labelType) { //拖動物件
        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialPosition = label.getLocation();
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });
    
        label.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // get location of window
                int thisX = label.getLocation().x;
                int thisY = label.getLocation().y;
    
                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
    
                // Move picture to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                label.setLocation(X, Y);
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean collided = false;
                for (int i = 0; i < cardLabels.size(); i++) {
                    JLabel otherLabel = cardLabels.get(i);
                    String otherLabelType = cardTypes.get(i);
                    if (label != otherLabel && label.getBounds().intersects(monsterLabel.getBounds())) {
                        collided = true;
                        JOptionPane.showMessageDialog(null, labelType + " collided with monster " + "!");

                        label.setLocation(initialPosition);
                        break;
                    }
                }
                if (!collided) {
                    label.setLocation(initialPosition);
                }
            }
        });
    }

    @SuppressWarnings("removal")
    private int showRandomCards() {
        List<String> cards = new ArrayList<>();
        // 添加卡片到列表
        for (int i = 0; i < attackNumber; i++) {
            cards.add("image/attack.png");
            //player.addCardToDeck(new AttackCard("Strike", 6, 0, 1, "image/attack.png"));
        }
        for (int i = 0; i < defendNumber; i++) {
            cards.add("image/defend.png");
        }
        for (int i = 0; i < bashNumber; i++) {
            cards.add("image/bash.png");
        }
        for (int i = 0; i < muscleNumber; i++) {
            cards.add("image/muscle.png");
        }

        // 打乱卡片顺序
        Collections.shuffle(cards);

        // 显示前五张卡片
        cardPanel.removeAll();
        cardLabels.clear();
        cardTypes.clear();
        for (int i = 0; i < 5; i++) {
            JLabel cardLabel = new JLabel(new ImageIcon(cards.get(i)));
            String cardType = cards.get(i);
            moveObject(cardLabel, cardType);
            cardLabel.setBounds(170 + i * 210, getScreenHeight() - 330, 220, 288); // 设置卡片位置和大小
            this.getLayeredPane().add(cardLabel, new Integer(Integer.MIN_VALUE + 4));
            cardLabels.add(cardLabel);
            cardTypes.add(cardType);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
        return cards.size();
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
