package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-07.
 */
public class ShoppingCartItem extends AnchorPane implements ShoppingCartListener{
    ShoppingCartController parentController;
    private Product product;
    IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();

    private Tooltip tooltipIncrease = new Tooltip("Öka Mängd");
    private Tooltip tooltipDecrease = new Tooltip("Minska Mängd");
    private Tooltip removeTooltip = new Tooltip("Ta bort varan från kundvagnen");
    private Tooltip amountTooltip = new Tooltip("Välj själv mängd");

    @FXML
    ImageView productImageView;
    @FXML
    ImageView cross;
    @FXML
    Label productLabel;
    @FXML
    Label priceLabel;
    @FXML
    Label unit;
    @FXML
    Button increaseButton;
    @FXML
    Button decreaseButton;
    @FXML
    TextField amountTextField;
    @FXML
    AnchorPane item_AnchorPane;


    private static final int tooltipDelay = 500;

    public ShoppingCartItem(Product product, ShoppingCartController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/steg1_betalning_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        productImageView.setImage(db.getFXImage(product));
        productLabel.setText(product.getName());
        this.product = product;
        //TODO lista ut vad fan den här grejen under här gör
        //itemHandler.setShoppingCartItem(this);
        //TODO återigen fixa så att den här hämtar antalet ifrån shoppingcart
        //amountTextField.textProperty().addListener(itemHandler.getChangeListener());
        update();
        shoppingCart.addShoppingCartListener(this);
        IMatController.addToolTip(increaseButton, tooltipIncrease, tooltipDelay);
        IMatController.addToolTip(decreaseButton, tooltipDecrease, tooltipDelay);
        IMatController.addToolTip(cross, removeTooltip, tooltipDelay);
        IMatController.addToolTip(amountTextField, amountTooltip, tooltipDelay);
        update();
        cross.setVisible(true);
        cross.setOpacity(0.6);
    }

    public void update() {
        //TODO fixa så att det här fungerar från shoppingcart istället
        amountTextField.setText("0");
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                //amountTextField.setText(Double.toString(s.getAmount()));
                priceLabel.setText(Double.toString(s.getTotal()) + " kr");
                //unit.setText(product.getUnitSuffix());
                amountTextField.setText(MyMath.doubleToString(s.getAmount())+ product.getUnitSuffix());
                priceLabel.setText(MyMath.doubleToString(s.getTotal()) + " kr");
                break;
            }
        }
        //amountTextField.setText(Double.toString(itemHandler.getShoppingItem().getAmount()));
        //priceLabel.setText(Double.toString(itemHandler.getShoppingItem().getTotal()));
    }
    @FXML
    public void remove() {
        if(!shoppingCart.getItems().isEmpty()){
            for(ShoppingItem s : shoppingCart.getItems()){
                if(s.getProduct().getName().equals(product.getName())){
                    shoppingCart.removeItem(s);
                }
            }
            shoppingCart.fireShoppingCartChanged(null, false);
        }
        parentController.changePliancyCart();
    }
    @FXML
    public void increaseAmount(){
        boolean finns = false;
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                if(s.getProduct().getUnitSuffix().equals("kg") || s.getProduct().getUnitSuffix().equals("l") ){
                    s.setAmount((double)(s.getAmount()*10+1.0)/10.0);
                }
                else{
                    s.setAmount(s.getAmount()+1);
                }
                finns = true;
                shoppingCart.fireShoppingCartChanged(null, false); //bara för att meddela att något hänt till övriga världen
            }
        }
        if(!finns){
            shoppingCart.addProduct(product);
        }
        update();
    }
    @FXML
    public void decreaseAmount() {
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                if(s.getProduct().getUnitSuffix().equals("kg") || s.getProduct().getUnitSuffix().equals("l") ){
                    s.setAmount((double)(s.getAmount()*10-1.0)/10.0);
                }
                else{
                    s.setAmount(s.getAmount()-1);
                }
                shoppingCart.fireShoppingCartChanged(null, false); //bara för att meddela att
                //TODO bestäm vad som ska hända med vagnen om det finns 0 av en vara
                if(s.getAmount() < 0){
                    shoppingCart.removeItem(s);
                }
            }
        }
        update();
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        update();
    }

    @FXML
    public void changeCrossPliant(){
        Image crossPliant = new Image("imat/layout/images/cross_pliant.png");
        cross.setImage(crossPliant);
    }
    @FXML
    public void changeCrossNotPliant(){
        Image crossNotPliant = new Image("imat/layout/images/cross.png");
        cross.setImage(crossNotPliant);
    }
    @FXML
    public void textFieldChanged(){
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                s.setAmount(Double.parseDouble(amountTextField.getText()));
                shoppingCart.fireShoppingCartChanged(null, false);
            }
        }
    }
    @FXML
    public void selectAll(){
        amountTextField.selectAll();
    }
   /* @FXML
    public void showTheCross(){
        System.out.println("show cross");
        cross.toFront();
        cross.setVisible(true);
    }
    @FXML
    public void hideTheCross(){
        System.out.println("hide cross");
        cross.toBack();
        cross.setVisible(false);
    }*/
}
