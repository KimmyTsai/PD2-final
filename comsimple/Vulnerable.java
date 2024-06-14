package comsimple;

public class Vulnerable {
    int duration;

    public Vulnerable(int duration) {
        this.duration = duration;
    }

    public void apply(Player player) {
        player.isVulnerable = true;
        player.vulnerableDuration = duration;
        //System.out.println(player.name + " is now Vulnerable for " + duration + " turns.");
    }
}
