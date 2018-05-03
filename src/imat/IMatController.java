package imat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jonathan Köre on 2018-05-03.
 */
public class IMatController  extends VBox implements Initializable {
    IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    FlowPane mainFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateRecipeList();
    }

    private void updateRecipeList() {
        mainFlowPane.getChildren().clear();
        StoreListItem s;
        for (Product p : db.getProducts()) {
            s = new StoreListItem(p);
            mainFlowPane.getChildren().add(s);
        }
    }

    public void yo() {
        System.out.println("yoooooooo");
    }
}
