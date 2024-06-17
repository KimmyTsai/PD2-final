package comsimple;

public abstract class Card {
    String name;
    public int damage;
    int block;
    public int energyCost;

    Card(String name, int damage, int block, int energyCost) {
        //String name, int damage, int block, int energyCost, String imagePath
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    abstract void use(Player player, Enemy enemy);
}
