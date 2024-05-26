package comsimple;

class DefendCard extends Card {
    DefendCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
    }

    @Override
    void use(Player player, Enemy enemy) {
        player.gainBlock(block);
    }
}
