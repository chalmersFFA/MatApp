package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jonathan Köre on 2018-05-05.
 */
public class CategoryItem extends AnchorPane {

    private IMatController parentController;
    private ProductCategory productCategory;
    private ArrayList<CategoryItem> subCategories = new ArrayList<>();
    private CategoryStrategy categoryStrategy;
    private String displayName;
    private boolean isExpanded = false;
    private String selectedClass;
    private String standardClass = "mainCategoryStandard";

    @FXML
    ImageView categoryImageView, arrowImageView;

    @FXML
    Label nameLabel;

    @FXML
    private Pane backgroundPane;

    public CategoryItem(ProductCategory productCategory, IMatController parentController, Image image) {
        this(Translator.swe(productCategory), parentController, image);
        this.productCategory = productCategory;
        categoryStrategy = new SubCategoryStrategy();
        selectedClass = "subCategorySelected";
    }

    public CategoryItem(String displayName, IMatController parentController, Image image) {
        initFxml();
        this.displayName = displayName;
        this.parentController = parentController;
        categoryStrategy = new MainCategoryStrategy();
        nameLabel.setText(displayName);
        selectedClass = "mainCategorySelected";
        backgroundPane.getStyleClass().add(standardClass);
        categoryImageView.setImage(image);
    }


    private void initFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addSubCategory(CategoryItem c) {
        c.setStandardClass("subCategoryStandard");
        c.getBackgroundPane().getStyleClass().clear();
        c.getBackgroundPane().getStyleClass().add(c.getStandardClass());
        subCategories.add(c);
    }

    @FXML
    private void onClick() {
        categoryStrategy.click(this, parentController);
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<CategoryItem> getSubCategories() {
        return subCategories;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public Pane getBackgroundPane() {
        return backgroundPane;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ImageView getArrowImageView() {
        return arrowImageView;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public String getStandardClass() {
        return standardClass;
    }

    public void setStandardClass(String standardClass) {
        this.standardClass = standardClass;
    }

}
