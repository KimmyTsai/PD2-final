package comsimple;

public class DefendCard extends Card {
    public DefendCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
    }

    @Override
    void use(Player player, Enemy enemy) {
        player.gainBlock(block);
        player.discardCard(this);  // 丢到弃牌堆
    }
}
