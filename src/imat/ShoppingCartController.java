package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ArrayList<ShoppingCartItem> visualItems = new ArrayList<>();
    private Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();

    @FXML
    FlowPane shoppingCartFlowPane;
    @FXML
    Button checkoutButton;
    @FXML
    Label totalLabel;
    @FXML
    Label emptyCartLabel;
    @FXML
    AnchorPane emptyCartAnchorPane;
    @FXML
    ImageView cross;

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
        totalLabel.setText(Double.toString(shoppingCart.getTotal()) +" kr");
        changePliancyCart();
    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        update();
        if(shoppingCart.getTotal()==0){
            changePliancyCart();
            setDisableCart();
        } else{
            endPliancyCart();
            setEnableCart();
        }
    }
    public void remove(ShoppingCartItem item) {
        //visualItems.remove(item);
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(item.getProduct().getName())){
                shoppingCart.removeItem(s);
            }
        }
        update();
        emptyCartLabel.setDisable(false);
        endPliancyCart();
    }

    public void update() {
        shoppingCartFlowPane.getChildren().clear();
        for(ShoppingItem s : shoppingCart.getItems()) {
            shoppingCartFlowPane.getChildren().add(shoppingCartItemMap.get(s.getProduct().getName()));
        }
        totalLabel.setText(MyMath.doubleToString(shoppingCart.getTotal()) +" kr");
    }

    public void addToHashMap(ShoppingCartItem s) {
        shoppingCartItemMap.put(s.getProduct().getName(), s);
    }

    public boolean isInCart(ItemHandler itemHandler) {
        return shoppingCart.getItems().contains(itemHandler.getShoppingItem());
    }

    @FXML
    public void toCheckout() {
        if(!shoppingCart.getItems().isEmpty())
            parentController.changeMode(IMatController.Mode.CHECKOUT);
    }

    public ArrayList<ShoppingCartItem> getVisualItems() {
        return visualItems;
    }

    public FlowPane getShoppingCartFlowPane() {
        return shoppingCartFlowPane;
    }

    @FXML
    public void emptyCartToFront(){
        if(shoppingCart.getTotal()!=0)
            emptyCartAnchorPane.toFront();
    }
    @FXML
    public void emptyCartToBack(){
        emptyCartAnchorPane.toBack();
    }

    @FXML
    public void emptyCart(){
        shoppingCart.clear();
        update();
        setDisableCart();
        emptyCartToBack();
    }
    @FXML
    public void changePliancyCart(){
        emptyCartLabel.setOpacity(0.6);
        checkoutButton.setOpacity(0.6);
    }
    @FXML
    public void endPliancyCart(){
        if(shoppingCart.getTotal()!=0) {
            emptyCartLabel.setOpacity(1);
            checkoutButton.setOpacity(1);
        }
    }
    public void setDisableCart(){
        emptyCartLabel.setDisable(true);
        checkoutButton.setDisable(true);
    }
    public void setEnableCart() {
        emptyCartLabel.setDisable(false);
        checkoutButton.setDisable(false);
    }
    @FXML
    public void changeCrossPliant(){
        Image crossPliant = new Image("imat/layout/images/cross_pliant.png");
        cross.setImage(crossPliant);
    }
    @FXML
    public void changeCrossNotPliant(){
        Image crossNotPliant = new Image("imat/layout/images/cross.png");
        cross.setImage(crossNotPliant);
    }
}
