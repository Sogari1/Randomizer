package items;

public class ItemEffect {
    private String name;
    private int healthChange;
    private int damageChange;
    private int speedChange;

    public ItemEffect(String name, int healthChange, int damageChange, int speedChange) {
        this.name = name;
        this.healthChange = healthChange;
        this.damageChange = damageChange;
        this.speedChange = speedChange;
    }

    public String getName() {
        return name;
    }

    public int getHealthChange() {
        return healthChange;
    }

    public int getDamageChange() {
        return damageChange;
    }

    public int getSpeedChange() {
        return speedChange;
    }
}
