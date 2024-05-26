// 卡牌:肌肉活性


import java.util.List;

import com.example.simpleslay.Card;
import com.example.simpleslay.Enemy;
import com.example.simpleslay.Player;
import com.example.simpleslay.Strength;

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
