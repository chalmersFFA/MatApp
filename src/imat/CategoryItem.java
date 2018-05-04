package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-05.
 */
public class CategoryItem extends AnchorPane {

    private ProductCategory productCategory;

    @FXML
    ImageView categoryImageView, arrowImageView;

    @FXML
    Label nameLabel;

    public CategoryItem(ProductCategory productCategory) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.productCategory = productCategory;

        nameLabel.setText(productCategory.toString());
    }


}
