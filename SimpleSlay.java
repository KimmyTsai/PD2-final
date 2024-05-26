package com.example.simpleslay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

class Card {
    String name;
    int damage;
    int block;
    int energyCost;

    Card(String name, int damage, int block, int energyCost) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }
}

class Player {
    int health;
    int block;
    int energy;
    int baseAttack;
    int muscleTurns;
    ArrayList<Card> deck;
    ArrayList<Card> hand;
    ArrayList<Card> discardPile;

    Player(int health, int baseAttack) {
        this.health = health;
        this.block = 0;
        this.energy = 3;
        this.baseAttack = baseAttack;
        this.muscleTurns = 0;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    void addCardToDeck(Card card) {
        deck.add(card);
    }

    void drawCards(int num) {
        for (int i = 0; i < num; i++) {
            if (deck.isEmpty() && !discardPile.isEmpty()) {
                reshuffleDeck();
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(deck.size() - 1));
            }
        }
    }

    void reshuffleDeck() {
        if (!discardPile.isEmpty()) {
            System.out.println("Out of cards! Reshuffling discard pile...");
            deck.addAll(discardPile);
            discardPile.clear();
            Collections.shuffle(deck);
        }
    }

    void resetBlock() {
        block = 0;
    }

    void takeDamage(int damage) {
        int effectiveDamage = damage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
        }
        resetBlock();
    }

    void endTurn() {
        energy = 3;
        discardPile.addAll(hand);
        hand.clear();
        if (muscleTurns > 0) {
            muscleTurns--;
        }
    }

    void refillHand() {
        drawCards(5 - hand.size());
    }

    void useMuscle() {
        muscleTurns = 2;
    }

    int getEffectiveAttack() {
        if (muscleTurns > 0) {
            return baseAttack + 2;
        }
        return baseAttack;
    }
}

class Enemy {
    String name;
    int health;
    int damage;
    int block;
    int turnCounter;
    int attackCounter;
    int blockCounter;

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.block = 0;
        this.turnCounter = 0;
        this.attackCounter = 0;
        this.blockCounter = 0;
    }

    void takeDamage(int damage) {
        int effectiveDamage = damage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
        }
        block = 0;
    }

    void act(Player player) {
        turnCounter++;
        attackCounter++;
        if (attackCounter % 3 == 0) {
            block += 5;
            blockCounter++;
            System.out.println(name + " gains 5 block!");
            if (blockCounter % 5 == 0) {
                health += 5;
                System.out.println(name + " heals for 5 health!");
            }
        } else {
            System.out.println(name + " attacks you for " + damage + " damage!");
            player.takeDamage(damage);
        }
    }
}

public class SimpleSlay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Initialize player
        Player player = new Player(30, 2);
        // Create cards and add to player's deck
        for (int i = 0; i < 10; i++) {
            player.addCardToDeck(new Card("Strike", 6, 0, 1));
        }
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new Card("Defend", 0, 5, 1));
        }
        player.addCardToDeck(new Card("Muscle", 0, 0, 0));  // Muscle card

        Collections.shuffle(player.deck);

        // Start the game with five levels
        String[] enemyNames = {"python", "java", "javascript", "fortran"};
        for (int level = 0; level < 4; level++) {
            String enemyName = enemyNames[level];
            System.out.println("\n--- Level " + (level + 1) + " ---");
            int enemyHealth = 20 + (level + 1) * 10;
            int enemyDamage = 3 + (level + 1);  // Lowered enemy attack power
            Enemy enemy = new Enemy(enemyName, enemyHealth, enemyDamage);

            int turnCounter = 0;

            // Each level combat
            while (player.health > 0 && enemy.health > 0) {
                turnCounter++;
                System.out.println("\nLevel " + (level + 1) + " - Turn " + turnCounter);
                System.out.println("Your HP: " + player.health);
                System.out.println("Your BaseAttack: " + player.baseAttack);
                System.out.println(enemy.name + " HP: " + enemy.health);
                System.out.println(enemy.name + " Block: " + enemy.block);
                System.out.println("Your energy: " + player.energy); // Output current energy
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
                        if (chosenCard.name.equals("Muscle")) {
                            player.useMuscle();
                            System.out.println("You used Muscle! Base attack increased by 2 for 2 turns.");
                        } else {
                            enemy.takeDamage(chosenCard.damage + player.getEffectiveAttack());
                            player.block += chosenCard.block;
                            System.out.println("You played " + chosenCard.name);
                        }
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
            player.health = Math.min(player.health + 10, 30);
            System.out.println("You have been healed. Your current HP: " + player.health);
        }

        // Level 5 with two enemies
        System.out.println("\n--- Level 5 ---");
        Enemy cPlusPlus = new Enemy("c++", 50, 5);
        Enemy c = new Enemy("c", 50, 5);
        int turnCounter = 0;

        while (player.health > 0 && (cPlusPlus.health > 0 || c.health > 0)) {
            turnCounter++;
            System.out.println("\nLevel 5 - Turn " + turnCounter);
            System.out.println("Your HP: " + player.health);
            System.out.println("c++ HP: " + cPlusPlus.health);
            System.out.println("c HP: " + c.health);
            System.out.println("c++ Block: " + cPlusPlus.block);
            System.out.println("c Block: " + c.block);
            System.out.println("Your energy: " + player.energy); // Output current energy
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
                        System.out.println("You used Muscle! Base attack increased by 2 for 2 turns.");
                    } else {
                        if (cPlusPlus.health > 0) {
                            cPlusPlus.takeDamage(chosenCard.damage + player.getEffectiveAttack());
                        } else {
                            c.takeDamage(chosenCard.damage + player.getEffectiveAttack());
                        }
                        player.block += chosenCard.block;
                        System.out.println("You played " + chosenCard.name);
                    }
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
