package srpfacadelab;

import java.util.List;
import java.util.ArrayList;


public class RpgPlayer {
    public static final int MAX_CARRYING_CAPACITY = 1000;

    private final IGameEngine gameEngine;
    
    private final ItemFacade itemFacade;

    private int health;

    private int maxHealth;

    private int armour;

    private List<Item> inventory;

    // How much the player can carry in pounds
    private int carryingCapacity;

    public RpgPlayer(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
        inventory = new ArrayList<Item>();
        itemFacade = new ItemFacade();
        carryingCapacity = MAX_CARRYING_CAPACITY;
    }

    public void useItem(Item item, IGameEngine gameEngine) {
        itemFacade.useItem(item, gameEngine, this);
    }

    public boolean pickUpItem(Item item) {
        int itemHeal;
        int weight = calculateInventoryWeight();
        if (weight + itemFacade.getItemWeight(item) > carryingCapacity)
            return false;

        if (itemFacade.isItemUnique(item) && checkIfItemExistsInInventory(item))
            return false;

        itemHeal = itemFacade.pickUpItem(item, gameEngine);

        if (itemHeal > 0) {
            health += itemHeal;

            if (health > maxHealth)
                health = maxHealth;

            return true;
        }

        // Don't pick up items that give health, just consume them.
        if (itemHeal == 0)
            inventory.add(item);

        calculateStats();

        return true;
    }

    private void calculateStats() {
        for (Item i: inventory) {
            armour += i.getArmour();
        }
    }

    private boolean checkIfItemExistsInInventory(Item item) {
        for (Item i: inventory) {
            if (i.getId() == item.getId())
                return true;
        }
        return false;
    }

    private int calculateInventoryWeight() {
        int sum=0;
        for (Item i: inventory) {
            sum += i.getWeight();
        }
        return sum;
    }

    public void takeDamage(int damage) {
        if (this.calculateInventoryWeight() < this.getCarryingCapacity() * 0.5)
            damage = (int)Math.round(damage * 0.75); 

        if (damage < armour) {
            gameEngine.playSpecialEffect("parry");
        }

        int damageToDeal = damage - armour;
        health -= damageToDeal;

        gameEngine.playSpecialEffect("lots_of_gore");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getArmour() {
        return armour;
    }

    private void setArmour(int armour) {
        this.armour = armour;
    }

    public int getCarryingCapacity() {
        return carryingCapacity;
    }

    private void setCarryingCapacity(int carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }
}
