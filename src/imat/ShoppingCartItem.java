package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-07.
 */
public class ShoppingCartItem extends AnchorPane {
    ShoppingCartController parentController;
    ItemHandler itemHandler;
    IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    ImageView productImageView;
    @FXML
    Label productLabel;
    @FXML
    Label priceLabel;
    @FXML
    Button increaseButton;
    @FXML
    Button decreaseButton;
    @FXML
    TextField amountTextField;

    public ShoppingCartItem(ItemHandler itemHandler, ShoppingCartController parentController) {
        FXMLLoader fxmlLoader = parentController.getFXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/steg1_betalning_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        productImageView.setImage(db.getFXImage(itemHandler.getShoppingItem().getProduct()));
        productLabel.setText(itemHandler.getShoppingItem().getProduct().getName());
        this.itemHandler = itemHandler;
        itemHandler.setShoppingCartItem(this);
        amountTextField.textProperty().addListener(itemHandler.getChangeListener());
        update();
    }

    public void update() {
        amountTextField.setText(Double.toString(itemHandler.getShoppingItem().getAmount()));
        priceLabel.setText(Double.toString(itemHandler.getShoppingItem().getTotal()));
    }

    public void remove() {
        parentController.remove(this);
    }
    public void increaseAmount(){
        itemHandler.increaseAmount();

    }
    public void decreaseAmount() {
        itemHandler.decreaseAmount();
    }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }
}
