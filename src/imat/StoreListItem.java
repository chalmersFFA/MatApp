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
    private IMatController parentController;
    ItemHandler itemHandler;
    IMatDataHandler db = IMatDataHandler.getInstance();
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

    public StoreListItem(ItemHandler item, IMatController parentController) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/shopping_item_square.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.itemHandler = item;
        this.parentController = parentController;

        amountTextField.textProperty().addListener(itemHandler.getChangeListener());

        String ecoImagePath = "layout/images/svanen.png";
        if (item.getShoppingItem().getProduct().isEcological()) {
            ecoImageView.setImage(new Image("imat/layout/images/svanen.png"));
        }
        productImageView.setImage(db.getFXImage(item.getShoppingItem().getProduct()));

        productNameLabel.setText(item.getShoppingItem().getProduct().getName());
        //amountTextField.setText(Integer.toString(amount));
        priceLabel.setText(Double.toString(item.getShoppingItem().getProduct().getPrice()));
        itemHandler.setStoreListItem(this);
    }



    public void update() {
        amountTextField.setText(Double.toString(itemHandler.getShoppingItem().getAmount()));
    }

    @FXML
    public void increaseAmount(){
        itemHandler.increaseAmount();
    }
    @FXML
    public void decreaseAmount() {
        itemHandler.decreaseAmount();
        if(itemHandler.getShoppingItem().getAmount() == 0){
            itemHandler.getShoppingCartItem().remove();
        }
    }
}

