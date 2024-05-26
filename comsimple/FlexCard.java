package comsimple;

public class FlexCard extends Card {
    FlexCard(String name, int damage, int block, int energyCost) {
        super(name, damage, block, energyCost);
    }

    @Override
    void use(Player player, Enemy enemy) {
        player.useMuscle();
    }
}
