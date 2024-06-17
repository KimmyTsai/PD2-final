package comsimple;

public class CombustCard extends Card {
    public CombustCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
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
