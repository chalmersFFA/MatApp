package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private MyDetails myDetails;

    @FXML
    AnchorPane myDetailsAnchorPane, betalkortAnchorPane, sequenceMapAnchorPane;
    @FXML
    ComboBox dayCombo, monthCombo, timeCombo;
    @FXML
    ImageView timeError, dayError, monthError;
    private Image errorImage = new Image("imat/layout/images/redCross.png");

    Tooltip dayErrorTooltip = new Tooltip("Välj en dag");
    Tooltip monthErrorTooltip = new Tooltip("Välj en månad");
    Tooltip timeErrorTooltip = new Tooltip("Välj en tid");
    private static final int tooltipDelay = 500;




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
        myDetails = parentController.getMyDetails();
        resetCheckoutController2();

        comboRefresh();
        errorRefresh();

        parentController.addToolTip(dayError, dayErrorTooltip, tooltipDelay);
        parentController.addToolTip(monthError, monthErrorTooltip, tooltipDelay);
        parentController.addToolTip(timeError, timeErrorTooltip, tooltipDelay);
    }

    private void comboRefresh(){
        dayCombo.getItems().clear();
        dayCombo.getItems().addAll("30","31");
        monthCombo.getItems().clear();
        monthCombo.getItems().add("Maj");
        timeCombo.getItems().clear();
        timeCombo.getItems().addAll("7:00","8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00");
    }
    private void errorRefresh(){
        dayError.setImage(errorImage);
        dayError.setVisible(false);
        dayCombo.getStyleClass().removeAll("badComboBox");

        monthError.setImage(errorImage);
        monthError.setVisible(false);
        monthCombo.getStyleClass().removeAll("badComboBox");

        timeError.setImage(errorImage);
        timeError.setVisible(false);
        timeCombo.getStyleClass().removeAll("badComboBox");
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
        if(myDetails.detailsIsValid() && (dayCombo.getSelectionModel().getSelectedItem() != null) && (monthCombo.getSelectionModel().getSelectedItem() != null) &&
                (timeCombo.getSelectionModel().getSelectedItem() != null) && myDetails.isVerified()){
            parentController.setDeliveryTime(dayCombo.getSelectionModel().getSelectedItem().toString() + " " + monthCombo.getSelectionModel().getSelectedItem().toString() + " " + timeCombo.getSelectionModel().getSelectedItem().toString());
            parentController.toFinalPaymentStep();
        }else{
            if(dayCombo.getSelectionModel().getSelectedItem() == null){
                dayError.setVisible(true);
                dayCombo.getStyleClass().add("badComboBox");
            }else{
                dayError.setVisible(false);
                dayCombo.getStyleClass().removeAll("badComboBox");
            }
            if(monthCombo.getSelectionModel().getSelectedItem() == null){
                monthError.setVisible(true);
                monthCombo.getStyleClass().add("badComboBox");
            }else{
                monthError.setVisible(false);
                monthCombo.getStyleClass().removeAll("badComboBox");
            }
            if(timeCombo.getSelectionModel().getSelectedItem() == null){
                timeError.setVisible(true);
                timeCombo.getStyleClass().add("badComboBox");
            }else{
                timeError.setVisible(false);
                timeCombo.getStyleClass().removeAll("badComboBox");
            }
            if(!myDetails.isVerified())
                myDetails.produceVerifyError();
        }

    }

    public void update() {
        errorRefresh();
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
