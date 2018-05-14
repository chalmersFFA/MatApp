package imat;

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
public class steg3_betalning_item_controller extends AnchorPane implements ShoppingCartListener{
    ShoppingCartController parentController;
    private Product product;
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
    Label unit;
    @FXML
    Label unitPrice;

    public steg3_betalning_item_controller(Product product, ShoppingCartController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/steg3_varukorg_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        image.setImage(db.getFXImage(product));
        name.setText(product.getName());
        this.product = product;
        //TODO lista ut vad fan den här grejen under här gör
        //itemHandler.setShoppingCartItem(this);
        //TODO återigen fixa så att den här hämtar antalet ifrån shoppingcart
        //amountTextField.textProperty().addListener(itemHandler.getChangeListener());
        update();
        shoppingCart.addShoppingCartListener(this);
    }

    public void update() {
        //TODO fixa så att det här fungerar från shoppingcart istället
        amount.setText("0");
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                amount.setText(Double.toString(s.getAmount()) + s.getProduct().getUnitSuffix());
                totalPrice.setText(Double.toString(s.getTotal()));
                break;
            }
        }
        //amountTextField.setText(Double.toString(itemHandler.getShoppingItem().getAmount()));
        //priceLabel.setText(Double.toString(itemHandler.getShoppingItem().getTotal()));
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        update();
    }
}
