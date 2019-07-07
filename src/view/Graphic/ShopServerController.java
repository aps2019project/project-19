package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Shop;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShopServerController extends MenuController implements Initializable {
    @FXML
    private JFXMasonryPane shopServerPane = new JFXMasonryPane();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Shop shop = Shop.getInstance();
        ArrayList<Object> products = new ArrayList<>();
        products.addAll(shop.getCards());
        products.addAll(shop.getItems());
        createCards(shopServerPane, products);
    }

    public void putCards() {}

}
