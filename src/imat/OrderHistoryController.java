package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan Köre on 2018-05-09.
 */
public class OrderHistoryController extends VBox{
    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    @FXML
    FlowPane orderHistoryFlowPane;
    @FXML
    Label totalPriceLabel;


    public OrderHistoryController(IMatController parentController, ShoppingCartController shoppingCartController) {
        this.shoppingCartController = shoppingCartController;
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/kop_historik.fxml"));
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
        parentController.updateProductList(ProductCategory.BERRY);
    }


    public void createHistory() {
        orderHistoryFlowPane.getChildren().clear();
        if(!db.getOrders().isEmpty() ) {
            for (Order o: db.getOrders()) {
                orderHistoryFlowPane.getChildren().add(new OrderHistoryItemController(parentController,shoppingCartController, o));
            }
        }
    }
}
