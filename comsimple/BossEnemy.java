package comsimple;

public class BossEnemy extends Enemy {
    BossEnemy(String name, int health, int damage) {
        super(name, health, damage);
    }

    @Override
    void act(Player player) {
        if (name.equals("c++")) {
            System.out.println(name + " will heal c by 10 and attack for " + damage + " next turn.");
        } else if (name.equals("c")) {
            System.out.println(name + " will attack for " + damage + " and apply Weak to the player next turn.");
        } else {
            super.act(player);
        }
    }

    @Override
    String getNextAction() {
        if (name.equals("c++")) {
            return "Heal c for 10 and attack for " + damage;
        } else if (name.equals("c")) {
            return "Attack for " + damage + " and apply Weak to the player";
        } else {
            return super.getNextAction();
        }
    }
}
