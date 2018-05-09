package imat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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
    IMatDataHandler db = IMatDataHandler.getInstance();
    static ArrayList<CategoryItem> cList = new ArrayList<>();
    private Map<String, StoreListItem> storeListItemMap = new HashMap<String, StoreListItem>();
    private CategoryItem currentExpandedSub;
    private ShoppingCart shoppingCart = db.getShoppingCart();

    MyDetails myDetails;
    ShoppingCartController shoppingCartController;
    CheckoutController checkoutController;


    @FXML
    FlowPane mainFlowPane;

    @FXML
    FlowPane categoryFlowPane;

    @FXML
    AnchorPane categoriesAnchorPane, shoppingCartAnchorPane;

    @FXML
    Label myDetailsLabel;

    @FXML
    ImageView logoImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myDetails = new MyDetails();
        shoppingCartController = new ShoppingCartController(this);
        checkoutController = new CheckoutController(shoppingCartController);
        //showDetailsScreen();
        showShoppingCart();
        initProducts();
        initCategories();
        updateRecipeList(ProductCategory.BERRY);
    }

    private void initProducts() {
        for (Product p : db.getProducts(ProductCategory.BERRY)){
            ItemHandler itemHandler = new ItemHandler(new ShoppingItem(p,0));
            storeListItemMap.put(p.getName(), new StoreListItem(itemHandler, this));
            shoppingCartController.addToHashMap(new ShoppingCartItem(itemHandler, shoppingCartController));
        }
    }


    public void updateRecipeList(ProductCategory category) {
        mainFlowPane.getChildren().clear();
        for (Product p : db.getProducts(category)){
            mainFlowPane.getChildren().add(storeListItemMap.get(p.getName()));
        }
    }

    @FXML
    private void showDetailsScreen() {
        mainFlowPane.getChildren().clear();
        mainFlowPane.getChildren().add(myDetails);
        myDetails.initDetails();
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

    public void showCheckoutScreen() {
        mainFlowPane.getChildren().clear();
        checkoutController.initCheckoutController();
        mainFlowPane.getChildren().add(checkoutController);
    }
}
