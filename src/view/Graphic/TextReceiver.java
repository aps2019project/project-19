package view.Graphic;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class TextReceiver {
    public static String getText(String title, String requestMessage) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle(title);
        textInputDialog.setHeaderText(requestMessage);
        textInputDialog.setContentText("test");
        Optional<String> result = textInputDialog.showAndWait();
        return result.orElse(null);
    }
}
