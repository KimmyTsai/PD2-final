// 卡牌:防禦
package com.example.simpleslay;

import java.util.List;

public class DefendCard extends Card {
    public DefendCard() {
        super("Defend", 0, 5, 1);
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        player.gainBlock(block);
    }
}
