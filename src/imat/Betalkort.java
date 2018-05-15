package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-15.
 */
public class Betalkort extends AnchorPane {

    private IMatDataHandler db = IMatDataHandler.getInstance();
    private boolean editingDetails = false;
    private CreditCard creditCard = db.getCreditCard();

    @FXML
    Label cardHolderLabel, cardNumberLabel, cardValidLabel, cardTypeLabel, cardVerificationLabel, whatIsCVCLabel,
    errorHolderLabel, errorNumberLabel, errorValidationLabel, errorCvcLabel;

    @FXML
    TextField cardHolderTextField, cardNumberTextField, cardVerificationTextField;

    @FXML
    ComboBox cardValidYearComboBox, cardValidMonthComboBox, cardTypeComboBox;

    @FXML
    Button editCardButton, saveCardButton;

    public Betalkort() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/betalkort.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initComboBoxes();
        initDetails();
    }

    private void initComboBoxes() {
        String[] nummer = {"18", "19", "20", "21", "22", "23"};
        cardValidYearComboBox.getItems().add("År");
        cardValidYearComboBox.getItems().addAll(nummer);
        cardValidYearComboBox.getSelectionModel().select("År");

        cardValidYearComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {


            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

            }
        });

        String[] dag = new String[31];

        for(int i = 0; i < 12; i++) {
            dag[i] = Integer.toString(i + 1);
        }
        cardValidMonthComboBox.getItems().add("Dag");
        cardValidMonthComboBox.getItems().addAll(dag);
        cardValidMonthComboBox.getSelectionModel().select("Dag");

        cardValidMonthComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {


            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

            }
        });
    }

    public void initDetails() {
        setDetailTextFieldsVisibility(false);
        loadDetails();
        resetErrorLabels();
    }

    private void saveDetails() {
        creditCard.setCardNumber(cardNumberTextField.getText());
        creditCard.setHoldersName(cardHolderTextField.getText());
        creditCard.setValidMonth(Integer.valueOf((String)cardValidMonthComboBox.getSelectionModel().getSelectedItem()));
        creditCard.setValidYear(Integer.valueOf((String)cardValidYearComboBox.getSelectionModel().getSelectedItem()));
        loadDetails();
    }

    private void setDetailTextFieldsVisibility(boolean b) {
        cardHolderLabel.setVisible(!b);
        cardNumberLabel.setVisible(!b);
        editCardButton.setVisible(!b);

        cardValidYearComboBox.setVisible(b);
        cardValidMonthComboBox.setVisible(b);
        cardHolderTextField.setVisible(b);
        cardNumberTextField.setVisible(b);
        cardVerificationTextField.setVisible(b);
        cardValidLabel.setVisible(b);
        cardVerificationLabel.setVisible(b);
        whatIsCVCLabel.setVisible(b);

        saveCardButton.setVisible(b);
    }


    public void loadDetails() {
        cardHolderTextField.setText(creditCard.getHoldersName());
        cardHolderLabel.setText(creditCard.getHoldersName());
        String tempCardNumber = "";
        for(int i = 0; i < creditCard.getCardNumber().length(); i++) {
            if(i < creditCard.getCardNumber().length() - 4)
                tempCardNumber += "X";
            else
                tempCardNumber += creditCard.getCardNumber().charAt(i);
        }

        cardNumberLabel.setText(tempCardNumber);


    }

    @FXML
    private void saveButtonClick() {
        if(infoIsValid()) {
            editingDetails = false;
            setDetailTextFieldsVisibility(false);
            saveDetails();
        }
    }

    @FXML
    private void editButtonClick() {
        setDetailTextFieldsVisibility(true);
        editingDetails = true;
    }

    private boolean infoIsValid() {
        resetErrorLabels();
        boolean valid = true;
        if(cardValidYearComboBox.getSelectionModel().getSelectedItem() == "År" || cardValidMonthComboBox.getSelectionModel().getSelectedItem() == "Månad") {
            errorValidationLabel.setVisible(true);
            valid = false;
        }
        if(cardVerificationTextField.getText().length() != 3) {
            errorCvcLabel.setVisible(true);
            valid = false;
        }
        if(cardHolderTextField.getText().length() <= 0) {
            errorHolderLabel.setVisible(true);
            valid = false;
        }
        if(cardNumberTextField.getText().length() != 16) {
            errorNumberLabel.setVisible(true);
            valid = false;
        }
        return valid;
    }

    private void resetErrorLabels() {
        errorHolderLabel.setVisible(false);
        errorNumberLabel.setVisible(false);
        errorValidationLabel.setVisible(false);
        errorCvcLabel.setVisible(false);
    }
}
