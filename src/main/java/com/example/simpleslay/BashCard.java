package com.example.simpleslay;

import java.util.List;

public class BashCard extends Card {
    private int vulnerableDuration;

    public BashCard() {
        super("Bash", 8, 0, 2);
        this.vulnerableDuration = 2;
    }

    @Override
    public void use(Player player, List<Enemy> enemies) {
        if (!enemies.isEmpty()) {
            Enemy enemy = enemies.get(0);
            enemy.takeDamage(player.applyStrength(damage));
            enemy.applyEffect(new Vulnerable(vulnerableDuration));
        }
    }
}
