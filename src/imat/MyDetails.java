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
    boolean verified = false;
    boolean shoppingVersion;

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
    Label cardHolderLabel, cardNumberLabel, cardValidLabel, cardVerificationLabel, whatIsCVCLabel, verifyVerificationLabel;

    @FXML
    TextField cardHolderTextField, cardNumberTextField, cardVerificationTextField, verifyVerificationTextField;

    @FXML
    ComboBox cardValidYearComboBox, cardValidMonthComboBox;

    @FXML
    Button saveCardButton, verifyCardButton;

    @FXML
    RadioButton changeCardRadioButton, useSavedCardRadioButton;

    @FXML
    AnchorPane newCardAnchorPane, savedCardAnchorPane, savedCardExtendedAnchorPane;

    @FXML
    ImageView errorD1, errorD2, errorD3, errorD4, errorD5, errorD6, errorD7,
            errorB1, errorB2, errorB3, errorB4, errorB5, errorS1;

    @FXML
    TextField card1, card2, card3, card4;

    @FXML
    ImageView helpCVC, helpCVCS;

    @FXML
    AnchorPane CVCinfo, CVCinfoS;

    @FXML
    Label errorLabelD, errorLabelB;

    private static final int tooltipDelay = 500;

    Tooltip errorFirstName = new Tooltip("Fältet får bara innehålla bokstäver, T.ex. Hjördis");
    Tooltip errorLastName = new Tooltip("Fältet får bara innehålla bokstäver, T.ex. Svensson");
    Tooltip errorEmail = new Tooltip("Skriv in din e-postadress, T.ex. hjordis@hotmail.com");
    Tooltip errorAddress = new Tooltip("Skriv in din adress, T.ex. Bovägen 123");
    Tooltip errorPostalCode = new Tooltip("Skriv på formatet #####, bara siffror");
    Tooltip errorCity = new Tooltip("Den ort du bor i, T.ex. Göteborg");
    Tooltip errorPhoneNumber = new Tooltip("Fältet får bara innehålla siffror, T.ex. 0701231234");

    Tooltip errorCVCSaved = new Tooltip("Fältet måste enbart innehålla tre siffror och måste matcha det sparade kortets kod");
    Tooltip errorCardName = new Tooltip("Fältet får bara innehålla bokstäver, T.ex. Hjördis Svensson");
    Tooltip errorCardNumber = new Tooltip("Varje ruta måste ernbart innehålla fyra siffror var, hittas på framsidan av ditt betalkort");
    Tooltip errorExpiryMonth = new Tooltip("Månaden på året ditt kort går ut, hittas på framsidan av ditt betalkort");
    Tooltip errorExpiryYear = new Tooltip("Året ditt kort går ut, hittas på framsidan av ditt betalkort");
    Tooltip errorCVCNew = new Tooltip("Fältet måste enbart innehålla tre siffror");
    //Tooltip errorCVCWrong = new Tooltip("Fältet måste enbart innehålla tre siffror och måste matcha det sparade kortets kod");




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
        resetSavedCard();

        registerListener(card1, card2);
        registerListener(card2, card3);
        registerListener(card3, card4);
        registerListener(card4, cardVerificationTextField);
        CVCinfo.setVisible(false);
        CVCinfoS.setVisible(false);

    }

    private void resetSavedCard() {
        removeError(verifyVerificationTextField, errorS1);
        verifyVerificationTextField.setDisable(false);
        verifyCardButton.setVisible(true);
        verified = false;
    }

    @FXML
    private void verifyCVC() {
        if(!shoppingVersion){
            System.out.println(creditCard.getVerificationCode());
            if(verifyVerificationTextField.getText().equals(Integer.toString(creditCard.getVerificationCode()))) {
                verifyVerificationTextField.setDisable(true);
                verifyCardButton.setVisible(false);
                removeError(verifyVerificationTextField, errorS1);
                verified = true;
            } else {
                produceError(verifyVerificationTextField, errorS1);

            }
        }
    }


    public void loadShoppingVersion() {
        shoppingVersion = true;
        savedCardAnchorPane.setStyle("-fx-border-color: #d3d3d3");
        savedCardExtendedAnchorPane.setVisible(false);
        verifyVerificationLabel.setVisible(false);
        verifyVerificationTextField.setVisible(false);
        removeError(verifyVerificationTextField, errorS1);
        helpCVCS.setVisible(false);
    }

    public void loadCheckoutVersion() {
        shoppingVersion = false;
        savedCardAnchorPane.setStyle("-fx-border-color: white");
        savedCardExtendedAnchorPane.setVisible(true);
        verifyVerificationLabel.setVisible(true);
        verifyVerificationTextField.setVisible(true);
        helpCVCS.setVisible(true);
        resetSavedCard();
        System.out.println(db.getCreditCard().getHoldersName());
    }

    public void update(){
        if (db.getCreditCard().getHoldersName().equals("")){
            changeCardRadioButton.setSelected(true);
            changeCard();
            toggleCardEdit(true);
        }
        resetCardErrors();
        resetDetailsErrors();
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
        errorS1.setImage(errorImage);

        parentController.addToolTip(errorD1, errorFirstName, tooltipDelay);
        parentController.addToolTip(errorD2, errorLastName, tooltipDelay);
        parentController.addToolTip(errorD3, errorEmail, tooltipDelay);
        parentController.addToolTip(errorD4, errorAddress, tooltipDelay);
        parentController.addToolTip(errorD5, errorPostalCode, tooltipDelay);
        parentController.addToolTip(errorD6, errorCity, tooltipDelay);
        parentController.addToolTip(errorD7, errorPhoneNumber, tooltipDelay);

        parentController.addToolTip(errorS1, errorCVCSaved, tooltipDelay);
        parentController.addToolTip(errorB1, errorCardName, tooltipDelay);
        parentController.addToolTip(errorB2, errorCardNumber, tooltipDelay);
        parentController.addToolTip(errorB3, errorExpiryMonth, tooltipDelay);
        parentController.addToolTip(errorB4, errorExpiryYear, tooltipDelay);
        parentController.addToolTip(errorB5, errorCVCNew, tooltipDelay);
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

            firstNameLabel.toFront();
            lastNameLabel.toFront();
            emailLabel.toFront();
            phoneNumberLabel.toFront();
            addressLabel.toFront();
            postAddressLabel.toFront();
            postCodeLabel.toFront();

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

            firstNameLabel.toBack();
            lastNameLabel.toBack();
            emailLabel.toBack();
            phoneNumberLabel.toBack();
            addressLabel.toBack();
            postAddressLabel.toBack();
            postCodeLabel.toBack();
        }
        editDetailsButton.setVisible(!b);
        saveDetailsButton.setVisible(b);
    }


    @FXML
    private void editDetailsClick() {
        System.out.println("yes");
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

    private void clearChangeCardFields(){
        card1.clear();
        card2.clear();
        card3.clear();
        card4.clear();
        cardHolderTextField.clear();
        cardValidMonthComboBox.getSelectionModel().clearSelection();
        cardValidYearComboBox.getSelectionModel().clearSelection();
        cardVerificationTextField.clear();
    }

    private void toggleCardEdit(boolean b) {
        newCardAnchorPane.setVisible(b);

    }

    private void saveCard() {
        creditCard.setCardNumber(card1.getText() + card2.getText() + card3.getText() + card4.getText());
        creditCard.setHoldersName(cardHolderTextField.getText());
        creditCard.setValidMonth(Integer.valueOf((String)cardValidMonthComboBox.getSelectionModel().getSelectedItem()));
        creditCard.setValidYear(Integer.valueOf((String)cardValidYearComboBox.getSelectionModel().getSelectedItem()));
        creditCard.setVerificationCode(Integer.valueOf(cardVerificationTextField.getText()));
        resetSavedCard();
        verifyVerificationTextField.setText(Integer.toString(creditCard.getVerificationCode()));
        verifyCVC();
        clearChangeCardFields();
    }


    public void loadSavedCard() {
        cardHolderLabel.setText(creditCard.getHoldersName());

        String tempCardNumber = "";
        for(int i = 0; i < creditCard.getCardNumber().length(); i++) {
            if(i < creditCard.getCardNumber().length() - 4)
                tempCardNumber += "x";
            else
                tempCardNumber += creditCard.getCardNumber().charAt(i);
        }

        cardNumberLabel.setText(tempCardNumber);


    }

    @FXML
    public void saveCardClick() {
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
        if(cardVerificationTextField.getText().length() != 3 || (!isDigit(cardVerificationTextField.getText()))) {
            valid = false;
            produceError(cardVerificationTextField, errorB5);
        }
        if(cardHolderTextField.getText().length() <= 0 && isLetter(cardHolderTextField.getText())) {
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
        if(cardValidYearComboBox.getSelectionModel().getSelectedItem().toString().equals("18")) {
            switch(cardValidMonthComboBox.getSelectionModel().getSelectedItem().toString()) {
                case("1"):
                case("2"):
                case("3"):
                case("4"):
                    produceError(cardValidMonthComboBox, errorB3);
                    valid = false;
            }
        }
        if(!valid)
            errorLabelB.setVisible(true);
        return valid;
    }

    public boolean detailsInfoIsValid() {
        resetDetailsErrors();
        boolean valid = true;
        if(firstNameTextField.getText().length() == 0 && isLetter(firstNameTextField.getText())) {
            valid = false;
            produceError(firstNameTextField, errorD1);
        }
        if(lastNameTextField.getText().length() == 0 && isLetter(lastNameTextField.getText())) {
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
        if(!valid)
            errorLabelD.setVisible(true);
        return valid;
    }

    public boolean detailsIsValid() {
        if(customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty() ||
                customer.getEmail().isEmpty() || customer.getMobilePhoneNumber().isEmpty() || customer.getPostAddress().isEmpty()
                || customer.getPostCode().isEmpty()) {
            System.out.println("yo");
            editDetailsClick();
            saveDetailsClick();
            return false;
        }
        return true;
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
    public void produceVerifyError() {
        produceError(verifyVerificationTextField, errorS1);
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
        errorLabelB.setVisible(false);
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
        errorLabelD.setVisible(false);
    }
    public void resetCard() {
        resetCardErrors();
        toggleCardEdit(false);
        useSavedCardRadioButton.setSelected(true);
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
    @FXML
    public void showCVCS() {
        CVCinfoS.setVisible(true);
    }
    @FXML
    public void hideCVCS() {
        CVCinfoS.setVisible(false);
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean otherCardSelected(){
        if (cardChoice.getSelectedToggle() != null) {
            RadioButton selected = (RadioButton) cardChoice.getSelectedToggle();
            if(selected.equals(changeCardRadioButton)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}