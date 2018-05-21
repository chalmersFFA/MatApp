package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

//import jdk.nashorn.internal.runtime.SharedPropertyMap;
import se.chalmers.cse.dat216.project.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @FXML
    ImageView expandImageView;

    Image arrowDown = new Image("imat/layout/images/arrow_downward.png");
    Image arrowRight = new Image("imat/layout/images/arrow_forward.png");

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


        //LocalDateTime datetime = LocalDateTime.parse(order.getDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        //String date = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        orderNameLabel.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getDate()));
        double totalAmount = 0;
        for(ShoppingItem item : order.getItems()){
            totalAmount += item.getTotal();
        }
        total.setText(MyMath.doubleToString(totalAmount) + " kr");
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
        expandImageView.setImage(arrowDown);
    }
    public void collapse(){
        orderHistoryItemFlowPane.getChildren().clear();
        this.getChildren().get(0).getStyleClass().remove("historyItemExpanded");
        expandImageView.setImage(arrowRight);
    }

    @FXML
    public void addToCart() {
        boolean finns = false;
        for(ShoppingItem s : order.getItems()) {
            for (ShoppingItem cartItem : shoppingCart.getItems()) {
                if (cartItem.getProduct().getName().equals(s.getProduct().getName())) {
                    finns = true;
                }
            }
            if (finns) {
                for (ShoppingItem cartItem : shoppingCart.getItems()) {
                    if (cartItem.getProduct().getName().equals(s.getProduct().getName())) {
                        cartItem.setAmount(s.getAmount() + cartItem.getAmount());
                        break;
                    }
                }
            }else{
                shoppingCart.addItem(new ShoppingItem(s.getProduct(), s.getAmount()));
            }
            finns = false;
            shoppingCart.fireShoppingCartChanged(null, false);
        }

        /*
        boolean finns = false;
        for (ShoppingItem s : order.getItems()) {
            if(!shoppingCart.getItems().isEmpty()) {
                for (ShoppingItem s2 : shoppingCart.getItems()) {
                    if (s.getProduct().getName().equals(s2.getProduct().getName())) {
                        s2.setAmount(s.getAmount() + s2.getAmount());
                        finns = true;
                        break;
                    }
                    else if(!shoppingCart.getItems().contains(s)){
                        shoppingCart.addItem(new ShoppingItem(s.getProduct(), s.getAmount()));
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
        shoppingCart.fireShoppingCartChanged(null, false);*/
    }


}
