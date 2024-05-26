// 卡牌:攻擊


import java.util.List;

import com.example.simpleslay.Card;
import com.example.simpleslay.Enemy;
import com.example.simpleslay.Player;

public class AttackCard extends Card {
    public AttackCard() {
        super("Attack", 6, 0, 1);
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        if (!enemies.isEmpty()) {
            Enemy enemy = enemies.get(0);
            enemy.takeDamage(player.applyStrength(damage));
        }
    }
}
