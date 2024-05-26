// 卡牌:自燃
import java.util.List;

import com.example.simpleslay.Card;
import com.example.simpleslay.Enemy;
import com.example.simpleslay.Player;

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
