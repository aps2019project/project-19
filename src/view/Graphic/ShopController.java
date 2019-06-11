package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Cards.Card;

public class ShopController {
    private static ShopController controller = new ShopController();
    @FXML
    private JFXMasonryPane pane = new JFXMasonryPane();

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
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getStyleClass().add("soldierCard");
            Label label = new Label();
            label.setText(card.getName());
            label.getStyleClass().add("labelName");
            anchorPane.getChildren().add(label);
            pane.getChildren().add(anchorPane);
        }
    }
}
