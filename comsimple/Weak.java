package comsimple;

public class Weak {
    int weakDuration;

    Weak(int weakDuration) {
        this.weakDuration = weakDuration;
    }

    void apply(Enemy enemy) {
        enemy.isWeak = true;
        enemy.weakDuration = weakDuration;
    }
}
