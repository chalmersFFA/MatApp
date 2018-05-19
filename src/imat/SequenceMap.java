package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Jonathan Köre on 2018-05-14.
 */
public class SequenceMap extends AnchorPane {
    @FXML
    Label label1, label2, label3;
    private int state = 1;

    public SequenceMap() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/sequence_map.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void nextState() {
        setState(state + 1);
        System.out.println("sih");
    }

    public void previousState() {
        setState(state--);
    }

    public void setState(int i) {
        if (i > 0 && i < 4)
            state = i;
        update();
    }

    private void update() {
        switch (state) {
            case 1:
                label1.getStyleClass().add("red");
                label2.getStyleClass().remove("red");
                label3.getStyleClass().remove("red");
                return;
            case 2:
                label1.getStyleClass().remove("red");
                label2.getStyleClass().add("red");
                label3.getStyleClass().remove("red");
                return;
            case 3:
                label1.getStyleClass().remove("red");
                label2.getStyleClass().remove("red");
                label3.getStyleClass().add("red");
                System.out.println("suh");
                return;

        }
    }

}
