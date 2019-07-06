package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cards.*;
import model.Deck;
import model.Item;
import netWork.Client;
import netWork.ClientController;
import view.ErrorType;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class MenuController {
    private static boolean customGame = false;
    private String deckName;
    private String customGameDeck;

    private Stage stage;
    private ClientController mainController;

    public void setCustomGameDeck(String customGameDeck) {
        this.customGameDeck = customGameDeck;
    }

    public void setMainController(ClientController mainController) {
        this.mainController = mainController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public ClientController getMainController() {
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
            MenuController controller = loader.getController();
            controller.setStage(this.getStage());
            controller.setMainController(this.getMainController());
            if (controller instanceof ShopController) {
                ShopController.setController(((ShopController) controller));
                ((ShopController) controller).changeTab();
            }
            if (controller instanceof CollectionController) {
                ((CollectionController) controller).putCardsInCollection();
                ((CollectionController) controller).putDecks();
                CollectionController.setController(((CollectionController) controller));

            }
            if (controller instanceof SingleGameCustomModeController) {
                ((SingleGameCustomModeController) controller).putCardsInPane();
            }
            if (controller instanceof GameModeMenu) {
                controller.setCustomGameDeck(customGameDeck);
            }
            if (controller instanceof BattleViewController) {
                ((BattleViewController) controller).loadGame();
            }

            this.getStage().getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void createCards(JFXMasonryPane pane, ArrayList<Object> products) {
        pane.getChildren().removeIf(node -> node instanceof AnchorPane);
        for (Object product : products) {
            ImageView gif = new ImageView();
//            Image gifImage = new Image("/view/Graphic/cards/afsaneh idle.gif");
//            gif.setImage(gifImage);
            gif.setFitWidth(140);
            gif.setFitHeight(140);
            AnchorPane cardView = new AnchorPane();
            Label kind = new Label();
            Label nameLabel = new Label();
            Label mana = new Label();
            Label price = new Label();
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
                Image gifImage;
                try {
                    gifImage = new Image("/view/Graphic/cards/" + ((SoldierCard) product).getName() + " idle.gif");
                } catch (Exception e) {
                    gifImage = new Image("view/Graphic/cards/camandare farse idle.gif");
                }
                gif.setImage(gifImage);
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
                price.setText(((SoldierCard) product).getPrice() + "");
            } else if (product instanceof SpellCard) {
                Image gifImage;
                try {
                    gifImage = new Image("/view/Graphic/cards/" + ((SpellCard) product).getName() + " idle.gif");
                } catch (Exception e) {
                    gifImage = new Image("/view/Graphic/cards/weakening idle.gif");
                }
                gif.setImage(gifImage);
                cardView.getStyleClass().add("spellCard");
                nameLabel.setText(((SpellCard) product).getName());
                mana.setText(((SpellCard) product).getMana() + "");
                kind.setText("SPELL CARD");
                price.setText(((SpellCard) product).getPrice() + "");
            } else if (product instanceof Item) {
//                Image gifImage = new Image("/view/Graphic/cards/" + ((Item) product).getName() +".gif");
//                gif.setImage(gifImage);
                cardView.getStyleClass().add("item");
                nameLabel.setText(((Item) product).getName());
                kind.setText("ITEM");
                price.setText(((Item) product).getPrice() + "");
            }
            nameLabel.getStyleClass().add("labelName");
            nameLabel.setId("nameLabel");
            price.getStyleClass().add("priceLabel");
            cardView.getChildren().add(price);
            cardView.getChildren().add(nameLabel);
            cardView.getChildren().add(kind);
            cardView.getChildren().add(gif);
            gif.relocate(38, 5);
            price.relocate(102, 275);
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
                cardView.getChildren().add(gif);
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
                boolean gotError =true;
                if (pane.getId().equals("heroPane"))
                    customGame(((Label) cardView.lookup("#nameLabel")).getText());
                if (pane.getId().equals("shopPane"))
                    gotError = !mainController.buyFromShop(((Label) cardView.lookup("#nameLabel")).getText());
                if (pane.getId().equals("collectionInShopPane")) {
                    Object selling = mainController.searchInCollection(((Label) cardView.lookup("#nameLabel")).getText()).get(0);
                    if (selling instanceof SoldierCard)
                        gotError = !mainController.sellToShop(((SoldierCard) selling).getCardId());
                    if (selling instanceof SpellCard)
                        gotError = !mainController.sellToShop(((SpellCard) selling).getCardId());
                    if (selling instanceof Item)
                        gotError = !mainController.sellToShop(((Item) selling).getItemId());
                    ShopController.getController().putCards();
                }
                if (pane.getId().equals("collectionPane") && CollectionController.getController().isInDeck()) {
                    Object card = mainController.searchInCollection(((Label) cardView.lookup("#nameLabel")).getText()).get(0);
                    System.out.println(((Label) cardView.lookup("#nameLabel")).getText());
                    if (card instanceof Card) {
                        gotError = !getMainController().addToDeck(deckName, ((Card) card).getCardId());
                        mainController.getLoggedInAccount().getCollection().getCards().remove(card);
                        mainController.getLoggedInAccount().getCollection().getCards().add(((Card) card));
                    }

                    if (card instanceof Item) {
                        gotError = !mainController.addToDeck(deckName, ((Item) card).getItemId());
                        mainController.getLoggedInAccount().getCollection().getItems().remove(card);
                        mainController.getLoggedInAccount().getCollection().getItems().add(((Item) card));
                    }
                    CollectionController.getController().putCardInDeck(mainController.getLoggedInAccount().getCollection().getDecks().get(deckName));
                    putUnusedCard(pane);
                }
                ShopController.getController().getErrorLabel().setText("");
                if (gotError) {
                    AlertBox.display(Alert.AlertType.ERROR, "Shop", mainController.getErrorType().getMessage());
                    mainController.setErrorType(null);
                }
                ShopController.getController().getMoneyLabel().setText(mainController.getLoggedInAccount().getMoney() + "");
            });
        }
    }

    public void putUnusedCard(JFXMasonryPane pane) {
        ArrayList<Object> collectionCards = new ArrayList<>();
        collectionCards.addAll(mainController.getLoggedInAccount().getCollection().getCards());
        collectionCards.addAll(mainController.getLoggedInAccount().getCollection().getItems());
        ArrayList<Card> cards = new ArrayList<>(mainController.getLoggedInAccount().getCollection().getDecks().get(deckName).getCards().values());
        ArrayList<Item> items = new ArrayList<>(mainController.getLoggedInAccount().getCollection().getDecks().get(deckName).getItems().values());
        cards.stream().<Predicate<? super Object>>map(card -> o -> o instanceof Card && ((Card) o).getCardId() == card.getCardId()).forEach(collectionCards::removeIf);
        items.stream().<Predicate<? super Object>>map(item -> o -> o instanceof Item && ((Item) o).getItemId() == item.getItemId()).forEach(collectionCards::removeIf);
        createCards(pane, collectionCards);
    }

    public void customGame(String heroName) {
        if (mainController.getLoggedInAccount().getCollection().isAnyDeckAvailable()) {
            mainController.chooseHero(heroName);
            AlertBox.display(Alert.AlertType.INFORMATION, "Custom Game", heroName + " selected");
            ChoiceDialog<String> decks = new ChoiceDialog<>();
            for (Deck deck : getMainController().getLoggedInAccount().getCollection().getDecks().values()) {
                if (deck.deckIsValid())
                    decks.getItems().addAll(deck.getName());
            }
            decks.setTitle("Deck");
            decks.setContentText("Choose the deck you wanna play with");
            Optional<String> result = decks.showAndWait();
            if (decks.getItems().size() != 0 && result.isPresent()) {
                customGameDeck = result.get();
                customGame = true;
                changeMenu("gameModeMenu.fxml");
            } else {
                AlertBox.display(Alert.AlertType.WARNING, "Warning", "no deck is selected");
            }
        } else {
            AlertBox.display(Alert.AlertType.WARNING, "Warning", ErrorType.NO_VALID_DECK.getMessage());
        }
    }

    public String getCustomGameDeck() {
        return customGameDeck;
    }
}
