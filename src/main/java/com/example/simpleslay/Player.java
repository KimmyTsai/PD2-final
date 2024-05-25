package com.example.simpleslay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int block;
    private int energy;
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> discardPile;
    private List<Strength> strengths;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.block = 0;
        this.energy = 3;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.strengths = new ArrayList<>();
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getBlock() {
        return block;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public List<Strength> getStrengths() {
        return strengths;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void takeDamage(int damage) {
        int effectiveDamage = damage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
        }
        block = 0;
    }

    public void gainBlock(int block) {
        this.block += block;
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public void useCard(Card card, List<Enemy> enemies) {
        if (energy >= card.getEnergyCost()) {
            card.use(this, enemies);
            energy -= card.getEnergyCost();
            hand.remove(card);
            discardPile.add(card);
        }
    }

    public void endTurn() {
        energy = 3;
        discardPile.addAll(hand);
        hand.clear();
        for (Strength strength : strengths) {
            strength.tick();
        }
        strengths.removeIf(strength -> !strength.isActive());
    }

    public void applyStrengthEffect(Strength strength) {
        strengths.add(strength);
    }

    public int applyStrength(int baseDamage) {
        int totalStrength = strengths.stream()
                                     .filter(Strength::isActive)
                                     .mapToInt(Strength::getAmount)
                                     .sum();
        return baseDamage + totalStrength;
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {
            if (deck.isEmpty()) {
                reshuffleDeck();
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(deck.size() - 1));
            }
        }
    }

    public void reshuffleDeck() {
        deck.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(deck);
    }

    public void resetBlock() {
        block = 0;
    }
}
