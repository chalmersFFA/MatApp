package imat;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan Köre on 2018-05-09.
 */
public class CheckoutController4 extends AnchorPane{

    private IMatController parentController;
    @FXML
    Button exitApplication;

    public CheckoutController4(IMatController parentController) {
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Steg4_betalning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    public void backButton() {
        parentController.changeMode(IMatController.Mode.SHOPPING);
    }

    @FXML
    public void continueShopping() {
        parentController.changeMode(IMatController.Mode.SHOPPING);
    }
    @FXML
    public void exitApplication() {
        IMatDataHandler.getInstance().shutDown();
        System.exit(0);
    }
}
