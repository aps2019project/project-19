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
    private static Stage stage;
    private static Controller mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(
                Graphic.class.getResource("Lato-Light.ttf").toExternalForm(),
                10);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        Parent root = (Parent) loader.load();
        LoginController controller = (LoginController) loader.getController();
        controller.setStage(primaryStage);
        controller.setMainController(mainController);
        primaryStage.setTitle("Duelyst");
        stage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root));
        Image image = new Image("view/Graphic/images/mouseCursor.png");
        primaryStage.getScene().setCursor(new ImageCursor(image));
        primaryStage.getScene();
        primaryStage.getScene().getStylesheets().add("view/Graphic/Styles.css");
        primaryStage.show();

    }

    public static void main(String[] args, Controller controller) {
        mainController = controller;
        launch(args);
    }


    public static Stage getStage() {
        return stage;
    }
}
