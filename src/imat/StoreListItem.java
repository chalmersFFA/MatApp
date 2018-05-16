package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * Created by Jonathan Köre on 2018-05-03.
 */
public class StoreListItem extends AnchorPane implements ShoppingCartListener {
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private IMatController parentController;
    private Tooltip tooltipFavourite = new Tooltip("Lägg till i Favoriter");
    private Tooltip tooltipIncrease = new Tooltip("Öka Mängd");
    private Tooltip tooltipDecrease = new Tooltip("Minska Mängd");
    Product product;
    @FXML
    ImageView ecoImageView;
    @FXML
    ImageView favouriteImageView;
    @FXML
    ImageView productImageView;
    @FXML
    Label productNameLabel;
    @FXML
    Label priceLabel;
    @FXML
    TextField amountTextField;
    @FXML
    Button decreaseButton, increaseButton;
    Image favouriteImage = new Image("imat/layout/images/favourite.png");
    Image notFavouriteImage = new Image("imat/layout/images/notFavourite.png");
    Image notFavouriteImagePliant = new Image("imat/layout/images/notFavourite_pliant.png"); //...notFavourite_pliant_border.png
    Image favouriteImagePliant = new Image("imat/layout/images/Favourite_pliant.png"); //...Favourite_pliant_border.png


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
        //TODO fixa så att textfältet tar in hur många det finns av den i varukorgen ifrån varukorgen
        //amountTextField.textProperty().addListener(itemHandler.getChangeListener());

        String ecoImagePath = "layout/images/svanen.png";
        if (product.isEcological()) {
            ecoImageView.setImage(new Image("imat/layout/images/svanen.png"));
        }

        updateFavourite();

        productImageView.setImage(db.getFXImage(product));

        productNameLabel.setText(product.getName());
        //amountTextField.setText(Integer.toString(amount));

        priceLabel.setText(Integer.toString((int)product.getPrice()) + " " + product.getUnit());
        //itemHandler.setStoreListItem(this);

        shoppingCart.addShoppingCartListener(this);

        IMatController.addToolTip(favouriteImageView, tooltipFavourite, 100);

        IMatController.addToolTip(increaseButton, tooltipIncrease, 100);
        IMatController.addToolTip(decreaseButton, tooltipDecrease, 100);
        update();

    }


    public void updateFavourite(){
        if (db.isFavorite(product)){
            favouriteImageView.setImage(favouriteImage);
            tooltipFavourite.setText("Ta bort från Favoriter");

        }else{
            favouriteImageView.setImage(notFavouriteImage);
            tooltipFavourite.setText("Lägg till i Favoriter");
        }
    }

    public void update() {
        //TODO fixa så att den här tar in ifrån shoppingcart, kanske ska den vara en listener?
        amountTextField.setText("0");
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                amountTextField.setText(Double.toString(MyMath.round(s.getAmount(),3)) + product.getUnitSuffix());
                break;
            }
        }
    }

    @FXML
    public void increaseAmount(){
        //itemHandler.increaseAmount();
        boolean finns = false;
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                if(s.getProduct().getUnitSuffix().equals("kg") || s.getProduct().getUnitSuffix().equals("l") ){
                    s.setAmount(s.getAmount()+0.1);
                }
                else{    
                    s.setAmount(s.getAmount()+1);
                }
                finns = true;
                shoppingCart.fireShoppingCartChanged(null, false); //bara för att meddela att något hänt till övriga världen
            }
        }
        if(!finns){
            shoppingCart.addProduct(product); /** Det visar sig att .addProduct lägger till 1 som mängd, alltså är detta ett problem i backend
                                                * och inte något vi behöver ändra på. Sen tycker jag att 1 kg är en bra mängd tbh /rob2
                                                */
        }
        update();

    }
    @FXML
    public void decreaseAmount() {
        /*itemHandler.decreaseAmount();
        if(itemHandler.getShoppingItem().getAmount() == 0){
            itemHandler.getShoppingCartItem().remove();
        }*/
        //TODO fixa så att man kollar om det finns något i kundvagnen först!
        for(ShoppingItem s : shoppingCart.getItems()){
            if(s.getProduct().getName().equals(product.getName())){
                if(s.getProduct().getUnitSuffix().equals("kg") || s.getProduct().getUnitSuffix().equals("l") ){
                    s.setAmount(s.getAmount()-0.1);
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

    @FXML
    public void favourite(){
        if(db.isFavorite(product)){
            db.removeFavorite(product);
        }else{
            db.addFavorite(product);
        }
        updateFavourite();
    }

    @FXML
    public void ChangePliancyFavorite(){
        /*if(db.isFavorite(product)){
            favouriteImageView.setImage(favouriteImagePliant);
        }else{
            favouriteImageView.setImage(notFavouriteImagePliant);
        } */
        favouriteImageView.setOpacity(0.6);
    }
    @FXML
    public void endChangePliancyFavorite(){
        /*if(db.isFavorite(product)){
            favouriteImageView.setImage(favouriteImage);
        }else{
            favouriteImageView.setImage(notFavouriteImage);
        }*/
        favouriteImageView.setOpacity(1);
    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        //TODO fixa så att den kollar på hur många saker av sig själv som finns i vagnen
        update();
    }

}

