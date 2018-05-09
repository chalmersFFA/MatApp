package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import se.chalmers.cse.dat216.project.*;

import java.awt.event.FocusListener;


/**
 * Created by Jonathan Köre on 2018-05-08.
 */
public class ItemHandler {
    private ShoppingItem shoppingItem;
    private StoreListItem storeListItem;
    private ShoppingCartItem shoppingCartItem;
    private ChangeListener<String>  changeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setAmount(Double.parseDouble(newValue));
        }
    };

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

    public void update() {
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

    public void setStoreListItem(StoreListItem storeListItem) {
        this.storeListItem = storeListItem;
    }

    public void setShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
    }

    public ChangeListener<String> getChangeListener() {
        return changeListener;
    }

    public ShoppingCartItem getShoppingCartItem() {
        return shoppingCartItem;
    }
}
