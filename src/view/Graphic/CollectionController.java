package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Cards.Card;
import model.Deck;

import java.util.ArrayList;

public class CollectionController extends MenuController {
    private static CollectionController controller = new CollectionController();
    @FXML
    AnchorPane mainPane = new AnchorPane();
    @FXML
    private JFXMasonryPane collectionPane = new JFXMasonryPane();
    @FXML
    private TextField searchBar = new TextField();
    @FXML
    private ImageView newDeckButton = new ImageView();
    @FXML
    private Label deckButtonLabel = new Label();
    @FXML
    private JFXMasonryPane decksPane = new JFXMasonryPane();
    @FXML
    private TextField deckName = new TextField();
    @FXML
    private ImageView backButton = new ImageView();
    @FXML
    private Button importButton = new Button();
    private boolean inDeck = false;
    private String selectedDeckName;

    public boolean isInDeck() {
        return inDeck;
    }

    public static CollectionController getController() {
        return controller;
    }

    public static void setController(CollectionController controller) {
        CollectionController.controller = controller;
    }

    public void exit() {
        changeMenu("MainMenu.fxml");
    }

    public void putCardsInCollection() {
        collectionPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        Controller controller = getMainController();
        ArrayList<Object> products = new ArrayList<>();
        if (searchBar.getText().equals("")) {
            products.addAll(controller.getLoggedInAccount().getCollection().getCards());
            products.addAll(controller.getLoggedInAccount().getCollection().getItems());
        } else products.addAll(controller.searchInCollection(searchBar.getText()));
        createCards(collectionPane, products);
    }

    public void putDecks() {
        decksPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        for (Deck deck : getMainController().getLoggedInAccount().getCollection().getDecks().values()) {
            Button button = new Button();
            button.getStyleClass().add("deleteButton");
            System.out.println(deck.getName());
            AnchorPane deckView = new AnchorPane();
            Label name = new Label();
            name.setText(deck.getName());
            name.getStyleClass().add("labelName");
            name.setId("name");
            name.relocate(135, 15);
            AnchorPane.setRightAnchor(deckView, 0.0);
            AnchorPane.setTopAnchor(deckView, 0.0);
            deckView.getStyleClass().add("decks");
            deckView.getChildren().add(name);
            deckView.getChildren().add(button);
            decksPane.getChildren().add(deckView);
            button.setOnMouseClicked(event -> {
                getMainController().deleteDeck(deck.getName());
                putDecks();
            });
            deckView.setOnMouseClicked(event -> {
                importButton.setText("export");
                selectedDeckName = deck.getName();
                super.setDeckName(deck.getName());
                setMainDeckButton();
                deckButtonLabel.setText("exit deck");
                super.setDeckName(((Label) deckView.lookup("#name")).getText());
                putCardInDeck(getMainController().getLoggedInAccount().getCollection().getDecks().get(((Label) deckView.lookup("#name")).getText()));
                putUnusedCard(collectionPane);
            });
        }
    }

    public void glowButton() {
        Image image = new Image("view/Graphic/images/rightButtonGlow.png");
        newDeckButton.setImage(image);
    }

    public void removeGlow() {
        Image image = new Image("view/Graphic/images/rightButton.png");
        newDeckButton.setImage(image);
    }

    public void handleDeckButton() {
        if (!inDeck) {
            createDeck();
        }
        if (inDeck) {
            importButton.setText("import");
            inDeck = false;
            putCardsInCollection();
            putDecks();
            deckButtonLabel.setText("new deck");
            mainPane.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Label);
            mainPane.getChildren().add(backButton);
            mainPane.getChildren().add(newDeckButton);
            mainPane.getChildren().add(deckButtonLabel);
        }
    }

    public void createDeck() {
        if (deckName.getText().equals("")) {
            AlertBox.display(Alert.AlertType.ERROR, "collection", "enter deck name");
            return;
        }
        getMainController().createDeck(deckName.getText());
        if (getMainController().getErrorType() != null) {
            AlertBox.display(Alert.AlertType.ERROR, "Collection", getMainController().getErrorType().getMessage());
            getMainController().setErrorType(null);
        }
        deckName.setText("");
        putDecks();
    }

    public void putCardInDeck(Deck deck) {
        inDeck = true;
        decksPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        for (Card card : deck.getCards().values()) {
            AnchorPane cardView = new AnchorPane();
            cardView.getStyleClass().add("cardsInDeck");
            Label name = new Label();
            Label mana = new Label();
            name.setText(card.getName());
            name.setTextFill(Color.WHITE);
            mana.setText(card.getMana() + "");
            cardView.getChildren().add(name);
            cardView.getChildren().add(mana);
            name.relocate(200, 25);
            mana.relocate(35, 30);
            decksPane.getChildren().add(cardView);
            cardView.setOnMouseClicked(event -> {
                getMainController().removeFromDeck(deck.getName(), card.getCardId());
                putCardInDeck(deck);
                putUnusedCard(collectionPane);
            });
        }
    }

    public void setMainDeckButton() {
        ImageView mainButton = new ImageView();
        Image mainButtonImage = new Image("view/Graphic/images/rightButton.png");
        Label setMain = new Label("main deck");
        mainButton.setId("mainButtonImage");
        setMain.setId("mainButtonText");
        mainButton.setFitHeight(80);
        mainButton.setFitWidth(250);
        mainButton.setImage(mainButtonImage);
        setMain.getStyleClass().add("setMainDeckBtn");
        mainPane.getChildren().add(mainButton);
        AnchorPane.setBottomAnchor(mainButton, 130.0);
        AnchorPane.setRightAnchor(mainButton, 130.0);
        AnchorPane.setBottomAnchor(setMain, 130.0);
        AnchorPane.setRightAnchor(setMain, 130.0);
        mainPane.getChildren().add(setMain);
        setMain.setOnMouseEntered(event -> {
            Image glowImage = new Image("view/Graphic/images/rightButtonGlow.png");
            mainButton.setImage(glowImage);
        });
        setMain.setOnMouseExited(event -> mainButton.setImage(mainButtonImage));
        setMain.setOnMouseClicked(event -> {
            System.err.println(selectedDeckName);
            getMainController().selectMainDeck(selectedDeckName);
            if (getMainController().getErrorType() != null)
                AlertBox.display(Alert.AlertType.ERROR, "Deck", getMainController().getErrorType().getMessage());
        });
    }

    public void glowImportButton() {
        importButton.getStyleClass().remove("importButton");
        importButton.getStyleClass().add("importButtonGlow");
    }

    public void removeImportGlow() {
        importButton.getStyleClass().remove("importButtonGlow");
        importButton.getStyleClass().add("importButton");
    }

    public void importDeck() {
        if (inDeck) {
            String name = TextReceiver.getText("Export", "please enter a name for exporting deck");
            if (!(name == null || name.equals(""))) {

                if (getMainController().exportDeck(
                        getMainController().getLoggedInAccount().getCollection().getDecks().get(selectedDeckName), name)) {
                    AlertBox.display(Alert.AlertType.INFORMATION, "Export", "Deck exported with name : " + name);
                } else {
                    AlertBox.display(Alert.AlertType.ERROR, "Export", getMainController().getErrorType().getMessage());
                    getMainController().setErrorType(null);
                }
            }
        } else {
            String name = TextReceiver.getText("Export", "please enter a name for importing a deck");
            if (!(name == null || name.equals(""))) {
                if (getMainController().importDeck(name)) {
                    AlertBox.display(Alert.AlertType.INFORMATION, "Export", "Deck imported successfully");
                    putDecks();
                } else {
                    AlertBox.display(Alert.AlertType.ERROR, "Export", getMainController().getErrorType().getMessage());
                    getMainController().setErrorType(null);
                }
            }
        }
    }
}
