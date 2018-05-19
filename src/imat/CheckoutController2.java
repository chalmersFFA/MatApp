package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan Köre on 2018-05-09.
 */
public class CheckoutController2 extends AnchorPane{

    private ShoppingCartController shoppingCartController;
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    AnchorPane myDetailsAnchorPane, betalkortAnchorPane, sequenceMapAnchorPane;



    public CheckoutController2(IMatController parentController, ShoppingCartController shoppingCartController) {
        this.shoppingCartController = shoppingCartController;
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/steg2_betalning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        sequenceMapAnchorPane.getChildren().add(parentController.getSequenceMap());

        resetCheckoutController2();

    }

    public void refreshSequenceMap() {
        sequenceMapAnchorPane.getChildren().clear();
        sequenceMapAnchorPane.getChildren().add(parentController.getSequenceMap());
        parentController.getSequenceMap().setState(2);
    }

    public void resetCheckoutController2() {
        myDetailsAnchorPane.getChildren().clear();
        myDetailsAnchorPane.getChildren().add(parentController.getMyDetails());
    }

    @FXML
    public void backButton() {
        parentController.toCheckout1();
    }

    @FXML
    public void nextButton() {
        parentController.toFinalPaymentStep();

    }

    public void update() {

    }

    @FXML
    private void toFinalPaymentStep(){
        parentController.toFinalPaymentStep();
    }
}
