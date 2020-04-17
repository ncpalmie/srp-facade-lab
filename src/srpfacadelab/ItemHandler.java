package srpfacadelab;

import java.util.List;
import java.util.ArrayList;

public class ItemHandler {

    public ItemHandler(){
    }

    public void useStinkBomb(IGameEngine gameEngine, RpgPlayer player) {
        List<IEnemy> enemies = gameEngine.getEnemiesNear(player);

        for (IEnemy enemy: enemies){
            enemy.takeDamage(100);
        }
    }

    public int pickUpHeal(Item item, IGameEngine gameEngine) {
        if (item.getHeal() > 500) {
            gameEngine.playSpecialEffect("green_swirly");
        }
        return item.getHeal();
    }

    public int pickUpRealItem(Item item, IGameEngine gameEngine) {
        if (item.isRare())
            gameEngine.playSpecialEffect("cool_swirly_particles");

        if (item.isRare() && item.isUnique())
            gameEngine.playSpecialEffect("blue_swirly");

        return 0;
    }
}
