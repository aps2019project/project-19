package view.Graphic;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Game.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleViewController extends MenuController implements Initializable {
    @FXML
    AnchorPane center;
    @FXML
    HBox deckBar;
//    private Game game = getMainController().getGame();
    //    private final int cellsLength = getMainController().getGame().getLength();
//    private final int cellsWeight = getMainController().getGame().getWidth();
    //todo: set wight and lenght with game
    private final int cellsLength = 9;
    private final int cellsWeight = 5;
    private AnchorPane[][] anchorPaneCells;
    @FXML
    private AnchorPane rightHeroAnchor;
    @FXML
    private AnchorPane leftHeroAnchor;
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

                Image image = new Image("view/Graphic/cards/arash idle.gif");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setLayoutY(-20);
                imageView.setPreserveRatio(true);
                imageView.setPickOnBounds(true);
                anchorPane.getChildren().add(imageView);

            }
        ImageView rightHeroIcon = new ImageView(new Image("view/Graphic/cards/arash.png"));
        rightHeroIcon.setFitHeight(300);
        rightHeroIcon.setFitWidth(300);
        rightHeroIcon.setLayoutY(-50);
        rightHeroIcon.setPreserveRatio(true);
        rightHeroIcon.setPickOnBounds(true);
        rightHeroAnchor.getChildren().add(rightHeroIcon);
        ImageView leftHeroIcon = new ImageView(new Image("view/Graphic/cards/afsaneh.png"));
        leftHeroIcon.setFitHeight(300);
        leftHeroIcon.setFitWidth(300);
        leftHeroIcon.setPreserveRatio(true);
        leftHeroIcon.setPickOnBounds(true);
        leftHeroIcon.setLayoutY(-50);
        leftHeroAnchor.getChildren().add(leftHeroIcon);
        for (Node child : deckBar.getChildren()) {
            Image image = new Image("view/Graphic/cards/arash idle.gif");
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setLayoutY(-50);

            ((AnchorPane)child).getChildren().add(imageView);
        }

    }
}
