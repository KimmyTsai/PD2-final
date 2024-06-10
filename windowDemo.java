import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

import comsimple.AttackCard;

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
    private JLabel hpLabel;
    private JLabel hpNumber;
    private JLabel monsterhpLabel;
    private JLabel monsterhpNumber;
    private JLabel nextLabel;
    private JLabel energyLabel;
    private JLabel energyNumber;
    private int[] pass =  new int[4];
    private Point initialClick;
    private Point initialPosition;
    private JPanel cardPanel;
    private List<String> cardTypes = new ArrayList<>();
    List<String> cards = new ArrayList<>();  //卡牌總表
    private int discardDeck = 0;
    private int deck;
    private JLabel discardDeckLabel;
    private JLabel deckLabel;
    private JLabel discardDeckNumber;
    private JLabel deckNumber;
    private int HP = 80;
    private int monsterHP = 20;
    private int energy;
    private int round = 0;
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
                    energy = 3;
                    callAllLabel();

                    deck = showRandomCards();
                    deckNumber.setText(deck + "");
                    nextRound();

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
                    System.out.println(cardLabels.size());
                    JLabel otherLabel = cardLabels.get(i);
                    String otherLabelType = cardTypes.get(i);
                    if (label != otherLabel && label.getBounds().intersects(monsterLabel.getBounds())) {
                        collided = true;

                        cards.remove(labelType);
                        // 移除
                        cardPanel.remove(label);
                        //cardLabels.remove(label);
                        //cardTypes.remove(labelType); //有問題

                        int countLabelName = Collections.frequency(cards, labelType);
                        JOptionPane.showMessageDialog(null, labelType + " 碰撞到怪物\n" +
                        labelType + " 總共 " + countLabelName + " 張\n");
                        switch (labelType){
                            case "image/attack.png" :
                                System.out.println("attack");
                                break;

                            case "image/defend.png" :
                                System.out.println("defend");
                                break;

                            case "image/muscle.png" :
                                System.out.println("muscle");
                                break;

                            case "image/bash.png" :
                                System.out.println("defend");
                                break;

                            default:
                            System.out.println("");
                        }

                        label.setVisible(false);
                        System.out.println(cards);
                        discardDeck++;
                        discardDeckNumber.setText(discardDeck + "");
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

        // 添加卡片到列表
        for (int i = 0; i < attackNumber; i++) {
            cards.add("image/attack.png");
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
            JLabel cardLabel = new JLabel(new ImageIcon(cards.get(i))); //手牌
            String cardType = cards.get(i);
            moveObject(cardLabel, cardType);
            cardLabel.setBounds(190 + i * 210, getScreenHeight() - 330, 220, 288); // 设置卡片位置和大小
            this.getLayeredPane().add(cardLabel, new Integer(Integer.MIN_VALUE + 4));
            cardLabels.add(cardLabel);
            cardTypes.add(cardType);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
        return cards.size();
    }
    @SuppressWarnings("removal")
    private void callAllLabel(){
        monsterLabel = new JLabel(new ImageIcon("image/monster1.png"));  //怪物
        monsterLabel.setBounds(1050, 100, 400, 400);
        getLayeredPane().add(monsterLabel, new Integer(Integer.MIN_VALUE + 3));

        manLabel = new JLabel(new ImageIcon("image/fighter.png")); //角色
        manLabel.setBounds(45, 95, 500, 500);
        getLayeredPane().add(manLabel, new Integer(Integer.MIN_VALUE + 3));

        discardDeckLabel = new JLabel(new ImageIcon("image/棄牌堆.png")); //棄牌堆
        discardDeckLabel.setBounds(1300, 700, 156, 133);
        getLayeredPane().add(discardDeckLabel, new Integer(Integer.MIN_VALUE + 3));

        discardDeckNumber = new JLabel(discardDeck + ""); //棄牌堆數量
        discardDeckNumber.setFont(new Font("Arial", Font.BOLD, 25)); 
        discardDeckNumber.setForeground(Color.WHITE);
        getLayeredPane().add(discardDeckNumber, new Integer(Integer.MIN_VALUE + 4));
        discardDeckNumber.setBounds(1325, 767, 50, 50);

        deckLabel = new JLabel(new ImageIcon("image/牌堆.png")); //牌堆
        deckLabel.setBounds(10, 700, 148, 135);
        getLayeredPane().add(deckLabel, new Integer(Integer.MIN_VALUE + 3));
        
        deckNumber = new JLabel((attackNumber + defendNumber + bashNumber + muscleNumber) + ""); //牌堆數量
        deckNumber.setFont(new Font("Arial", Font.BOLD, 25));
        deckNumber.setForeground(Color.WHITE);
        getLayeredPane().add(deckNumber, new Integer(Integer.MIN_VALUE + 4));
        deckNumber.setBounds(113, 767, 50, 50);

        hpLabel = new JLabel(new ImageIcon("image/hp.png")); //HP
        hpLabel.setBounds(180, 500, 289, 20);
        getLayeredPane().add(hpLabel, new Integer(Integer.MIN_VALUE + 3));

        hpNumber = new JLabel(HP + "/80"); //HP數字
        hpNumber.setFont(new Font("Arial", Font.BOLD, 25));
        hpNumber.setForeground(Color.WHITE);
        getLayeredPane().add(hpNumber, new Integer(Integer.MIN_VALUE + 4));
        hpNumber.setBounds(290, 485, 120, 50);

        monsterhpLabel = new JLabel(new ImageIcon("image/hp.png")); //怪物HP
        monsterhpLabel.setBounds(1120, 500, 289, 20);
        getLayeredPane().add(monsterhpLabel, new Integer(Integer.MIN_VALUE + 3));

        monsterhpNumber = new JLabel(monsterHP + "/20"); //怪物HP數量
        monsterhpNumber.setFont(new Font("Arial", Font.BOLD, 25));
        monsterhpNumber.setForeground(Color.WHITE);
        getLayeredPane().add(monsterhpNumber, new Integer(Integer.MIN_VALUE + 4));
        monsterhpNumber.setBounds(1230, 485, 120, 50);

        nextLabel = new JLabel(new ImageIcon("image/next.png")); //結束回合
        nextLabel.setBounds(1290, 570, 195, 83);
        getLayeredPane().add(nextLabel, new Integer(Integer.MIN_VALUE + 4));

        energyLabel = new JLabel(new ImageIcon("image/energy.png")); //能量圖示
        energyLabel.setBounds(10, 530, 130, 131);
        getLayeredPane().add(energyLabel, new Integer(Integer.MIN_VALUE + 4));

        energyNumber = new JLabel(energy + "/3"); //能量數量
        energyNumber.setFont(new Font("Arial", Font.BOLD, 35));
        energyNumber.setForeground(Color.WHITE);
        getLayeredPane().add(energyNumber, new Integer(Integer.MIN_VALUE + 5));
        energyNumber.setBounds(52, 570, 120, 50);

    }
    private void nextRound() {
        nextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Next Round!");
                round ++;
            }
        });
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
