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

        initShoppingCartItems();
        test();

    }

    private void initShoppingCartItems() {
        for (Product p : db.getProducts(ProductCategory.BERRY)){
            shoppingCartItemMap.put(p.getName(), new ShoppingCartItem(new ShoppingItem(p), this));
        }
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        /*ShoppingItem shoppingItem = cartEvent.getShoppingItem();
        if(item_list.contains(shoppingCartItemMap.get(shoppingItem.getProduct().getName()))) {
            shoppingCartItemMap.get(shoppingItem.getProduct().getName()).update();
        }*/
    }

    private void test() {
        //item_list.add( new ShoppingCartItem(new ShoppingItem(db.getProduct(87)), this));
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartFlowPane.getChildren().add(new ShoppingCartItem(new ShoppingItem(db.getProduct(87)), this));
    }
}
