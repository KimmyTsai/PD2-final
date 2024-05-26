// 卡牌:自燃
package com.example.simpleslay;

import java.util.List;

public class CombustCard extends Card {
    private int selfDamage;

    public CombustCard() {
        super("Combust", 5, 0, 0);
        this.selfDamage = 1;
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        player.takeDamage(selfDamage);
        for (Enemy enemy : enemies) {
            enemy.takeDamage(player.applyStrength(damage));
        }
    }
}
