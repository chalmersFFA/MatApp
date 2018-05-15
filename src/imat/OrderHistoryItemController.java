package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan Köre on 2018-05-09.
 */
public class OrderHistoryItemController extends VBox{

    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private Order order;
    private boolean open;
    @FXML
    FlowPane orderHistoryItemFlowPane;
    @FXML
    Label orderNameLabel;


    public OrderHistoryItemController(IMatController parentController, ShoppingCartController shoppingCartController, Order order) {
        this.order = order;
        this.shoppingCartController = shoppingCartController;
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/history_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        orderNameLabel.setText(order.getDate().toString());

    }

    @FXML
    public void toggleExpand(){
        if(open){
            open = false;
            collapse();
        }
        else{
            expand();
            open = true;
        }
    }

    public void expand() {
        orderHistoryItemFlowPane.getChildren().clear();
        for (ShoppingItem s : order.getItems()) {
            orderHistoryItemFlowPane.getChildren().add(new OrderHistorySubItem(s));
        }
    }
    public void collapse(){
        orderHistoryItemFlowPane.getChildren().clear();
    }


}
