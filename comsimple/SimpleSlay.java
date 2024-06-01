package comsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SimpleSlay {
    public static ArrayList<Enemy> enemies;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize player
        Player player = new Player(80, 0);
        // Create cards and add to player's deck
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new AttackCard("Strike", 6, 0, 1));
        }
        for (int i = 0; i < 4; i++) {
            player.addCardToDeck(new DefendCard("Defend", 0, 5, 1));
        }
        for (int i = 0; i < 2; i++) {
            player.addCardToDeck(new FlexCard("Muscle", 0, 0, 0));
            player.addCardToDeck(new CombustCard("Combust", 5, 0, 0));
            player.addCardToDeck(new BashCard("Bash", 8, 0, 2));
        }

        Collections.shuffle(player.deck);

        // Start the game with three levels
        String[] enemyNames = {"python", "java", "javascript"};
        enemies = new ArrayList<>();
        for (int level = 0; level < 2; level++) {
            System.out.println("\n--- Level " + (level + 1) + " ---");

            if (level == 0) {
                // First level with one enemy
                Enemy python = new Enemy("python", 30, 7);
                enemies.add(python);
                combat(player, new Enemy[]{python});
            } else {
                // Second level with two enemies
                Enemy java = new Enemy("java", 40, 8);
                Enemy javascript = new Enemy("javascript", 40, 8);
                enemies.add(java);
                enemies.add(javascript);
                combat(player, new Enemy[]{java, javascript});
            }

            if (player.health <= 0) {
                System.out.println("You have been defeated!");
                return;
            }

            // Rest stop between levels
            System.out.println("\n--- Rest Stop ---");
            System.out.println("You can heal or choose a new card:");
            System.out.println("1. Heal 20 HP");
            System.out.println("2. Choose a new card (Muscle, Combust, Bash)");

            int choice = scanner.nextInt();
            if (choice == 1) {
                player.health = Math.min(player.health + 20, 80);
                System.out.println("You have been healed. Your current HP: " + player.health);
            } else if (choice == 2) {
                System.out.println("Choose a card to add to your deck:");
                System.out.println("1. Muscle");
                System.out.println("2. Combust");
                System.out.println("3. Bash");

                int cardChoice = scanner.nextInt();
                if (cardChoice == 1) {
                    player.addCardToDeck(new FlexCard("Muscle", 0, 0, 0));
                } else if (cardChoice == 2) {
                    player.addCardToDeck(new CombustCard("Combust", 5, 0, 0));
                } else if (cardChoice == 3) {
                    player.addCardToDeck(new BashCard("Bash", 8, 0, 2));
                }
            }
            Collections.shuffle(player.deck);
        }

        // Third level with two bosses
        System.out.println("\n--- Level 3 (Boss) ---");
        Enemy cPlusPlus = new Enemy("c++", 80, 10) {
            @Override
            void act(Player player) {
                if (this.turnCounter % 2 == 1) {
                    super.act(player);
                    player.applyWeak();
                } else {
                    heal(this.ally, 10);
                }
                this.turnCounter++;
            }
        };
        Enemy c = new Enemy("c", 80, 10) {
            @Override
            void act(Player player) {
                super.act(player);
                player.applyVulnerable();
            }
        };
        // 設置盟友
        cPlusPlus.ally = c;
        c.ally = cPlusPlus;
        enemies.add(cPlusPlus);
        enemies.add(c);

        combat(player, new Enemy[]{cPlusPlus, c});

        if (player.health > 0) {
            System.out.println("Congratulations! You have completed all levels.");
        } else {
            System.out.println("You have been defeated!");
        }
        scanner.close();
    }

    public static void combat(Player player, Enemy[] enemies) {
        int turnCounter = 0;

        while (player.health > 0 && enemiesStillAlive(enemies)) {
            turnCounter++;
            System.out.println("\nTurn " + turnCounter);
            System.out.println("Your HP: " + player.health);
            for (Enemy enemy : enemies) {
                if (enemy.health > 0) {
                    System.out.println(enemy.name + " HP: " + enemy.health);
                    System.out.println(enemy.name + " Block: " + enemy.block);
                }
            }
            System.out.println("Your energy: " + player.energy);
            if (player.muscleTurns > 0) {
                System.out.println("Muscle effect: " + player.muscleTurns + " turns remaining");
            }

            // Draw cards to hand
            player.refillHand();

            // Player's turn
            while (player.energy > 0 && player.health > 0 && enemiesStillAlive(enemies)) {
                // Show hand
                System.out.println("Choose a card to play:");
                for (int i = 0; i < player.hand.size(); i++) {
                    Card card = player.hand.get(i);
                    System.out.println((i + 1) + ". " + card.name + " (Damage: " + card.damage + ", Block: " + card.block + ", Energy: " + card.energyCost + ")");
                }

                // Player chooses a card
                int choice = scanner.nextInt() - 1;
                if (choice < 0 || choice >= player.hand.size()) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }

                Card chosenCard = player.hand.get(choice);
                if (player.energy >= chosenCard.energyCost) {
                    player.energy -= chosenCard.energyCost;
                    if (chosenCard.name.equals("Muscle")) {
                        player.useMuscle();
                        System.out.println("You used Muscle! Base attack increased by 2 for 1 turn.");
                    } else if (chosenCard.name.equals("Combust")) {
                        player.health -= 1;
                        for (Enemy enemy : enemies) {
                            enemy.takeDamage(chosenCard.damage);
                        }
                        System.out.println("You used Combust! Dealt 5 damage to all enemies and lost 1 health.");
                    } else {
                        for (Enemy enemy : enemies) {
                            if (enemy.health > 0) {
                                chosenCard.use(player, enemy);
                                break;
                            }
                        }
                    }
                    player.block += chosenCard.block;
                    System.out.println("You played " + chosenCard.name);
                    player.discardPile.add(chosenCard);  // Add used card to discard pile
                } else {
                    System.out.println("Not enough energy. Choose another card.");
                    continue;
                }

                if (!enemiesStillAlive(enemies)) {
                    System.out.println("You defeated all enemies!");
                    break;
                }

                player.hand.remove(choice);  // Remove the card from hand
            }

            for (Enemy enemy : enemies) {
                if (enemy.health > 0) {
                    enemy.act(player);
                }
            }

            if (player.health <= 0) {
                System.out.println("You have been defeated!");
                return;
            }

            // End of player's turn, reset energy and block
            player.endTurn();
        }
    }

    public static boolean enemiesStillAlive(Enemy[] enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.health > 0) {
                return true;
            }
        }
        return false;
    }
}
