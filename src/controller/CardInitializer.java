package controller;

import model.*;
import model.Buff.*;
import model.Target.SoldierTargetType;
import model.Target.Target;
import model.Target.Type;

import java.util.ArrayList;

public class CardInitializer {
    private ArrayList<Minion> minionCards = new ArrayList<>();
    private ArrayList<SpellCard> spellCards = new ArrayList<>();
    private ArrayList<Hero> heroes = new ArrayList<>();

    private final static CardInitializer CARD_INITIALIZER = new CardInitializer();
    public static CardInitializer getInstance(){
        return CARD_INITIALIZER;
    }
    private CardInitializer(){
    }
    public void createCards(Account account){
         ArrayList<Minion> minionCards = new ArrayList<>();
         ArrayList<SpellCard> spellCards = new ArrayList<>();
         ArrayList<Hero> heroes = new ArrayList<>();
         createMinions(minionCards,new ArrayList<Buff>());
         createHeroes(heroes);
         createSpellCards(spellCards,new ArrayList<Buff>());
        for (Minion minionCard : minionCards) {

        }
        for (SpellCard spellCard : spellCards) {

        }
    }
    public void createMinions(ArrayList<Minion> minions, ArrayList<Buff> buffs) {
        Minion camandareFars = new Minion(101,"camandareFarse",300,2,4,6, SoldierTypes.RANGED,7,null,null);
        minions.add(camandareFars);

        buffs.add(new StunBuff(Kind.POSITIVE,1,false));
        Minion shamshirZaneFars = new Minion(102,"shamshir zane fars",400,2,6,4,SoldierTypes.MELEE,0, AbilityCastTime.ON_ATTACK,buffs);
        minions.add(shamshirZaneFars);

        Minion neyzeDareFars = new Minion(103,"neyzedare Farse",500,1,3,5,SoldierTypes.HYBRID,3,null,null);
        minions.add(neyzeDareFars);

        Minion asbSavarFars = new Minion(104,"asbsavare Fars",200,4,6,10,SoldierTypes.MELEE,0,null,null);
        minions.add(asbSavarFars);

//        buffs = new ArrayList<>();
//        Buff buff = new buff()
        Minion pahlavaneFarse = new Minion(105,"pahlavane Fars",600,9,6,24, SoldierTypes.MELEE,0,null,null);
        minions.add(pahlavaneFarse);
//
    }
    public void createHeroes(ArrayList<Hero> heroes){
        Buff buff = new PowerBuff(Kind.POSITIVE,0,true,0,4);
        Hero diveSepid = new Hero(301,"dive sepid",8000,1,4,50,SoldierTypes.MELEE,0,buff);
        heroes.add(diveSepid);

        buff = new StunBuff(Kind.POSITIVE,1,false);
        Hero simorgh = new Hero(302,"simorgh",9000,5,4,50,SoldierTypes.MELEE,0,buff);
        //todo:must have target area

    }
    private static void createSpellCards(ArrayList<SpellCard> spellCards, ArrayList<Buff> buffs) {
        buffs.add(new DisArmBuff(Kind.NEGATIVE, 0, true));
        Target target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY);
        SpellCard totalDisarm = new SpellCard(201, "total Disarm", 1000, 0, buffs, target);
        spellCards.add(totalDisarm);

        buffs = new ArrayList<>();
        buffs.add(new AntiNegativeBuff(Kind.POSITIVE, 0, false));
        target = new Target(Type.AREA, 2, null);
        SpellCard areaDispel = new SpellCard(202, "Area Dispel", 1500, 2, buffs, target);
        spellCards.add(areaDispel);

    }
}
