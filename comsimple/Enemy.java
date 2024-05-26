package comsimple;

public class Enemy {
    String name;
    int health;
    int block;
    int damage;

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.block = 0;
        this.damage = damage;
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
        player.takeDamage(damage);
    }

    void applyEffect(Vulnerable vulnerable) {
        // Assuming Vulnerable has some effect on the enemy
    }
}
