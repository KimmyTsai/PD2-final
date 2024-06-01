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

        // Level 1
        System.out.println("Entering Level 1");
        Enemy python = new Enemy("python", 30, 7);
        combat(player, new Enemy[]{python}, scanner, 1);

        // Level 2
        System.out.println("Entering Level 2");
        Enemy java = new Enemy("java", 40, 9);
        Enemy javascript = new Enemy("javascript", 40, 9);
        combat(player, new Enemy[]{java, javascript}, scanner, 2);

        // Resting station before Level 3
        rest(player, scanner);

        // Level 3 (Boss)
        System.out.println("Entering Level 3");
        Enemy cPlusPlus = new Enemy("c++", 80, 10);
        Enemy c = new Enemy("c", 80, 10);
        cPlusPlus.ally = c;
        c.ally = cPlusPlus;
        combat(player, new Enemy[]{cPlusPlus, c}, scanner, 3);

        System.out.println("Congratulations! You have completed all levels.");
        scanner.close();
    }

    public static void combat(Player player, Enemy[] enemies, Scanner scanner, int level) {
        int turnCounter = 0;

        while (player.health > 0 && enemiesStillAlive(enemies)) {
            turnCounter++;
            System.out.println("\nLevel " + level + " - Turn " + turnCounter);
            System.out.println("Your HP: " + player.health);
            for (Enemy enemy : enemies) {
                System.out.println(enemy.name + " HP: " + enemy.health + " (" + enemy.nextAction() + ")");
            }
            System.out.println("Your energy: " + player.energy);
            if (player.muscleTurns > 0) {
                System.out.println("Muscle effect: " + player.muscleTurns + " turns remaining");
            }

            // Draw cards to hand
            player.refillHand();

            // Player's turn
            while (player.energy > 0 && player.health > 0 && enemiesStillAlive(enemies)) {
                if (player.hand.isEmpty()) {
                    System.out.println("No cards left in hand. Ending turn.");
                    break;
                }

                // Show hand
                System.out.println("Choose a card to play:");
                for (int i = 0; i < player.hand.size(); i++) {
                    Card card = player.hand.get(i);
                    System.out.println((i + 1) + ". " + card.name + " (Damage: " + card.damage + ", Block: " + card.block + ", Energy: " + card.energyCost + ")");
                }

                int choice = scanner.nextInt() - 1;
                if (choice < 0 || choice >= player.hand.size()) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }

                Card chosenCard = player.hand.get(choice);
                if (player.energy >= chosenCard.energyCost) {
                    System.out.println("Choose an enemy to target:");
                    for (int i = 0; i < enemies.length; i++) {
                        System.out.println((i + 1) + ". " + enemies[i].name + " (HP: " + enemies[i].health + ")");
                    }
                    int targetChoice = scanner.nextInt() - 1;
                    if (targetChoice < 0 || targetChoice >= enemies.length) {
                        System.out.println("Invalid target choice. Try again.");
                        continue;
                    }

                    player.energy -= chosenCard.energyCost;
                    chosenCard.use(player, enemies[targetChoice]);

                    if (enemies[targetChoice].health <= 0) {
                        System.out.println(enemies[targetChoice].name + " has been defeated!");
                    }

                    player.hand.remove(choice);
                } else {
                    System.out.println("Not enough energy. Choose another card.");
                }
            }

            // End player's turn
            player.endTurn();

            // Enemies' turn
            for (Enemy enemy : enemies) {
                if (enemy.health > 0) {
                    enemy.act(player);
                }
            }
        }

        if (player.health <= 0) {
            System.out.println("You have been defeated...");
        } else {
            System.out.println("You have defeated all enemies!");
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

    public static void rest(Player player, Scanner scanner) {
        System.out.println("You have reached a resting station.");
        System.out.println("1. Heal (recover 20 HP)");
        System.out.println("2. Remove a card from your deck");
        System.out.println("3. Add a special card to your deck");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                player.health += 20;
                System.out.println("You have recovered 20 HP. Your current HP is " + player.health);
                break;
            case 2:
                System.out.println("Choose a card to remove:");
                for (int i = 0; i < player.deck.size(); i++) {
                    System.out.println((i + 1) + ". " + player.deck.get(i).name);
                }
                int removeChoice = scanner.nextInt() - 1;
                if (removeChoice >= 0 && removeChoice < player.deck.size()) {
                    Card removedCard = player.deck.remove(removeChoice);
                    System.out.println("You have removed " + removedCard.name + " from your deck.");
                } else {
                    System.out.println("Invalid choice.");
                }
                break;
            case 3:
                System.out.println("Choose a special card to add:");
                System.out.println("1. Muscle");
                System.out.println("2. Combust");
                System.out.println("3. Bash");

                int specialChoice = scanner.nextInt();
                Card specialCard;
                switch (specialChoice) {
                    case 1:
                        specialCard = new FlexCard("Muscle", 0, 0, 0);
                        break;
                    case 2:
                        specialCard = new CombustCard("Combust", 5, 0, 0);
                        break;
                    case 3:
                        specialCard = new BashCard("Bash", 8, 0, 2);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                player.addCardToDeck(specialCard);
                System.out.println("You added " + specialCard.name + " to your deck.");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
}
