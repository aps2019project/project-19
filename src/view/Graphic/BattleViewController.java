package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Account;
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
    @FXML
    ImageView rightGraveYard;
    @FXML
    ImageView leftGraveYard;
    @FXML
    AnchorPane left;
    @FXML
    AnchorPane right;


    private Game game;
    private Account myAccount;
    private Account enemyAccount;
    private int cellsLength;
    private int cellsWeight;
    AnimationTimer requestUpdate;
    private CardImageView handSelectedCard;
    //todo: maybe we should change all getPlayer1 's to getActivePlayer
    //private final int cellsLength = 9;
//    private final int cellsWeight = 5;
    private AnchorPane[][] anchorPaneCells;
    @FXML
    private AnchorPane rightHeroAnchor;
    @FXML
    private AnchorPane leftHeroAnchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestUpdate = new AnimationTimer() {
            //            every 200 milisecond sends a request to server to see if game is started or not
            private long lastTime = 0;
            private long second = 1000000000;
            private double time = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (getMainController() == null)
                    return;
                if (now > lastTime + second) {
                    lastTime = now;
                    if (!isYourTurn(game)) {
                        updateCells();
                        updateStatus();
                    }
                }
            }
        };

    }

    public void loadGame() {
        game = getMainController().getGame();
        graveYardHandler();
        myAccount = getMainController().getLoggedInAccount();
        enemyAccount = getMainController().getOpponentAccount();
        cellsLength = game.getLength();
        cellsWeight = game.getWidth();
        anchorPaneCells = new AnchorPane[cellsWeight][cellsLength];
        //create battleGround cells
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
                setCellMouseHover(anchorPane, true, game);
                anchorPane.setOnMouseClicked(x -> {
                    handleCellsMouseClick(cell);
                });

            }

        updateCells();
        ArrayList<Card> hand = new ArrayList<>(game.getPlayer(myAccount.getUserName()).getHandCards().values());
        putHandsCards(hand);
        rightManaLabel.setText(game.getPlayer(myAccount.getUserName()).getMana() + "/" + game.getPlayer(myAccount.getUserName()).getMaxMana());
        leftManaLabel.setText(game.getPlayer(enemyAccount.getUserName()).getMana() + "/" + game.getPlayer(enemyAccount.getUserName()).getMaxMana());
        addHeroIcons(rightHeroAnchor, game.getPlayer(myAccount.getUserName()).getHero().getName(), false);
        addHeroIcons(leftHeroAnchor, game.getPlayer(enemyAccount.getUserName()).getHero().getName(), true);
        requestUpdate.start();
    }

    private void graveYardHandler() {
        leftGraveYard.setOnMouseEntered(e -> {
            leftGraveYard.setFitWidth(495);
            leftGraveYard.setOpacity(1);
            JFXMasonryPane pane = new JFXMasonryPane();
            for (Card card : getMainController().getGame().getPlayer1().getGraveYard().values()) {
                addCardToGraveYard(pane, card.getName(), "left");
            }
            left.getChildren().addAll(pane);
            AnchorPane.setTopAnchor(pane, 30.0);
        });
        graveYardOnExit(leftGraveYard, left);
        rightGraveYard.setOnMouseEntered(e -> {
            System.out.println("entered");
            rightGraveYard.setFitWidth(495);
            rightGraveYard.setOpacity(1);
            JFXMasonryPane pane = new JFXMasonryPane();
            pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            for (Card card : getMainController().getGame().getPlayer2().getGraveYard().values()) {
                addCardToGraveYard(pane, card.getName(), "right");
            }
            right.getChildren().addAll(pane);
            AnchorPane.setTopAnchor(pane, 30.0);
            AnchorPane.setRightAnchor(pane, 0.0);
        });
        graveYardOnExit(rightGraveYard, right);
    }

    private void graveYardOnExit(ImageView graveYard, AnchorPane anchorPane) {
        graveYard.setOnMouseExited(e -> {
            graveYard.setFitWidth(81);
            graveYard.setOpacity(0.5);
            anchorPane.getChildren().removeIf(node -> node instanceof JFXMasonryPane);
        });
    }

    private void addCardToGraveYard(JFXMasonryPane pane, String cardName, String toward) {
        AnchorPane anchorPane = new AnchorPane();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("view/Graphic/images/deckCard.png"));
        if (toward.equals("right"))
            imageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        imageView.setFitWidth(250);
        Label name = new Label(cardName);
        name.getStyleClass().add("labelName");
        name.relocate(30, 25);
        anchorPane.getChildren().addAll(imageView, name);
        pane.getChildren().addAll(anchorPane);
    }

    private void setCellMouseHover(AnchorPane anchorPane, boolean on, Game game) {
        if (!isYourTurn(game))
            return;
        if (!on) {
            anchorPane.setOnMouseEntered(x -> {
            });
            anchorPane.setOnMouseExited(x -> {
            });
            return;
        }
        anchorPane.setOnMouseEntered(x -> {
            anchorPane.getStyleClass().remove(0);
            anchorPane.getStyleClass().add("hoveredCells");
        });
        anchorPane.setOnMouseExited(x -> {
            anchorPane.getStyleClass().remove(0);
            anchorPane.getStyleClass().add("cells");
        });
    }

    private void handleCellsMouseClick(Cell cell) {
        game = getMainController().getGame();
        if (!isYourTurn(game))
            return;
        if (handSelectedCard != null) {
            //insert card block
            if (getMainController().insertCard(handSelectedCard.getCardName(), cell.getXCoordinate(), cell.getYCoordinate())) {
                updateCells();
                removeCardsFromHand();
                handSelectedCard = null;
                updateStatus();
                return;
            } else {
                errorBox.setText(getMainController().getErrorType().getMessage());
                getMainController().setErrorType(null);
            }
        }

        if (game.getPlayer(myAccount.getUserName()).isAnyCardSelected()) {
            Cell currentCell = game.getPlayer(myAccount.getUserName()).getInBattleCards().get(game.getPlayer(myAccount.getUserName()).getSelectedCard());
            AnchorPane currentAnchorePane = anchorPaneCells[currentCell.getYCoordinate() - 1][currentCell.getXCoordinate() - 1];
            if (cell.getCard() == null) {

                //move block

                if (getMainController().moveCard(cell.getXCoordinate(), cell.getYCoordinate())) {
                    playMoveAnimation(cell, currentCell, currentAnchorePane);
                    return;
                } else {
                    errorBox.setText(getMainController().getErrorType().getMessage());
                    getMainController().setErrorType(null);
                    game.getPlayer(myAccount.getUserName()).setSelectedCard(null);
                    currentAnchorePane.getStyleClass().remove(0);
                    currentAnchorePane.getStyleClass().add("cells");
                }
            } else {
                //attack block
                Cell targetCell = cell;
                switch (getMainController().attack(targetCell.getCard().getInBattleCardId())) {
                    case 1:
                        AnchorPane anchorPane = anchorPaneCells[cell.getYCoordinate() - 1][cell.getXCoordinate() - 1];
                        playAttackAnimation(anchorPane);
                    case 0:
                        playAttackAnimation(currentAnchorePane);
                        return;
                    case -1:
                        errorBox.setText(getMainController().getErrorType().getMessage());
                        getMainController().setErrorType(null);
                        game.getPlayer(myAccount.getUserName()).setSelectedCard(null);
                        currentAnchorePane.getStyleClass().remove(0);
                        currentAnchorePane.getStyleClass().add("cells");
                }
            }
        }
        if (cell.getCard() != null) {
            //select Card Block
            AnchorPane anchorPane = anchorPaneCells[cell.getYCoordinate() - 1][cell.getXCoordinate() - 1];
            if (getMainController().selectCardOrItem(game.getPlayer(myAccount.getUserName()), cell.getCard().getInBattleCardId(), 0)) {
                updateCells();
                setCellMouseHover(anchorPane, false, game);
                anchorPane.getStyleClass().remove(0);
                anchorPane.getStyleClass().add("selectedCell");
            } else {
                errorBox.setText(getMainController().getErrorType().getMessage());
                getMainController().setErrorType(null);
            }
        }
    }

    private boolean isYourTurn(Game game) {
        if (game == null)
            return true;
        String user1 = game.getPlayer1().getAccount().getUserName();
        String user2 = game.getPlayer2().getAccount().getUserName();
        if (game.isTurnOfPlayerOne())
            return user1.equals(myAccount.getUserName());
        else return user2.equals(myAccount.getUserName());
    }

    private void playAttackAnimation(AnchorPane anchorPane) {
        anchorPane.getChildren().stream().filter(node -> node instanceof CardImageView).forEach(
                node -> ((CardImageView) node).changeStance(CardImageView.Stance.ATTACKING));

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(2000));
        pauseTransition.setOnFinished(x -> {
            anchorPane.getChildren().stream().filter(node -> node instanceof CardImageView).forEach(
                    node -> ((CardImageView) node).changeStance(CardImageView.Stance.IDLING));
            updateCells();

        });
        pauseTransition.play();
    }

    private void playMoveAnimation(Cell targetCell, Cell currentCell, AnchorPane anchorPane) {
        anchorPane.getChildren().removeIf(node -> node instanceof CardImageView || node instanceof Label
                || node instanceof CardImageProperties);
        Path path = new Path(new MoveTo((currentCell.getXCoordinate() - 1) * 100 + 90, (currentCell.getYCoordinate() - 1) * 100 + 30),
                new LineTo((targetCell.getXCoordinate() - 1) * 100 + 90, (targetCell.getYCoordinate() - 1) * 100 + 30));
        path.setVisible(false);
        CardImageView cardImageView = new CardImageView(targetCell.getCard().getName(), CardImageView.Stance.RUNING);
        cardImageView.setFitHeight(100);
        cardImageView.setFitWidth(100);
        center.getChildren().addAll(cardImageView);
        PathTransition pathTransition = new PathTransition(Duration.millis(2000), path, cardImageView);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        anchorPane.getStyleClass().remove(0);
        anchorPane.getStyleClass().add("cells");
        pathTransition.setOnFinished(x -> {
            center.getChildren().removeAll(cardImageView);
            updateCells();
        });
        pathTransition.play();
    }

    private void removeCardsFromHand() {
        for (Node child : deckBar.getChildren()) {
            ((AnchorPane) child).getChildren().removeIf(node -> node instanceof CardImageView || node instanceof Label
                    || node instanceof CardImageProperties);
        }
    }

    private void putHandsCards(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            AnchorPane child = (AnchorPane) deckBar.getChildren().get(i);
            addCardGifInHand((AnchorPane) child, hand.get(i));
        }
    }

    private void addCardGifInHand(AnchorPane child, Card card) {
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
            ImageView attackImage = new CardImageProperties(new Image("view/Graphic/images/icon_atk.png"));
            ImageView healthImage = new CardImageProperties(new Image("view/Graphic/images/icon_hp.png"));
            ImageView manaImage = new CardImageProperties(new Image("view/Graphic/images/mana.png"));
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
            healthImage.relocate(110, 120);
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
            if (childChild instanceof CardImageView) {
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
//        for (Node childChild : anchorPane.getChildren()) {
//            if (childChild instanceof CardImageView) {
//                ((CardImageView) childChild).changeImage(cardName, CardImageView.Stance.IDLING);
//                return;
//            }
//        }
        CardImageView cardImageView = new CardImageView(cardName, CardImageView.Stance.IDLING);
        cardImageView.setFitWidth(100);
        cardImageView.setFitHeight(100);
        cardImageView.setLayoutY(-20);
        cardImageView.setPreserveRatio(true);
        cardImageView.setPickOnBounds(true);
        anchorPane.getChildren().add(cardImageView);
        if (card instanceof SoldierCard) {
            ImageView attackImage = new CardImageProperties(new Image("view/Graphic/images/icon_atk.png"));
            ImageView healthImage = new CardImageProperties(new Image("view/Graphic/images/icon_hp.png"));
            Label attack = new Label();
            Label health = new Label();
            attack.setText(((SoldierCard) card).getAp() + "");
            attackImage.setFitWidth(35);
            attackImage.setFitHeight(35);
            healthImage.setFitWidth(35);
            health.setText(((SoldierCard) card).getHp() + "");
            healthImage.setFitHeight(35);
            attackImage.relocate(15, 60);
            healthImage.relocate(55, 60);
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
        game = getMainController().getGame();
        updateStatus();
    }

    private void updateStatus() {
        rightManaLabel.setText(game.getPlayer(myAccount.getUserName()).getMana() + "/" + game.getPlayer(myAccount.getUserName()).getMaxMana());
        leftManaLabel.setText(game.getPlayer(enemyAccount.getUserName()).getMana() + "/" + game.getPlayer(enemyAccount.getUserName()).getMaxMana());
        putHandsCards(new ArrayList<>(game.getPlayer(myAccount.getUserName()).getHandCards().values()));
    }

    private void updateCells() {
        game = getMainController().getGame();
        for (int i = 0; i < cellsLength; i++) {
            for (int j = 0; j < cellsWeight; j++) {
                Cell cell = game.getCell(i + 1, j + 1);
                AnchorPane anchorPane = anchorPaneCells[j][i];
                anchorPane.getStyleClass().remove(0);
                anchorPane.getStyleClass().add("cells");
                setCellMouseHover(anchorPane, true, game);
                if (cell.getCard() != null) {
                    anchorPane.getChildren().removeIf(node -> node instanceof CardImageView || node instanceof Label
                            || node instanceof CardImageProperties);
                    addCardGifInGround(anchorPane, cell.getCard());
                }
            }
        }
    }

    class CardImageProperties extends ImageView {
        public CardImageProperties(Image image) {
            super(image);
        }

        public CardImageProperties(String url) {
            super(url);
        }
    }
}
