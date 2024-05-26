package comsimple;

class CombustCard extends Card {
    CombustCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
    }

    @Override
    void use(Player player, Enemy enemy) {
        enemy.takeDamage(player.applyStrength(damage));
        player.health -= 1;
    }
}
