package com.example.simpleslay;

import java.util.List;
import java.util.ArrayList;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private int block;
    private int turnCounter;
    private List<Vulnerable> effects;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.block = 0;
        this.turnCounter = 0;
        this.effects = new ArrayList<>();
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getBlock() {
        return block;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public List<Vulnerable> getEffects() {
        return effects;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void takeDamage(int damage) {
        for (Vulnerable effect : effects) {
            if (effect.isActive()) {
                damage = effect.apply(damage);
                break;
            }
        }
        int effectiveDamage = damage - block;
        if (effectiveDamage > 0) {
            health -= effectiveDamage;
        }
        block = 0;
    }

    public void applyEffect(Vulnerable effect) {
        effects.add(effect);
    }

    public void act(Player player) {
        turnCounter++;
        if (turnCounter % 5 == 0) {
            health += 5;
            System.out.println("Enemy heals for 5 health!");
        } else if (turnCounter % 3 == 0) {
            block += 5;
            System.out.println("Enemy gains 5 block!");
        } else {
            System.out.println("Enemy attacks you for " + damage + " damage!");
            player.takeDamage(damage);
        }
    }

    public void endTurn() {
        for (Vulnerable effect : effects) {
            effect.tick();
        }
    }
}
