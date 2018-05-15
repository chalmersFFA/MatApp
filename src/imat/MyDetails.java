package imat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-04.
 */
public class MyDetails extends AnchorPane {
    private IMatController parentController;
    private Uppgifter uppgifter;
    private Betalkort betalkort;

    @FXML
    AnchorPane betalkortAnchorPane, uppgifterAnchorPane;

    @FXML
    Button backButton;


    public MyDetails(IMatController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/my_details_copy.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        uppgifter = new Uppgifter();
        betalkort = new Betalkort();
        initDetails();
    }

    public void initDetails() {
        betalkortAnchorPane.getChildren().clear();
        uppgifterAnchorPane.getChildren().clear();
        uppgifterAnchorPane.getChildren().add(uppgifter);
        betalkortAnchorPane.getChildren().add(betalkort);
    }
    @FXML
    public void editCard() {

    }
    @FXML
    public void backButton() {
        parentController.updateProductList(ProductCategory.BERRY);
    }

    public Uppgifter getUppgifter() {
        return uppgifter;
    }

    public Betalkort getBetalkort() {
        return betalkort;
    }
    @FXML
    public void mouseTrap(Event event){
        parentController.mouseTrap(event);
    }
}