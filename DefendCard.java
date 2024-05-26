// 卡牌:防禦
import java.util.List;

import com.example.simpleslay.Card;
import com.example.simpleslay.Enemy;
import com.example.simpleslay.Player;

public class DefendCard extends Card {
    public DefendCard() {
        super("Defend", 0, 5, 1);
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        player.gainBlock(block);
    }
}
