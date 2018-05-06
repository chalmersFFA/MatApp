package imat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

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

    MyDetails myDetails;

    @FXML
    FlowPane mainFlowPane;

    @FXML
    FlowPane categoryFlowPane;

    @FXML
    AnchorPane categoriesAnchorPane;

    @FXML
    Label myDetailsLabel;

    @FXML
    ImageView logoImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myDetails = new MyDetails();
        //showDetailsScreen();
        initProducts();
        initCategories();
        updateRecipeList(ProductCategory.BERRY);
    }

    private void initProducts() {
        for (Product p : db.getProducts()){
            storeListItemMap.put(p.getName(), new StoreListItem(p));
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

        CategoryItem c = new CategoryItem("Frukt och grönt", this);
        c.addSubCategory(ProductCategory.BERRY);
        c.addSubCategory(ProductCategory.CABBAGE);
        c.addSubCategory(ProductCategory.CITRUS_FRUIT);
        c.addSubCategory(ProductCategory.EXOTIC_FRUIT);
        c.addSubCategory(ProductCategory.FRUIT);
        c.addSubCategory(ProductCategory.MELONS);
        c.addSubCategory(ProductCategory.ROOT_VEGETABLE);
        c.addSubCategory(ProductCategory.VEGETABLE_FRUIT);
        cList.add(c);

        c = new CategoryItem("Drycker", this);
        c.addSubCategory(ProductCategory.COLD_DRINKS);
        c.addSubCategory(ProductCategory.HOT_DRINKS);
        cList.add(c);

        c = new CategoryItem(ProductCategory.FISH, this);
        cList.add(c);

        c = new CategoryItem(ProductCategory.BREAD, this);
        cList.add(c);

        c = new CategoryItem(ProductCategory.SWEET, this);
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
        System.out.println(c.getStandardClass());
    }

    public void deSelectCategory(CategoryItem c) {
        c.getBackgroundPane().getStyleClass().remove(c.getSelectedClass());
        c.getBackgroundPane().getStyleClass().add(c.getStandardClass());
    }
}
