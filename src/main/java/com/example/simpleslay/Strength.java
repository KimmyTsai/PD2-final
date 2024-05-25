package com.example.simpleslay;

public class Strength {
    private int amount;
    private int duration;

    public Strength(int amount, int duration) {
        this.amount = amount;
        this.duration = duration;
    }

    public int getAmount() {
        return amount;
    }

    public void tick() {
        if (duration > 0) {
            duration--;
        }
    }

    public boolean isActive() {
        return duration > 0;
    }
}
