package imat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.util.ResourceBundle;

public class IMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(System.getProperty("user.home"));

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("imat/IMat");


        Parent root = FXMLLoader.load(getClass().getResource("fxml/IMat.fxml"), bundle);

        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setMaximized(true);

        stage.show();

    }

    //Detta sparar all info
    @Override
    public void stop() throws Exception {
        super.stop();
        IMatDataHandler.getInstance().shutDown();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
