package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-03.
 */
public class StoreListItem extends AnchorPane {
    private ShoppingItem shoppingItem;
    private Product product;
    private IMatController parentController;
    IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private int amount;
    @FXML
    ImageView ecoImageView;
    @FXML
    ImageView productImageView;
    @FXML
    Label productNameLabel;
    @FXML
    Label priceLabel;
    @FXML
    TextField amountTextField;

    public StoreListItem(Product product, IMatController parentController) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/shopping_item_square.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = parentController;
        shoppingItem = new ShoppingItem(product);

        amountTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //TODO
                //Fixa responsmetoden på amountTextField
                setAmount(Integer.parseInt(newValue));
            }
        });

        String ecoImagePath = "layout/images/svanen.png";
        if (product.isEcological()) {
            ecoImageView.setImage(new Image("imat/layout/images/svanen.png"));
        }
        productImageView.setImage(db.getFXImage(this.product));

        productNameLabel.setText(this.product.getName());
        amountTextField.setText(Integer.toString(amount));
        priceLabel.setText(Double.toString(product.getPrice()));
    }

    public void increaseAmount() {
        setAmount(amount + 1);
    }

    public void lowerAmount() {
        setAmount(amount - 1);
    }

    public void setAmount(int i) {
        if (i < 0)
            amount = 0;
        else
            amount = i;
        update();
    }

    public void update() {
        amountTextField.setText(Integer.toString(amount));
        if(shoppingCart.getItems().contains(shoppingItem)) {
            if(amount > 0)
                shoppingItem.setAmount(amount);
            else
                shoppingCart.removeItem(shoppingItem);
        }
        else {
            if(amount > 0)
                shoppingCart.addItem(shoppingItem);
        }

        for(ShoppingItem s : shoppingCart.getItems()) {
            System.out.println(s.getProduct().getName() + ": " + s.getAmount());
        }

    }


}

