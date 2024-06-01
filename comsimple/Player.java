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
    int vulnerableTurns;
    int weakTurns;
    ArrayList<Card> deck;
    ArrayList<Card> hand;
    ArrayList<Card> discardPile;

    Player(int health, int baseAttack) {
        this.name = "Player";
        this.health = health;
        this.block = 0;
        this.energy = 3;
        this.baseAttack = baseAttack;
        this.muscleTurns = 0;
        this.vulnerableTurns = 0;
        this.weakTurns = 0;
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
            }
        }
        if (vulnerableTurns > 0) {
            vulnerableTurns--;
        }
        if (weakTurns > 0) {
            weakTurns--;
        }
    }

    void useMuscle() {
        baseAttack += 2;
        muscleTurns = 1;
    }

    void takeDamage(int damage) {
        int effectiveDamage = damage - block;
        if (vulnerableTurns > 0) {
            effectiveDamage *= 1.5;  // 增加易伤效果
        }
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

    void applyVulnerable(int turns) {
        vulnerableTurns += turns;
    }

    void applyWeak(int turns) {
        weakTurns += turns;
    }
}
