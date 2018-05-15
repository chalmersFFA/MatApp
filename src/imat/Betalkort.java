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
    Label cardHolderLabel, cardNumberLabel, cardValidLabel, cardTypeLabel, cardVerificationLabel, whatIsCVCLabel;

    @FXML
    TextField cardHolderTextField, cardNumberTextField, cardVerificationTextField;

    @FXML
    ComboBox cardValidYearComboBox, cardValidDayComboBox, cardTypeComboBox;

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

        for(int i = 0; i < 30; i++) {
            dag[i] = Integer.toString(i + 1);
        }
        cardValidDayComboBox.getItems().add("Dag");
        cardValidDayComboBox.getItems().addAll(dag);
        cardValidDayComboBox.getSelectionModel().select("Dag");

        cardValidDayComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {


            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

            }
        });
    }

    public void initDetails() {
        setDetailTextFieldsVisibility(false);
        loadDetails();
    }

    private void saveDetails() {
        creditCard.setCardNumber(cardNumberTextField.getText());
        creditCard.setHoldersName(cardHolderTextField.getText());
        creditCard.setValidMonth((int)cardValidDayComboBox.getSelectionModel().getSelectedItem());
        creditCard.setValidYear((int)cardValidYearComboBox.getSelectionModel().getSelectedItem());
        loadDetails();
    }

    private void setDetailTextFieldsVisibility(boolean b) {
        cardHolderLabel.setVisible(!b);
        cardNumberLabel.setVisible(!b);
        editCardButton.setVisible(!b);

        cardValidYearComboBox.setVisible(b);
        cardValidDayComboBox.setVisible(b);
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
        setDetailTextFieldsVisibility(false);
        editingDetails = false;
        saveDetails();
    }

    @FXML
    private void editButtonClick() {
        setDetailTextFieldsVisibility(true);
        editingDetails = true;
    }
}
