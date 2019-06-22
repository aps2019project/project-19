package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Cards.Card;
import model.Deck;

import java.util.ArrayList;

public class CollectionController extends MenuController {
    private static CollectionController controller = new CollectionController();
    @FXML
    private JFXMasonryPane collectionPane = new JFXMasonryPane();
    @FXML
    private TextField searchBar = new TextField();
    @FXML
    private ImageView newDeckButton = new ImageView();
    @FXML
    private JFXMasonryPane decksPane = new JFXMasonryPane();
    @FXML
    private TextField deckName = new TextField();
    private boolean inDeck = false;

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
        Controller controller = Controller.getInstance();
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
            System.out.println(deck.getName());
            AnchorPane deckView = new AnchorPane();
            Label name = new Label();
            name.setText(deck.getName());
            name.getStyleClass().add("labelName");
            name.setId("name");
            name.relocate(135, 15);
            deckView.getStyleClass().add("decks");
            deckView.getChildren().add(name);
            decksPane.getChildren().add(deckView);
            deckView.setOnMouseClicked(event -> {
                super.setDeckName(((Label) deckView.lookup("#name")).getText());
                putCardInDeck(getMainController().getLoggedInAccount().getCollection().getDecks().get(((Label) deckView.lookup("#name")).getText()));
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
//            cardView.setPrefSize(30,6);
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
        }
    }
}
