package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cards.Hero;
import model.Cards.Minion;
import model.Cards.SoldierCard;
import model.Cards.SpellCard;
import model.Item;
import view.ErrorType;

import java.util.ArrayList;

public class MenuController {
    private static boolean customGame = false;
    @FXML
    private Label label;
    private Stage stage;
    private String deckName;
    private Controller mainController;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public Controller getMainController() {
        return mainController;
    }

    public static boolean isCustomGame() {
        return customGame;
    }

    public static void setCustomGame(boolean customGame) {
        MenuController.customGame = customGame;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }

    public void changeMenu(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            Parent root = loader.load();
            MenuController controller = (MenuController) loader.getController();
            controller.setStage(this.getStage());
            controller.setMainController(this.getMainController());
            if (controller instanceof ShopController) {
                ShopController.setController(((ShopController) controller));
            }
            if (controller instanceof CollectionController) {
                ((CollectionController) controller).putCardsInCollection();
                CollectionController.setController(((CollectionController) controller));
            }
            if (controller instanceof SingleGameCustomModeController) {
                ((SingleGameCustomModeController) controller).putCardsInPane();
            }

            this.getStage().getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void createCards(JFXMasonryPane pane, ArrayList<Object> products) {
        pane.getChildren().removeIf(node -> node instanceof AnchorPane);
        for (Object product : products) {
            AnchorPane cardView = new AnchorPane();
            Label kind = new Label();
            Label nameLabel = new Label();
            Label mana = new Label();
            kind.getStyleClass().add("kindLabel");
            mana.getStyleClass().add("manaLabel");
            mana.setId("manaLabel");
            ImageView manaView = new ImageView();
            mana.setId("mana");
            manaView.setImage(new Image("/view/Graphic/images/mana.png"));
            if (product instanceof SoldierCard) {
                if (product instanceof Hero)
                    kind.setText("HERO");
                if (product instanceof Minion)
                    kind.setText("MINION");
                nameLabel.setText(((SoldierCard) product).getName());
                Label aPLabel = new Label();
                Label hPLabel = new Label();
                aPLabel.getStyleClass().add("aPLabel");
                hPLabel.getStyleClass().add("hPLabel");
                aPLabel.setText(((SoldierCard) product).getAp() + "");
                hPLabel.setText(((SoldierCard) product).getHp() + "");
                mana.setText(((SoldierCard) product).getMana() + "");
                cardView.getStyleClass().add("soldierCard");
                cardView.getChildren().add(aPLabel);
                cardView.getChildren().add(hPLabel);
                aPLabel.relocate(39, 165);
                hPLabel.relocate(159, 165);
            } else if (product instanceof SpellCard) {
                cardView.getStyleClass().add("spellCard");
                nameLabel.setText(((SpellCard) product).getName());
                mana.setText(((SpellCard) product).getMana() + "");
                kind.setText("SPELL CARD");
            } else if (product instanceof Item) {
                cardView.getStyleClass().add("item");
                nameLabel.setText(((Item) product).getName());
                kind.setText("ITEM");
            }
            nameLabel.getStyleClass().add("labelName");
            nameLabel.setId("nameLabel");
            cardView.getChildren().add(nameLabel);
            cardView.getChildren().add(kind);
            if (!(product instanceof Item)) {
                cardView.getChildren().add(manaView);
                cardView.getChildren().add(mana);
                mana.relocate(8, 8);
                manaView.relocate(-7, -7);
            }
            kind.relocate(14, 140);
            nameLabel.relocate(10, 120);
            pane.getChildren().add(cardView);

            cardView.setOnMouseExited(event -> {
                cardView.getChildren().removeIf(node -> node instanceof ImageView);
                if (!(product instanceof Item)) {
                    cardView.getChildren().add(manaView);
                    cardView.getChildren().remove(mana);
                    cardView.getChildren().add(mana);
                }
            });

            cardView.setOnMouseEntered(event -> {
                for (int i = 0; i < 8; i++) {
                    Image image = new Image("view/Graphic/images/cardShadow.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(240);
                    imageView.setFitHeight(300);
                    imageView.setLayoutX(-10);
                    imageView.setLayoutY(-10);
                    cardView.getChildren().add(imageView);
                }
            });

            cardView.setOnMouseClicked(event -> {
                if (pane.getId().equals("heroPane"))
                    customGame(((Label) cardView.lookup("#nameLabel")).getText());
                if (pane.getId().equals("shopPane"))
                    mainController.buyFromShop(((Label) cardView.lookup("#nameLabel")).getText());
                if (pane.getId().equals("collectionInShopPane")) {
                    Object selling = mainController.searchInCollection(((Label) cardView.lookup("#nameLabel")).getText()).get(0);
                    if (selling instanceof SoldierCard)
                        mainController.sellToShop(((SoldierCard) selling).getCardId());
                    if (selling instanceof SpellCard)
                        mainController.sellToShop(((SpellCard) selling).getCardId());
                    if (selling instanceof Item)
                        mainController.sellToShop(((Item) selling).getItemId());
                    ShopController.getController().putCards();
//                    pane.getChildren().remove(cardView);
                }
                ShopController.getController().getErrorLabel().setText("");
                if (mainController.getErrorType() != null) {
                    ShopController.getController().getErrorLabel().setText(mainController.getErrorType().getMessage());
                    mainController.setErrorType(null);
                }
                ShopController.getController().getMoneyLabel().setText(mainController.getLoggedInAccount().getMoney() + "");
            });
        }
    }

    public void customGame(String heroName) {
        if (mainController.getLoggedInAccount().getCollection().isAnyDeckAvailable()) {
            mainController.chooseHero(heroName);
            AlertBox.display(Alert.AlertType.INFORMATION, "Custom Game", heroName + " selected");
            do {
                deckName = TextReceiver.getText("Custom Game", "please enter a deck to play with");
            } while (deckName == null || deckName.equals(""));
            customGame = true;
            changeMenu("gameModeMenu.fxml");
        }

        AlertBox.display(Alert.AlertType.WARNING, "Warning", ErrorType.NO_VALID_DECK.getMessage());
    }
}
