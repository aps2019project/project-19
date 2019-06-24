package view.Graphic;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleViewController extends MenuController implements Initializable {
    @FXML
    AnchorPane center;
    //    private final int cellsLength = getMainController().getGame().getLength();
//    private final int cellsWeight = getMainController().getGame().getWidth();
    //todo: set wight and lenght with game
    private final int cellsLength = 9;
    private final int cellsWeight = 5;
    private AnchorPane[][] anchorPaneCells;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPaneCells = new AnchorPane[cellsWeight][cellsLength];
        for (int i = 0; i < cellsWeight; i++)
            for (int j = 0; j < cellsLength; j++) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getStyleClass().add("cells");
                anchorPane.setLayoutX(j * 100 + 40);
                anchorPane.setLayoutY(i * 100);
                center.getChildren().add(anchorPane);
                anchorPaneCells[i][j] = anchorPane;
                anchorPane.setOnMouseEntered(x -> {
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("hoveredCells");
                });
                anchorPane.setOnMouseExited(x->{
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("cells");
                });
//                Image image = new Image("View/Graphic/cards/Arash_idle.gif");
//                ImageView imageView = new ImageView(image);
//                imageView.setFitWidth(70);
//                imageView.setFitHeight(70);
//                anchorPane.getChildren().add(imageView);
            }

    }
}
