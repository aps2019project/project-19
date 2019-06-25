package view.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Cell;
import model.Game.Game;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BattleViewController extends MenuController implements Initializable {
    @FXML
    AnchorPane center;
    @FXML
    HBox deckBar;
    @FXML
    Label rightManaLabel;
    @FXML
    Label leftManaLabel;
    @FXML
    Label errorBox;
    private Game game;

    private int cellsLength;
    private int cellsWeight;
    private CardImageView handSelectedCard;
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
        for (int i = 0; i < cellsWeight; i++) {
            for (int j = 0; j < cellsLength; j++) {
                anchorPaneCells[i][j] = new AnchorPane();
            }
        }
        for (int i = 0; i < cellsLength; i++)
            for (int j = 0; j < cellsWeight; j++) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getStyleClass().add("cells");
                anchorPane.setLayoutX(i * 100 + 40);
                anchorPane.setLayoutY(j * 100);
                center.getChildren().add(anchorPane);
                anchorPaneCells[j][i] = anchorPane;
                Cell cell = game.getCell(i + 1, j + 1);
                anchorPane.setOnMouseEntered(x -> {
                    if(game.getPlayer1().isAnyCardSelected() )
                        return;
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("hoveredCells");
                });
                anchorPane.setOnMouseExited(x -> {
                    if(game.getPlayer1().isAnyCardSelected())
                        return;
                    anchorPane.getStyleClass().remove(0);
                    anchorPane.getStyleClass().add("cells");
                });
                anchorPane.setOnMouseClicked(x -> {
                    handleCellsMouseClick(cell);

                });
                updateCells();

            }

        ArrayList<Card> hand = new ArrayList<>(game.getPlayer1().getHandCards().values());
        putHandsCards(hand);
        rightManaLabel.setText(game.getPlayer1().getMana() + "/" + game.getPlayer1().getMaxMana());
        leftManaLabel.setText(game.getPlayer2().getMana() + "/" + game.getPlayer2().getMaxMana());
        addHeroIcons(rightHeroAnchor, game.getPlayer1().getHero().getName(), false);
        addHeroIcons(leftHeroAnchor, game.getPlayer2().getHero().getName(), true);
    }

    private void handleCellsMouseClick(Cell cell) {
        if (handSelectedCard != null) {
            if (getMainController().insertCard(handSelectedCard.getCardName(), cell.getXCoordinate(), cell.getYCoordinate())) {
                updateCells();
                removeCardFromHand();
                handSelectedCard = null;
                updateStatus();
                return;
            } else {
                errorBox.setText(getMainController().getErrorType().getMessage());
                getMainController().setErrorType(null);

            }
        }
//        if(cell.getCard()!= null){
//            AnchorPane anchorPane = anchorPaneCells[cell.getYCoordinate()-1][cell.getXCoordinate()-1];
//            anchorPane.getStyleClass().remove(0);
//            anchorPane.getStyleClass().add("selectedCell");
//            getMainController().selectCardOrItem(game.getPlayer1(),cell.getCard().getInBattleCardId(),0);
//        }
    }

    private void removeCardFromHand() {
        for (Node child : deckBar.getChildren()) {
            ((AnchorPane) child).getChildren().removeIf(node -> node instanceof CardImageView);
        }
    }

    private void putHandsCards(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            AnchorPane child = (AnchorPane) deckBar.getChildren().get(i);
            addCardGifInDeck((AnchorPane) child, hand.get(i));
        }
    }

    private void addCardGifInDeck(AnchorPane child, Card card) {
        ((ImageView) child.getChildren().get(0)).setImage(new Image("view/Graphic/images/card_background.png"));
        for (Node childChild : child.getChildren()) {
            if (childChild instanceof CardImageView) {
                ((CardImageView) childChild).changeImage(card.getName(), CardImageView.Stance.IDLING);
                return;
            }
        }
        CardImageView cardImageView = new CardImageView(card.getName(), CardImageView.Stance.IDLING);
        cardImageView.setPreserveRatio(true);
        cardImageView.setPickOnBounds(true);
        cardImageView.setFitWidth(200);
        cardImageView.setFitHeight(200);
        cardImageView.setLayoutY(-50);
        child.getChildren().add(cardImageView);
        if (card instanceof SoldierCard) {
            ImageView attackImage = new ImageView(new Image("view/Graphic/images/icon_atk.png"));
            ImageView healthImage = new ImageView(new Image("view/Graphic/images/icon_hp.png"));
            ImageView manaImage = new ImageView(new Image("view/Graphic/images/mana.png"));
            Label attack = new Label();
            Label health = new Label();
            Label mana = new Label();
            attack.setText(((SoldierCard) card).getAp() + "");
            health.setText(((SoldierCard) card).getHp() + "");
            mana.setText(card.getMana() + "");
            attackImage.setFitWidth(70);
            attackImage.setFitHeight(70);
            healthImage.setFitWidth(70);
            healthImage.setFitHeight(70);
            manaImage.setFitWidth(50);
            manaImage.setFitHeight(50);
            attackImage.relocate(30, 120);
            healthImage.relocate(110,120);
            manaImage.relocate(20, 20);
            attack.getStyleClass().add("aPLabel");
            health.getStyleClass().add("hPLabel");
            mana.getStyleClass().add("manaLabel");
            attack.relocate(50, 140);
            health.relocate(130, 140);
            mana.relocate(30, 30);
            child.getChildren().add(attackImage);
            child.getChildren().add(attack);
            child.getChildren().add(healthImage);
            child.getChildren().add(health);
            child.getChildren().add(manaImage);
            child.getChildren().add(mana);
        }
        cardImageView.setOnMouseClicked(x -> {
            handleHandCardSelection(child, card);
        });
    }

    private void handleHandCardSelection(AnchorPane child, Card card) {
        for (Node childChild : child.getChildren()) {
            if(childChild instanceof CardImageView){
                handSelectedCard = (CardImageView) childChild;
                ImageView backGroundImage = ((ImageView) child.getChildren().get(0));
                for (Node deckBarChild : deckBar.getChildren()) {
                    if (((AnchorPane) deckBarChild).getChildren().size() != 1) {
                        ((ImageView) (((AnchorPane) deckBarChild).getChildren().get(0))).setImage(new Image("view/Graphic/images/card_background.png"));
                    }
                }
                backGroundImage.setImage(new Image("view/Graphic/images/card_background_highlight.png"));
            }
        }
    }

    private void addCardGifInGround(AnchorPane anchorPane, Card card) {
        String cardName = card.getName();
        for (Node childChild : anchorPane.getChildren()) {
            if (childChild instanceof CardImageView) {
                ((CardImageView) childChild).changeImage(cardName, CardImageView.Stance.IDLING);
                return;
            }
        }
        CardImageView cardImageView = new CardImageView(cardName, CardImageView.Stance.IDLING);
        cardImageView.setFitWidth(100);
        cardImageView.setFitHeight(100);
        cardImageView.setLayoutY(-20);
        cardImageView.setPreserveRatio(true);
        cardImageView.setPickOnBounds(true);
        anchorPane.getChildren().add(cardImageView);
        if (card instanceof SoldierCard) {
            ImageView attackImage = new ImageView(new Image("view/Graphic/images/icon_atk.png"));
            ImageView healthImage = new ImageView(new Image("view/Graphic/images/icon_hp.png"));
            Label attack = new Label();
            Label health = new Label();
            attack.setText(((SoldierCard) card).getAp() + "");
            attackImage.setFitWidth(35);
            attackImage.setFitHeight(35);
            healthImage.setFitWidth(35);
            health.setText(((SoldierCard) card).getHp() + "");
            healthImage.setFitHeight(35);
            attackImage.relocate(15, 60);
            healthImage.relocate(55,60);
            attack.getStyleClass().add("aPLabelSmall");
            health.getStyleClass().add("hPLabelSmall");
            attack.relocate(25, 70);
            health.relocate(65, 70);
            anchorPane.getChildren().add(attackImage);
            anchorPane.getChildren().add(attack);
            anchorPane.getChildren().add(healthImage);
            anchorPane.getChildren().add(health);
        }
    }

    private void addHeroIcons(AnchorPane anchorPane, String name, boolean left) {
        ImageView heroIcon = new ImageView(new Image("view/Graphic/cards/" + name + ".png"));
        heroIcon.setFitHeight(300);
        heroIcon.setFitWidth(300);
        heroIcon.setPreserveRatio(true);
        heroIcon.setPickOnBounds(true);
        heroIcon.setLayoutY(-50);
        if (left)
            AnchorPane.setRightAnchor(heroIcon, 0.0);
        anchorPane.getChildren().add(heroIcon);
    }

    public void endTurn() {
        handSelectedCard = null;
        getMainController().endTurn();
        getMainController().endTurn();
        updateStatus();
    }

    private void updateStatus() {
        rightManaLabel.setText(game.getPlayer1().getMana() + "/" + game.getPlayer1().getMaxMana());
        leftManaLabel.setText(game.getPlayer2().getMana() + "/" + game.getPlayer2().getMaxMana());
        putHandsCards(new ArrayList<>(game.getPlayer1().getHandCards().values()));
    }

    private void updateCells() {
        for (int i = 0; i < cellsLength; i++) {
            for (int j = 0; j < cellsWeight; j++) {
                Cell cell = game.getCell(i + 1, j + 1);
                if(cell.getCard()!= null){
                    addCardGifInGround(anchorPaneCells[j][i],cell.getCard());
                }
            }
        }
    }

}
