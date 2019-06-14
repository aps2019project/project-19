package view.Graphic;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        //window.setMinWidth(250);
        Label label = new Label(message);
        label.getStyleClass().add("alertBox");
        JFXButton closeBtn = new JFXButton("OK");
        closeBtn.setOnAction(e -> window.close());
        VBox vBox = new VBox(label, closeBtn);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }
}
