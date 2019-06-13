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

    public void putCardsInShop() {
        shopPane.getChildren().removeIf(node -> node instanceof AnchorPane);
        Controller controller = getMainController();
        ArrayList<Object> products = new ArrayList<>();
        if (searchBar.getText().equals("")) {
            products.addAll(controller.getShop().getCards());
            products.addAll(controller.getShop().getItems());
        } else products.addAll(controller.searchInShop(searchBar.getText()));
        createCards(shopPane, products);
        moneyLabel.setText(controller.getLoggedInAccount().getMoney() + "");
    }

    public void exit() {
        changeMenu("MainMenu.fxml");
    }
}
