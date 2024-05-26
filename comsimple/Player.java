package comsimple;

import java.util.ArrayList;
import java.util.Collections;

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

    void refillHand() {
        while (hand.size() < 5 && !deck.isEmpty()) {
            hand.add(deck.remove(0));
        }
        if (deck.isEmpty()) {
            Collections.shuffle(discardPile);
            deck.addAll(discardPile);
            discardPile.clear();
        }
    }

    void useMuscle() {
        baseAttack += 2;
        muscleTurns = 1;
    }

    void endTurn() {
        energy = 3;
        block = 0;
        if (muscleTurns > 0) {
            muscleTurns--;
            if (muscleTurns == 0) {
                baseAttack -= 2;
            }
        }
        discardPile.addAll(hand);
        hand.clear();
    }

    void takeDamage(int damage) {
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
        return baseDamage + baseAttack;
    }

    int getEffectiveAttack() {
        return baseAttack;
    }
}
