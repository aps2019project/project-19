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
        System.out.println(controller.getShop().getCards().size());
        for (Card card : controller.getShop().getCards()) {
            AnchorPane cardView = new AnchorPane();
            cardView.getStyleClass().add("soldierCard");
            Label label = new Label();
            label.setText(card.getName());
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
