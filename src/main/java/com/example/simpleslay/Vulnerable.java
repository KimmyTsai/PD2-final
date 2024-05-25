package com.example.simpleslay;

public class Vulnerable {
    private int duration;

    public Vulnerable(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void tick() {
        if (duration > 0) {
            duration--;
        }
    }

    public boolean isActive() {
        return duration > 0;
    }

    public int apply(int damage) {
        if (isActive()) {
            return (int) (damage * 1.5);
        }
        return damage;
    }
}
