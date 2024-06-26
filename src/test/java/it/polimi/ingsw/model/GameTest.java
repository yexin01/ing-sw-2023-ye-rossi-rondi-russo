package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class GameTest {

    /**
     * Creates a game with a default "player1" player
     * Generate a case that makes testing easier and more immediate,
     * but at the same time effective and fully functional
     * @return : initialized game
     */
    public Game singlePlayer () throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Game game = new Game(modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView, gameRules));
        game.setPlayers(players);
        return game;
    }

    @Test
    @DisplayName("addPlayers: generic check")
    void addPlayers() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Game game = new Game(modelView);
        ArrayList<String> nicknames= new ArrayList<>();
        nicknames.add("player1");
        nicknames.add("player2");
        game.addPlayers(nicknames);
        assertEquals(nicknames.size(), modelView.getPlayerPoints().length);
    }

    /*@Test
    @DisplayName("differentNickname: 2nd player has 1st player nickname")
    void differentNickname() throws Exception {
        Game game = singlePlayer();
        assertFalse(game.differentNickname("player1"));
    }

    @Test
    @DisplayName("differentNickname: 2nd player nickname different from the first one's")
    void differentNicknameCC1() throws Exception {
        Game game = singlePlayer();
        assertTrue(game.differentNickname("player2"));
    }

    @Test
    @DisplayName("differentNickname: 3rd player nickname different from the ones of the other two")
    void differentNicknameCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Game game = new Game(modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        players.add(new Player("player2", modelView));
        game.setPlayers(players);
        assertTrue(game.differentNickname("player3"));
    }

     */

    @Test
    @DisplayName("createCommonGoalCard: generic check for size and points")
    void createCommonGoalCard() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Game game = new Game(modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView, gameRules));
        players.add(new Player("player2", modelView, gameRules));
        game.setPlayers(players);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        game.createCommonGoalCard(gameRules, ids);
        assertEquals(2, game.getCommonGoalCards().size());
        ArrayList<Integer> points = new ArrayList<>();
        points.add(4);
        points.add(8);
        for(CommonGoalCard c: game.getCommonGoalCards()){
            assertIterableEquals(points, c.getPoints());
        }
    }

    @Test
    @DisplayName("createPersonalGoalCard: generic check for size and points")
    void createPersonalGoalCard() throws Exception {
        Game game = singlePlayer();
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        game.createPersonalGoalCard(new GameRules(), ids);
        assertEquals(1, game.getPlayers().get(0).getPersonalGoalCard().getIdPersonal());
    }


    @Test
    @DisplayName("updateAdjacentPoints: one 5 tiles group")
    void updateAdjacentPoints() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID);
        game.updateAdjacentPoints(new GameRules());
        assertEquals(5, game.getTurnPlayerOfTheGame().getAdjacentPoints());
    }

    @Test
    @DisplayName("updateAdjacentPoints: one 7 tiles group")
    void updateAdjacentPointsCC1() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        game.getPlayers().get(0).setBookshelf(bookshelf);
        bookshelf.matrix(6,5);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID);
        game.updateAdjacentPoints(new GameRules());
        assertEquals(8, game.getTurnPlayerOfTheGame().getAdjacentPoints());
    }

    @Test
    @DisplayName("updateAdjacentPoints: 3 two tiles groups")
    void updateAdjacentPointsCC2() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.BOOK, tileID);
        game.updateAdjacentPoints(new GameRules());
        assertEquals(0, game.getTurnPlayerOfTheGame().getAdjacentPoints());
    }

    @Test
    @DisplayName("updateAdjacentPoints: 3, 4, 5, 6 tiles groups")
    void updateAdjacentPointsCC3() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[3][2] = new ItemTile(Type.PLANT, tileID);
        game.updateAdjacentPoints(new GameRules());
        assertEquals(18, game.getTurnPlayerOfTheGame().getAdjacentPoints());
    }

    @Test
    @DisplayName("updatePointsCommonGoals: generic check (2P)")
    void updatePointsCommonGoals() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Game game = new Game(modelView);
        Player player1 = new Player("player1", modelView, gameRules);
        Player player2 = new Player("player2", modelView, gameRules);
        Bookshelf bookshelf1 = new Bookshelf();
        bookshelf1.matrix(6,5);
        player1.setBookshelf(bookshelf1);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        ArrayList<CommonGoalCard> commonGoalCards = new ArrayList<>();
        CommonGoalCard8 commonGoalCard8 = new CommonGoalCard8();
        modelView.setIdCommon(0,0,7);
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        modelView.setIdCommon(0,1,8);
        commonGoalCards.add(commonGoalCard8);
        commonGoalCards.add(commonGoalCard9);
        game.setCommonGoalCards(commonGoalCards);
        //game.createCommonGoalPlayer(gameRules);
        game.setCommonGoalCardsPoints(gameRules);
        int tileID = 0;
        bookshelf1.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf1.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf1.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf1.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID);
        Bookshelf bookshelf2 = new Bookshelf();
        bookshelf2.matrix(6,5);
        player2.setBookshelf(bookshelf2);
        bookshelf2.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf2.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf2.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf2.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID);
        game.updatePointsCommonGoals();
        assertEquals(8, game.getTurnPlayerOfTheGame().getCommonGoalPoints()[0]);
        modelView.setPlayerPoints(new PlayerPointsView(new int[] {0, 0}, 0, "player2"), 1);
        modelView.setNextPlayer();
        game.updatePointsCommonGoals();
        assertEquals(4, game.getTurnPlayerOfTheGame().getCommonGoalPoints()[0]);
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 6/6 tiles")
    void updatePersonalGoalPoints() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.PLANT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(12, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 5/6 tiles")
    void updatePersonalGoalPointsCC1() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(9, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 4/6 tiles")
    void updatePersonalGoalPointsCC2() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(6, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 3/6 tiles")
    void updatePersonalGoalPointsCC3() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(4, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 2/6 tiles")
    void updatePersonalGoalPointsCC4() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(2, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 1/6 tiles")
    void updatePersonalGoalPointsCC5() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(1, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 0/6 tiles")
    void updatePersonalGoalPointsCC6() throws Exception {
        Game game = singlePlayer();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        game.getPlayers().get(0).setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(0, personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        game.getPlayers().get(0).setPersonalGoalCard(personalGoalCard);
        game.updatePersonalGoalPoints(new GameRules());
        assertEquals(0, game.getTurnPlayerOfTheGame().getPersonalGoalPoints());
    }

    /*@Test
    @DisplayName("checkWinner: generic check")
    void checkWinner () throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Game game = new Game(gameRules, 4, modelView);
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("player1"); nicknames.add("player2");
        game.addPlayers(nicknames);
        game.getPlayerByNickname("player1").setBookshelf(new Bookshelf(6,5,3));
        game.getPlayerByNickname("player2").setBookshelf(new Bookshelf(6,5,3));
        //game.getPlayerByNickname("player1").setPlayerPoints(10);
        //game.getPlayerByNickname("player2").setPlayerPoints(15);
        modelView.setPlayerPoints(new PlayerPointsView(10, new int[] {0, 0}, 0, 0, "player1"), 0);
        modelView.setPlayerPoints(new PlayerPointsView(15, new int[] {0, 0}, 0, 0, "player2"), 1);
        assertEquals("player2", game.checkWinner().get(0));
     }

     */
}

