package srpfacadelab;

public class CombatHandler {

    private final IGameEngine gameEngine;

    public CombatHandler(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void takeDamage(int damage, int playerWeight, RpgPlayer player) {
        if (playerWeight < player.getCarryingCapacity() * 0.5) {
            damage = (int) Math.round(damage * 0.25);
        }

        if (damage < player.getArmour()) {
            gameEngine.playSpecialEffect("parry");
        }

        int damageToDeal = damage - player.getArmour();
        player.setHealth(player.getHealth() - damageToDeal);

        gameEngine.playSpecialEffect("lots_of_gore");
    }
}
