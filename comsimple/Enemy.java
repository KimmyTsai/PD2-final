package comsimple;

public class Enemy {
    String name;
    int health;
    int block;
    int damage;
    boolean isVulnerable;
    int vulnerableTurns;
    boolean isWeak;
    int weakTurns;

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.block = 0;
        this.damage = damage;
        this.isVulnerable = false;
        this.vulnerableTurns = 0;
        this.isWeak = false;
        this.weakTurns = 0;
    }

    void takeDamage(int damage) {
        int effectiveDamage = isVulnerable ? (int)(damage * 1.5) : damage;
        effectiveDamage = effectiveDamage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
            block = 0;
        } else {
            block -= effectiveDamage;
        }
    }

    void act(Player player) {
        if (isWeak) {
            System.out.println(name + " is weak and attacks with half damage!");
        }
        if (isVulnerable) {
            System.out.println(name + " is vulnerable and takes extra damage!");
        }
        player.takeDamage(damage);
    }

    void applyEffect(Vulnerable vulnerable) {
        this.isVulnerable = true;
        this.vulnerableTurns = vulnerable.duration;
        System.out.println(name + " is now Vulnerable for " + vulnerable.duration + " turns.");
    }

    void applyEffect(Weak weak) {
        this.isWeak = true;
        this.weakTurns = weak.duration;
        System.out.println(name + " is now Weak for " + weak.duration + " turns.");
    }

    String getNextAction() {
        // Example of action logic; can be customized
        if (block > 0) {
            return "Block for " + block + " points";
        } else {
            return "Attack for " + damage + " points";
        }
    }
}
