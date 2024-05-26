// 卡牌:肌肉活性
package com.example.simpleslay;

import java.util.List;

public class FlexCard extends Card {
    private int strength;
    private int duration;

    public FlexCard() {
        super("Flex", 0, 0, 0);
        this.strength = 2;
        this.duration = 1;
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        player.applyStrengthEffect(new Strength(strength, duration));
    }
}
