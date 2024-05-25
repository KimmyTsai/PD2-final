import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Card {
    String name;
    int damage;
    int block;

    Card(String name, int damage, int block) {
        this.name = name;
        this.damage = damage;
        this.block = block;
    }
}

class Player {
    int health;
    int block;
    ArrayList<Card> deck;

    Player(int health) {
        this.health = health;
        this.block = 0;
        this.deck = new ArrayList<>();
    }

    void addCardToDeck(Card card) {
        deck.add(card);
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
}

class Enemy {
    int health;
    int damage;

    Enemy(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }
}

public class SimpleSlay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Initialize player and enemy
        Player player = new Player(30);
        Enemy enemy = new Enemy(20, 5);

        // Create some cards and add to player's deck
        player.addCardToDeck(new Card("Strike", 6, 0));
        player.addCardToDeck(new Card("Defend", 0, 5));

        System.out.println("Welcome to the simplified version of Slay the Spire!");

        while (player.health > 0 && enemy.health > 0) {
            System.out.println("\nYour HP: " + player.health);
            System.out.println("Enemy HP: " + enemy.health);

            System.out.println("\nChoose a card to play:");
            for (int i = 0; i < player.deck.size(); i++) {
                Card card = player.deck.get(i);
                System.out.println((i + 1) + ". " + card.name + " (Damage: " + card.damage + ", Block: " + card.block + ")");
            }

            int choice = scanner.nextInt() - 1;
            if (choice < 0 || choice >= player.deck.size()) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            Card chosenCard = player.deck.get(choice);
            enemy.health -= chosenCard.damage;
            player.block += chosenCard.block;

            if (enemy.health <= 0) {
                System.out.println("You defeated the enemy!");
                break;
            }

            player.takeDamage(enemy.damage);
            System.out.println("Enemy attacks you for " + enemy.damage + " damage!");

            if (player.health <= 0) {
                System.out.println("You have been defeated!");
            }
        }

        scanner.close();
    }
}
