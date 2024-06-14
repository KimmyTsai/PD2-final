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
    int turnCounter;

    //Enemy(String name, int health, int damage) {
    Enemy(int health, int damage) {
        //this.name = name;
        this.health = health;
        this.block = 0;
        this.damage = damage;
        this.isVulnerable = false;
        this.vulnerableTurns = 0;
        this.isWeak = false;
        this.weakTurns = 0;
        this.turnCounter = 0;
    }

    void takeDamage(int damage) {
        /*if (turnCounter % 2 == 0) {
            block = 2;
        } else {
            block = 0;
        }*/
        
        int effectiveDamage = isVulnerable ? (int)(damage * 1.5) : damage;
        //
        //
        effectiveDamage -= block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
            block = 0;
        } else {
            block -= effectiveDamage;
        }
    }

    void act(Player player, int turnCounter) {
        turnCounter++;
        if (isWeak) {
            System.out.println(name + " is weak and attacks with half damage!");
        }
        if (isVulnerable) {
            System.out.println(name + " is vulnerable!");
        }
        /*if (turnCounter % 2 == 0) {
            block = 2;
            System.out.println(name + " blocks for " + block + " points.");
        }*/
         else {
            player.takeDamage(damage);
            System.out.println(" attacks for " + damage + " points.");
        }
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

    String getNextAction(int turnCounter) {
        if (turnCounter % 2 == 1) {
            return "Block for 2 points";
        } else {
            return "Attack for " + damage + " points";
        }
    }
}
