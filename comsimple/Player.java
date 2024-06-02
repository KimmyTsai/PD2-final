package comsimple;

import java.util.ArrayList;
import java.util.Collections;

class Player {
    String name;
    int health;
    int block;
    int energy;
    int baseAttack;
    int muscleTurns;
    boolean isVulnerable;
    int vulnerableDuration;
    boolean isWeak;
    int weakDuration;
    ArrayList<Card> deck;
    ArrayList<Card> hand;
    ArrayList<Card> discardPile;

    Player(String name, int health, int baseAttack) {
        this.name = name;
        this.health = health;
        this.block = 0;
        this.energy = 3;
        this.baseAttack = baseAttack;
        this.muscleTurns = 0;
        this.isVulnerable = false;
        this.vulnerableDuration = 0;
        this.isWeak = false;
        this.weakDuration = 0;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    void addCardToDeck(Card card) {
        deck.add(card);
    }

    void drawCards(int numCards) {
        for (int i = 0; i < numCards; i++) {
            if (deck.isEmpty()) {
                reshuffleDiscardPileIntoDeck();
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            }
        }
    }

    void refillHand() {
        drawCards(5 - hand.size());
    }

    void reshuffleDiscardPileIntoDeck() {
        Collections.shuffle(discardPile);
        deck.addAll(discardPile);
        discardPile.clear();
    }

    void endTurn() {
        energy = 3;
        block = 0;
        discardPile.addAll(hand);
        hand.clear();
        refillHand();
        if (muscleTurns > 0) {
            muscleTurns--;
            if (muscleTurns == 0) {
                baseAttack -= 2;
                System.out.println("Muscle effect has ended. Base attack returned to normal.");
            }
        }
        if (isVulnerable) {
            vulnerableDuration--;
            if (vulnerableDuration == 0) {
                isVulnerable = false;
                System.out.println("Vulnerable effect has ended.");
            }
        }
        if (isWeak) {
            weakDuration--;
            if (weakDuration == 0) {
                isWeak = false;
                System.out.println("Weak effect has ended.");
            }
        }
    }

    void useMuscle() {
        baseAttack += 2;
        muscleTurns = 1;
    }

    void takeDamage(int damage) {
        if (isVulnerable) {
            damage = (int) (damage * 1.5);
        }
        int effectiveDamage = damage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
            block = 0;
        } else {
            block -= damage;
        }
    }

    void gainBlock(int amount) {
        block += amount;
    }

    int applyStrength(int baseDamage) {
        if (isWeak) {
            baseDamage = (int) (baseDamage * 0.5);
        }
        return baseDamage + baseAttack;
    }

    int getEffectiveAttack() {
        return baseAttack;
    }

    void discardCard(Card card) {
        hand.remove(card);
        discardPile.add(card);
    }
}
