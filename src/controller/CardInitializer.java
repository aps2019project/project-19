package controller;

import model.*;
import model.Buff.*;
import model.Cards.Hero;
import model.Cards.Minion;
import model.Cards.SoldierTypes;
import model.Cards.SpellCard;
import model.Target.SoldierTargetType;
import model.Target.Target;
import model.Target.Type;

import java.util.ArrayList;

public class CardInitializer {
    private ArrayList<Minion> minionCards = new ArrayList<>();
    private ArrayList<SpellCard> spellCards = new ArrayList<>();
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Shop shop = Shop.getInstance();

    private final static CardInitializer CARD_INITIALIZER = new CardInitializer();

    public static CardInitializer getInstance() {
        return CARD_INITIALIZER;
    }

    private CardInitializer() {
    }

    public void createCards() {
        ArrayList<Minion> minionCards = new ArrayList<>();
        ArrayList<SpellCard> spellCards = new ArrayList<>();
        ArrayList<Hero> heroes = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        createMinions(minionCards, new ArrayList<Buff>());
        createHeroes(heroes);
        createSpellCards(spellCards, new ArrayList<Buff>());
        createItem(items, new ArrayList<Buff>());
        for (Minion minionCard : minionCards) {
            shop.getCards().add(minionCard);
        }
        for (SpellCard spellCard : spellCards) {
            shop.getCards().add(spellCard);
        }
        for (Hero hero : heroes) {
            shop.getCards().add(hero);
        }
        for (Item item : items) {
            shop.getItems().add(item);
        }
    }

    public void createMinions(ArrayList<Minion> minions, ArrayList<Buff> buffs) {
        Minion camandareFars = new Minion(101, "camandare Farse", 300, 2, 49, 6,
                SoldierTypes.RANGED, 7, null, null, null);
        minions.add(camandareFars);

        buffs.add(new StunBuff(Kind.NEGATIVE, 1, false));
        Minion shamshirZaneFars = new Minion(102, "shamshir zane fars", 400, 2, 6, 4,
                SoldierTypes.MELEE, 1, "stun enemy for one turn", AbilityCastTime.ON_ATTACK, buffs);
        minions.add(shamshirZaneFars);

        Minion neyzeDareFars = new Minion(103, "neyzedare Farse", 500, 1, 3, 5,
                SoldierTypes.HYBRID, 3, null, null, null);
        minions.add(neyzeDareFars);

        Minion asbSavarFars = new Minion(104, "asb savare Fars", 200, 4, 6, 10,
                SoldierTypes.MELEE, 1, null, null, null);
        minions.add(asbSavarFars);

//        buffs = new ArrayList<>();
//        Buff buff = new buff()
        Minion pahlavaneFarse = new Minion(105, "pahlavane Fars", 600, 9, 6, 24,
                SoldierTypes.MELEE, 1, "for every attack in last turn,deal 5 damage to enemy",
                null, null);
        minions.add(pahlavaneFarse);
//
        Minion sepahsalarfars = new Minion(106, "sepah salare fars", 600, 9, 6, 24,
                SoldierTypes.MELEE, 1, "combo",
                AbilityCastTime.COMBO, null);
        minions.add(sepahsalarfars);

        Minion camandareTorani = new Minion(107, "camandare torani", 500, 1, 4, 3,
                SoldierTypes.RANGED, 5, "be carefull!he will shot your head from miles away!",
                null, null);
        minions.add(camandareFars);

        Minion gholabSanghDareTorani = new Minion(108, "gholabsangdare torani", 600, 1, 2, 4,
                SoldierTypes.RANGED, 7, "get away,his rocks will smash your head!", null,
                null);
        minions.add(gholabSanghDareTorani);

        Minion neyzeDareTorani = new Minion(109, "neyzedare torani", 600, 1, 4, 4,
                SoldierTypes.HYBRID, 3, "he is a real treat to your cavalries!", null,
                null);
        minions.add(neyzeDareTorani);

        buffs = new ArrayList<>();
        Buff buff = new DisArmBuff(Kind.NEGATIVE, 1, false);
        buffs.add(buff);
        buff = new PoisonBuff(Kind.NEGATIVE, 4, false, 1);
        buffs.add(buff);
        Minion jasoseTorani = new Minion(110, "jasose torani", 700, 4, 6, 6, SoldierTypes.MELEE,
                1, "disarm the enemy for 1 turn and poison him for 4 turns", AbilityCastTime.ON_ATTACK,
                buffs);
        minions.add(jasoseTorani);

        Minion ghorzDareTorani = new Minion(111, "ghorzdare torani", 450, 2, 10, 3,
                SoldierTypes.MELEE, 1, "GHORZDAR SMASH!!!", null, null);
        minions.add(ghorzDareTorani);

        Minion shahzadeTorani = new Minion(112, "shahzade torani", 800, 6, 10, 6,
                SoldierTypes.MELEE, 1, "combo", AbilityCastTime.COMBO, null);
        minions.add(shahzadeTorani);

        Minion diveSiah = new Minion(113, "shahzade torani", 300, 9, 10, 14,
                SoldierTypes.HYBRID, 7, "gooraaaa!", null, null);
        minions.add(diveSiah);

        Minion ghooleSangAndaz = new Minion(114, "ghoole sang andaz", 300, 9, 12, 12,
                SoldierTypes.RANGED, 7, "gooraaa!", null, null);
        minions.add(ghooleSangAndaz);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 10, 0));
        Minion oghab = new Minion(115, "oghab", 200, 2, 2, 0,
                SoldierTypes.RANGED, 3, "increase health by 10", AbilityCastTime.PASSIVE, buffs);
        minions.add(oghab);

        Minion diveGorazSavar = new Minion(116, "dive goraz savar", 300, 6, 8, 16,
                SoldierTypes.MELEE, 1, "gooraaa!", null, null);
        minions.add(diveGorazSavar);

        buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(Kind.NEGATIVE, 1, false, 2, 0));
        // TODO: 4/28/19 target requires
        Minion ghooleTackCheshm = new Minion(117, "ghoole tack cheshm", 500, 7, 11, 12,
                SoldierTypes.HYBRID, 3, "hengame marg br minion ha dar 8 khane mojaver zarbeh mizanad", AbilityCastTime.ON_DEATH, buffs);
        minions.add(ghooleTackCheshm);

        buffs = new ArrayList<>();
        buffs.add(new PoisonBuff(Kind.NEGATIVE, 3, false, 1));
        Minion mareSammi = new Minion(118, "mare sammi", 300, 4, 6, 5,
                SoldierTypes.RANGED, 4, "poison enemy for 3 turn", AbilityCastTime.ON_ATTACK, buffs);
        minions.add(mareSammi);

        Minion ezhdehayeAtashAndaz = new Minion(119, "ezhdehaye atash andaz", 250, 5, 5, 9,
                SoldierTypes.RANGED, 4, "fireeee! fireeee!", null, null);
        minions.add(ezhdehayeAtashAndaz);

        //todo we don't have the buff :(
        Minion shireDarandeh = new Minion(120, "shire darandeh", 600, 2, 8, 1,
                SoldierTypes.MELEE, 1, "don't be effected by holly buff", AbilityCastTime.ON_ATTACK, null);
        minions.add(shireDarandeh);

        buffs = new ArrayList<>();
        buffs.add(new HolyInverseBuff(Kind.NEGATIVE, 0, true, 1));
        Minion mareGhoolPeykar = new Minion(121, "mare ghool peykar", 500, 8, 7, 14,
                SoldierTypes.RANGED, 5, "minion ha ta 2 khane mojaver be tor daem yek vahed bishtar zarbeh mikhorand", AbilityCastTime.ON_SPAWN, buffs);
        minions.add(mareGhoolPeykar);

        buffs = new ArrayList<>();
        //todo check second buff
        buffs.add(new AttackBuff(Kind.NEGATIVE, 0, false, 6));
        buffs.add(new AttackBuff(Kind.NEGATIVE, -1, false, 4));
        Minion gorgeSephid = new Minion(122, "gorge sephid", 400, 5, 2, 8,
                SoldierTypes.MELEE, 1, "miniony ke behesh zarbe zade dar nobat baad 6  va dar nobat baady 4 vahed zaebe mikhorad", AbilityCastTime.ON_ATTACK, buffs);
        minions.add(gorgeSephid);

        buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(Kind.NEGATIVE, 0, false, 8, 0));
        Minion palang = new Minion(123, "palang", 400, 4, 2, 6,
                SoldierTypes.MELEE, 1, "when attack a minion it damage in next turn by 8", AbilityCastTime.ON_ATTACK, buffs);
        minions.add(palang);

        buffs = new ArrayList<>();
        buffs.add(new AttackBuff(Kind.NEGATIVE, 0, false, 6));
        Minion gorg = new Minion(124, "gorg", 400, 3, 1, 6,
                SoldierTypes.MELEE, 1, "when attack a minion it damage in next turn by 6", AbilityCastTime.ON_ATTACK, buffs);
        minions.add(gorg);

        buffs = new ArrayList<>();
        //todo needs target
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 0, 2));
        buffs.add(new WeaknessBuff(Kind.NEGATIVE, 1, false, 1, 0));
        Minion jadoogar = new Minion(125, "jadoogar", 550, 4, 4, 5,
                SoldierTypes.RANGED, 3, "be khodash va har minion khody dar 8 khaneye mojaver 2 vahed ghodrat ezafe va 1 vahed salamaty kam mikonad", AbilityCastTime.PASSIVE, buffs);
        minions.add(jadoogar);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 0, 2));
        buffs.add(new HolyBuff(Kind.POSITIVE, 1, true, 1));
        Minion jadoogareAzam = new Minion(126, "jadoogare azam", 550, 6, 6, 6,
                SoldierTypes.RANGED, 5, "be khodash va har minion khody dar 8 khaneye mojaver 1 vahed ghodrat va 1 holy buff be soorate continuous midahad", AbilityCastTime.PASSIVE, buffs);
        minions.add(jadoogareAzam);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, true, 0, 1));
        Minion jen = new Minion(127, "jen", 500, 5, 4, 10,
                SoldierTypes.RANGED, 4, "be hameye minion haye khody 1 vahed afzayeshe ghodrat be soorate continuous midahad", AbilityCastTime.ON_TURN, buffs);
        minions.add(jen);

        buffs = new ArrayList<>();
        buffs.add(new AntiDisArmBuff(Kind.POSITIVE, Integer.MAX_VALUE, true));
        Minion gorazeVahshi = new Minion(128, "goraze vahshi", 500, 6, 14, 10,
                SoldierTypes.MELEE, 1, "disarm nemishavad", AbilityCastTime.ON_DEFEND, buffs);
        minions.add(gorazeVahshi);

        buffs = new ArrayList<>();
        buffs.add(new AntiPoisonBuff(Kind.POSITIVE, Integer.MAX_VALUE, true));
        Minion piran = new Minion(129, "piran", 400, 8, 12, 20,
                SoldierTypes.MELEE, 1, "masmoom nemishavad", AbilityCastTime.ON_DEFEND, buffs);
        minions.add(piran);

        buffs = new ArrayList<>();
        buffs.add(new AntiNegativeBuff(Kind.POSITIVE, Integer.MAX_VALUE, true));
        Minion giv = new Minion(130, "giv", 450, 4, 7, 5,
                SoldierTypes.RANGED, 5, "az cart ha asar manfy nemibinad", AbilityCastTime.ON_DEFEND, buffs);
        minions.add(giv);

        buffs = new ArrayList<>();
        buffs.add(new AttackBuff(Kind.NEGATIVE, 1, false, 16));
        Minion bahman = new Minion(131, "bahman", 450, 8, 9, 16,
                SoldierTypes.MELEE, 1, "tasadofy az 1 minion 16 vahed joon kam mikonad", AbilityCastTime.ON_SPAWN, buffs);
        minions.add(bahman);

        buffs = new ArrayList<>();
        buffs.add(new ApStatus(Kind.POSITIVE, 0, true));
        Minion ashkboos = new Minion(132, "ashkboos", 400, 7, 8, 14,
                SoldierTypes.MELEE, 1, "az nirooha ba ghodrat zarbeye kamtar attack nemikhorad", AbilityCastTime.ON_DEFEND, buffs);
        minions.add(ashkboos);

        Minion iraj = new Minion(133, "iraj", 500, 4, 20, 6,
                SoldierTypes.RANGED, 3, "how dare you!!!", null, null);
        minions.add(iraj);

        Minion ghooleBozorg = new Minion(134, "ghoole bozorg", 600, 9, 8, 30,
                SoldierTypes.HYBRID, 2, "goooraaaaa!", null, null);
        minions.add(ghooleBozorg);

        buffs = new ArrayList<>();
        buff = new DispellBuff(Kind.NEGATIVE, 1, false);
        buffs.add(buff);
        // TODO: 2019-04-29 only positive spells must be displaced
        Minion ghooleDoSar = new Minion(135, "goole do sar", 550, 4, 4, 10, SoldierTypes.MELEE,
                1, "displl his target's positive spells", AbilityCastTime.ON_ATTACK, buffs);
        minions.add(ghooleDoSar);

        buffs = new ArrayList<>();
        buff = new StunBuff(Kind.NEGATIVE, 1, false);
        buffs.add(buff);
        Minion naneSarma = new Minion(136, "nane sarma", 500, 3, 4, 3, SoldierTypes.RANGED, 5,
                "stun enemies next to him for 1 turn", AbilityCastTime.ON_SPAWN, buffs);
        minions.add(naneSarma);

        buffs = new ArrayList<>();
        buff = new HolyBuff(Kind.POSITIVE, 1, true, 1);
        for (int i = 0; i < 12; i++) {
            buffs.add(buff);
        }
        Minion foladZereh = new Minion(137, "folad zereh", 650, 3, 1, 1, SoldierTypes.MELEE, 1,
                "has 12 holy buff", AbilityCastTime.PASSIVE, buffs);
        minions.add(foladZereh);

        buffs = new ArrayList<>();
        buff = new AttackBuff(Kind.NEGATIVE, 0, false, 6);
        // TODO: 2019-04-29 target not handeled
        buffs.add(buff);
        Minion siavash = new Minion(138, "siavash", 350, 4, 5, 8, SoldierTypes.MELEE, 1
                , "deals 6 damage to enemy hero", AbilityCastTime.ON_DEATH, buffs);
        minions.add(siavash);

        Minion shahGhol = new Minion(139, "shah ghol", 600, 5, 4, 10, SoldierTypes.MELEE, 1,
                "can attack multiple targets", AbilityCastTime.COMBO, null);
        minions.add(shahGhol);

        Minion arjanghDiv = new Minion(140, "arjang div", 600, 3, 6, 6, SoldierTypes.MELEE,
                1, "combo", AbilityCastTime.COMBO, null);
        minions.add(arjanghDiv);

    }


    public void createHeroes(ArrayList<Hero> heroes) {
        Buff buff = new PowerBuff(Kind.POSITIVE, 0, true, 0, 4);
        Hero diveSepid = new Hero(301, "dive sepid", 8000, 1, 4, 50,
                SoldierTypes.MELEE, 1, "give itself power buff with increasing power by 4 continuously", buff, 2,
                new Target(Type.SOLDIER, 0, SoldierTargetType.FRIENDLY_HERO));
        heroes.add(diveSepid);

        buff = new StunBuff(Kind.POSITIVE, 1, false);
        Hero simorgh = new Hero(302, "simorgh", 9000, 5, 4, 50,
                SoldierTypes.MELEE, 1, "stun all enemy's forces for 1 turn", buff, 8,
                new Target(Type.SOLDIER, 0, SoldierTargetType.ALL_ENEMIES));
        heroes.add(simorgh);
        buff = new DisArmBuff(Kind.NEGATIVE, 1, false);
        Hero ezhdehaYeHaftSar = new Hero(303, "ezhdeha ye haft sar", 8000, 0, 4, 50,
                SoldierTypes.MELEE, 1, "disarm one enemy", buff, 1,
                new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_SOLDIER));
        heroes.add(ezhdehaYeHaftSar);
        buff = new StunBuff(Kind.NEGATIVE, 1, false);
        Hero rakhsh = new Hero(304, "rakhsh", 8000, 1, 4, 50,
                SoldierTypes.MELEE, 1, "stun one enemy for 1 turn", buff, 2,
                new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY));
        heroes.add(rakhsh);
        buff = new PoisonBuff(Kind.NEGATIVE, 3, false, 1);
        Hero zahack = new Hero(305, "zahack", 10000, 0, 2, 50,
                SoldierTypes.MELEE, 1, "on attack poison enemy for 3 turn", buff, 0,
                new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY));
        heroes.add(zahack);
        buff = new HolyBuff(Kind.POSITIVE, 3, false, 1);
        Hero kaveh = new Hero(306, "kaveh", 8000, 1, 4, 50,
                SoldierTypes.MELEE, 1, "make one cell holy for 3 turn", buff, 3,
                new Target(Type.AREA, 1, null));
        heroes.add(kaveh);
        buff = new WeaknessBuff(Kind.NEGATIVE, 1, false, 4, 0);
        Hero arash = new Hero(307, "arash", 10000, 2, 2, 30,
                SoldierTypes.RANGED, 6, "give all enemy force in row with it 4 damage", buff, 2,
                new Target(Type.SOLDIER, 0, SoldierTargetType.ALL_SOLDIERS_IN_A_ROW));
        heroes.add(arash);
        buff = new DispellBuff(Kind.NEGATIVE, 1, false);
        Hero afsaneh = new Hero(308, "afsaneh", 11000, 1, 3, 40,
                SoldierTypes.RANGED, 3, "dispel one enemy force", buff, 2,
                new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY));
        heroes.add(afsaneh);
        buff = new HolyBuff(Kind.POSITIVE, 3, false, 1);
        Hero esfandiar = new Hero(309, "esfandiar", 12000, 0, 3, 35,
                SoldierTypes.HYBRID, 3, "have 3 holy buff continuously", buff, 0,
                new Target(Type.SOLDIER, 0, SoldierTargetType.FRIENDLY_HERO));
        heroes.add(esfandiar);
        Hero rostam = new Hero(310, "rostam", 8000, 0, 7, 55,
                SoldierTypes.HYBRID, 4, "", null, 0, null);
        heroes.add(rostam);
        //todo:must have target area

    }

    private static void createSpellCards(ArrayList<SpellCard> spellCards, ArrayList<Buff> buffs) {
        buffs.add(new DisArmBuff(Kind.NEGATIVE, 0, true));
        Target target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY);
        SpellCard totalDisarm = new SpellCard(201, "total Disarm", 1000, 0,
                "disarm forever", buffs, target);
        spellCards.add(totalDisarm);

        buffs = new ArrayList<>();
        buffs.add(new DispellBuff(Kind.NEGATIVE, 0, false));
        target = new Target(Type.AREA, 2, null);
        SpellCard areaDispel = new SpellCard(202, "Area Dispel", 1500, 2,
                "eliminate enemy's positive buffs and friendly negatives buffs", buffs, target);
        spellCards.add(areaDispel);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 0, 2));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_FRIENDLY_SOLDIER);
        SpellCard empower = new SpellCard(203, "Empower", 250, 1,
                "increase friendly force power by 2", buffs, target);
        spellCards.add(empower);

        buffs = new ArrayList<>();
        buffs.add(new AttackBuff(Kind.NEGATIVE, 1, false, 4));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY);
        SpellCard fireBall = new SpellCard(204, "Fire Ball", 400, 1,
                "damage enemy by 4", buffs, target);
        spellCards.add(fireBall);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 0, 4));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.FRIENDLY_HERO);
        SpellCard godStrength = new SpellCard(205, "God Strength", 450, 2,
                "give friendly hero 4 strength", buffs, target);
        spellCards.add(godStrength);

        buffs = new ArrayList<>();
        buffs.add(new FireBuff(Kind.NEGATIVE, 2, false, 2));
        target = new Target(Type.AREA, 2, null);
        SpellCard hellFire = new SpellCard(206, "Hell Fire", 600, 3,
                "create fire effect on target cells for 2 turns", buffs, target);
        spellCards.add(hellFire);

        buffs = new ArrayList<>();
        buffs.add(new AttackBuff(Kind.NEGATIVE, 1, false, 7));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ENEMY_HERO);
        SpellCard lightingBolt = new SpellCard(207, "Lighting Bolt", 1250, 2,
                "damage enemy hero by 8", buffs, target);
        spellCards.add(lightingBolt);

        buffs = new ArrayList<>();
        buffs.add(new PoisonBuff(Kind.NEGATIVE, 1, false, 1));
        target = new Target(Type.AREA, 3, null);
        SpellCard poisonLake = new SpellCard(208, "Poison Lake", 900, 5,
                "create poison effect on target cells for 1 turn", buffs, target);
        spellCards.add(poisonLake);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 3, false, 0, 4));
        buffs.add(new DisArmBuff(Kind.NEGATIVE, 3, false));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_FRIENDLY_SOLDIER);
        SpellCard madness = new SpellCard(209, "Madness", 650, 0,
                "increase friendly force power by 4 for 3 turns but disarm it", buffs, target);
        spellCards.add(madness);

        buffs = new ArrayList<>();
        buffs.add(new DisArmBuff(Kind.NEGATIVE, 1, false));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ALL_ENEMIES);
        SpellCard allDisarm = new SpellCard(210, "All Disarm", 2000, 9,
                "disarm for 1 turn", buffs, target);
        spellCards.add(allDisarm);

        buffs = new ArrayList<>();
        buffs.add(new PoisonBuff(Kind.NEGATIVE, 4, false, 1));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ALL_ENEMIES);
        SpellCard allPoison = new SpellCard(211, "All Poison", 1500, 8,
                "all force poisoned for 4 turns", buffs, target);
        spellCards.add(allPoison);

        buffs = new ArrayList<>();
        buffs.add(new AntiNegativeBuff(Kind.POSITIVE, 1, false));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_SOLDIER);
        SpellCard dispel = new SpellCard(212, "Dispel", 2100, 0,
                "eliminate enemy's positive buffs and friendly negatives buffs", buffs, target);
        spellCards.add(dispel);

        buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(Kind.NEGATIVE, 1, false, 6, 0));
        buffs.add(new HolyBuff(Kind.POSITIVE, 3, false, 2));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_FRIENDLY_SOLDIER);
        SpellCard healthWithProfit = new SpellCard(213, "Health with profit", 2250, 0,
                "decrease friendly force health by 6 but give it 2 holy buff for 3 turns", buffs, target);
        spellCards.add(healthWithProfit);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 0, 6));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_FRIENDLY_SOLDIER);
        SpellCard powerUp = new SpellCard(214, "Power Up", 2500, 2,
                "increase force power by 6", buffs, target);
        spellCards.add(powerUp);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 0, true, 0, 2));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ALL_FRIENDLY_SOLDIERS);
        SpellCard allPower = new SpellCard(215, "All Power", 2000, 4,
                "give all friendly force 2 power buff continuously", buffs, target);
        spellCards.add(allPower);

        buffs = new ArrayList<>();
        buffs.add(new AttackBuff(Kind.NEGATIVE, 1, false, 6));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ALL_ENEMIES_IN_A_COLUMN);
        SpellCard allAttack = new SpellCard(216, "All Attack", 1500, 4,
                "damage all enemy force by 6", buffs, target);
        spellCards.add(allAttack);

        buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(Kind.NEGATIVE, 1, false, 0, 4));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY_MINION);
        SpellCard weakening = new SpellCard(217, "Weakening", 1000, 1,
                "decrease one enemy minion,s power by 4", buffs, target);
        spellCards.add(weakening);

        buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(Kind.NEGATIVE, 1, false, 6, 0));
        buffs.add(new PowerBuff(Kind.POSITIVE, 1, false, 0, 8));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_FRIENDLY_MINION);
        SpellCard sacrifice = new SpellCard(218, "Sacrifice", 1600, 2,
                "give friendly minion weakness buff with decreasing health by 6 and power buff with increasing power by 8", buffs, target);
        spellCards.add(sacrifice);

        buffs = new ArrayList<>();
        buffs.add(new KillBuff(Kind.NEGATIVE, 1, false));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.FRIENDLY_HERO);
        SpellCard kingsGuard = new SpellCard(219, "Kings Guard", 1750, 9,
                "kill enemy", buffs, target);
        spellCards.add(kingsGuard);

        buffs = new ArrayList<>();
        buffs.add(new StunBuff(Kind.NEGATIVE, 2, false));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_ENEMY);
        SpellCard shock = new SpellCard(220, "Shock", 1200, 1,
                "stun enemy force for two turn  ", buffs, target);
        spellCards.add(shock);

    }

    private static void createItem(ArrayList<Item> items, ArrayList<Buff> buffs) {
        //todo: first item
        //todo: random target
        //todo: specific soldier effected
        buffs.add(new HolyBuff(Kind.POSITIVE, 1, false, 12));
        Target target = new Target(Type.SOLDIER, 0, SoldierTargetType.FRIENDLY_HERO);
        Item namooseSepar = new Item(002, "Namoose Separ", 400,
                "", ItemTypes.USABLE, buffs, target);
        items.add(namooseSepar);

        buffs = new ArrayList<>();
        buffs.add(new DisArmBuff(Kind.NEGATIVE, 1, false));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.FRIENDLY_HERO);
        Item camaneDamol = new Item(003, "camane damol", 30000,
                "disarm enemy for one turn", ItemTypes.USABLE, buffs, target);
        items.add(camaneDamol);

        buffs = new ArrayList<>();
        buffs.add(new PowerBuff(Kind.POSITIVE, 0, true, 6, 0));
        target = new Target(Type.SOLDIER, 0, SoldierTargetType.ONE_FRIENDLY_SOLDIER);
        Item nooshDaroo = new Item(004, "noosh Daroo", 0,
                "one randomly minion healths increase by 6", ItemTypes.COLLECTABLE, buffs, target);
        items.add(nooshDaroo);

    }
}
