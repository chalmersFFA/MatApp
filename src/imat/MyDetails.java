package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-04.
 */
public class MyDetails extends AnchorPane {
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private Customer customer = db.getCustomer();
    private CreditCard creditCard = db.getCreditCard();

    @FXML
    Button backButton;

    @FXML
    Label firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, addressLabel, postAddressLabel, postCodeLabel;

    @FXML
    TextField firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, addressTextField,
            postAddressTextField, postCodeTextField;

    @FXML
    Button editDetailsButton, saveDetailsButton;


    @FXML
    ToggleGroup cardChoice;

    @FXML
    Label cardHolderLabel, cardNumberLabel, cardValidLabel, cardVerificationLabel, whatIsCVCLabel;

    @FXML
    TextField cardHolderTextField, cardNumberTextField, cardVerificationTextField;

    @FXML
    ComboBox cardValidYearComboBox, cardValidMonthComboBox;

    @FXML
    Button saveCardButton;

    @FXML
    RadioButton changeCardRadioButton, useSavedCardRadioButton;

    @FXML
    AnchorPane newCardAnchorPane;


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

        loadSavedCard();
        initComboBoxes();

    }

    public void resetDetails() {
        toggleDetailsEdit(false);
    }

    private void saveDetails() {
        customer.setFirstName(firstNameTextField.getText());
        customer.setLastName(lastNameTextField.getText());
        customer.setEmail(emailTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostAddress(postAddressTextField.getText());
        customer.setPostCode(postCodeTextField.getText());
        customer.setMobilePhoneNumber(phoneNumberTextField.getText());

    }

    private void toggleDetailsEdit(boolean b) {
        firstNameTextField.setDisable(!b);
        lastNameTextField.setDisable(!b);
        emailTextField.setDisable(!b);
        phoneNumberTextField.setDisable(!b);
        addressTextField.setDisable(!b);
        postAddressTextField.setDisable(!b);
        postCodeTextField.setDisable(!b);

        if(!b) {
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            emailTextField.setText("");
            phoneNumberTextField.setText("");
            addressTextField.setText("");
            postAddressTextField.setText("");
            postCodeTextField.setText("");

            firstNameLabel.setText(customer.getFirstName());
            lastNameLabel.setText(customer.getLastName());
            emailLabel.setText(customer.getEmail());
            phoneNumberLabel.setText(customer.getMobilePhoneNumber());
            addressLabel.setText(customer.getAddress());
            postAddressLabel.setText(customer.getPostAddress());
            postCodeLabel.setText(customer.getPostCode());
        } else {
            firstNameTextField.setText(customer.getFirstName());
            lastNameTextField.setText(customer.getLastName());
            emailTextField.setText(customer.getEmail());
            phoneNumberTextField.setText(customer.getMobilePhoneNumber());
            addressTextField.setText(customer.getAddress());
            postAddressTextField.setText(customer.getPostAddress());
            postCodeTextField.setText(customer.getPostCode());

            firstNameLabel.setText("");
            lastNameLabel.setText("");
            emailLabel.setText("");
            phoneNumberLabel.setText("");
            addressLabel.setText("");
            postAddressLabel.setText("");
            postCodeLabel.setText("");
        }
        editDetailsButton.setVisible(!b);
        saveDetailsButton.setVisible(b);
    }


    @FXML
    private void editDetailsClick() {
        toggleDetailsEdit(true);
    }

    @FXML
    private void saveDetailsClick() {
        saveDetails();
        toggleDetailsEdit(false);
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

    private void saveCard() {
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
    private void saveCardClick() {
        if(infoIsValid()) {
            saveCard();
            loadSavedCard();
            toggleCardEdit(false);
            useSavedCardRadioButton.setSelected(true);
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

    @FXML
    public void backButton() {
        parentController.updateProductList(ProductCategory.BERRY);
    }

    @FXML
    public void mouseTrap(Event event){
        parentController.mouseTrap(event);
    }
}