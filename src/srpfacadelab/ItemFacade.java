package srpfacadelab;

public class ItemFacade {

    private final ItemHandler itemHandler;

    public ItemFacade(){
        itemHandler = new ItemHandler();
    }

    public void useItem(Item item, IGameEngine gameEngine, RpgPlayer player){
        if (item.getName().equals("Stink Bomb"))
        {
            itemHandler.useStinkBomb(gameEngine, player);
        }
    }

    public int pickUpItem(Item item, IGameEngine gameEngine) {
        if (item.getHeal() > 0)
            return itemHandler.pickUpHeal(item, gameEngine);
        else
            return itemHandler.pickUpRealItem(item, gameEngine);
    }

    public boolean isItemUnique(Item item) {
        return item.isUnique();
    }

    public int getItemWeight(Item item) {
        return item.getWeight();
    }
}
