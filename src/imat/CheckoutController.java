package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
public class CheckoutController extends AnchorPane implements ShoppingCartListener{

    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();

    @FXML
    FlowPane orderFlowPane;
    @FXML
    Label totalLabel;
    @FXML
    AnchorPane sequenceMapAnchorPane;

    public CheckoutController(IMatController parentController, ShoppingCartController shoppingCartController) {
        this.shoppingCartController = shoppingCartController;
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Steg1_betalning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        shoppingCart.addShoppingCartListener(this);
        totalLabel.setText(Double.toString(MyMath.round(shoppingCart.getTotal(),3)) +" kr");

    }

    public void refreshSequenceMap() {
        sequenceMapAnchorPane.getChildren().clear();
        sequenceMapAnchorPane.getChildren().add(parentController.getSequenceMap());
        parentController.getSequenceMap().setState(1);
    }
    @FXML
    public void backButton() {
        parentController.changeMode(IMatController.Mode.SHOPPING);
    }

    @FXML
    public void toPayment() {
        parentController.toPayment();

    }

    public void update() {
        orderFlowPane.getChildren().clear();
        for(ShoppingItem s : shoppingCart.getItems()) {
            orderFlowPane.getChildren().add(shoppingCartItemMap.get(s.getProduct().getName()));
        }
        totalLabel.setText(Double.toString(MyMath.round(shoppingCart.getTotal(),3)) +" kr");
    }

    public FlowPane getOrderFlowPane() {
        return orderFlowPane;
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
