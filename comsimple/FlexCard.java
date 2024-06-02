package comsimple;

class FlexCard extends Card {
    FlexCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
    }

    @Override
    void use(Player player, Enemy enemy) {
        player.useMuscle();
    }

    @Override
    public String toString() {
        return name + " (Gain +2 Strength for 1 turn)";
    }
}
