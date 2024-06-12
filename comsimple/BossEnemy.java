package comsimple;

public class BossEnemy extends Enemy {
    /*BossEnemy(String name, int health, int damage) {
        super(name, health, damage);*/
    BossEnemy( int health, int damage) {
        super( health, damage);
    }
    

    @Override
    void act(Player player, int turnCounter) {
        if (name.equals("C++")) {
            if (turnCounter % 2 == 0) {
                block = 2;
                System.out.println(name + " blocks for " + block + " points.");
            } else {
                for (Enemy enemy : SimpleSlay.enemies) {
                    if (enemy.name.equals("C Boss")) {
                        enemy.health = Math.min(enemy.health + 10, 80);
                        System.out.println(name + " heals C Boss for 10.");
                    }
                }
            }
        } else if (name.equals("C Boss")) {
            if (turnCounter % 2 == 0) {
                block = 2;
                System.out.println(name + " blocks for " + block + " points.");
            } else {
               
                    player.takeDamage(damage);
                    System.out.println(name + " attacks for " + damage + " points.");
                
            }
        } else {
            super.act(player, turnCounter);
        }
    }

    @Override
    String getNextAction(int turnCounter) {
        if (name.equals("C++")) {
            if (turnCounter % 2 == 0) {
                return "Block for 2 points";
            } else {
                return "Heal C Boss for 10";
            }
        } else if (name.equals("C Boss")) {
            if (turnCounter % 2 == 0) {
                return "Block for 2 points";
            } else {
                
                    return "Attack for " + damage + " points";
                
            }
        } else {
            return super.getNextAction(turnCounter);
        }
    }
}
