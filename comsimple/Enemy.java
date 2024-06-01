package comsimple;

public class Enemy {
    String name;
    int health;
    int block;
    int damage;
    int turnCounter;
    int vulnerableDuration;
    Enemy ally;

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.block = 0;
        this.damage = damage;
        this.turnCounter = 1;
        this.ally = null;
        this.vulnerableDuration = 0; // 初始化為0
    }

    void takeDamage(int damage) {
        int effectiveDamage = damage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
            block = 0;
        } else {
            block -= damage;
        }
    }

    void act(Player player) {
        if (turnCounter % 3 == 0) {
            block += 10;
            System.out.println(name + " is blocking.");
        } else {
            int finalDamage = damage;
            if (vulnerableDuration > 0) {
                finalDamage *= 1.5;
                vulnerableDuration--;
            }
            player.takeDamage(finalDamage);
            System.out.println(name + " attacked for " + finalDamage + " damage.");
        }
        turnCounter++;
    }

    void heal(Enemy ally, int healAmount) {
        if (ally.health > 0) {
            ally.health += healAmount;
            System.out.println(name + " healed " + ally.name + " for " + healAmount + " health.");
        }
    }

    void applyEffect(Vulnerable vulnerable) {
        vulnerable.apply(null);
        vulnerableDuration = vulnerable.duration;
        System.out.println(name + " is now vulnerable for " + vulnerableDuration + " turns.");
    }
}
