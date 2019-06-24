package model.Cards;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum SoldierTypes {
    RANGED,
    MELEE,
    HYBRID;

    public static ObservableList<SoldierTypes> getTypes() {
        ObservableList<SoldierTypes> types = FXCollections.observableArrayList();
        types.addAll(RANGED, MELEE, HYBRID);
        return types;
    }
}
