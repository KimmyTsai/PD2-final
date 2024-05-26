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
    ArrayList<Card> deck;
    ArrayList<Card> hand;
    ArrayList<Card> discardPile;

    Player(int health, int baseAttack) {
        this.health = health;
        this.block = 0;
        this.energy = 3;
        this.baseAttack = baseAttack;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    void addCardToDeck(Card card) {
        deck.add(card);
    }

    void drawCards(int num) {
        for (int i = 0; i < num; i++) {
            if (deck.isEmpty()) {
                reshuffleDeck();
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(deck.size() - 1));
            }
        }
    }

    void reshuffleDeck() {
        deck.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(deck);
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
    }
}

class Enemy {
    int health;
    int damage;
    int block;
    int turnCounter;
    int attackCounter;
    int blockCounter;

    Enemy(int health, int damage) {
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
            System.out.println("Enemy gains 5 block!");
            if (blockCounter % 5 == 0) {
                health += 5;
                System.out.println("Enemy heals for 5 health!");
            }
        } else {
            System.out.println("Enemy attacks you for " + damage + " damage!");
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
        // Create 15 cards and add to player's deck
        for (int i = 0; i < 10; i++) {
            player.addCardToDeck(new Card("Strike", 6, 0, 1));
        }
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new Card("Defend", 0, 5, 1));
        }
        Collections.shuffle(player.deck);

        // Start the game with three levels
        for (int level = 1; level <= 3; level++) {
            System.out.println("\n--- Level " + level + " ---");
            int enemyHealth = 20 + level * 10;
            int enemyDamage = 3 + level;  // Lowered enemy attack power
            Enemy enemy = new Enemy(enemyHealth, enemyDamage);

            // Each level combat
            while (player.health > 0 && enemy.health > 0) {
                System.out.println("\nYour HP: " + player.health);
                System.out.println("Enemy HP: " + enemy.health);
                System.out.println("Enemy Block: " + enemy.block);

                // Draw cards to hand
                player.drawCards(5);

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
                        enemy.takeDamage(chosenCard.damage + player.baseAttack);
                        player.block += chosenCard.block;
                        System.out.println("You played " + chosenCard.name);
                    } else {
                        System.out.println("Not enough energy. Choose another card.");
                        continue;
                    }

                    if (enemy.health <= 0) {
                        System.out.println("You defeated the enemy!");
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

                // Draw new hand
                player.drawCards(5);
            }

            // Heal player before next level
            player.health = Math.min(player.health + 10, 30);
            System.out.println("You have been healed. Your current HP: " + player.health);
        }

        System.out.println("Congratulations! You have completed all levels.");
        scanner.close();
    }
}
