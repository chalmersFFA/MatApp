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
        setState(1);
    }



    public void nextState() {
        setState(state + 1);
        System.out.println("sih");
    }
    public void previousState() {
        setState(state--);
    }
    private void setState(int i) {
        if(i > 0 && i < 4)
            state = i;
        update();
    }

    private void update() {
        switch (state) {
            case 1:
                label1.setDisable(false);
                label2.setDisable(true);
                label3.setDisable(true);
                return;
            case 2:
                label1.setDisable(false);
                label2.setDisable(false);
                label3.setDisable(true);
                return;
            case 3:
                label1.setDisable(false);
                label2.setDisable(false);
                label3.setDisable(false);
                System.out.println("suh");
                return;

        }
    }

    @FXML
    private void button1Clicked() {
        setState(1);
    }
    @FXML
    private void button2Clicked() {
        setState(2);
    }
    @FXML
    private void button3Clicked() {
        setState(3);
    }
}
