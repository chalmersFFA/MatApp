package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-09.
 */
public class CheckoutController extends AnchorPane{

    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();

    @FXML
    FlowPane orderFlowPane;
    @FXML
    Label totalLabel;

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

    }

    @FXML
    public void backButton() {
        parentController.changeMode(IMatController.Mode.SHOPPING);
    }

    @FXML
    public void toPayment() {

    }

    public void initCheckoutController() {
        orderFlowPane.getChildren().clear();
        for(ShoppingCartItem s : shoppingCartController.getVisualItems())
            orderFlowPane.getChildren().add(s);
    }

    public FlowPane getOrderFlowPane() {
        return orderFlowPane;
    }
}
