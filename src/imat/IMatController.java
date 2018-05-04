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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Jonathan Köre on 2018-05-03.
 */
public class IMatController  extends VBox implements Initializable {
    IMatDataHandler db = IMatDataHandler.getInstance();

    MyDetails myDetails;

    @FXML
    FlowPane mainFlowPane;

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

}
