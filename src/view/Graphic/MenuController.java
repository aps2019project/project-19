package view.Graphic;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

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
    public void changeMenu(String fxmlFile){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            Parent root = (Parent) loader.load();
            MenuController controller = (MenuController) loader.getController();
            controller.setStage(this.getStage());
            controller.setMainController(this.getMainController());
            if(controller instanceof ShopController){
                ((ShopController) controller).createCards();
            }
            this.getStage().getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
