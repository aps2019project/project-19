package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Cards.SoldierCard;
import model.Cards.SpellCard;
import model.Item;

import java.util.ArrayList;

public class ShopController extends MenuController {
    private static ShopController controller = new ShopController();
    @FXML
    private JFXMasonryPane pane = new JFXMasonryPane();
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

    public void createCards() {
        pane.getChildren().removeIf(node -> node instanceof AnchorPane);
        Controller controller = Controller.getInstance();
        ArrayList<Object> products = new ArrayList<>();
        if (searchBar.getText().equals("")) {
            System.out.println("empty");
            products.addAll(controller.getShop().getCards());
            products.addAll(controller.getShop().getItems());
        } else products.addAll(controller.searchInShop(searchBar.getText()));
        for (Object product : products) {
            AnchorPane cardView = new AnchorPane();
            Label nameLabel = new Label();
            if (product instanceof SoldierCard) {
                cardView.getStyleClass().add("soldierCard");
                nameLabel.setText(((SoldierCard) product).getName());
            } else if (product instanceof SpellCard) {
                cardView.getStyleClass().add("spellCard");
                nameLabel.setText(((SpellCard) product).getName());
            } else if (product instanceof Item) {
                cardView.getStyleClass().add("item");
                nameLabel.setText(((Item) product).getName());
            }
            nameLabel.getStyleClass().add("labelName");
            nameLabel.setId("nameLabel");
            cardView.getChildren().add(nameLabel);
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
            cardView.setOnMouseExited(event -> {
                cardView.getChildren().removeIf(node -> node instanceof ImageView);
            });
            nameLabel.relocate(10, 120);
            pane.getChildren().add(cardView);
            cardView.setOnMouseClicked(event -> {
                controller.buyFromShop(((Label) cardView.lookup("#nameLabel")).getText());
                if (controller.getErrorType() != null)
                    errorLabel.setText(controller.getErrorType().getMessage());
                moneyLabel.setText(controller.getLoggedInAccount().getMoney() + "");
            });
        }
        moneyLabel.setText(controller.getLoggedInAccount().getMoney() + "");
    }

    public void exit() {
        changeMenu("MainMenu.fxml");
    }
}
