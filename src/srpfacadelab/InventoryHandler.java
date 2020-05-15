package srpfacadelab;

import java.util.List;
import java.util.ArrayList;

public class InventoryHandler {

    private final IGameEngine gameEngine;

    public InventoryHandler(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void useItem(Item item, RpgPlayer player) {
        if (item.getName().equals("Stink Bomb"))
        {
            List<IEnemy> enemies = gameEngine.getEnemiesNear(player);

            for (IEnemy enemy: enemies){
                enemy.takeDamage(100);
            }
        }
    }

    public boolean pickUpItem(Item item, RpgPlayer player) {
        int weight = calculateInventoryWeight(player.getInventory());
        if (weight + item.getWeight() > player.getCarryingCapacity())
            return false;

        if (item.isUnique() && checkIfItemExistsInInventory(item, player.getInventory()))
            return false;

        // Don't pick up items that give health, just consume them.
        if (item.getHeal() > 0) {
            player.setHealth(player.getHealth() + item.getHeal());

            if (player.getHealth() > player.getMaxHealth())
                player.setHealth(player.getMaxHealth());

            if (item.getHeal() > 500) {
                gameEngine.playSpecialEffect("green_swirly");
            }

            return true;
        }

        if (item.isRare())
            gameEngine.playSpecialEffect("cool_swirly_particles");

        if (item.isRare() && item.isUnique())
            gameEngine.playSpecialEffect("blue_swirly");
        
        player.getInventory().add(item);

        calculateStats(player);

        return true;
    }

    public void calculateStats(RpgPlayer player) {
        for (Item i: player.getInventory()) {
            player.setArmour(player.getArmour() + i.getArmour());
        }
    }

    public boolean checkIfItemExistsInInventory(Item item, List<Item> inventory) {
        for (Item i: inventory) {
            if (i.getId() == item.getId())
                return true;
        }
        return false;
    }

    public int calculateInventoryWeight(List<Item> inventory) {
        int sum=0;
        for (Item i: inventory) {
            sum += i.getWeight();
        }
        return sum;
    }
}
