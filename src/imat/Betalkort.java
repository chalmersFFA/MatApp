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
public class Betalkort extends AnchorPane {

    private IMatDataHandler db = IMatDataHandler.getInstance();
    private boolean editingDetails = false;
    private Customer customer = db.getCustomer();

    @FXML
    Label cardOwnerLabel, cardNumberLabel;

    @FXML
    TextField cardOwnerTextField, cardNumberTextField;


    @FXML
    Button editCardNumber;

    public Betalkort() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/betalkort.fxml"));
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

    private void saveDetails() {


        // TODO: 2018-05-04
        // Tänka angående om de ska ha mobilnummer eller vanligt telefonnummer?


        loadDetails();
    }

    private void setDetailTextFieldsVisibility(boolean b) {
    }

    @FXML
    public void editDetails() {
        if (editingDetails) {
            setDetailTextFieldsVisibility(false);
            editCardNumber.setText("Redigera Betalkort");
            editingDetails = false;
            saveDetails();
        } else {
            setDetailTextFieldsVisibility(true);
            editCardNumber.setText("Spara Ändringar");
            editingDetails = true;
        }
    }

    public void loadDetails() {



        //TODO
        //Finns fler attribut vi måste ha med, typ korttyp

        /*cardOwnerLabel.setText(db.getCreditCard().getHoldersName());
        cardNumberLabel.setText(db.getCreditCard().getCardNumber());*/
    }
}
