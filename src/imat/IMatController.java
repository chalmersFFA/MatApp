package imat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Jonathan Köre on 2018-05-03.
 */
public class IMatController extends VBox implements Initializable {
    private IMatDataHandler db = IMatDataHandler.getInstance();
    static ArrayList<CategoryItem> cList = new ArrayList<>();
    private Map<String, StoreListItem> storeListItemMap = new HashMap<String, StoreListItem>();
    private CategoryItem currentExpandedSub;
    private ShoppingCart shoppingCart = db.getShoppingCart();
    public enum Mode {
        SHOPPING,
        CHECKOUT
    }
    private Mode currentMode = Mode.SHOPPING;

    MyDetails myDetails;
    ShoppingCartController shoppingCartController;
    CheckoutController checkoutController;
    CheckoutController2 checkoutController2;
    CheckoutController3 checkoutController3;


    @FXML
    FlowPane mainFlowPane;

    @FXML
    FlowPane categoryFlowPane;

    @FXML
    AnchorPane categoriesAnchorPane, shoppingCartAnchorPane;

    @FXML
    Label myDetailsLabel;

    @FXML
    Label favouriteLabel;

    @FXML
    ImageView logoImageView;

    @FXML
    HBox bigHBox;

    @FXML
    VBox mainVBox;

    @FXML
    StackPane browsingStackPane;

    @FXML
    StackPane mainStackPane;

    @FXML
    HBox displayPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myDetails = new MyDetails(this);
        shoppingCartController = new ShoppingCartController(this);
        checkoutController = new CheckoutController(this, shoppingCartController);
        checkoutController2 = new CheckoutController2(this, shoppingCartController);
        checkoutController3 = new CheckoutController3(this, shoppingCartController);
        showShoppingCart();
        initProducts();
        initCategories();
        updateProductList(ProductCategory.BERRY);
        shoppingCart.clear();

    }

    private void initProducts() {
        ShoppingCartItem s;
        for (Product p : db.getProducts(ProductCategory.BERRY)){
            //ItemHandler itemHandler = new ItemHandler(new ShoppingItem(p,0));
            storeListItemMap.put(p.getName(), new StoreListItem(p, this));
            shoppingCartController.addToHashMap(new ShoppingCartItem(p, shoppingCartController));
            checkoutController.addToHashMap(new ShoppingCartItem(p, shoppingCartController));
            checkoutController3.addToHashMap(new steg3_betalning_item_controller(p, shoppingCartController));
        }
    }


    public void updateProductList(ProductCategory category) {
        mainFlowPane.getChildren().clear();
        for (Product p : db.getProducts(category)){
            mainFlowPane.getChildren().add(storeListItemMap.get(p.getName()));
        }
    }

    public void displayFavourites(){
        mainFlowPane.getChildren().clear();
        for (Product p: db.favorites()){
            mainFlowPane.getChildren().add(storeListItemMap.get(p.getName()));
        }
        //favouriteLabel.setId("current");
    }

    @FXML
    private void showDetailsScreen() {
        displayPane.toFront();
        displayPane.getChildren().clear();
        displayPane.getChildren().add(myDetails);
        displayPane.setAlignment(Pos.CENTER);
        myDetails.initDetails();
    }

    @FXML
    private void escapeHatch() {
        System.out.println("hej");
        updateProductList(ProductCategory.BERRY);
    }
    @FXML
    private void pressedHelp() {
        System.out.println("help me N***UH");
    }

    private void initCategories() {

        CategoryItem c = new CategoryItem(ProductCategory.BREAD, this, new Image("imat/layout/images/categoryIcons/bread.png")); //BRÖD
        cList.add(c);

        c = new CategoryItem("Drycker", this, new Image("imat/layout/images/categoryIcons/drink.png"));
        c.addSubCategory(new CategoryItem(ProductCategory.HOT_DRINKS, this, new Image("imat/layout/images/categoryIcons/hotdrink.png")));   //VARMA DRYCKER
        c.addSubCategory(new CategoryItem(ProductCategory.COLD_DRINKS, this, new Image("imat/layout/images/categoryIcons/colddrink.png"))); //KALLA DRYCKER
        cList.add(c);

        c = new CategoryItem(ProductCategory.FISH, this, new Image("imat/layout/images/categoryIcons/fish.png"));   //FISK
        cList.add(c);

        c = new CategoryItem("Frukt och grönt", this, new Image("imat/layout/images/categoryIcons/fruitvegetable.png"));
        c.addSubCategory(new CategoryItem(ProductCategory.VEGETABLE_FRUIT, this, new Image("imat/layout/images/categoryIcons/miscfruit.png"))); //ÖVRIGT
        c.addSubCategory(new CategoryItem(ProductCategory.ROOT_VEGETABLE, this, new Image("imat/layout/images/categoryIcons/root.png")));   //ROTFRUKTER
        c.addSubCategory(new CategoryItem(ProductCategory.MELONS, this, new Image("imat/layout/images/categoryIcons/melon.png")));  //MELONER
        c.addSubCategory(new CategoryItem(ProductCategory.CABBAGE, this, new Image("imat/layout/images/categoryIcons/cabbage.png")));   //KÅL
        c.addSubCategory(new CategoryItem(ProductCategory.FRUIT, this, new Image("imat/layout/images/categoryIcons/fruit.png")));   //FRUKT
        c.addSubCategory(new CategoryItem(ProductCategory.EXOTIC_FRUIT, this, new Image("imat/layout/images/categoryIcons/exotic.png")));   //EXOTISKA FRUKT
        c.addSubCategory(new CategoryItem(ProductCategory.CITRUS_FRUIT, this, new Image("imat/layout/images/categoryIcons/citrus.png")));   //CITRUSFRUKT
        c.addSubCategory(new CategoryItem(ProductCategory.BERRY, this, new Image("imat/layout/images/categoryIcons/berry.png")));   //BÄR
        c.addSubCategory(new CategoryItem(ProductCategory.POD, this, new Image("imat/layout/images/categoryIcons/pod.png")));   //BALJVÄXTER
        cList.add(c);

        c = new CategoryItem(ProductCategory.DAIRIES, this, new Image("imat/layout/images/categoryIcons/dairy.png"));   //MEJERI
        cList.add(c);

        c = new CategoryItem("Potatis, Ris och Pasta", this, new Image("imat/layout/images/categoryIcons/potatoricepasta.png"));
        c.addSubCategory(new CategoryItem(ProductCategory.POTATO_RICE, this, new Image("imat/layout/images/categoryIcons/potato.png")));    //POTATIS OCH RIS
        c.addSubCategory(new CategoryItem(ProductCategory.PASTA, this, new Image("imat/layout/images/categoryIcons/pasta.png")));   //PASTA

        c = new CategoryItem("Skafferi", this, new Image("imat/layout/images/categoryIcons/pantry.png"));
        c.addSubCategory(new CategoryItem(ProductCategory.HERB, this, new Image("imat/layout/images/categoryIcons/herb.png"))); //ÖRTER OCH KRYDDOR
        c.addSubCategory(new CategoryItem(ProductCategory.NUTS_AND_SEEDS, this, new Image("imat/layout/images/categoryIcons/nutseed.png")));    //NÖTTER OCH FRÖN
        c.addSubCategory(new CategoryItem(ProductCategory.FLOUR_SUGAR_SALT, this, new Image("imat/layout/images/categoryIcons/flour.png")));    //MJÖL, SOCKER OCH SALT
        cList.add(c);

        c = new CategoryItem(ProductCategory.SWEET, this, new Image("imat/layout/images/categoryIcons/sweet.png")); //SÖTSAKER
        cList.add(c);





        categoryFlowPane.getChildren().clear();
        for(CategoryItem ci : cList)
            categoryFlowPane.getChildren().add(ci);
    }

    public void expandCategory(CategoryItem c) {
        for(CategoryItem sub : c.getSubCategories()) {
            int index = categoryFlowPane.getChildren().indexOf(c) + 1;
            categoryFlowPane.getChildren().add(index, sub);
        }
    }

    public void collapseCategory(CategoryItem c) {
        int index = categoryFlowPane.getChildren().indexOf(c) + 1;
        categoryFlowPane.getChildren().remove(index, index + c.getSubCategories().size());
    }

    public CategoryItem getCurrentExpandedSub() {
        return currentExpandedSub;
    }

    public void setCurrentExpandedSub(CategoryItem currentExpandedSub) {
        this.currentExpandedSub = currentExpandedSub;
    }

    public void selectCategory(CategoryItem c) {
        c.getBackgroundPane().getStyleClass().remove(c.getStandardClass());
        c.getBackgroundPane().getStyleClass().add(c.getSelectedClass());
    }

    public void deSelectCategory(CategoryItem c) {
        c.getBackgroundPane().getStyleClass().remove(c.getSelectedClass());
        c.getBackgroundPane().getStyleClass().add(c.getStandardClass());
    }

    public void showShoppingCart() {
        shoppingCartAnchorPane.getChildren().clear();
        shoppingCartAnchorPane.getChildren().add(shoppingCartController);
    }

    public void changeMode(Mode m) {
        switch (m) {
            case SHOPPING:
                toggleShoppingMode();
                return;
            case CHECKOUT:
                toggleCheckoutMode();

        }
    }

    private void toggleCheckoutMode() {
        //bigHBox.getChildren().clear();
        checkoutController.update();
        displayPane.setAlignment(Pos.CENTER);
        //bigHBox.getChildren().add(checkoutController);
        displayPane.getChildren().add(checkoutController);
        bigHBox.toBack();
        displayPane.toFront();
    }

    public void toPayment(){
        //TODO update checkoutcontroller2
        //checkoutController.update();
        displayPane.getChildren().clear();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(checkoutController2);
        bigHBox.toBack();
        displayPane.toFront();
    }

    public void toFinalPaymentStep(){
        displayPane.getChildren().clear();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(checkoutController3);
        bigHBox.toBack();
        displayPane.toFront();
    }

    public void toggleShoppingMode() {

        shoppingCartController.update();
        displayPane.toBack();
        bigHBox.toFront();
        displayPane.getChildren().clear();
        //ska prova lägga in betalsteget i en stackpane istället.
        /*bigHBox.getChildren().clear();
        bigHBox.setAlignment(Pos.CENTER_LEFT);
        bigHBox.getChildren().add(categoriesAnchorPane);
        bigHBox.getChildren().add(mainVBox);
        bigHBox.getChildren().add(shoppingCartAnchorPane);
        */
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }


}
