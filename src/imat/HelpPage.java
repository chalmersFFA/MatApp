package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class HelpPage extends AnchorPane {
    @FXML
    Label titleLabel;
    @FXML
    TextArea welcomeTextArea;
    @FXML
    Line dividerLine;

    private IMatController parentController;

    public HelpPage(IMatController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Welcome_Page.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
