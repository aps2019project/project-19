package view.Graphic;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Graphic extends Application {
    private Stage stage;
    private Controller mainController;

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(
                Graphic.class.getResource("Lato-Light.ttf").toExternalForm(),
                10);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        Parent root = (Parent) loader.load();
        LoginController controller = (LoginController) loader.getController();
        controller.setStage(stage);
        controller.setMainController(mainController);
        stage.setTitle("Duelyst");
        this.stage = stage;
        stage.setFullScreen(true);
        stage.setScene(new Scene(root));
        Image image = new Image("view/Graphic/images/mouseCursor.png");
        stage.getScene().setCursor(new ImageCursor(image));
        stage.getScene();
        stage.getScene().getStylesheets().add("view/Graphic/Styles.css");
        stage.show();

    }

    public void main(String[] args, Controller controller) {
        mainController = controller;
        launch(args);
    }
    public void showNewStage(){

    }

    public Stage getStage() {
        return stage;
    }
}
