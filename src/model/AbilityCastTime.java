package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AbilityCastTime {
    ON_ATTACK,
    COMBO,
    ON_DEFEND,
    PASSIVE,
    ON_DEATH,
    ON_SPAWN,
    ON_TURN;

    public static ObservableList<AbilityCastTime> getTypes() {
        ObservableList<AbilityCastTime> types = FXCollections.observableArrayList();
        types.addAll(ON_ATTACK, COMBO, ON_DEFEND, PASSIVE, ON_DEATH, ON_SPAWN, ON_TURN);
        return types;
    }
}