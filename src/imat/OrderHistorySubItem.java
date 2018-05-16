package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-07.
 */
public class OrderHistorySubItem extends AnchorPane{
    private ShoppingItem shoppingItem;
    IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();

    @FXML
    ImageView image;
    @FXML
    Label name;
    @FXML
    Label totalPrice;
    @FXML
    Label amount;
    @FXML
    Label unitPrice;

    public OrderHistorySubItem(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/steg3_varukorg_item.fxml"));
        this.shoppingItem = shoppingItem;
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        image.setImage(db.getFXImage(this.shoppingItem.getProduct()));
        name.setText(this.shoppingItem.getProduct().getName());
        update();
    }

    public void update() {
        //TODO fixa så att det här fungerar från shoppingcart istället
        amount.setText("0");
        amount.setText(Double.toString(MyMath.round(shoppingItem.getAmount(),2)) + shoppingItem.getProduct().getUnitSuffix());
        totalPrice.setText(Double.toString(MyMath.round(shoppingItem.getTotal(),2)) + " kr");
        unitPrice.setText("à " + Double.toString(shoppingItem.getProduct().getPrice()) + shoppingItem.getProduct().getUnit());

    }
}
        //amountTextField.setText(Double.toString(itemHandler.getShoppingItem().getAmount()));
        //priceLabel.setText(Double.toString(itemHandler.getShoppingItem().getTotal()));
