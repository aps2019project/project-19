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
        Parent root = FXMLLoader.load(getClass().getResource("loginView.fxml"));
        primaryStage.setTitle("Duelyst");
        stage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
