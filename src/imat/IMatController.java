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
import java.util.ResourceBundle;

/**
 * Created by Jonathan Köre on 2018-05-03.
 */
public class IMatController extends VBox implements Initializable {
    IMatDataHandler db = IMatDataHandler.getInstance();
    static ArrayList<CategoryItem> cList = new ArrayList<>();

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
        //updateRecipeList();
        showDetailsScreen();
        //showCategories();
        initCategories();
    }


    @FXML
    private void updateRecipeList() {
        mainFlowPane.getChildren().clear();
        ArrayList<StoreListItem> storeListItems = new ArrayList<>();
        for (Product p : db.getProducts()){
            storeListItems.add(new StoreListItem(p));
        }
        for(StoreListItem s : storeListItems){
            mainFlowPane.getChildren().add(s);
        }
    }

    @FXML
    private void showDetailsScreen() {
        mainFlowPane.getChildren().clear();
        mainFlowPane.getChildren().add(myDetails);
        myDetails.initDetails();
    }

    private void showCategories() {
        categoryFlowPane.getChildren().clear();
        for(ProductCategory p : ProductCategory.values()) {
            categoryFlowPane.getChildren().add(new CategoryItem(p, this));
        }
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

        c = new CategoryItem(ProductCategory.FISH, this);
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

    


}
