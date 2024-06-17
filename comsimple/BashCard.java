package comsimple;

public class BashCard extends Card {
    int vulnerableDuration;

    public BashCard(String name, int damage, int block, int vulnerableDuration) {
        super(name, damage, block, 2); // 能量耗费为2
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
