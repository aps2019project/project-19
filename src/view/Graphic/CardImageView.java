package view.Graphic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardImageView extends ImageView {
    String cardName;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public CardImageView(String cardName,Stance stance){
       super(new Image("view/Graphic/cards/" + cardName + " "+stance.getMessage()+".gif"));
        this.cardName=cardName;
    }
    public void changeImage(String cardName,Stance stance){
        this.setImage(new Image("view/Graphic/cards/" + cardName + " "+stance.getMessage()+".gif"));
        this.cardName = cardName;
    }
    public void changeStance(Stance newStance){
        this.setImage(new Image("view/Graphic/cards/" + cardName + " "+newStance.getMessage()+".gif"));
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
