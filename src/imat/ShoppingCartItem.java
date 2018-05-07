package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-07.
 */
public class ShoppingCartItem extends AnchorPane {
    ShoppingCartController parentController;

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
    }
}
