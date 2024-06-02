package comsimple;

public class Weak {
    int duration;

    public Weak(int duration) {
        this.duration = duration;
    }

    public void apply(Player player) {
        player.isWeak = true;
        player.weakDuration = duration;
        System.out.println(player.name + " is now Weak for " + duration + " turns.");
    }
}
