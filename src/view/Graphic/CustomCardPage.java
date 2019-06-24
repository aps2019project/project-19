package view.Graphic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.AbilityCastTime;
import model.Buff.Buff;
import model.Buff.DisArmBuff;
import model.Buff.DispellBuff;
import model.Buff.Kind;
import model.Cards.CustomCard;
import model.Cards.SoldierTypes;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomCardPage extends MenuController implements Initializable {
    @FXML
    public ChoiceBox<SoldierTypes> minionAttackType;
    public ChoiceBox<AbilityCastTime> minionAbilityCastTime;
    public AnchorPane minionTabAnchorPane;
    public ChoiceBox<String> minionBuffType;
    public AnchorPane buffSection;

    private CustomCard customCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customCard = new CustomCard();
        minionAttackType.getItems().addAll(SoldierTypes.getTypes());
        minionAbilityCastTime.getItems().addAll(AbilityCastTime.getTypes());
        minionBuffType.getItems().addAll(Buff.getSoldierBuffNames());
        minionBuffType.setOnAction(e -> {
            buffSection.getChildren().removeIf(node -> node instanceof AnchorPane);
            AnchorPane commonAttributes = buffCommonInputs();
            buffSection.getChildren().add(commonAttributes);
            AnchorPane.setTopAnchor(commonAttributes, 0.0);
            AnchorPane.setLeftAnchor(commonAttributes, 0.0);
            handleBuff(commonAttributes);
        });

    }

    private void handleBuff(AnchorPane commonAttributes) {
        switch (minionBuffType.getValue()) {
            case "DisArm Buff":
                JFXButton button = getButton("Add Buff");
                commonAttributes.getChildren().add(button);
                AnchorPane.setTopAnchor(button, 240.0);
                button.setOnAction(event -> {
                    if (checkTextField((JFXTextField) commonAttributes.getChildren().get(1)) &&
                            checkTextField((JFXTextField) commonAttributes.getChildren().get(3))) {
                        DisArmBuff buff = new DisArmBuff(Kind.NEGATIVE, Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()), false);
                        customCard.getSpecialPowers().add();
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            case "Stun Buff":
                break;
            case "Attack Buff":
                break;
            case "Holy Buff":
                break;
            case "Poison Buff":
                break;
            case "Dispell Buff":
                break;
            case "Power Buff":
                break;
            case "Weakness Buff":
                break;
            default:
        }
    }

    private boolean checkTextField(JFXTextField textField) {
        if (textField.getText() == null)
            return false;
        boolean check = true;
        try {
            int y = Integer.parseInt(textField.getText());
        } catch (Exception e) {
            check = false;
        }
        if (check)
            return true;
        return false;
    }

    public void exit() {
        changeMenu("shopView.fxml");
    }

    private AnchorPane buffCommonInputs() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("buffAnchors");
        Label duration = new Label("Duration");
        duration.getStyleClass().add("labelInput");
        Label castTurn = new Label("Cast Turn");
        castTurn.getStyleClass().add("labelInput");
        JFXTextField durationText = new JFXTextField();
        durationText.setId("duration");
        durationText.getStyleClass().add("textInput");
        JFXTextField castTurnText = new JFXTextField();
        castTurnText.setId("castTurn");
        castTurnText.getStyleClass().add("textInput");
        //index 1 = durationText 3 = castTurnText
        anchorPane.getChildren().addAll(duration, durationText, castTurn, castTurnText);
        AnchorPane.setTopAnchor(duration, 0.0);
        AnchorPane.setTopAnchor(durationText, 60.0);
        AnchorPane.setTopAnchor(castTurn, 120.0);
        AnchorPane.setTopAnchor(castTurnText, 180.0);
        return anchorPane;
    }

    private AnchorPane oneAttributeBuff() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().addAll("buffAnchors");
        Label label = new Label();
        label.getStyleClass().add("labelInput");
        JFXTextField textField = new JFXTextField();
        textField.getStyleClass().add("textInput");
        JFXButton button = getButton("Add Buff");
        //index: 0 = label 1 = textfield 2 = button
        anchorPane.getChildren().addAll(label, textField, button);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setTopAnchor(textField, 60.0);
        AnchorPane.setTopAnchor(button, 120.0);
        return anchorPane;
    }

    public AnchorPane twoAttributeBuff() {
        AnchorPane anchorPane = oneAttributeBuff();
        Label label = new Label();
        label.getStyleClass().add("labelInput");
        JFXTextField textField = new JFXTextField();
        textField.getStyleClass().add("textInput");
        JFXButton button = (JFXButton) anchorPane.getChildren().get(2);
        anchorPane.getChildren().remove(button);
        //index 0 = label1 1 = text1 2 = label2 3 = text2 4 = button
        anchorPane.getChildren().addAll(label, textField, button);
        AnchorPane.setTopAnchor(label, 120.0);
        AnchorPane.setTopAnchor(textField, 180.0);
        AnchorPane.setTopAnchor(button, 240.0);
        return anchorPane;
    }

    private JFXButton getButton(String text) {
        JFXButton button = new JFXButton(text);
        button.getStyleClass().add("addBuffBtn");
        return button;
    }

}
