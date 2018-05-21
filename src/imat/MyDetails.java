package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Jonathan Köre on 2018-05-04.
 */
public class MyDetails extends AnchorPane {
    private IMatController parentController;
    private IMatDataHandler db = IMatDataHandler.getInstance();
    private Customer customer = db.getCustomer();
    private CreditCard creditCard = db.getCreditCard();
    private Image errorImage = new Image("imat/layout/images/redCross.png");

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

    @FXML
    ImageView errorD1, errorD2, errorD3, errorD4, errorD5, errorD6, errorD7,
            errorB1, errorB2, errorB3, errorB4, errorB5;

    @FXML
    TextField card1, card2, card3, card4;

    @FXML
    ImageView helpCVC;

    @FXML
    AnchorPane CVCinfo;

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
        initErrors();
        resetDetails();
        resetCard();

        registerListener(card1, card2);
        registerListener(card2, card3);
        registerListener(card3, card4);
        registerListener(card4, cardVerificationTextField);
        CVCinfo.setVisible(false);
    }

    private void registerListener(TextField tf1, TextField tf2) {
        tf1.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() < 4 && newText.length() >= 4) {
                tf2.requestFocus();
            }
        });
    }

    private void initErrors() {
        errorD1.setImage(errorImage);
        errorD2.setImage(errorImage);
        errorD3.setImage(errorImage);
        errorD4.setImage(errorImage);
        errorD5.setImage(errorImage);
        errorD6.setImage(errorImage);
        errorD7.setImage(errorImage);
        errorB1.setImage(errorImage);
        errorB2.setImage(errorImage);
        errorB3.setImage(errorImage);
        errorB4.setImage(errorImage);
        errorB5.setImage(errorImage);

    }

    public void resetDetails() {
        resetDetailsErrors();
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
        if(detailsInfoIsValid()) {
            saveDetails();
            toggleDetailsEdit(false);
        }

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



        creditCard.setCardNumber(card1.getText() + card2.getText() + card3.getText() + card4.getText());
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
        if(cardInfoIsValid()) {
            saveCard();
            loadSavedCard();
            toggleCardEdit(false);
            useSavedCardRadioButton.setSelected(true);
        }
    }


    private boolean cardInfoIsValid() {
        resetCardErrors();
        boolean valid = true;
        if(cardValidYearComboBox.getSelectionModel().getSelectedItem() == "År") {
            valid = false;
            produceError(cardValidYearComboBox, errorB4);
        }
        if(cardValidMonthComboBox.getSelectionModel().getSelectedItem() == "Månad") {
            valid = false;
            produceError(cardValidMonthComboBox, errorB3);
        }
        if(cardVerificationTextField.getText().length() != 3) {
            valid = false;
            produceError(cardVerificationTextField, errorB5);
        }
        if(cardHolderTextField.getText().length() <= 0) {
            valid = false;
            produceError(cardHolderTextField, errorB1);
        }
        if(!isDigit(card1.getText()) || card1.getText().length()!=4){
            produceError(card1, errorB2);
            valid = false;
        }
        if(!isDigit(card2.getText()) || card2.getText().length()!=4){
            produceError(card2, errorB2);
            valid = false;
        }
        if(!isDigit(card3.getText()) || card3.getText().length()!=4){
            produceError(card3, errorB2);
            valid = false;
        }
        if(!isDigit(card4.getText()) || card4.getText().length()!=4){
            produceError(card4, errorB2);
            valid = false;
        }

        /*if(cardNumberTextField.getText().length() != 16) {
            valid = false;
            produceError(cardNumberTextField, errorB2);
        }*/
        return valid;
    }

    private boolean detailsInfoIsValid() {
        resetDetailsErrors();
        boolean valid = true;
        if(firstNameTextField.getText().length() == 0) {
            valid = false;
            produceError(firstNameTextField, errorD1);
        }
        if(lastNameTextField.getText().length() == 0) {
            valid = false;
            produceError(lastNameTextField, errorD2);
        }
        if(!emailTextField.getText().contains("@")) {
            valid = false;
            produceError(emailTextField, errorD3);
        }
        if(!isDigit(phoneNumberTextField.getText()) || phoneNumberTextField.getText().length() == 0) {
            valid = false;
            produceError(phoneNumberTextField, errorD7);
        }
        if(addressTextField.getText().length() == 0) {
            valid = false;
            produceError(addressTextField, errorD4);
        }
        if(!isLetter(postAddressTextField.getText()) || postAddressTextField.getText().length() == 0) {
            valid = false;
            produceError(postAddressTextField, errorD6);
        }
        if(!isDigit(postCodeTextField.getText()) || postCodeTextField.getText().length() != 5) {
            valid = false;
            produceError(postCodeTextField, errorD5);
        }
        return valid;
    }


    private boolean isDigit(String s) {
        boolean b = true;
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isDigit(s.charAt(i))) {
                b = false;
            }
        }
        return b;
    }
    private boolean isLetter(String s) {
        boolean b = true;
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isLetter(s.charAt(i))) {
                b = false;
            }
        }
        return b;
    }


    private void produceError(Node n, ImageView v) {
        n.setStyle("-fx-border-style: solid solid solid solid");
        n.setStyle("-fx-border-color: red");
        v.setVisible(true);
    }
    private void removeError(Node n, ImageView v) {
        n.setStyle("-fx-border-width: hidden hidden hidden hidden");
        v.setVisible(false);
    }
    private void resetCardErrors() {
        removeError(cardValidYearComboBox, errorB4);
        removeError(cardValidMonthComboBox, errorB3);
        removeError(cardVerificationTextField, errorB5);
        removeError(cardHolderTextField, errorB1);
        removeError(card1, errorB2);
        removeError(card2, errorB2);
        removeError(card3, errorB2);
        removeError(card4, errorB2);

        //removeError(cardNumberTextField, errorB2);
    }
    private void resetDetailsErrors() {
        removeError(firstNameTextField, errorD1);
        removeError(lastNameTextField, errorD2);
        removeError(emailTextField, errorD3);
        removeError(phoneNumberTextField, errorD4);
        removeError(addressTextField, errorD5);
        removeError(postAddressTextField, errorD6);
        removeError(postCodeTextField, errorD7);
    }
    public void resetCard() {
        resetCardErrors();
        toggleCardEdit(false);
    }

    @FXML
    public void backButton() {
        parentController.updateProductList(ProductCategory.BERRY);
    }

    @FXML
    public void mouseTrap(Event event){
        parentController.mouseTrap(event);
    }


    @FXML
    public void showCVC() {
        CVCinfo.setVisible(true);
    }
    @FXML
    public void hideCVC() {
        CVCinfo.setVisible(false);
    }

}