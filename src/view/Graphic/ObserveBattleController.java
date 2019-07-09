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
import model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ObserveBattleController extends MenuController implements Initializable {
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
    private Cell selectedCardCell;
    AnimationTimer requestUpdate;
    private CardImageView handSelectedCard;
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
                    updateCells();
                    updateStatus();

                }
            }
        };

    }

    public void loadGame() {
        game = getMainController().getGame();
        graveYardHandler();
        cellsWeight = game.getWidth();
        cellsLength = game.getLength();
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
                anchorPane.setLayoutY(j * 100);
                anchorPane.setLayoutX(i * 100 + 40);
                anchorPaneCells[j][i] = anchorPane;
                center.getChildren().add(anchorPane);
                Cell cell = game.getCell(i + 1, j + 1);
                setCellMouseHover(anchorPane);

            }
        myAccount = game.getPlayer1().getAccount();
        enemyAccount = game.getPlayer2().getAccount();
        updateCells();
        leftManaLabel.setText(game.getPlayer(enemyAccount.getUserName()).getMana() + "/" + game.getPlayer(enemyAccount.getUserName()).getMaxMana());
        addHeroIcons(leftHeroAnchor, game.getPlayer(enemyAccount.getUserName()).getHero().getName(), true);
        addHeroIcons(rightHeroAnchor, game.getPlayer(myAccount.getUserName()).getHero().getName(), false);
        rightManaLabel.setText(game.getPlayer(myAccount.getUserName()).getMana() + "/" + game.getPlayer(myAccount.getUserName()).getMaxMana());
        requestUpdate.start();
    }

    private void graveYardHandler() {
        leftGraveYard.setOnMouseEntered(e -> {
            leftGraveYard.setFitWidth(495);
            leftGraveYard.setOpacity(1);
            JFXMasonryPane pane = new JFXMasonryPane();
            pane.setPrefWidth(495);
            pane.setPrefHeight(509.0);
            game = getMainController().getGame();
            for (Card card : game.getPlayer1().getGraveYard().values()) {
                addCardToGraveYard(pane, card.getName(), "left");
            }
            left.getChildren().addAll(pane);
            AnchorPane.setTopAnchor(pane, 30.0);
        });
        graveYardOnExit(leftGraveYard, left);
        rightGraveYard.setOnMouseEntered(e -> {
            System.out.println("entered");
            rightGraveYard.setOpacity(1);
            rightGraveYard.setFitWidth(495);
            JFXMasonryPane pane = new JFXMasonryPane();
            pane.setPrefHeight(509.0);
            pane.setPrefWidth(495);
            pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            game = getMainController().getGame();
            for (Card card : game.getPlayer2().getGraveYard().values()) {
                addCardToGraveYard(pane, card.getName(), "right");
            }
            right.getChildren().addAll(pane);
            AnchorPane.setTopAnchor(pane, 30.0);
            AnchorPane.setRightAnchor(pane, 0.0);
        });
        graveYardOnExit(rightGraveYard, right);
    }

    private void graveYardOnExit(ImageView graveYard, AnchorPane anchorPane) {
        anchorPane.setOnMouseExited(e -> {
            graveYard.setFitWidth(81);
            graveYard.setOpacity(0.5);
            anchorPane.getChildren().removeIf(node -> node instanceof JFXMasonryPane);
        });
    }

    private void addCardToGraveYard(JFXMasonryPane pane, String cardName, String toward) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("graveYardCard");
        if (toward.equals("right"))
            anchorPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Label name = new Label(cardName);
        name.relocate(30, 25);
        name.getStyleClass().add("labelName");
        anchorPane.getChildren().addAll(name);
        pane.getChildren().addAll(anchorPane);
    }

    private void setCellMouseHover(AnchorPane anchorPane) {
        anchorPane.setOnMouseEntered(x -> {
            anchorPane.getStyleClass().remove(0);
            anchorPane.getStyleClass().add("hoveredCells");
        });
        anchorPane.setOnMouseExited(x -> {
            anchorPane.getStyleClass().remove(0);
            anchorPane.getStyleClass().add("cells");
        });
    }


//    private void playAttackAnimation(AnchorPane anchorPane) {
//        anchorPane.getChildren().stream().filter(node -> node instanceof CardImageView).forEach(
//                node -> ((CardImageView) node).changeStance(CardImageView.Stance.ATTACKING));
//
//        PauseTransition pauseTransition = new PauseTransition(Duration.millis(2000));
//        pauseTransition.setOnFinished(x -> {
//            anchorPane.getChildren().stream().filter(node -> node instanceof CardImageView).forEach(
//                    node -> ((CardImageView) node).changeStance(CardImageView.Stance.IDLING));
//            updateCells();
//
//        });
//        pauseTransition.play();
//    }

//    private void playMoveAnimation(Cell targetCell, Cell currentCell, AnchorPane anchorPane) {
//        anchorPane.getChildren().removeIf(node -> node instanceof CardImageView || node instanceof Label
//                || node instanceof CardImageProperties);
//        Path path = new Path(new MoveTo((currentCell.getXCoordinate() - 1) * 100 + 90, (currentCell.getYCoordinate() - 1) * 100 + 30),
//                new LineTo((targetCell.getXCoordinate() - 1) * 100 + 90, (targetCell.getYCoordinate() - 1) * 100 + 30));
//        path.setVisible(false);
//        CardImageView cardImageView = CardImageView.createCardImageView(currentCell.getCard().getName(), CardImageView.Stance.RUNING);
//        cardImageView.setFitHeight(100);
//        cardImageView.setFitWidth(100);
//        center.getChildren().addAll(cardImageView);
//        PathTransition pathTransition = new PathTransition(Duration.millis(2000), path, cardImageView);
//        pathTransition.setCycleCount(1);
//        pathTransition.setAutoReverse(false);
//        anchorPane.getStyleClass().remove(0);
//        anchorPane.getStyleClass().add("cells");
//        pathTransition.setOnFinished(x -> {
//            center.getChildren().removeAll(cardImageView);
//            updateCells();
//        });
//        pathTransition.play();
//    }

    private void addCardGifInGround(AnchorPane anchorPane, Card card) {
        String cardName = card.getName();

        CardImageView cardImageView = CardImageView.createCardImageView(cardName, CardImageView.Stance.IDLING);
        cardImageView.setLayoutY(-20);
        cardImageView.setFitWidth(100);
        cardImageView.setFitHeight(100);
        cardImageView.setPickOnBounds(true);
        cardImageView.setPreserveRatio(true);
        anchorPane.getChildren().add(cardImageView);
        if (card instanceof SoldierCard) {
            ImageView attackImage = new ObserveBattleController.CardImageProperties(new Image("view/Graphic/images/icon_atk.png"));
            ImageView healthImage = new ObserveBattleController.CardImageProperties(new Image("view/Graphic/images/icon_hp.png"));
            Label attack = new Label();
            Label health = new Label();
            attack.setText(((SoldierCard) card).getAp() + "");
            attackImage.setFitWidth(35);
            healthImage.setFitWidth(35);
            attackImage.setFitHeight(35);
            healthImage.setFitHeight(35);
            health.setText(((SoldierCard) card).getHp() + "");
            attackImage.relocate(15, 60);
            healthImage.relocate(55, 60);
            attack.getStyleClass().add("aPLabelSmall");
            health.getStyleClass().add("hPLabelSmall");
            health.relocate(65, 70);
            attack.relocate(25, 70);
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
        heroIcon.setPickOnBounds(true);
        heroIcon.setPreserveRatio(true);
        heroIcon.setLayoutY(-50);
        if (left)
            AnchorPane.setRightAnchor(heroIcon, 0.0);
        anchorPane.getChildren().add(heroIcon);
    }

    private void updateStatus() {
        rightManaLabel.setText(game.getPlayer(myAccount.getUserName()).getMana() + "/" + game.getPlayer(myAccount.getUserName()).getMaxMana());
        leftManaLabel.setText(game.getPlayer(enemyAccount.getUserName()).getMana() + "/" + game.getPlayer(enemyAccount.getUserName()).getMaxMana());
    }

    private void updateCells() {
        game = getMainController().getGame();
        for (int i = 0; i < cellsLength; i++) {
            for (int j = 0; j < cellsWeight; j++) {
                AnchorPane anchorPane = anchorPaneCells[j][i];
                Cell cell = game.getCell(i + 1, j + 1);
                anchorPane.getStyleClass().remove(0);
                anchorPane.getStyleClass().add("cells");
                setCellMouseHover(anchorPane);
                anchorPane.getChildren().removeIf(node -> node instanceof CardImageView || node instanceof Label
                        || node instanceof CardImageProperties);
                if (cell.getCard() != null) {
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
