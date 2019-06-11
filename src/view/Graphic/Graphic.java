package view.Graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Graphic extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(
                Graphic.class.getResource("Lato-Light.ttf").toExternalForm(),
                10
        );
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Duelyst");
        stage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add("view/Graphic/Styles.css");
        ShopController.setController(loader.getController());
        ShopController.getController().createCards();
        primaryStage.show();
    }

    public void main(String[] args) {
        launch(args);
    }
}
