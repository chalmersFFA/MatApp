package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
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
    @FXML
    ComboBox dayCombo, monthCombo, timeCombo;



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

        comboRefresh();
    }

    private void comboRefresh(){
        dayCombo.getItems().clear();
        dayCombo.getItems().addAll("25","26","27","28","29","30","31");
        monthCombo.getItems().clear();
        monthCombo.getItems().add("Maj");
        timeCombo.getItems().clear();
        timeCombo.getItems().addAll("7:00","8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00");
    }

    public void refreshSequenceMap() {
        sequenceMapAnchorPane.getChildren().clear();
        sequenceMapAnchorPane.getChildren().add(parentController.getSequenceMap());
        parentController.getSequenceMap().setState(2);
    }

    public void resetCheckoutController2() {
        myDetailsAnchorPane.getChildren().clear();
        myDetailsAnchorPane.getChildren().add(parentController.getMyDetails());
        comboRefresh();
    }

    @FXML
    public void backButton() {
        parentController.toCheckout1();
    }

    @FXML
    public void nextButton() {
        if((dayCombo.getSelectionModel().getSelectedItem().toString() != null) && (monthCombo.getSelectionModel().getSelectedItem().toString() != null) && (timeCombo.getSelectionModel().getSelectedItem().toString() != null)){
            parentController.setDeliveryTime(dayCombo.getSelectionModel().getSelectedItem().toString() + " " + monthCombo.getSelectionModel().getSelectedItem().toString() + " " + timeCombo.getSelectionModel().getSelectedItem().toString());
            parentController.toFinalPaymentStep();
        }

    }

    public void update() {

    }

    @FXML
    public void extraBackButton() {
        parentController.toggleShoppingMode();
    }

    @FXML
    private void toFinalPaymentStep(){
        parentController.toFinalPaymentStep();
    }
}
