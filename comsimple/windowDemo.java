package comsimple;
import comsimple.*;

import java.awt.*;
import java.awt.event.*;
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
    private List<JLabel> cardLabels = new ArrayList<>(); // 手牌表
    private int attackNumber = 5;
    private int defendNumber = 4; 
    private int bashNumber = 2;
    private int muscleNumber = 2;
    private int combustNumber = 2;
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
    private JLabel vulnerableLabel;
    public int health = 20;
    private int block = 0;
    private JLabel blockLabel;
    private JLabel blockNumber;
    private int vulnerableDuration = 0;
    private JLabel bossattackLabel;
    private JLabel bossattackNumber;
    private int bossattack;
    public MusicPlayer musicPlayer; // 添加MusicPlayer變量
    
    public windowDemo() {
        init();
    }

    
    // 全局變數
    ArrayList<Enemy> enemies = new ArrayList<>();
    Player player;

    // 初始化敵人和玩家，只在遊戲開始時調用一次
    public void initGame() {
        for (int level = 0; level < 3; level++) {
            if (level == 0) {
                enemies.add(new Enemy(20, 6));  
            } else if (level == 1) {
                enemies.add(new Enemy(20, 10));
                enemies.add(new Enemy(20, 10));
            }
        }
        player = new Player("Player", 80, 0);
    }

    @SuppressWarnings("removal")
    private void init() {

        this.setTitle("FINAL");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // 設置視窗大小
       // GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
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

        // 初始化并播放背景音乐
        musicPlayer = new MusicPlayer();
        System.out.println("Attempting to play background music.");
        musicPlayer.playBackgroundMusic("C:\\Users\\User\\OneDrive\\附件\\PD2-FINAL\\resources\\Age of Empires 2 - Age of Kings.wav");
    }
    
    private void levelChoose() { //關卡選擇頁面
        // 隱藏按鈕
        btn1.setVisible(false);
        btn2.setVisible(false);
        for(JLabel label : cardLabels){
            label.setVisible(false);
        }
        if(pass[0] != 0){
            monsterLabel.setVisible(false);
            manLabel.setVisible(false);
            hpLabel.setVisible(false);
            hpNumber.setVisible(false);
            monsterhpLabel.setVisible(false);
            monsterhpNumber.setVisible(false);
            nextLabel.setVisible(false);
            energyLabel.setVisible(false);
            energyNumber.setVisible(false);
            discardDeckLabel.setVisible(false);
            deckLabel.setVisible(false);
            discardDeckNumber.setVisible(false);
            deckNumber.setVisible(false);
            blockLabel.setVisible(false);
            blockNumber.setVisible(false);
            vulnerableLabel.setVisible(false);
        }
        
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
        addBlockAndVulnerable();
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
                    energy = 3; //能量值
                    callAllLabel();
                    initCards();
                    initGame();

                    showRandomCards();
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
                        
                        

                        int countLabelName = Collections.frequency(cards, labelType);
                        JOptionPane.showMessageDialog(null, labelType + " 碰撞到怪物\n" +
                                labelType + " 總共 " + countLabelName + " 張\n");
            
                        Enemy enemy = enemies.get(0);
                        
                        switch (labelType) {
                            case "image/attack.png":
                                AttackCard attackCard = new AttackCard("Strike", 6, 0, 1); 
                                if (player.energy >= attackCard.energyCost) {
                                    discardDeck++;
                                    discardDeckNumber.setText(discardDeck + "");
                                    label.setVisible(false);
                                    cards.remove(labelType);
                                    cardPanel.remove(label);
                                    cardLabels.remove(label); //有問題？
                                    cardTypes.remove(labelType);

                                    //enemy.takeDamage(6);
                                    int totalDamage = player.baseAttack + attackCard.damage;
                                    enemy.takeDamage(totalDamage);
                                    player.energy -= attackCard.energyCost;
                                    if (enemy.health <= 0) levelChoose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "能量不足");
                                    label.setLocation(initialPosition);
                                    System.out.println("Not enough energy to play Strike.");
                                }
                                break;
                        
                            case "image/defend.png":
                                DefendCard defendCard = new DefendCard("Defend", 0, 5, 1); 
                                if (player.energy >= defendCard.energyCost) {
                                    discardDeck++;
                                    discardDeckNumber.setText(discardDeck + "");
                                    label.setVisible(false);
                                    cards.remove(labelType);
                                    cardPanel.remove(label);
                                    cardLabels.remove(label);
                                    cardTypes.remove(labelType);
                                    player.gainBlock(5);
                                    player.energy -= defendCard.energyCost;
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "能量不足");
                                    label.setLocation(initialPosition);
                                    System.out.println("Not enough energy to play Defend.");
                                }
                                break;
                        
                            case "image/muscle.png":
                                FlexCard muscleCard = new FlexCard("Muscle", 0, 0, 0); 
                                if (player.energy >= muscleCard.energyCost) {
                                    discardDeck++;
                                    discardDeckNumber.setText(discardDeck + "");
                                    label.setVisible(false);
                                    cards.remove(labelType);
                                    cardPanel.remove(label);
                                    cardLabels.remove(label);
                                    cardTypes.remove(labelType);
                                    //player.useMuscle();
                                    player.baseAttack += 2; 
                                    System.out.println("Muscle: Base attack increased by 2 for 1 turn.");
                                    player.energy -= muscleCard.energyCost;
                                } else {
                                    System.out.println("Not enough energy to play Muscle.");
                                }
                                break;
                        
                            case "image/bash.png":
                                BashCard bashCard = new BashCard("Bash", 8, 0, 2); 
                                if (player.energy >= bashCard.energyCost) {
                                    discardDeck++;
                                    discardDeckNumber.setText(discardDeck + "");
                                    label.setVisible(false);
                                    cards.remove(labelType);
                                    cardPanel.remove(label);
                                    cardLabels.remove(label);
                                    cardTypes.remove(labelType);
                                    //enemy.takeDamage(8); 
                                    int totalDamage = player.baseAttack + bashCard.damage;
                                    enemy.takeDamage(totalDamage);
                                    enemy.applyEffect(new Vulnerable(2));
                                    vulnerableDuration += 2;

                                    vulnerableLabel.setVisible(true);

                                    player.energy -= bashCard.energyCost;
                                    if (enemy.health <= 0) levelChoose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "能量不足");
                                    label.setLocation(initialPosition);
                                    System.out.println("Not enough energy to play Bash.");
                                }
                                break;
                        
                            case "image/combust.png":
                                CombustCard combustCard = new CombustCard("Combust", 5, 0, 0); 
                                if (player.energy >= combustCard.energyCost) {
                                    discardDeck++;
                                    discardDeckNumber.setText(discardDeck + "");
                                    label.setVisible(false);
                                    cards.remove(labelType);
                                    cardPanel.remove(label);
                                    cardLabels.remove(label);
                                    cardTypes.remove(labelType);
                                    player.health -= 1;
                                    for (Enemy en : enemies) {
                                        if (en.health > 0) {
                                            //en.takeDamage(5);
                                            int totalDamage = player.baseAttack + combustCard.damage; 
                                            en.takeDamage(totalDamage);
                                        }
                                    }
                                    System.out.println("Combust: Dealt 5 damage to all enemies, player loses 1 health.");
                                    player.energy -= combustCard.energyCost;
                                    if (enemy.health <= 0) levelChoose();
                                } else {
                                    System.out.println("Not enough energy to play Combust.");
                                }
                                break;
                        
                            default:
                                System.out.println("Unknown card type.");
                                break;
                        }
                        monsterhpNumber.setText(enemy.health + "/20");
                        hpNumber.setText(player.health + "/80");
                        energyNumber.setText(player.energy + "/3");
                        blockNumber.setText(player.block + "");
                        deck = cards.size();
                        deckNumber.setText(deck + "");

                        if(player.block == 0 && blockNumber != null){
                            blockNumber.setVisible(false);
                            blockLabel.setVisible(false);
                        }
                        else if(player.block > 0){
                            blockNumber.setVisible(true);
                            blockLabel.setVisible(true);
                        }
            
                        
                        
                        System.out.println(cards);
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
    private void addBlockAndVulnerable(){ //因為兩者都會視情況消失
        blockLabel = new JLabel(new ImageIcon("image/block.png")); //格擋
        blockLabel.setBounds(155, 435, 50, 50);
        getLayeredPane().add(blockLabel, new Integer(Integer.MIN_VALUE + 4));
        blockNumber = new JLabel(block + "", SwingConstants.CENTER); //格擋值
        blockNumber.setFont(new Font("Arial", Font.BOLD, 25));
        blockNumber.setForeground(Color.BLACK);
        getLayeredPane().add(blockNumber, new Integer(Integer.MIN_VALUE + 5));
        blockNumber.setBounds(153, 435, 50, 50);
        blockNumber.setVisible(false);
        blockLabel.setVisible(false);

        vulnerableLabel = new JLabel(new ImageIcon("image/vulnerable.png")); //易傷
        vulnerableLabel.setBounds(1120, 470, 35, 35);
        getLayeredPane().add(vulnerableLabel, new Integer(Integer.MIN_VALUE + 4));
        vulnerableLabel.setVisible(false);
    }
    private void initCards() {
        cards.clear();
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
        for (int i = 0; i < combustNumber; i++) {
            cards.add("image/combust.png");
        }
        deck = cards.size();
        deckNumber.setText(deck + "");
    }
    @SuppressWarnings("removal")
    private int showRandomCards() {
        // 打亂卡牌順序
        Collections.shuffle(cards);

        // 選前五張
        cardPanel.removeAll();
        cardLabels.clear();
        cardTypes.clear();
        if(cards.size() >= 5){
            for (int i = 0; i < 5; i++) {
                JLabel cardLabel = new JLabel(new ImageIcon(cards.get(i))); //手牌
                String cardType = cards.get(i);
                moveObject(cardLabel, cardType);
                cardLabel.setBounds(190 + i * 210, getScreenHeight() - 330, 220, 288); // 手牌位置與大小
                this.getLayeredPane().add(cardLabel, new Integer(Integer.MIN_VALUE + 4));
                cardLabels.add(cardLabel);
                cardTypes.add(cardType);
            }
        }
        else{
            int temp = cards.size();
            for (int i = 0; i < temp; i++) {
                JLabel cardLabel = new JLabel(new ImageIcon(cards.get(i))); //手牌
                String cardType = cards.get(i);
                moveObject(cardLabel, cardType);
                cardLabel.setBounds(190 + i * 210, getScreenHeight() - 330, 220, 288); // 手牌位置與大小
                this.getLayeredPane().add(cardLabel, new Integer(Integer.MIN_VALUE + 4));
                cardLabels.add(cardLabel);
                cardTypes.add(cardType);
            }
            initCards();
            Collections.shuffle(cards);

            for (int i = 0; i < 5 - temp; i++) {
                JLabel cardLabel = new JLabel(new ImageIcon(cards.get(i))); //手牌
                String cardType = cards.get(i);
                moveObject(cardLabel, cardType);
                cardLabel.setBounds(190 + i * 210, getScreenHeight() - 330, 220, 288); // 手牌位置與大小
                this.getLayeredPane().add(cardLabel, new Integer(Integer.MIN_VALUE + 4));
                cardLabels.add(cardLabel);
                cardTypes.add(cardType);
            }
            discardDeck = 0;
            discardDeckNumber.setText(discardDeck + "");
        }
        deck = cards.size();
        deckNumber.setText(deck + "");
        cardPanel.revalidate();
        cardPanel.repaint();
        return cards.size();
    }
    @SuppressWarnings("removal")
    private void callAllLabel(){
        monsterLabel = new JLabel(new ImageIcon("image/monster1.png"));  //怪物
        monsterLabel.setBounds(1050, 50, 400, 400);
        getLayeredPane().add(monsterLabel, new Integer(Integer.MIN_VALUE + 3));

        manLabel = new JLabel(new ImageIcon("image/fighter.png")); //角色
        manLabel.setBounds(45, 45, 500, 500);
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
        
        deckNumber = new JLabel((attackNumber + defendNumber + bashNumber + muscleNumber + combustNumber) + ""); //牌堆數量
        deckNumber.setFont(new Font("Arial", Font.BOLD, 25));
        deckNumber.setForeground(Color.WHITE);
        getLayeredPane().add(deckNumber, new Integer(Integer.MIN_VALUE + 4));
        deckNumber.setBounds(113, 767, 50, 50);

        hpLabel = new JLabel(new ImageIcon("image/hp.png")); //HP
        hpLabel.setBounds(180, 450, 289, 20);
        getLayeredPane().add(hpLabel, new Integer(Integer.MIN_VALUE + 3));

        hpNumber = new JLabel(HP + "/80"); //HP數字
        hpNumber.setFont(new Font("Arial", Font.BOLD, 25));
        hpNumber.setForeground(Color.WHITE);
        getLayeredPane().add(hpNumber, new Integer(Integer.MIN_VALUE + 4));
        hpNumber.setBounds(290, 435, 120, 50);

        monsterhpLabel = new JLabel(new ImageIcon("image/hp.png")); //怪物HP
        monsterhpLabel.setBounds(1120, 450, 289, 20);
        getLayeredPane().add(monsterhpLabel, new Integer(Integer.MIN_VALUE + 3));

        monsterhpNumber = new JLabel(health + "/20"); //怪物HP數量
        monsterhpNumber.setFont(new Font("Arial", Font.BOLD, 25));
        monsterhpNumber.setForeground(Color.WHITE);
        getLayeredPane().add(monsterhpNumber, new Integer(Integer.MIN_VALUE + 4));
        monsterhpNumber.setBounds(1230, 435, 120, 50);

        nextLabel = new JLabel(new ImageIcon("image/next.png")); //結束回合
        nextLabel.setBounds(1290, 570, 195, 83);
        getLayeredPane().add(nextLabel, new Integer(Integer.MIN_VALUE + 4));

        energyLabel = new JLabel(new ImageIcon("image/energy.png")); //能量圖示!!!!!!!!!!!!!!!!!!!!!!!!!
        energyLabel.setBounds(10, 530, 130, 131);
        getLayeredPane().add(energyLabel, new Integer(Integer.MIN_VALUE + 4));

        energyNumber = new JLabel(energy + "/3"); //能量數量!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        energyNumber.setFont(new Font("Arial", Font.BOLD, 35));
        energyNumber.setForeground(Color.BLACK);
        getLayeredPane().add(energyNumber, new Integer(Integer.MIN_VALUE + 5));
        energyNumber.setBounds(52, 570, 120, 50);

        bossattackLabel = new JLabel(new ImageIcon("image/bossattack.png")); //怪物攻擊提示
        bossattackLabel.setBounds(1180, 40, 42, 43);
        getLayeredPane().add(bossattackLabel, new Integer(Integer.MIN_VALUE + 4));

        bossattackNumber = new JLabel(bossattack + "", SwingConstants.CENTER); //怪物攻擊傷害
        bossattackNumber.setFont(new Font("Arial", Font.BOLD, 30));
        bossattackNumber.setForeground(Color.WHITE);
        getLayeredPane().add(bossattackNumber, new Integer(Integer.MIN_VALUE + 5));
        bossattackNumber.setBounds(1125, 50, 120, 50);

    }
    private void nextRound() {
        nextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                discardDeck += cardLabels.size();
                discardDeckNumber.setText(discardDeck + "");
                JOptionPane.showMessageDialog(null, "Next Round!");
                round ++;
                for(String cardname : cardTypes){
                    cards.remove(cardname);
                }
                for(JLabel label : cardLabels){
                    label.setVisible(false);
                }
                
                showRandomCards();
                deck = cards.size();
                deckNumber.setText(deck + "");
                player.energy = 3;
                energyNumber.setText(player.energy + "/3");
                if(vulnerableDuration > 0){
                    vulnerableDuration --;
                    if(vulnerableDuration > 0){
                        vulnerableLabel.setVisible(true);
                    }
                    else vulnerableLabel.setVisible(false);
                }
                else vulnerableLabel.setVisible(false);

                if(player.block != 0){
                    player.block = 0;
                    blockNumber.setVisible(false);
                    blockLabel.setVisible(false);
                }
                
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
