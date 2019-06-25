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
import model.Cards.Card;
import model.Cell;
import model.Collection;
import model.Game.Game;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BattleViewController extends MenuController implements Initializable {
    @FXML
    AnchorPane center;
    @FXML
    HBox deckBar;
    private Game game;

    private int cellsLength;
    private int cellsWeight;
    private Card handSelectedCard;

    //todo: set wight and lenght with game
//    private final int cellsLength = 9;
//    private final int cellsWeight = 5;
    private AnchorPane[][] anchorPaneCells;
    @FXML
    private AnchorPane rightHeroAnchor;
    @FXML
    private AnchorPane leftHeroAnchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void loadGame() {
        game = getMainController().getGame();
        cellsLength = getMainController().getGame().getLength();
        cellsWeight = getMainController().getGame().getWidth();
        getMainController().setActivePlayer();
        anchorPaneCells = new AnchorPane[cellsWeight][cellsLength];
        for (int i = 0; i < cellsLength; i++)
            for (int j = 0; j < cellsWeight; j++) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getStyleClass().add("cells");
                anchorPane.setLayoutX(i * 100 + 40);
                anchorPane.setLayoutY(j * 100);
                center.getChildren().add(anchorPane);
                anchorPaneCells[j][i] = anchorPane;
                anchorPane.setOnMouseEntered(x -> {
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("hoveredCells");
                });
                anchorPane.setOnMouseExited(x -> {
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("cells");
                });
                Cell cell = game.getCell(i+1, j+1);
                anchorPane.setOnMouseClicked(x->{
                    if(handSelectedCard!=null)
                        if(getMainController().insertCard(handSelectedCard.getName(), cell.getXCoordinate(), cell.getYCoordinate())){
                            addCardGifInGround(anchorPane,cell.getCard().getName());
                        }

                });
                if (cell.getCard() != null)
                    addCardGifInGround(anchorPane, cell.getCard().getName());

            }

        ArrayList<Card> hand = new ArrayList<>(game.getPlayer1().getHandCards().values());
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(hand.get(i).getName());
            AnchorPane child = (AnchorPane) deckBar.getChildren().get(i);
            addCardGifInDeck((AnchorPane) child, hand.get(i));
        }
        addHeroIcons(rightHeroAnchor, game.getPlayer1().getHero().getName());
        addHeroIcons(leftHeroAnchor, game.getPlayer2().getHero().getName());
    }

    private void addCardGifInDeck(AnchorPane child, Card card) {
        ((ImageView) child.getChildren().get(0)).setImage(new Image("view/Graphic/images/card_background.png"));
        CardImageView cardImageView = new CardImageView(card.getName(), CardImageView.Stance.IDLING);
        cardImageView.setPreserveRatio(true);
        cardImageView.setPickOnBounds(true);
        cardImageView.setFitWidth(200);
        cardImageView.setFitHeight(200);
        cardImageView.setLayoutY(-50);
        child.getChildren().add(cardImageView);
        cardImageView.setOnMouseClicked(x -> {
            handleHandCardSelection(child, card);
        });
    }

    private void handleHandCardSelection(AnchorPane child,Card card) {
        handSelectedCard = card;
        ImageView backGroundImage = ((ImageView) child.getChildren().get(0));
        for (Node deckBarChild : deckBar.getChildren()) {
            if (((AnchorPane) deckBarChild).getChildren().size() != 1) {
                ((ImageView) (((AnchorPane) deckBarChild).getChildren().get(0))).setImage(new Image("view/Graphic/images/card_background.png"));
            }
        }
        backGroundImage.setImage(new Image("view/Graphic/images/card_background_highlight.png"));
    }

    private void addCardGifInGround(AnchorPane anchorPane, String cardName) {
//        ImageView attack = new ImageView(new Image("view/Graphic/images/icon_atk.png"));
        CardImageView cardImageView = new CardImageView(cardName, CardImageView.Stance.IDLING);
        cardImageView.setFitWidth(100);
        cardImageView.setFitHeight(100);
        cardImageView.setLayoutY(-20);
        cardImageView.setPreserveRatio(true);
        cardImageView.setPickOnBounds(true);
        anchorPane.getChildren().add(cardImageView);
//        anchorPane.getChildren().add(attack);
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
