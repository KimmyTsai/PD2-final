package comsimple;

class Vulnerable {
    int duration;

    Vulnerable(int duration) {
        this.duration = duration;
    }

    void apply(Player player) {
        player.isVulnerable = true;
    }

    void remove(Player player) {
        player.isVulnerable = false;
    }
}
