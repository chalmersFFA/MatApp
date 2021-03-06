package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-15.
 */
public class Uppgifter extends AnchorPane {

    private IMatDataHandler db = IMatDataHandler.getInstance();
    private boolean editingDetails = false;
    private Customer customer = db.getCustomer();
    private IMatController parentController;

    @FXML
    Label firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, addressLabel, postAddressLabel, postCodeLabel,
            cardOwnerLabel, cardNumberLabel;

    @FXML
    TextField firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, addressTextField,
            postAddressTextField, postCodeTextField, cardOwnerTextField, cardNumberTextField;

    @FXML
    ImageView myDetailsImageView, cardImageView;

    @FXML
    Button editDetailsButton, saveDetailsButton, editCardNumber;


    public Uppgifter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/uppgifter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initDetails();
    }

    public void initDetails() {

    }

    private void saveUppgifter() {
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
    private void editDetails() {
        toggleDetailsEdit(true);
    }

    @FXML
    private void saveDetails() {
        toggleDetailsEdit(false);
    }

}
