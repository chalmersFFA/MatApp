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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-07.
 */
public class ShoppingCartItem extends AnchorPane {
    ShoppingCartController parentController;
    ShoppingItem shoppingItem;
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

    public ShoppingCartItem(ShoppingItem shoppingItem, ShoppingCartController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/steg1_betalning_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        this.shoppingItem = shoppingItem;
        priceLabel.setText(Double.toString(shoppingItem.getProduct().getPrice()));
        productImageView.setImage(db.getFXImage(this.shoppingItem.getProduct()));
        productLabel.setText(this.shoppingItem.getProduct().getName());

        amountTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setAmount(Double.parseDouble(newValue));
            }
        });
        updateTextField();
    }

    public void updateTextField() {
        amountTextField.setText(Double.toString(shoppingItem.getAmount()));
        priceLabel.setText(Double.toString(shoppingItem.getTotal()));
    }
    @FXML
    public void increaseAmount() {
        setAmount(shoppingItem.getAmount() + 1);
    }
    @FXML
    public void decreaseAmount() {
        setAmount(shoppingItem.getAmount()-1);
    }

    public void setAmount(double i) {
        if (i < 0)
            shoppingItem.setAmount(0);
        else
            shoppingItem.setAmount(i);
        updateTextField();
    }
}
