package comsimple;

abstract class Card {
    String name;
    int damage;
    int block;
    int energyCost;

    Card(String name, int damage, int block, int energyCost) {
        //String name, int damage, int block, int energyCost, String imagePath
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.energyCost = energyCost;
    }

    abstract void use(Player player, Enemy enemy);
}
