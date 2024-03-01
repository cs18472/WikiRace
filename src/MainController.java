import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.io.IOException;
import java.util.LinkedList;

public class MainController {
    @FXML
    AnchorPane mainContainer;
    @FXML
    GridPane thread0;
    @FXML
    GridPane thread1;
    @FXML
    GridPane thread2;
    @FXML
    GridPane thread3;
    @FXML
    GridPane thread4;
    @FXML
    GridPane thread5;
    @FXML
    GridPane thread6;
    @FXML
    GridPane thread7;
    @FXML
    GridPane thread8;
    @FXML
    GridPane thread9;
    @FXML
    Button solve;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private List<String> solutionPath;
    private Manager wikiRace;
    private List<Racer> racers;

    public void setUpThreads(String source, String target) {
        wikiRace = new Manager(source, target, 5, this);
        racers = new LinkedList<>();
    }

    public void startThread(ActionEvent event) throws InterruptedException{
        Node source = (Node) event.getSource();
        source.setVisible(false);
        Parent parent = source.getParent();
        Text title = (Text) parent.getChildrenUnmodifiable().get(0);
        Racer racer = new Racer(title.getText(), wikiRace);
        racers.add(racer);
        racers.get(racers.size() - 1).start();
    }

    public void setPane(int thread, String title) {
        GridPane pane = getGridPaneByThreadNumber(thread);

        Node text = pane.getChildren().get(1);
        if (text instanceof Text) {
            Text textNode = (Text) text;
            textNode.setText(title);
        }
    }

    public void setSolved(List<String> path) {
        System.out.println("called");
        this.solutionPath = path;
        solve.setVisible(true);
    }

    public void solve(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Solved.fxml"));
        root = loader.load();

        SolvedController controller = loader.getController();
        controller.setUpSolved(event, solutionPath);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane getGridPaneByThreadNumber(int thread) {
        switch (thread) {
            case 0:
                return thread0;
            case 1:
                return thread1;
            case 2:
                return thread2;
            case 3:
                return thread3;
            case 4:
                return thread4;
            case 5:
                return thread5;
            case 6:
                return thread6;
            case 7:
                return thread7;
            case 8:
                return thread8;
            case 9:
                return thread9;
            default:
                return null;
        }
    }

}
