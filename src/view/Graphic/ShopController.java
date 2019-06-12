package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import model.Cards.Card;
import model.Cards.SoldierCard;

import java.util.ArrayList;

public class ShopController extends MenuController{
    private static ShopController controller = new ShopController();
    @FXML
    private JFXMasonryPane pane = new JFXMasonryPane();
    @FXML private TextField searchBar = new TextField();

    public static ShopController getController() {
        return controller;
    }

    public static void setController(ShopController controller) {
        ShopController.controller = controller;
    }

    public void createCards() {
        Controller controller = Controller.getInstance();
        ArrayList<Object> products = new ArrayList<>();
        if (searchBar.getText().equals("")){
            products.addAll(controller.getShop().getCards());
            products.addAll(controller.getShop().getItems());
        }
        else products.addAll(controller.searchInShop(searchBar.getText()));
        for (Object product : products) {
            AnchorPane cardView = new AnchorPane();
            cardView.getStyleClass().add("soldierCard");
            Label label = new Label();
            if (product instanceof SoldierCard)
                label.setText(((SoldierCard) product).getName());
            label.getStyleClass().add("labelName");
            cardView.getChildren().add(label);
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
            label.relocate(10, 120);
            pane.getChildren().add(cardView);
        }
    }
}
