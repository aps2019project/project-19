package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import netWork.ClientController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShopController extends MenuController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Label getMoneyLabel() {
        return moneyLabel;
    }

    public void putCards() {
        if(getMainController() == null)
            return;
        shopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        collectionInShopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        ClientController controller = getMainController();
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
        getMainController().exitMenu();
        changeMenu("MainMenu.fxml");
    }

    public void goToCustomCardMenu() {
        getMainController().enterMenu("custom card menu");
        changeMenu("customCardPage.fxml");
    }
}
