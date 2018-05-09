package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan Köre on 2018-05-07.
 */
public class ShoppingCartController extends AnchorPane implements ShoppingCartListener {

    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private ArrayList<ShoppingCartItem> item_list = new ArrayList<>();
    private Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();

    @FXML
    FlowPane shoppingCartFlowPane;

    public ShoppingCartController(IMatController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/shopping_cart_controller.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        shoppingCart.addShoppingCartListener(this);

    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        if(cartEvent.isAddEvent() && !item_list.contains(shoppingCartItemMap.get(cartEvent.getShoppingItem().getProduct().getName())))
            add(shoppingCartItemMap.get(cartEvent.getShoppingItem().getProduct().getName()));
        /*else
            remove(shoppingCartItemMap.get(cartEvent.getShoppingItem().getProduct().getName()));*/
    }
    public void remove(ShoppingCartItem s) {
        item_list.remove(s);
        s.getItemHandler().getShoppingItem().setAmount(0);
        s.getItemHandler().update();
        update();
    }
    public void add(ShoppingCartItem s) {
        item_list.add(s);
        update();
    }

    public void update() {
        shoppingCartFlowPane.getChildren().clear();
        for(ShoppingCartItem s : item_list) {
            shoppingCartFlowPane.getChildren().add(s);
        }
    }

    public void addToHashMap(ShoppingCartItem s) {
        shoppingCartItemMap.put(s.getItemHandler().getShoppingItem().getProduct().getName(), s);
    }

    public void addToCart(ItemHandler itemHandler) {
        shoppingCart.addItem(itemHandler.getShoppingItem());
    }
    public void removeFromCart(ItemHandler itemHandler) {
        shoppingCart.removeItem(itemHandler.getShoppingItem());
    }
    public boolean isInCart(ItemHandler itemHandler) {
        return shoppingCart.getItems().contains(itemHandler.getShoppingItem());
    }

    public FXMLLoader getFXMLLoader() {
        return parentController.getFxmlLoader();
    }
}
