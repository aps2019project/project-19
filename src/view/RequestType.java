package view;

public enum RequestType {
    ERROR,
    HELP,
    EXIT_MENU,
    ENTER_MENU,
    /////////// ACCOUNT /////////
    CREATE_ACCOUNT,
    LOGIN,
    SHOW_LEADER_BOARD,
    SAVE,
    LOGOUT,
    ///////// COLLECTION /////////
    SHOW_COLLECTION_ITEMS,
    SEARCH_IN_COLLECTION,
    SAVE_COLLECTION,
    CREATE_DECK,
    DELETE_DECK,
    ADD_TO_DECK,
    EXPORT_DECK,
    IMPORT_DECK,
    REMOVE_FROM_DECK,
    VALIDATE_DECK,
    SELECT_MAIN_DECK,
    SHOW_ALL_DECKS,
    SHOW_DECK,
    //////// SHOP ////////
    SEARCH_IN_SHOP,
    BUY_FROM_SHOP,
    SELL_TO_SHOP,
    SHOW_SHOP,
    /////// BATTLE ///////
    SHOW_GAME_INFO,
    SHOW_MY_MINIONS,
    SHOW_OPPONENT_MINIONS,
    SHOW_CARD_INFO_IN_BATTLE,
    MOVE_CARD,
    ATTACK,
    COMBO_ATTACK,
    USE_SPECIAL_POWER,
    SHOW_HAND,
    INSERT_CARD,
    END_TURN,
    SHOW_GATHERED_COLLECTABLES,
    SELECT_CARD_OR_COLLECTABLE,
    SELECT_STORY_LEVEL,
    SHOW_COLLECATBLE_INFO,
    SHOW_All_CARDS_IN_GRAVEYARD,
    SHOW_MY_CHOICES,
    USE_COLLECTABLE,
    SHOW_NEXT_CARD,
    ENTER_GRAVEYARD,
    SHOW_CARD_INFO_IN_GRAVEYARD,
    END_GAME,
    ////// CREATING GAME //////
    SHOW_ALL_PLAYERS,
    SELECT_OPPONENT_USER,
    SELECT_MULTI_PLAYER_MODE,
    CHOOSE_HERO,
    SELECT_CUSTOM_GAME_GAMEMODE,
    ////////default////////
    GET_SHOP,
    GET_ACCOUNT,
}
