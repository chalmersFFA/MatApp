package imat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //ResourceBundle bundle = java.util.ResourceBundle.getBundle("recipesearch/resources/RecipeSearch");


        Parent root = FXMLLoader.load(getClass().getResource("imat/IMat.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        //stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
