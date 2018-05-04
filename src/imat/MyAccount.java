package imat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Jonathan Köre on 2018-05-04.
 */
public class MyAccount extends AnchorPane {

    @FXML
    Label surname, lastname, email, phoneNumber, address, postalNumber, postalArea;

    @FXML
    ImageView myInfoImageView, cardImageView;

    @FXML
    Button editInfoButton, editCardNumber;
}
