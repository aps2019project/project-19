package view.Graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.Request;

public class Graphic extends Application {
    private static Request request;
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(
                Graphic.class.getResource("Lato-Light.ttf").toExternalForm(),
                10
        );
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
//        LoginController controller = loader.getController();
//        controller.setStage(primaryStage);
//        controller.setRequest(request);
        ShopController controller = loader.getController();
        ShopController.setController(controller);
        primaryStage.setTitle("Duelyst");
        stage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add("view/Graphic/Styles.css");
        controller.createCards();
        primaryStage.show();
    }
    public static void main(String[] args,Request request){
        Graphic.request = request;
        launch(args);
    }

    public static Request getRequest() {
        return request;
    }

    public static Stage getStage() {
        return stage;
    }
}