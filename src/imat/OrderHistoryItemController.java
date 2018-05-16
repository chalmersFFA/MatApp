package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
public class OrderHistoryItemController extends AnchorPane{

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
    @FXML
    Label total;


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
        double totalAmount = 0;
        for(ShoppingItem item : order.getItems()){
            totalAmount += item.getTotal();
        }
        total.setText("Totalt: " + Double.toString(MyMath.round(totalAmount,2)) + " kr");
        //this.getChildren().get(0).getStyleClass().remove("historyItem");


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
            if(s.getAmount() >= 0.1)
                orderHistoryItemFlowPane.getChildren().add(new OrderHistorySubItem(s));
        }
        this.getChildren().get(0).getStyleClass().add("historyItemExpanded");
    }
    public void collapse(){
        orderHistoryItemFlowPane.getChildren().clear();
        this.getChildren().get(0).getStyleClass().remove("historyItemExpanded");
    }

    @FXML
    public void addToCart() {
        boolean finns = false;
        for (ShoppingItem s : order.getItems()) {
            if(!shoppingCart.getItems().isEmpty()) {
                for (ShoppingItem s2 : shoppingCart.getItems()) {
                    if (s.getProduct().getName().equals(s2.getProduct().getName())) {
                        s2.setAmount(s.getAmount() + s2.getAmount());
                        finns = true;
                        break;
                    }
                }
                if(!finns){
                    shoppingCart.addItem(new ShoppingItem(s.getProduct(), s.getAmount()));
                    finns = true;
                }
            }else{
                shoppingCart.addItem(new ShoppingItem(s.getProduct(), s.getAmount()));
            }
            shoppingCart.fireShoppingCartChanged(null, false);
        }
        shoppingCart.fireShoppingCartChanged(null, false);
    }


}
