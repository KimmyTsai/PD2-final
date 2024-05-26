package comsimple;

class BashCard extends Card {
    int vulnerableDuration;

    BashCard(String name, int damage, int block, int vulnerableDuration) {
        super(name, damage, block, 1);
        this.vulnerableDuration = vulnerableDuration;
    }

    @Override
    void use(Player player, Enemy enemy) {
        enemy.takeDamage(player.applyStrength(damage));
        enemy.applyEffect(new Vulnerable(vulnerableDuration));
    }
}
