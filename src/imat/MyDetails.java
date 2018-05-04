package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-04.
 */
public class MyDetails extends AnchorPane {

    private IMatDataHandler db = IMatDataHandler.getInstance();
    private boolean editing = false;

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


    }

    public void loadDetails() {
        firstNameLabel.setText(db.getCustomer().getFirstName());
        lastNameLabel.setText(db.getCustomer().getLastName());
        emailLabel.setText(db.getCustomer().getEmail());
        addressLabel.setText(db.getCustomer().getAddress());
        postAddressLabel.setText(db.getCustomer().getPostAddress()); //Vilket måste vara Orten?
        postCodeLabel.setText(db.getCustomer().getPostCode());
        phoneNumberLabel.setText(db.getCustomer().getMobilePhoneNumber());


        //TODO
        //Finns fler attribut vi måste ha med, typ korttyp

        cardOwnerLabel.setText(db.getCreditCard().getHoldersName());
        cardNumberLabel.setText(db.getCreditCard().getCardNumber());
    }

    @FXML
    public void editDetails() {
        if(editing) {
            setDetailTextFieldsVisibility(true);
            editDetailsButton.setText("Spara Ändringar");
            editing = false;
        }
        else {
            setDetailTextFieldsVisibility(false);
            editDetailsButton.setText("Redigera Uppgifter");
            editing = true;
        }


    }
    @FXML
    public void editCard() {
        firstNameLabel.setDisable(true);
        firstNameTextField.setDisable(false);
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