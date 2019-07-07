package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Cards.Card;
import model.Item;
import model.Shop;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShopServerController extends MenuController implements Initializable {
    @FXML
    private JFXMasonryPane shopServerPane = new JFXMasonryPane();
    @FXML
    private TextField searchBar = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Shop shop = Shop.getInstance();
        ArrayList<Object> products = new ArrayList<>();
        products.addAll(shop.getCards());
        products.addAll(shop.getItems());
        createCards(shopServerPane, products);
    }

    public void putCards() {
        Shop shop = Shop.getInstance();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(shop.getCards());
        objects.addAll(shop.getItems());
        createCards(shopServerPane, search(searchBar.getText(), objects));
    }

    private ArrayList<Object> search(String name, ArrayList searchable) {
        ArrayList<Object> result = new ArrayList<>();
        for (Object o : searchable) {
            if (o instanceof Card && ((Card) o).getName().matches(name + "[\\w ]*"))
                result.add(o);
            if (o instanceof Item && ((Item) o).getName().matches(name + "[\\w ]*"))
                result.add(o);
        }
        return result;
    }

}
