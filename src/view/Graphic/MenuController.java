package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cards.SoldierCard;
import model.Cards.SpellCard;
import model.Item;

import java.util.ArrayList;

public class MenuController {
    private Stage stage;
    private Controller mainController;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public Controller getMainController() {
        return mainController;
    }

    public void changeMenu(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            Parent root = loader.load();
            MenuController controller = (MenuController) loader.getController();
            controller.setStage(this.getStage());
            controller.setMainController(this.getMainController());
            if (controller instanceof ShopController) {
                ((ShopController) controller).putCardsInShop();
                ShopController.setController(((ShopController) controller));
            } else if (controller instanceof CollectionController) {
                ((CollectionController) controller).putCardsInCollection();
            }
            this.getStage().getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void createCards(JFXMasonryPane pane, ArrayList<Object> products) {
        pane.getChildren().removeIf(node -> node instanceof AnchorPane);
        Controller controller = Controller.getInstance();
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
            cardView.setOnMouseExited(event -> cardView.getChildren().removeIf(node -> node instanceof ImageView));
            nameLabel.relocate(10, 120);
            pane.getChildren().add(cardView);
            if (pane.getId().equals("shopPane"))
                cardView.setOnMouseClicked(event -> {
                    controller.buyFromShop(((Label) cardView.lookup("#nameLabel")).getText());
                    ShopController.getController().getErrorLabel().setText("");
                    if (controller.getErrorType() != null) {
                        ShopController.getController().getErrorLabel().setText(controller.getErrorType().getMessage());
                        controller.setErrorType(null);
                    }
                    ShopController.getController().getMoneyLabel().setText(controller.getLoggedInAccount().getMoney() + "");
                });
        }
    }
}
