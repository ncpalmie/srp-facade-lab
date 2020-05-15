package srpfacadelab;

public class PlayerFacade {

    private final IGameEngine gameEngine;

    private final RpgPlayer player;

    private final CombatHandler combatHandler;
    
    private final InventoryHandler inventoryHandler;

    public PlayerFacade(IGameEngine gameEngine, RpgPlayer player) {
        this.gameEngine = gameEngine;
        this.player = player;
        this.combatHandler = new CombatHandler(gameEngine);
        this.inventoryHandler = new InventoryHandler(gameEngine);
    }

    public void playerUseItem(Item item) {
        this.inventoryHandler.useItem(item, this.player);
    } 

    public boolean playerPickUpItem(Item item) {
        return this.inventoryHandler.pickUpItem(item, this.player);
    }

    private void playerCalculateStats() {
        this.inventoryHandler.calculateStats(this.player);
    }

    private boolean playerCheckIfItemExistsInInventory(Item item) {
        return this.inventoryHandler.checkIfItemExistsInInventory(item, this.player.getInventory());
    }

    private int playerCalculateInventoryWeight() {
        return this.inventoryHandler.calculateInventoryWeight(this.player.getInventory());
    }

    public void playerTakeDamage(int damage) {
        this.combatHandler.takeDamage(damage, this.playerCalculateInventoryWeight(), this.player);
    }
}
