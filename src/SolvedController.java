import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SolvedController {

    @FXML
    private GridPane pane;
    @FXML
    private Button restart;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setUpSolved(ActionEvent event, List<String> solutionPath) {
        Collections.reverse(solutionPath);

        pane.getChildren().clear();

        pane.getColumnConstraints().clear();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        pane.getColumnConstraints().add(column);

        pane.getRowConstraints().clear();
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        pane.getRowConstraints().addAll(Collections.nCopies(solutionPath.size(), rowConstraints));

        pane.setAlignment(Pos.CENTER);

        for (int i = 0; i < solutionPath.size(); i++) {
            String item = solutionPath.get(i);

            Text text = new Text(getTitle(item));
            text.setStyle("-fx-font-weight: bold;"); 

            Label arrowLabel = new Label();
            arrowLabel.setAlignment(Pos.CENTER);

            if (i < solutionPath.size() - 1) {
                Image arrowImage = new Image(getClass().getResourceAsStream("resources/down_arrow.png"));
                ImageView arrowImageView = new ImageView(arrowImage);
                arrowImageView.setFitHeight(text.getFont().getSize());
                arrowImageView.setPreserveRatio(true);
                arrowLabel.setGraphic(arrowImageView);
            }

            GridPane grid = new GridPane();
            grid.addRow(0, text);
            grid.addRow(1, arrowLabel);

            GridPane.setHalignment(text, HPos.CENTER);
            GridPane.setHalignment(arrowLabel, HPos.CENTER);

            grid.setAlignment(Pos.CENTER);

            pane.add(grid, 0, i);
        }
    }

    public void returnToStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public String getTitle(String link) {
        return link.substring(link.lastIndexOf('/')).replace("_", " ").replace("/", "");
    }
}
