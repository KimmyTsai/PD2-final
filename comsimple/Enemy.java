package comsimple;

public class Enemy {
    String name;
    int health;
    int block;
    int damage;
    Enemy ally;
    int actionCounter;
    boolean isVulnerable;
    int vulnerableDuration;
    boolean isWeak;
    int weakDuration;

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.block = 0;
        this.damage = damage;
        this.actionCounter = 0;
        this.isVulnerable = false;
        this.vulnerableDuration = 0;
        this.isWeak = false;
        this.weakDuration = 0;
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
        if (name.equals("c++") && actionCounter % 2 == 1) {
            ally.heal(10);
            System.out.println(name + " heals " + ally.name + " for 10 HP.");
        } else {
            player.takeDamage(damage);
            if (name.equals("c")) {
                player.applyVulnerable(1);
                System.out.println(player.name + " is now Vulnerable.");
            } else if (name.equals("c++")) {
                player.applyWeak(1);
                System.out.println(player.name + " is now Weak.");
            }
        }
        actionCounter++;
        updateEffects();
    }

    void heal(int amount) {
        health += amount;
    }

    String nextAction() {
        if (name.equals("c++") && actionCounter % 2 == 0) {
            return "Healing " + ally.name + " for 10 HP";
        } else if (actionCounter % 3 == 2) {
            return "Blocking for " + damage;
        } else {
            return "Attacking for " + damage;
        }
    }

    void applyEffect(Vulnerable vulnerable) {
        vulnerable.apply(this);
    }

    void applyEffect(Weak weak) {
        weak.apply(this);
    }

    void updateEffects() {
        if (isVulnerable) {
            vulnerableDuration--;
            if (vulnerableDuration <= 0) {
                isVulnerable = false;
            }
        }
        if (isWeak) {
            weakDuration--;
            if (weakDuration <= 0) {
                isWeak = false;
            }
        }
    }
}
