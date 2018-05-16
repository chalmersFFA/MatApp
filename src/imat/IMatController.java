package imat;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.*;

import javax.tools.Tool;
import java.lang.reflect.Field;
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

    private CategoryItem currentExpandedMain;
    private ShoppingCart shoppingCart = db.getShoppingCart();
    private SequenceMap sequenceMap = new SequenceMap();

    public enum Mode {
        SHOPPING,
        CHECKOUT
    }

    private Mode currentMode = Mode.SHOPPING;
    HelpPage helpPage;
    MyDetails myDetails;
    ShoppingCartController shoppingCartController;
    CheckoutController checkoutController;
    CheckoutController2 checkoutController2;
    CheckoutController3 checkoutController3;
    CheckoutController4 checkoutController4;
    OrderHistoryController orderHistoryController;

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
    Label helpLabel;

    @FXML
    Label orderHistoryLabel;

    @FXML
    ImageView myFavourites;

    @FXML
    Label currentSiteLabel;

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

    @FXML
    AnchorPane headerAnchorPane;

    Image favouriteImage = new Image("imat/layout/images/favourite.png");
    Image favouriteImagePliant = new Image("imat/layout/images/favourite_pliant.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myDetails = new MyDetails(this);
        helpPage = new HelpPage(this);
        shoppingCartController = new ShoppingCartController(this);
        checkoutController = new CheckoutController(this, shoppingCartController);
        checkoutController2 = new CheckoutController2(this, shoppingCartController);
        checkoutController3 = new CheckoutController3(this, shoppingCartController);
        checkoutController4 = new CheckoutController4(this);
        orderHistoryController = new OrderHistoryController(this, shoppingCartController);
        showShoppingCart();
        initProducts();
        initCategories();
        shoppingCart.clear();
        updateProductList(ProductCategory.BERRY);
        shoppingCart.fireShoppingCartChanged(null, false);
        orderHistoryController.createHistory();
    }

    private void initProducts() {
        ShoppingCartItem s;
        for (Product p : db.getProducts(ProductCategory.BERRY)) {
            //ItemHandler itemHandler = new ItemHandler(new ShoppingItem(p,0));
            storeListItemMap.put(p.getName(), new StoreListItem(p, this));
            shoppingCartController.addToHashMap(new ShoppingCartItem(p, shoppingCartController));
            checkoutController.addToHashMap(new ShoppingCartItem(p, shoppingCartController));
            checkoutController3.addToHashMap(new steg3_betalning_item_controller(p, shoppingCartController));
        }
    }


    public void updateProductList(ProductCategory category) {
        mainFlowPane.getChildren().clear();
        for (Product p : db.getProducts(category)) {
            mainFlowPane.setHgap(30);
            mainFlowPane.setVgap(10);
            mainFlowPane.setPadding(new Insets(30, 10, 10, 10));
            mainFlowPane.getChildren().add(storeListItemMap.get(p.getName()));
            currentSiteLabel.setText("Kategori: " + Translator.swe(category));

        }
    }

    public void displayFavourites() {
        mainFlowPane.getChildren().clear();
        for (Product p : db.favorites()) {
            mainFlowPane.getChildren().add(storeListItemMap.get(p.getName()));
        }
        //favouriteLabel.setId("current");
        currentSiteLabel.setText("Mina Favoritvaror");

    }

    @FXML
    private void showDetailsScreen() {
        toggleShoppingMode();
        mainFlowPane.toFront();
        mainFlowPane.getChildren().clear();
        mainFlowPane.getChildren().add(myDetails);
        mainFlowPane.setAlignment(Pos.CENTER);
        myDetails.initDetails();
        currentSiteLabel.setText("Mitt Konto");

    }
    @FXML
    private void showOrderScreen() {
        toggleShoppingMode();
        mainFlowPane.toFront();
        mainFlowPane.getChildren().clear();
        mainFlowPane.getChildren().add(orderHistoryController);
        mainFlowPane.setAlignment(Pos.CENTER);
        currentSiteLabel.setText("Mina Ordrar");

    }

    @FXML
    private void escapeHatch() {
        pressedHelp();
        /**
         * En temporär lösning
         */
    }

    @FXML
    private void pressedHelp() {
        toggleShoppingMode();
        mainFlowPane.toFront();
        mainFlowPane.getChildren().clear();
        mainFlowPane.getChildren().add(helpPage);
        mainFlowPane.setAlignment(Pos.CENTER);
        currentSiteLabel.setText("Startsida");
        System.out.println("help clicked");

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
        for (CategoryItem ci : cList)
            categoryFlowPane.getChildren().add(ci);
    }

    public void expandCategory(CategoryItem c) {
        for (CategoryItem sub : c.getSubCategories()) {
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
        toCheckout1();
    }

    public void toCheckout1() {
        checkoutController.refreshSequenceMap();
        displayPane.getChildren().clear();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(checkoutController);
        bigHBox.toBack();
        displayPane.toFront();
    }

    public void toPayment() {
        checkoutController2.refreshSequenceMap();
        checkoutController2.init();
        displayPane.getChildren().clear();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(checkoutController2);
        bigHBox.toBack();
        displayPane.toFront();
    }

    public void toFinalPaymentStep() {
        checkoutController3.refreshSequenceMap();
        displayPane.getChildren().clear();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(checkoutController3);
        bigHBox.toBack();
        displayPane.toFront();
    }

    public void thankYou() {
        refreshHistory();
        displayPane.getChildren().clear();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(checkoutController4);
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
    public void mouseTrap(Event event) {
        event.consume();
    }


    public MyDetails getMyDetails() {
        return myDetails;
    }

    public SequenceMap getSequenceMap() {
        return sequenceMap;
    }

    public CategoryItem getCurrentExpandedMain() {
        return currentExpandedMain;
    }

    public void setCurrentExpandedMain(CategoryItem currentExpandedMain) {
        this.currentExpandedMain = currentExpandedMain;
    }

    public static void hackTooltipStartTiming(Tooltip tooltip, int delay) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(delay)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToolTip(Node n, Tooltip t, int delay) {
        t.setStyle("-fx-font-size: 1.5em");
        IMatController.hackTooltipStartTiming(t, delay);
        Tooltip.install(n, t);
    }
    public void refreshHistory(){
        orderHistoryController.createHistory();
    }
    @FXML
    public void ChangePliancyFavourite(){
       // myFavourites.setImage(favouriteImagePliant);
        favouriteLabel.setOpacity(0.6);
    }
    @FXML
    public void endPliancyFavourite(){
        //myFavourites.setImage(favouriteImage);
        favouriteLabel.setOpacity(1);
    }
    @FXML
    public void ChangePliancyMyAccount(){
        myDetailsLabel.setOpacity(0.6);
    }
    @FXML
    public void endPliancyMyAccount(){
        myDetailsLabel.setOpacity(1);
    }
    @FXML
    public void ChangePliancyhelpPage(){
        helpLabel.setOpacity(0.6);
    }
    @FXML
    public void endPliancyHelpPage(){
        helpLabel.setOpacity(1);
    }
    @FXML
    public void ChangePliancyOrderHistory(){
        orderHistoryLabel.setOpacity(0.6);
    }
    @FXML
    public void endPliancyOrderHistory(){
        orderHistoryLabel.setOpacity(1);
    }
}