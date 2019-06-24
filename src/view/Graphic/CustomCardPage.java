package view.Graphic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.AbilityCastTime;
import model.Buff.*;
import model.Cards.Card;
import model.Cards.CustomCard;
import model.Cards.SoldierTypes;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomCardPage extends MenuController implements Initializable {
    @FXML
    public ChoiceBox<SoldierTypes> minionAttackType;
    public ChoiceBox<AbilityCastTime> minionAbilityCastTime;
    public AnchorPane minionTabAnchorPane;
    public ChoiceBox<String> minionBuffType;
    public AnchorPane buffSection;
    public JFXTextField name;
    public JFXTextField mana;
    public JFXTextField cost;
    public JFXTextField description;
    public JFXTextField minionAP;
    public JFXTextField minionHP;

    private CustomCard customCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customCard = new CustomCard();
        minionAttackType.getItems().addAll(SoldierTypes.getTypes());
        minionAttackType.setOnAction(e -> {
            customCard.setAttackType(minionAttackType.getValue());
            if (minionAttackType.getValue().equals(SoldierTypes.RANGED)) {
                String range;
                do {
                    range = TextReceiver.getText("Range", "please enter minion range");
                } while (range == null || range.equals(""));
                try {
                    customCard.setAttackRange(Integer.parseInt(range));
                } catch (Exception exception) {
                    AlertBox.display(Alert.AlertType.ERROR, "Range", "invalid input\ntry again");
                }

            }
        });
        minionAbilityCastTime.getItems().addAll(AbilityCastTime.getTypes());
        minionAbilityCastTime.setOnAction(e -> customCard.setAbilityCastTime(minionAbilityCastTime.getValue()));
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
            case "DisArm Buff": {
                JFXButton addBtn = getButton("Add Buff");
                commonAttributes.getChildren().add(addBtn);
                AnchorPane.setTopAnchor(addBtn, 240.0);
                addBtn.setOnAction(event -> {
                    if (checkCommonAttributes(commonAttributes)) {
                        DisArmBuff buff = new DisArmBuff(Kind.NEGATIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()), false);
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "Stun Buff": {
                JFXButton addBtn = getButton("Add Buff");
                commonAttributes.getChildren().add(addBtn);
                AnchorPane.setTopAnchor(addBtn, 240.0);
                addBtn.setOnAction(event -> {
                    if (checkCommonAttributes(commonAttributes)) {
                        StunBuff buff = new StunBuff(Kind.NEGATIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()), false);
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "Attack Buff": {
                AnchorPane anchorPane = oneAttributeBuff();
                JFXButton addBtn = labelingOneAttributeBuffs(anchorPane, "Damage Point");
                addBtn.setOnAction(event -> {
                    if (checkCommonAttributes(commonAttributes) && checkTextField((JFXTextField) anchorPane.getChildren().get(1))) {
                        AttackBuff buff = new AttackBuff(Kind.NEGATIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()),
                                false, Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(1)).getText()));
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "Holy Buff": {
                AnchorPane anchorPane = oneAttributeBuff();
                JFXButton addBtn = labelingOneAttributeBuffs(anchorPane, "Less Damage Point");
                addBtn.setOnAction(event -> {
                    if (checkCommonAttributes(commonAttributes) && checkTextField((JFXTextField) anchorPane.getChildren().get(1))) {
                        HolyBuff buff = new HolyBuff(Kind.POSITIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()),
                                false, Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(1)).getText()));
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "Poison Buff": {
                AnchorPane anchorPane = oneAttributeBuff();
                JFXButton addBtn = labelingOneAttributeBuffs(anchorPane, "Damage Point");
                addBtn.setOnAction(event -> {
                    if (checkCommonAttributes(commonAttributes) && checkTextField((JFXTextField) anchorPane.getChildren().get(1))) {
                        PoisonBuff buff = new PoisonBuff(Kind.NEGATIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()),
                                false, Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(1)).getText()));
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "Power Buff": {
                AnchorPane anchorPane = twoAttributeBuff();
                JFXButton addBtn = labelingTwoAttributeBuffs(anchorPane, "Health Point", "Attack Point");
                addBtn.setOnAction(e -> {
                    if (checkCommonAttributes(commonAttributes) && checkTextField((JFXTextField) anchorPane.getChildren().get(1))
                            && checkTextField((JFXTextField) anchorPane.getChildren().get(3))) {
                        PowerBuff buff = new PowerBuff(Kind.POSITIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()),
                                false, Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(1)).getText()),
                                Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(3)).getText()));
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "Weakness Buff": {
                AnchorPane anchorPane = twoAttributeBuff();
                JFXButton addBtn = labelingTwoAttributeBuffs(anchorPane, "Health Decrement Point", "Attack Decrement Point");
                addBtn.setOnAction(e -> {
                    if (checkCommonAttributes(commonAttributes) && checkTextField((JFXTextField) anchorPane.getChildren().get(1))
                            && checkTextField((JFXTextField) anchorPane.getChildren().get(3))) {
                        WeaknessBuff buff = new WeaknessBuff(Kind.NEGATIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()),
                                false, Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(1)).getText()),
                                Integer.parseInt(((JFXTextField) anchorPane.getChildren().get(3)).getText()));
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
                });
                break;
            }
            case "DisPell Buff": {
                JFXCheckBox enemy = new JFXCheckBox("Enemy");
                enemy.getStyleClass().add("labelInput");
                JFXCheckBox friend = new JFXCheckBox("Friend");
                friend.getStyleClass().add("labelInput");
                JFXButton addBtn = getButton("Add Buff");
                commonAttributes.getChildren().addAll(addBtn, enemy, friend);
                AnchorPane.setTopAnchor(addBtn, 360.0);
                AnchorPane.setTopAnchor(enemy, 240.0);
                AnchorPane.setTopAnchor(friend, 300.0);
                addBtn.setOnAction(e -> {
                    if (checkCommonAttributes(commonAttributes) && (enemy.isSelected() || friend.isSelected())) {
                        DispellBuff buff = new DispellBuff(Kind.NEGATIVE,
                                Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(1)).getText()),
                                false, enemy.isSelected(), friend.isSelected());
                        addBuff(commonAttributes, buff);
                        return;
                    }
                    AlertBox.display(Alert.AlertType.ERROR, "Inputs",
                            "Enter all inputs correctly and select at least on of the checkBoxes");
                });
                break;
            }
        }
    }

    private JFXButton labelingOneAttributeBuffs(AnchorPane anchorPane, String labelText) {
        Label label = (Label) anchorPane.getChildren().get(0);
        label.setText(labelText);
        buffSection.getChildren().add(anchorPane);
        AnchorPane.setTopAnchor(anchorPane, 300.0);
        return (JFXButton) anchorPane.getChildren().get(2);
    }

    private JFXButton labelingTwoAttributeBuffs(AnchorPane anchorPane, String label1, String label2) {
        Label labelOne = (Label) anchorPane.getChildren().get(0);
        Label labelTwo = (Label) anchorPane.getChildren().get(2);
        labelOne.setText(label1);
        labelTwo.setText(label2);
        buffSection.getChildren().add(anchorPane);
        AnchorPane.setTopAnchor(anchorPane, 300.0);
        return (JFXButton) anchorPane.getChildren().get(4);

    }

    private void addBuff(AnchorPane commonAttributes, Buff buff) {
        buff.setCastTurn(Integer.parseInt(((JFXTextField) commonAttributes.getChildren().get(3)).getText()));
        customCard.getSpecialPowers().add(buff);
        AlertBox.display(Alert.AlertType.INFORMATION, "Buff", "Buff added successfully");
        cleanUpBuff();
    }

    private void cleanUpBuff() {
        buffSection.getChildren().removeIf(node -> node instanceof AnchorPane);
        minionBuffType.getItems().removeAll();
    }

    private boolean checkCommonAttributes(AnchorPane commonAttributes) {
        return checkTextField((JFXTextField) commonAttributes.getChildren().get(1)) &&
                checkTextField((JFXTextField) commonAttributes.getChildren().get(3));
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

    public void createMinion() {
        if (checkGeneralFields() && checkTextField(minionAP) && checkTextField(minionHP) &&
                minionAttackType.getValue() != null && ((customCard.getSpecialPowers().size() != 0 &&
                minionAbilityCastTime.getValue() != null) || customCard.getSpecialPowers().size() == 0)) {
            customCard.setAp(Integer.parseInt(minionAP.getText()));
            customCard.setHp(Integer.parseInt(minionHP.getText()));
            customCard.setCost(Integer.parseInt(cost.getText()));
            customCard.setName(name.getText());
            customCard.setMana(Integer.parseInt(mana.getText()));
            if (description.getText() == null) {
                customCard.setDesc("");
            } else {
                customCard.setDesc(description.getText());
            }
            customCard.setType("minion");
            Card card = customCard.createCard();
            getMainController().getShop().getCards().add(card);
            AlertBox.display(Alert.AlertType.INFORMATION, "Custom Card", "Custom Card created successfully");
            changeMenu("shopView.fxml");
        } else
            AlertBox.display(Alert.AlertType.ERROR, "Inputs", "Enter all inputs correctly");
    }

    public boolean checkGeneralFields() {
        boolean x = checkTextField(mana) && checkTextField(cost);
        if (name.getText() != null) {
            boolean z = !getMainController().getShop().existsInShop(name.getText());
            return x && z;
        }
        return false;
    }
}
