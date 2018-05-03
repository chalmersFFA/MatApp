package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-03.
 */
public class StoreListItem extends AnchorPane {
    private Product product;
    @FXML
    ImageView ecoImageView;
    @FXML
    ImageView productImageView;
    @FXML
    Label productNameLabel;
    @FXML
    Label priceLabel;

    public StoreListItem(Product product) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("store_item_test.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;


        IMatDataHandler db = IMatDataHandler.getInstance();


        String ecoImagePath = "layout/images/svanen.png";
        if(product.isEcological()){
//            ecoImageView.setImage(new Image("imat/layout/images/svanen.png"));
        }
        productImageView.setImage(db.getFXImage(this.product));

//        productNameLabel.setText(product.getName());
        //priceLabel.setText(Double.toString(product.getPrice()));
    }
}
