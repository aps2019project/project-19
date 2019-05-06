package controller;

import model.Cards.Card;
import model.Cell;
import model.Player;
import view.Request;

import java.util.ArrayList;
import java.util.Random;

public class Ai {
    Player player;
    private int counter = -1;
    Random random = new Random();
    public Ai(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public String sendRandomRequest(){
        counter++;
            switch (counter) {
                case 0:
                    return insertRandomCard();
                case 1:
                    return moveRandomCard();
                case 2:
                    return attackRandomCard();
                case 3:
                    return "show hand";
                case 4:
                    return "end turn";
                case 5:
                    counter = -1;
            }
        return "chert";
    }
    private String insertRandomCard(){
         ArrayList<Cell> cardCells = new ArrayList<>(player.getInBattleCards().values());
        Cell randomInsertionCell = cardCells.get(random.nextInt(cardCells.size()));
        ArrayList<Card> handCards = new ArrayList<>(player.getHandCards().values());
        Card randomCard ;
        outer:
        for (Card handCard : handCards) {
            if (handCard.getMana()<player.getMana()) {
                return "insert " + handCard.getName()+" in ("+(randomInsertionCell.getXCoordinate()-1)+", "+randomInsertionCell.getYCoordinate()+")";
            }
        }
        return "chert";
    }
    private String attackRandomCard(){
       return  "chert attack random";
    }
    private String moveRandomCard(){
       return  "chert move random";
    }
}
