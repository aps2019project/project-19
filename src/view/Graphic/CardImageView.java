package view.Graphic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Cards.Card;

public class CardImageView extends ImageView {
    String cardName;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public CardImageView(){
        super();
    }
    public static boolean testImage(String cardName,Stance stance){
        try {
            new Image("view/Graphic/cards/" + cardName + " "+stance.getMessage()+".gif");
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static CardImageView createCardImageView(String cardName, Stance stance){
        if (testImage(cardName,stance))
            return new CardImageView(cardName,stance);
        else
            return new CardImageView("default",stance);
    }
    private CardImageView(String cardName,Stance stance){
        super(new Image("view/Graphic/cards/" + cardName + " "+stance.getMessage()+".gif"));
        this.cardName=cardName;
    }
    public void changeImage(String cardName,Stance stance){
        if (testImage(cardName,stance))
            this.setImage(new Image("view/Graphic/cards/" + cardName + " "+stance.getMessage()+".gif"));
        else
            this.setImage(new Image("view/Graphic/cards/" + "default "+stance.getMessage()+".gif"));
        this.cardName = cardName;
    }
    public void changeStance(Stance newStance){
        if (testImage(cardName,newStance))
            this.setImage(new Image("view/Graphic/cards/" + cardName + " "+newStance.getMessage()+".gif"));
        else
            this.setImage(new Image("view/Graphic/cards/" + "default "+newStance.getMessage()+".gif"));
    }
    public enum Stance{

        RUNING("run"),
        ATTACKING("attack"),
        CASTING("casting"),
        IDLING("idle"),
        BREATHING("breathing"),
        DYING("death");
        private String stance;

        Stance(String message) {
            this.stance = message;
        }

        public String getMessage() {
            return stance;
        }

        }

}
