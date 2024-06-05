package comsimple;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlayGame {
    public static void main(String[] args) {
        Player player = new Player("Player 1");

        // Create cards and add to player's deck
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new AttackCard("Strike", 6, 0, 1));
        }
        for (int i = 0; i < 4; i++) {
            player.addCardToDeck(new DefendCard("Defend", 0, 5, 1));
        }
        for (int i = 0; i < 2; i++) {
            player.addCardToDeck(new FlexCard("Muscle (增加2點基礎攻擊力，持續1回合)", 0, 0, 0));
            player.addCardToDeck(new CombustCard("Combust (對所有敵人造成5點傷害，自己損失1點生命)", 5, 0, 0));
            player.addCardToDeck(new BashCard("Bash (造成8點傷害，使敵人虛弱2回合)", 8, 0, 2));
        }
    }
}

class AttackCard extends Card {
    String name;
    int damage;
    int block;
    int energyCost;

    AttackCard(String name, int damage, int block, int energyCost) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    @Override
    void use(Player player, Enemy enemy) {
        enemy.takeDamage(player.applyStrength(damage));
    }
}

class DefendCard extends Card {
    String name;
    int damage;
    int block;
    int energyCost;

    DefendCard(String name, int damage, int block, int energyCost) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    @Override
    void use(Player player, Enemy enemy) {
        player.gainBlock(block);
    }
}

class FlexCard extends Card {
    String name;
    int damage;
    int block;
    int energyCost;

    FlexCard(String name, int damage, int block, int energyCost) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    @Override
    void use(Player player, Enemy enemy) {
        player.useMuscle();
    }

    @Override
    public String toString() {
        return name + " (Gain +2 Strength for 1 turn)";
    }
}

class CombustCard extends Card {
    String name;
    int damage;
    int block;
    int energyCost;

    CombustCard(String name, int damage, int block, int energyCost) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    @Override
    void use(Player player, Enemy enemy) {
        for (Enemy e : SimpleSlay.enemies) {
            e.takeDamage(damage);
        }
    }

    @Override
    public String toString() {
        return name + " (Deal 5 damage to all enemies)";
    }
}

class BashCard extends Card {
    String name;
    int damage;
    int block;
    int energyCost;
    int vulnerableDuration;

    BashCard(String name, int damage, int block, int vulnerableDuration) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = 2; // 能量耗费为2
        this.vulnerableDuration = vulnerableDuration;
    }

    @Override
    void use(Player player, Enemy enemy) {
        enemy.takeDamage(player.applyStrength(damage));
        enemy.applyEffect(new Vulnerable(vulnerableDuration));
    }

    @Override
    public String toString() {
        return name + " (Deal " + damage + " damage and apply " + vulnerableDuration + " turn(s) of Vulnerable)";
    }
}

abstract class Card {
    abstract void use(Player player, Enemy enemy);
}

class Player {
    private String name;
    private List<Card> deck;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void gainBlock(int block) {
        // 增加防御力的方法实现
    }

    public void useMuscle() {
        // 增加攻击力的方法实现
    }

    public int applyStrength(int damage) {
        // 返回基于当前攻击力的伤害值
        return damage;
    }
}

class Enemy {
    public void takeDamage(int damage) {
        // 敌人受伤方法实现
    }

    public void applyEffect(Vulnerable vulnerable) {
        // 应用虚弱效果的方法实现
    }
}

class Vulnerable {
    int duration;

    Vulnerable(int duration) {
        this.duration = duration;
    }
}

class SimpleSlay {
    public static List<Enemy> enemies;

    static {
        enemies = new ArrayList<>();
        // 初始化敌人列表
        enemies.add(new Enemy()); // 假设初始化一个敌人实例
    }
}
