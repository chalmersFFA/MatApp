package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
    ToggleGroup cardChoice;

    @FXML
    Label cardHolderLabel, cardNumberLabel, cardValidLabel, cardTypeLabel, cardVerificationLabel, whatIsCVCLabel;

    @FXML
    TextField cardHolderTextField, cardNumberTextField, cardVerificationTextField;

    @FXML
    ComboBox cardValidYearComboBox, cardValidMonthComboBox, cardTypeComboBox;

    @FXML
    Button editCardButton, saveCardButton;

    @FXML
    RadioButton changeCardRadioButton, useSavedCardRadioButton;

    @FXML
    AnchorPane newCardAnchorPane;

    public Betalkort() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/betalkort.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        loadSavedCard();
        initComboBoxes();
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

        String[] dag = new String[12];

        for(int i = 0; i < 12; i++) {
            dag[i] = Integer.toString(i + 1);
        }
        cardValidMonthComboBox.getItems().add("Månad");
        cardValidMonthComboBox.getItems().addAll(dag);
        cardValidMonthComboBox.getSelectionModel().select("Månad");

        cardValidMonthComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {


            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

            }
        });

        cardChoice = new ToggleGroup();
        changeCardRadioButton.setToggleGroup(cardChoice);
        useSavedCardRadioButton.setToggleGroup(cardChoice);
        useSavedCardRadioButton.setSelected(true);

        cardChoice.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (cardChoice.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) cardChoice.getSelectedToggle();
                    if(selected.equals(changeCardRadioButton)) {
                        changeCard();
                    } else {
                        useSavedCard();
                    }
                }
            }
        });
    }

    private void useSavedCard() {
        toggleCardEdit(false);
    }

    private void changeCard() {
        toggleCardEdit(true);
    }

    private void toggleCardEdit(boolean b) {
        newCardAnchorPane.setVisible(b);

    }

    private void saveDetails() {
        creditCard.setCardNumber(cardNumberTextField.getText());
        creditCard.setHoldersName(cardHolderTextField.getText());
        creditCard.setValidMonth(Integer.valueOf((String)cardValidMonthComboBox.getSelectionModel().getSelectedItem()));
        creditCard.setValidYear(Integer.valueOf((String)cardValidYearComboBox.getSelectionModel().getSelectedItem()));
    }


    public void loadSavedCard() {
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
            saveDetails();
            loadSavedCard();
            toggleCardEdit(false);
        }
    }


    private boolean infoIsValid() {
        resetErrorLabels();
        boolean valid = true;
        if(cardValidYearComboBox.getSelectionModel().getSelectedItem() == "År" || cardValidMonthComboBox.getSelectionModel().getSelectedItem() == "Månad") {
            valid = false;
        }
        if(cardVerificationTextField.getText().length() != 3) {
            valid = false;
        }
        if(cardHolderTextField.getText().length() <= 0) {
            valid = false;
        }
        if(cardNumberTextField.getText().length() != 16) {
            valid = false;
        }
        return valid;
    }

    private void resetErrorLabels() {
    }
}
