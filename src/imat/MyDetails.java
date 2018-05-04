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
 * Created by Jonathan Köre on 2018-05-04.
 */
public class MyDetails extends AnchorPane {

    private IMatDataHandler db = IMatDataHandler.getInstance();
    private boolean editingDetails = false;
    private Customer customer = db.getCustomer();

    @FXML
    Label firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, addressLabel, postAddressLabel, postCodeLabel,
            cardOwnerLabel, cardNumberLabel;

    @FXML
    TextField firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, addressTextField,
            postAddressTextField, postCodeTextField, cardOwnerTextField, cardNumberTextField;

    @FXML
    ImageView myDetailsImageView, cardImageView;

    @FXML
    Button editDetailsButton, editCardNumber;

    public MyDetails() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("my_details.fxml"));
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
        setDetailTextFieldsVisibility(false);
        loadDetails();
    }

    public void loadDetails() {

        firstNameLabel.setText(customer.getFirstName());
        lastNameLabel.setText(customer.getLastName());
        emailLabel.setText(customer.getEmail());
        addressLabel.setText(customer.getAddress());
        postAddressLabel.setText(customer.getPostAddress()); //Vilket måste vara Orten?
        postCodeLabel.setText(customer.getPostCode());
        phoneNumberLabel.setText(customer.getMobilePhoneNumber());

        firstNameTextField.setText(customer.getFirstName());
        lastNameTextField.setText(customer.getLastName());
        emailTextField.setText(customer.getEmail());
        addressTextField.setText(customer.getAddress());
        postAddressTextField.setText(customer.getPostAddress()); //Vilket måste vara Orten?
        postCodeTextField.setText(customer.getPostCode());
        phoneNumberTextField.setText(customer.getMobilePhoneNumber());


        //TODO
        //Finns fler attribut vi måste ha med, typ korttyp

        cardOwnerLabel.setText(db.getCreditCard().getHoldersName());
        cardNumberLabel.setText(db.getCreditCard().getCardNumber());
    }

    @FXML
    public void editDetails() {
        if(editingDetails) {
            setDetailTextFieldsVisibility(false);
            editDetailsButton.setText("Redigera Uppgifter");
            editingDetails = false;
            saveDetails();
        }
        else {
            setDetailTextFieldsVisibility(true);
            editDetailsButton.setText("Spara Ändringar");
            editingDetails = true;
        }


    }
    @FXML
    public void editCard() {

    }

    private void saveDetails() {
        customer.setFirstName(firstNameTextField.getText());
        customer.setLastName(lastNameTextField.getText());
        customer.setEmail(emailTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostAddress(postAddressTextField.getText());
        db.getCustomer().setPostCode(postCodeTextField.getText());

        // TODO: 2018-05-04
        // Tänka angående om de ska ha mobilnummer eller vanligt telefonnummer?

        customer.setMobilePhoneNumber(phoneNumberTextField.getText());

        loadDetails();
    }

    private void setDetailTextFieldsVisibility(boolean b) {
        firstNameTextField.setVisible(b);
        lastNameTextField.setVisible(b);
        emailTextField.setVisible(b);
        phoneNumberTextField.setVisible(b);
        addressTextField.setVisible(b);
        postAddressTextField.setVisible(b);
        postCodeTextField.setVisible(b);
    }

}