package controller;

import model.Cards.Card;
import model.Cell;
import model.Collection;
import model.Player;
import view.Request;

import java.util.ArrayList;
import java.util.Collections;
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
    public String sendRandomRequest(Player opponent){
        counter++;
            switch (counter) {
                case 0:
                    return insertRandomCard();
                case 1:
                    return selectRandomCard();
                case 2:
                    return moveRandomCard();
                case 3:
                    return attackRandomCard(opponent);
                case 4:
                    return "show hand";
                case 5:
                    return "end turn";
                case 6:
                    counter = -1;
                    return "";
            }
        return "chert";
    }
    private String insertRandomCard(){
         ArrayList<Cell> cardCells = new ArrayList<>(player.getInBattleCards().values());
        Cell randomInsertionCell = cardCells.get(random.nextInt(cardCells.size()));
        ArrayList<Card> handCards = new ArrayList<>(player.getHandCards().values());
        Card randomCard ;
        Collections.shuffle(handCards);
        outer:
        for (Card handCard : handCards) {
            if (handCard.getMana()<player.getMana()) {
                return "insert " + handCard.getName()+" in ("+(randomInsertionCell.getXCoordinate()-1)+", "+randomInsertionCell.getYCoordinate()+")";
            }
        }
        return "chert";
    }
    private String attackRandomCard(Player opponent){
        Card card = player.getSelectedCard();
        if (card == null)
            return "i don't have any card ://";
        ArrayList<Card> opponentInBattleCards = new ArrayList<>(opponent.getInBattleCards().keySet());
        if(opponentInBattleCards.size() == 0)
            return "enemy has no card";
        Card opponentCard = opponentInBattleCards.get(random.nextInt(opponentInBattleCards.size()));
        return "attack "+opponentCard.getInBattleCardId();

    }
    private String moveRandomCard(){
        Card card = player.getSelectedCard();
        if (card == null)
            return "i don't have any card ://";
        Cell cell = player.getInBattleCards().get(card);
        return "move to ("+(cell.getXCoordinate()-2)+", "+cell.getYCoordinate()+")";
    }
    private String selectRandomCard(){
        ArrayList<Card> inBattleCards = new ArrayList<>(player.getInBattleCards().keySet());
        if(inBattleCards.size()==0)
            return "i don't have any card ://";
        Card card = inBattleCards.get(random.nextInt(inBattleCards.size()));
        return "select "+card.getInBattleCardId();
    }
}
