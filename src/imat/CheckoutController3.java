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
public class CheckoutController3 extends AnchorPane implements ShoppingCartListener{

    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private Map<String, steg3_betalning_item_controller> steg3ItemMap = new HashMap<String, steg3_betalning_item_controller>();
    @FXML
    FlowPane finalOrderFlowPane;
    @FXML
    Label totalPriceLabel;
    @FXML
    AnchorPane sequenceMapAnchorPane;
    @FXML
    Label firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, addressLabel, postAddressLabel, postCodeLabel, cardHolderName, cardNumber;
    private Customer customer = db.getCustomer();
    private CreditCard creditCard = db.getCreditCard();


    public CheckoutController3(IMatController parentController, ShoppingCartController shoppingCartController) {
        this.shoppingCartController = shoppingCartController;
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Steg3_betalning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        sequenceMapAnchorPane.getChildren().add(parentController.getSequenceMap());
        shoppingCart.addShoppingCartListener(this);
    }

    public void refreshSequenceMap() {
        sequenceMapAnchorPane.getChildren().clear();
        sequenceMapAnchorPane.getChildren().add(parentController.getSequenceMap());
        parentController.getSequenceMap().setState(3);
    }
    @FXML
    public void backButton() {
        parentController.toPayment();
    }

    @FXML
    public void placeOrder() {
        db.placeOrder(true);
        shoppingCart.fireShoppingCartChanged(null, false);
        parentController.thankYou();

    }

    public void update() {
        finalOrderFlowPane.getChildren().clear();
        if(!shoppingCart.getItems().isEmpty() ) {
            for (ShoppingItem s : shoppingCart.getItems()) {
                finalOrderFlowPane.getChildren().add(steg3ItemMap.get(s.getProduct().getName()));
            }
        }
        totalPriceLabel.setText(Double.toString(MyMath.round(shoppingCart.getTotal(),3)) +" kr");

        firstNameLabel.setText(customer.getFirstName());
        lastNameLabel.setText(customer.getLastName());
        emailLabel.setText(customer.getEmail());
        addressLabel.setText(customer.getAddress());
        postAddressLabel.setText(customer.getPostAddress()); //Vilket måste vara Orten?
        postCodeLabel.setText(customer.getPostCode());
        phoneNumberLabel.setText(customer.getMobilePhoneNumber());
        cardHolderName.setText(creditCard.getHoldersName());
        cardNumber.setText(creditCard.getCardNumber());
        String tempCardNumber = "";
        for(int i = 0; i < creditCard.getCardNumber().length(); i++) {
            if(i < creditCard.getCardNumber().length() - 4)
                tempCardNumber += "X";
            else
                tempCardNumber += creditCard.getCardNumber().charAt(i);
        }

        cardNumber.setText(tempCardNumber);


    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        update();
    }
    public void addToHashMap(steg3_betalning_item_controller s) {
        steg3ItemMap.put(s.getProduct().getName(), s);
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
