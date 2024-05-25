package com.example.simpleslay;

import java.util.List;

public abstract class Card {
    protected String name;
    protected int damage;
    protected int block;
    protected int energyCost;

    public Card(String name, int damage, int block, int energyCost) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getBlock() {
        return block;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public abstract void use(Player player, List<Enemy> enemies);
}
