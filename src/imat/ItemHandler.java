package imat;

import se.chalmers.cse.dat216.project.*;

/**
 * Created by Jonathan Köre on 2018-05-08.
 */
public class ItemHandler {
    private ShoppingItem shoppingItem;
    private StoreListItem storeListItem;
    private ShoppingCartItem shoppingCartItem;


    public ItemHandler(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
    }

    public void increaseAmount() {
        setAmount(shoppingItem.getAmount() + 1);
    }

    public void decreaseAmount() {
        setAmount(shoppingItem.getAmount() - 1);
    }

    public void setAmount(double i) {
        if (i < 0)
            shoppingItem.setAmount(0);
        else
            shoppingItem.setAmount(i);
        update();

    }

    private void update() {
        if(shoppingItem.getAmount() > 0 && !IMatDataHandler.getInstance().getShoppingCart().getItems().contains(shoppingItem))
            IMatDataHandler.getInstance().getShoppingCart().addItem(shoppingItem);
        else if(shoppingItem.getAmount() <= 0)
            IMatDataHandler.getInstance().getShoppingCart().removeItem(shoppingItem);
        storeListItem.update();
        shoppingCartItem.update();
    }

    public ShoppingItem getShoppingItem() {
        return shoppingItem;
    }

    public void setShoppingItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
    }

    /*@Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        if(cartEvent.getShoppingItem().equals(shoppingItem)) {
            if(shoppingItem.getAmount() <= 0) {
                shoppingCartItem.remove();
            }
            else {

            }
        }
    }*/

    public void setStoreListItem(StoreListItem storeListItem) {
        this.storeListItem = storeListItem;
    }

    public void setShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
    }
}
