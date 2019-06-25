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
                anchorPane.setOnMouseExited(x -> {
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("cells");
                });
                addCardGifInGround(anchorPane, "arash");

            }
        addHeroIcons(rightHeroAnchor, "arash");
        addHeroIcons(leftHeroAnchor, "afsaneh");
        for (Node child : deckBar.getChildren()) {
            addCardGifInDeck((AnchorPane) child, "arash");
        }

    }

    private void addCardGifInDeck(AnchorPane child, String cardName) {
        ((ImageView) child.getChildren().get(0)).setImage(new Image("view/Graphic/images/card_background.png"));
        Image image = new Image("view/Graphic/cards/" + cardName + " idle.gif");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setLayoutY(-50);
        child.getChildren().add(imageView);
        imageView.setOnMouseClicked(x -> {

            ImageView backGroundImage = ((ImageView) child.getChildren().get(0));

            for (Node deckBarChild : deckBar.getChildren()) {
                if (((AnchorPane) deckBarChild).getChildren().size() != 1) {
                    ((ImageView) (((AnchorPane) deckBarChild).getChildren().get(0))).setImage(new Image("view/Graphic/images/card_background.png"));
                }
            }
            backGroundImage.setImage(new Image("view/Graphic/images/card_background_highlight.png"));
        });
    }

    private void addCardGifInGround(AnchorPane anchorPane, String cardName) {
        Image image = new Image("view/Graphic/cards/" + cardName + " idle.gif");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setLayoutY(-20);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        anchorPane.getChildren().add(imageView);
    }

    private void addHeroIcons(AnchorPane anchorPane, String name) {
        ImageView heroIcon = new ImageView(new Image("view/Graphic/cards/" + name + ".png"));
        heroIcon.setFitHeight(300);
        heroIcon.setFitWidth(300);
        heroIcon.setPreserveRatio(true);
        heroIcon.setPickOnBounds(true);
        heroIcon.setLayoutY(-50);
        anchorPane.getChildren().add(heroIcon);
    }
}
