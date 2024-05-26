package comsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SimpleSlay {
    public static ArrayList<Enemy> enemies;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize player
        Player player = new Player(80, 0);
        // Create cards and add to player's deck
        for (int i = 0; i < 10; i++) {
            player.addCardToDeck(new AttackCard("Strike", 6, 0, 1));
        }
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new DefendCard("Defend", 0, 5, 1));
        }
        player.addCardToDeck(new FlexCard("Muscle", 0, 0, 0));
        player.addCardToDeck(new CombustCard("Combust", 5, 0, 0));
        player.addCardToDeck(new BashCard("Bash", 8, 0, 2));

        Collections.shuffle(player.deck);

        // Start the game with four levels
        String[] enemyNames = {"python", "java", "javascript", "fortran"};
        enemies = new ArrayList<>();
        for (int level = 0; level < 4; level++) {
            String enemyName = enemyNames[level];
            System.out.println("\n--- Level " + (level + 1) + " ---");
            int enemyHealth = 20 + (level + 1) * 10;
            int enemyDamage = 5 + (level + 1) * 2;
            Enemy enemy = new Enemy(enemyName, enemyHealth, enemyDamage);
            enemies.add(enemy);

            int turnCounter = 0;

            // Each level combat
            while (player.health > 0 && enemy.health > 0) {
                turnCounter++;
                System.out.println("\nLevel " + (level + 1) + " - Turn " + turnCounter);
                System.out.println("Your HP: " + player.health);
                System.out.println(enemy.name + " HP: " + enemy.health);
                System.out.println(enemy.name + " Block: " + enemy.block);
                System.out.println("Your energy: " + player.energy);
                if (player.muscleTurns > 0) {
                    System.out.println("Muscle effect: " + player.muscleTurns + " turns remaining");
                }

                // Draw cards to hand
                player.refillHand();

                // Player's turn
                while (player.energy > 0 && player.health > 0 && enemy.health > 0) {
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
                        chosenCard.use(player, enemy);
                        player.discardPile.add(chosenCard);  // Add used card to discard pile
                    } else {
                        System.out.println("Not enough energy. Choose another card.");
                        continue;
                    }

                    if (enemy.health <= 0) {
                        System.out.println("You defeated " + enemy.name + "!");
                        break;
                    }

                    player.hand.remove(choice);  // Remove the card from hand
                }

                if (enemy.health > 0) {
                    enemy.act(player);
                    if (player.health <= 0) {
                        System.out.println("You have been defeated!");
                        return;
                    }
                }

                // End of player's turn, reset energy and block
                player.endTurn();
            }

            // Heal player before next level
            player.health = Math.min(player.health + 10, 80);
            System.out.println("You have been healed. Your current HP: " + player.health);
        }

        // Level 5 with two enemies
        System.out.println("\n--- Level 5 ---");
        Enemy cPlusPlus = new Enemy("c++", 80, 10);
        Enemy c = new Enemy("c", 80, 10);
        enemies.add(cPlusPlus);
        enemies.add(c);
        int turnCounter = 0;

        while (player.health > 0 && (cPlusPlus.health > 0 || c.health > 0)) {
            turnCounter++;
            System.out.println("\nLevel 5 - Turn " + turnCounter);
            System.out.println("Your HP: " + player.health);
            System.out.println("c++ HP: " + cPlusPlus.health);
            System.out.println("c HP: " + c.health);
            System.out.println("c++ Block: " + cPlusPlus.block);
            System.out.println("c Block: " + c.block);
            System.out.println("Your energy: " + player.energy);
            if (player.muscleTurns > 0) {
                System.out.println("Muscle effect: " + player.muscleTurns + " turns remaining");
            }

            // Draw cards to hand
            player.refillHand();

            // Player's turn
            while (player.energy > 0 && player.health > 0 && (cPlusPlus.health > 0 || c.health > 0)) {
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
                        cPlusPlus.takeDamage(chosenCard.damage);
                        c.takeDamage(chosenCard.damage);
                        System.out.println("You used Combust! Dealt 5 damage to all enemies and lost 1 health.");
                    } else if (cPlusPlus.health > 0) {
                        chosenCard.use(player, cPlusPlus);
                    } else {
                        chosenCard.use(player, c);
                    }
                    player.block += chosenCard.block;
                    System.out.println("You played " + chosenCard.name);
                    player.discardPile.add(chosenCard);  // Add used card to discard pile
                } else {
                    System.out.println("Not enough energy. Choose another card.");
                    continue;
                }

                if (cPlusPlus.health <= 0 && c.health <= 0) {
                    System.out.println("You defeated c++ and c!");
                    break;
                }

                player.hand.remove(choice);  // Remove the card from hand
            }

            if (cPlusPlus.health > 0) {
                cPlusPlus.act(player);
            }
            if (c.health > 0) {
                c.act(player);
            }

            if (player.health <= 0) {
                System.out.println("You have been defeated!");
                return;
            }

            // End of player's turn, reset energy and block
            player.endTurn();
        }

        System.out.println("Congratulations! You have completed all levels.");
        scanner.close();
    }
}
