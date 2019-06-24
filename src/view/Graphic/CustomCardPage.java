package view.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import model.AbilityCastTime;
import model.Cards.SoldierTypes;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomCardPage extends MenuController implements Initializable {
    @FXML
    public ChoiceBox<SoldierTypes> minionAttackType;
    public ChoiceBox<AbilityCastTime> minionAbilityCastTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minionAttackType.getItems().addAll(SoldierTypes.getTypes());
        minionAbilityCastTime.getItems().addAll(AbilityCastTime.getTypes());
        minionAttackType.setOnAction(e -> System.out.println("aaaa"));
    }

    public void exit() {
        changeMenu("shopView.fxml");
    }
}
