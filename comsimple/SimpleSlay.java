package comsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SimpleSlay {
    public static ArrayList<Enemy> enemies;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize player
        Player player = new Player("Player", 80, 0);
        // Create cards and add to player's deck
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new AttackCard("Strike", 6, 0, 1));
        }
        for (int i = 0; i < 4; i++) {
            player.addCardToDeck(new DefendCard("Defend", 0, 5, 1));
        }
        for (int i = 0; i < 2; i++) {
            player.addCardToDeck(new FlexCard("Muscle (增加2點基礎攻擊力，持續1回合)", 0, 0, 0));
            player.addCardToDeck(new CombustCard("Combust (對所有敵人造成5點傷害，自己損失1點生命)", 5, 0, 0));
            player.addCardToDeck(new BashCard("Bash (造成8點傷害，使敵人虚弱2回合)", 8, 0, 2));
        }

        Collections.shuffle(player.deck);

       

        // Start the game with three levels
        String[] enemyNames = {"python", "java", "javascript"};
        enemies = new ArrayList<>();
        for (int level = 0; level < 3; level++) {
            System.out.println("\n--- Level " + (level + 1) + " ---");
            if (level == 0) {
                enemies.add(new Enemy(enemyNames[level], 30, 7));
            } else {
                enemies.add(new Enemy(enemyNames[level], 40, 10));
                enemies.add(new Enemy(enemyNames[level + 1], 40, 10));
            }

            int turnCounter = 0;

            // Each level combat
            while (player.health > 0 && !allEnemiesDefeated()) {
                turnCounter++;
                System.out.println("\nLevel " + (level + 1) + " - Turn " + turnCounter);

                for (Enemy enemy : enemies) {
                    if (enemy.health > 0) {
                        System.out.println(enemy.name + " HP: " + enemy.health + " (" + enemy.getNextAction() + ")");
                    }
                }

                System.out.println("你的 HP: " + player.health);
                System.out.println("你的能量: " + player.energy);
                if (player.muscleTurns > 0) {
                    System.out.println("Muscle 效果: " + player.muscleTurns + " 回合剩餘");
                }

                // Draw cards to hand
                player.refillHand();

                // Player's turn
                while (player.energy > 0 && player.health > 0 && !allEnemiesDefeated()) {
                    if (player.hand.isEmpty()) {
                        break;
                    }

                    // Show hand
                    System.out.println("選擇要打出的牌 (或輸入 -1 結束回合):");
                    for (int i = 0; i < player.hand.size(); i++) {
                        Card card = player.hand.get(i);
                        System.out.println((i + 1) + ". " + card.name + " (傷害: " + card.damage + ", 格擋: " + card.block + ", 能量消耗: " + card.energyCost + ")");
                    }

                    // Player chooses a card
                    int choice = scanner.nextInt() - 1;
                    if (choice == -2) {
                        System.out.println("結束回合.");
                        break;
                    }
                    if (choice < 0 || choice >= player.hand.size()) {
                        System.out.println("無效選擇. 請再試一次.");
                        continue;
                    }

                    Card chosenCard = player.hand.get(choice);
                    if (player.energy >= chosenCard.energyCost) {
                        player.energy -= chosenCard.energyCost;
                        if (chosenCard.name.contains("Muscle")) {
                            player.useMuscle();
                            System.out.println("你使用了 Muscle! 基礎攻擊力增加2，持續1回合.");
                        } else if (chosenCard.name.contains("Combust")) {
                            player.health -= 1;
                            for (Enemy enemy : enemies) {
                                if (enemy.health > 0) {
                                    enemy.takeDamage(chosenCard.damage);
                                }
                            }
                            System.out.println("你使用了 Combust! 對所有敵人造成5點傷害，自身損失1點生命.");
                        } else {
                            if (chosenCard.name.contains("Defend")) {
                                chosenCard.use(player, null);
                            } else {
                                System.out.println("選擇一個敵人進行攻擊:");
                                for (int i = 0; i < enemies.size(); i++) {
                                    if (enemies.get(i).health > 0) {
                                        System.out.println((i + 1) + ". " + enemies.get(i).name + " (HP: " + enemies.get(i).health + ")");
                                    }
                                }
                                System.out.println("請輸入要攻擊的敵人編號:");
                                int enemyChoice = scanner.nextInt() - 1;
                                if (enemyChoice < 0 || enemyChoice >= enemies.size() || enemies.get(enemyChoice).health <= 0) {
                                    System.out.println("無效選擇. 請再試一次.");
                                    continue;
                                }
                                chosenCard.use(player, enemies.get(enemyChoice));
                            }
                        }
                        player.discardPile.add(chosenCard); // Add used card to discard pile
                        player.hand.remove(choice); // Remove the card from hand

                        // Print remaining hand
                    
                        
                    } else {
                        System.out.println("能量不足. 選擇另一張牌.");
                    }
                }

                // Enemies' turn
                if (!allEnemiesDefeated()) {
                    for (Enemy enemy : enemies) {
                        if (enemy.health > 0) {
                            enemy.act(player);
                        }
                    }
                }

                // End of player's turn, reset energy and block
                player.endTurn();
            }

            if (player.health <= 0) {
                System.out.println("你已被擊敗!");
                return;
            }

            // Between level 2 and 3, offer a rest stop
            if (level == 1) {
                System.out.println("\n--- 休息站 ---");
                System.out.println("你可以恢復生命並選擇一張牌添加到你的牌組.");
                player.health = Math.min(player.health + 20, 80);
                System.out.println("你當前的 HP: " + player.health);

                System.out.println("選擇一張牌添加到你的牌組:");
                System.out.println("1. Muscle (增加2點基礎攻擊力，持續1回合)");
                System.out.println("2. Combust (對所有敵人造成5點傷害，自己損失1點生命)");
                System.out.println("3. Bash (造成8點傷害，使敵人虚弱2回合)");

                int cardChoice = scanner.nextInt();
                switch (cardChoice) {
                    case 1:
                        player.addCardToDeck(new FlexCard("Muscle (增加2點基礎攻擊力，持續1回合)", 0, 0, 0));
                        break;
                    case 2:
                        player.addCardToDeck(new CombustCard("Combust (對所有敵人造成5點傷害，自己損失1點生命)", 5, 0, 0));
                        break;
                    case 3:
                        player.addCardToDeck(new BashCard("Bash (造成8點傷害，使敵人虚弱2回合)", 8, 0, 2));
                        break;
                    default:
                        System.out.println("無效選擇. 未添加任何牌.");
                }
                Collections.shuffle(player.deck);
            }
        }

        // Level 3 with two bosses
        System.out.println("\n--- Level 3 ---");
        Enemy cPlusPlus = new Enemy("C++", 50, 15);
        BossEnemy pythonBoss = new BossEnemy("Python Boss", 80, 20);
        enemies.add(cPlusPlus);
        enemies.add(pythonBoss);

        int turnCounter = 0;
        while (player.health > 0 && !allEnemiesDefeated()) {
            turnCounter++;
            System.out.println("\nLevel 3 - Turn " + turnCounter);

            for (Enemy enemy : enemies) {
                if (enemy.health > 0) {
                    System.out.println(enemy.name + " HP: " + enemy.health + " (" + enemy.getNextAction() + ")");
                }
            }

            System.out.println("你的 HP: " + player.health);
            System.out.println("你的能量: " + player.energy);
            if (player.muscleTurns > 0) {
                System.out.println("Muscle 效果: " + player.muscleTurns + " 回合剩餘");
            }

            // Draw cards to hand
            player.refillHand();

            // Player's turn
            while (player.energy > 0 && player.health > 0 && !allEnemiesDefeated()) {
                if (player.hand.isEmpty()) {
                    break;
                }

                // Show hand
                System.out.println("選擇要打出的牌 (或輸入 -1 結束回合):");
                for (int i = 0; i < player.hand.size(); i++) {
                    Card card = player.hand.get(i);
                    System.out.println((i + 1) + ". " + card.name + " (傷害: " + card.damage + ", 格擋: " + card.block + ", 能量消耗: " + card.energyCost + ")");
                }

                // Player chooses a card
                int choice = scanner.nextInt() - 1;
                if (choice == -2) {
                    System.out.println("結束回合.");
                    break;
                }
                if (choice < 0 || choice >= player.hand.size()) {
                    System.out.println("無效選擇. 請再試一次.");
                    continue;
                }

                Card chosenCard = player.hand.get(choice);
                if (player.energy >= chosenCard.energyCost) {
                    player.energy -= chosenCard.energyCost;
                    if (chosenCard.name.contains("Muscle")) {
                        player.useMuscle();
                        System.out.println("你使用了 Muscle! 基礎攻擊力增加2，持續1回合.");
                    } else if (chosenCard.name.contains("Combust")) {
                        player.health -= 1;
                        for (Enemy enemy : enemies) {
                            if (enemy.health > 0) {
                                enemy.takeDamage(chosenCard.damage);
                            }
                        }
                        System.out.println("你使用了 Combust! 對所有敵人造成5點傷害，自身損失1點生命.");
                    } else {
                        if (chosenCard.name.contains("Defend")) {
                            chosenCard.use(player, null);
                        } else {
                            System.out.println("選擇一個敵人進行攻擊:");
                            for (int i = 0; i < enemies.size(); i++) {
                                if (enemies.get(i).health > 0) {
                                    System.out.println((i + 1) + ". " + enemies.get(i).name + " (HP: " + enemies.get(i).health + ")");
                                }
                            }
                            System.out.println("請輸入要攻擊的敵人編號:");
                            int enemyChoice = scanner.nextInt() - 1;
                            if (enemyChoice < 0 || enemyChoice >= enemies.size() || enemies.get(enemyChoice).health <= 0) {
                                System.out.println("無效選擇. 請再試一次.");
                                continue;
                            }
                            chosenCard.use(player, enemies.get(enemyChoice));
                        }
                    }
                    player.discardPile.add(chosenCard); // Add used card to discard pile
                    player.hand.remove(choice); // Remove the card from hand

                    
                  
                } else {
                    System.out.println("能量不足. 選擇另一張牌.");
                }
            }

            // Enemies' turn
            if (!allEnemiesDefeated()) {
                for (Enemy enemy : enemies) {
                    if (enemy.health > 0) {
                        enemy.act(player);
                    }
                }
            }

            // End of player's turn, reset energy and block
            player.endTurn();
        }

        if (player.health <= 0) {
            System.out.println("你已被擊敗!");
        } else {
            System.out.println("恭喜你! 你已經擊敗了所有敵人!");
        }

        scanner.close();
    }

    public static boolean allEnemiesDefeated() {
        for (Enemy enemy : enemies) {
            if (enemy.health > 0) {
                return false;
            }
        }
        return true;
    }
}
