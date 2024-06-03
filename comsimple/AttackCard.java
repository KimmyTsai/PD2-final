package comsimple;

public class AttackCard extends Card {
    public AttackCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
    }

    @Override
    void use(Player player, Enemy enemy) {
        enemy.takeDamage(player.applyStrength(damage));
        // 不在这里直接丢弃卡片
    }
}