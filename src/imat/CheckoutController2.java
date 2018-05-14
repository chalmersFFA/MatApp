package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan Köre on 2018-05-09.
 */
public class CheckoutController2 extends AnchorPane implements ShoppingCartListener{

    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();

    public CheckoutController2(IMatController parentController, ShoppingCartController shoppingCartController) {
        this.shoppingCartController = shoppingCartController;
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Steg2_betalning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        shoppingCart.addShoppingCartListener(this);
    }

    @FXML
    public void backButton() {
        parentController.changeMode(IMatController.Mode.SHOPPING);
    }

    @FXML
    public void toPayment() {

    }

    public void update() {

    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        update();
    }
    public void addToHashMap(ShoppingCartItem s) {
        shoppingCartItemMap.put(s.getProduct().getName(), s);
    }
    public void remove(ShoppingCartItem item) {
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(item.getProduct().getName())){
                shoppingCart.removeItem(s);
            }
        }
        update();
    }
}
