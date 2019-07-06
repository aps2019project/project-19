package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Account;
import model.Collection;
import model.Shop;
import netWork.Client;
import netWork.ClientController;
import netWork.ClientController;

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
        if (getMainController() == null) {
            System.out.println("main controller null");
            return;
        }
        shopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        collectionInShopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        ClientController controller = getMainController();
        ArrayList<Object> shopProducts = new ArrayList<>();
        ArrayList<Object> collectionProducts = new ArrayList<>();
        Shop shop = controller.getShop();
        Collection collection = controller.getLoggedInAccount().getCollection();
        if (searchBar.getText().equals("")) {
            shopProducts.addAll(shop.getCards());
            shopProducts.addAll(shop.getItems());
            collectionProducts.addAll(collection.getCards());
            collectionProducts.addAll(collection.getItems());
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
        getMainController().exitMenu();
        changeMenu("MainMenu.fxml");
    }

    public void goToCustomCardMenu() {
        getMainController().enterMenu("custom card menu");
        changeMenu("customCardPage.fxml");
    }
}
