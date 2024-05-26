// 卡牌:重擊


import java.util.List;

import com.example.simpleslay.Card;
import com.example.simpleslay.Enemy;
import com.example.simpleslay.Player;
import com.example.simpleslay.Vulnerable;

public class BashCard extends Card {
    private int vulnerableDuration;

    public BashCard() {
        super("Bash", 8, 0, 2);
        this.vulnerableDuration = 2;
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        if (!enemies.isEmpty()) {
            Enemy enemy = enemies.get(0);
            enemy.takeDamage(player.applyStrength(damage));
            enemy.applyEffect(new Vulnerable(vulnerableDuration));
        }
    }
}
