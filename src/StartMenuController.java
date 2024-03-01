import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartMenuController {
    @FXML
    TextField source;
    @FXML
    TextField target;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void startWikiRace(ActionEvent event) throws IOException {
        String sourceLink = source.getText();
        String targetLink = target.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        root = loader.load();

        MainController mainController = loader.getController();
        mainController.setUpThreads(sourceLink, targetLink);


        //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
