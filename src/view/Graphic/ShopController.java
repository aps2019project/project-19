package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ShopController extends MenuController {
    private static ShopController controller = new ShopController();
    @FXML
    private JFXMasonryPane shopPane = new JFXMasonryPane();
    @FXML
    private JFXMasonryPane collectionInShopPane = new JFXMasonryPane();
    @FXML
    private TextField searchBar = new TextField();
    @FXML
    private Label errorLabel = new Label();
    @FXML
    private Label moneyLabel = new Label();

    public static ShopController getController() {
        return controller;
    }

    public static void setController(ShopController controller) {
        ShopController.controller = controller;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Label getMoneyLabel() {
        return moneyLabel;
    }

    public void putCards() {
        shopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        collectionInShopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        Controller controller = Controller.getInstance();
        ArrayList<Object> shopProducts = new ArrayList<>();
        ArrayList<Object> collectionProducts = new ArrayList<>();
        if (searchBar.getText().equals("")) {
            shopProducts.addAll(controller.getShop().getCards());
            shopProducts.addAll(controller.getShop().getItems());
            collectionProducts.addAll(controller.getLoggedInAccount().getCollection().getCards());
            collectionProducts.addAll(controller.getLoggedInAccount().getCollection().getItems());
        } else {
            shopProducts.addAll(controller.searchInShop(searchBar.getText()));
            collectionProducts.addAll(controller.searchInCollection(searchBar.getText()));
        }
        createCards(shopPane, shopProducts);
        createCards(collectionInShopPane, collectionProducts);
        moneyLabel.setText(controller.getLoggedInAccount().getMoney() + "");
    }

    public void changeTab() {
        putCards();
        searchBar.setText("");
    }

    public void exit() {
        changeMenu("MainMenu.fxml");
    }

    public void goToCustomCardMenu() {
        changeMenu("customCardPage.fxml");
    }
}
