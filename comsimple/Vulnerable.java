package comsimple;

public class Vulnerable {
    int vulnerableDuration;

    Vulnerable(int vulnerableDuration) {
        this.vulnerableDuration = vulnerableDuration;
    }

    void apply(Enemy enemy) {
        enemy.isVulnerable = true;
        enemy.vulnerableDuration = vulnerableDuration;
    }
}
