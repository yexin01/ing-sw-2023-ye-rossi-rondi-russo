package it.polimi.ingsw.model;



import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.server.ServerView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    @DisplayName("addPlayer: generic check")
    void addPlayer() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        game.addPlayer("player1", modelView);
        assertNotEquals(null, game.getPlayerByNickname("player1"));
    }

    /*
    @Test
    @DisplayName("insertNickname: 1st player to join")
    void insertNickname() throws Exception {
        Game game = new Game();
        Assertions.assertTrue(game.insertNickname("player1"));
    }

    @Test
    @DisplayName("insertNickname: 2nd player to join (legal nickname)")
    void insertNicknameCC1() throws Exception {
        Game game = new Game();
        game.insertNickname("player1");
        Assertions.assertTrue(game.insertNickname("player2"));
    }

    @Test
    @DisplayName("insertNickname: stop at 2 players")
    void insertNicknameCC2() throws Exception {
        Game game = new Game();
        game.insertNickname("player1");
        game.insertNickname("player2");
        Assertions.assertFalse(game.insertNickname("stop"));
    }

    @Test
    @DisplayName("insertNickname: automatic start at player 4 added")
    void insertNicknameCC3() throws Exception {
        Game game = new Game();
        game.insertNickname("player1");
        game.insertNickname("player2");
        game.insertNickname("player3");
        Assertions.assertFalse(game.insertNickname("player4"));
    }

     */

    @Test
    @DisplayName("differentNickname: 2nd player has 1st player nickname")
    void differentNickname() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        game.setPlayers(players);
        assertFalse(game.differentNickname("player1"));
    }

    @Test
    @DisplayName("differentNickname: 2nd player nickname different from the first one's")
    void differentNicknameCC1() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        game.setPlayers(players);
        assertTrue(game.differentNickname("player2"));
    }

    @Test
    @DisplayName("differentNickname: 3rd player nickname different from the ones of the other two")
    void differentNicknameCC2() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        players.add(new Player("player2", modelView));
        game.setPlayers(players);
        assertTrue(game.differentNickname("player3"));
    }

    @Test
    @DisplayName("createCommonGoalCard: generic check for size and points")
    void createCommonGoalCard() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        players.add(new Player("player2", modelView));
        game.setPlayers(players);
        game.createCommonGoalCard(gameRules, modelView);
        assertEquals(2, game.getCommonGoalCards().size());
        ArrayList<Integer> points = new ArrayList<>();
        points.add(4);
        points.add(8);
        for(CommonGoalCard c: game.getCommonGoalCards()){
            assertIterableEquals(points, c.getPoints());
        }
    }

    @Test
    @DisplayName("updateAdjacentPoints: one 5 tiles group")
    void updateAdjacentPoints() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID);
        assertEquals(5, game.updateAdjacentPoints(gameRules));
    }

    @Test
    @DisplayName("updateAdjacentPoints: one 7 tiles group")
    void updateAdjacentPointsCC1() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID);
        assertEquals(8, game.updateAdjacentPoints(gameRules));
    }

    @Test
    @DisplayName("updateAdjacentPoints: 3 two tiles groups")
    void updateAdjacentPointsCC2() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.BOOK, tileID);
        assertEquals(0, game.updateAdjacentPoints(gameRules));
    }

    @Test
    @DisplayName("updateAdjacentPoints: 3, 4, 5, 6 tiles groups")
    void updateAdjacentPointsCC3() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
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
        assertEquals(18, game.updateAdjacentPoints(gameRules));
    }

    @Test
    @DisplayName("updatePointsCommonGoals: generic check (2P)")
    void updatePointsCommonGoals() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player1 = new Player("player1", modelView);
        Player player2 = new Player("player2", modelView);
        Bookshelf bookshelf1 = new Bookshelf(6,5,3);
        player1.setBookshelf(bookshelf1);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        ArrayList<CommonGoalCard> commonGoalCards = new ArrayList<>();
        CommonGoalCard8 commonGoalCard8 = new CommonGoalCard8();
        commonGoalCard8.setModelView(modelView);
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        commonGoalCard9.setModelView(modelView);
        commonGoalCards.add(commonGoalCard8);
        commonGoalCards.add(commonGoalCard9);
        game.setCommonGoalCards(commonGoalCards);
        game.createCommonGoalPlayer(gameRules);
        game.setCommonGoalCardsPoints(gameRules);
        int tileID = 0;
        bookshelf1.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf1.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf1.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf1.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID);
        assertEquals(8, game.updatePointsCommonGoals());
        game.setNextPlayer();
        Bookshelf bookshelf2 = new Bookshelf(6,5,3);
        player2.setBookshelf(bookshelf2);
        bookshelf2.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf2.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf2.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf2.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID);
        assertEquals(4, game.updatePointsCommonGoals());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 6/6 tiles")
    void updatePersonalGoalPoints() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.PLANT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(12, game.updatePersonalGoalPoints(gameRules));
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 5/6 tiles")
    void updatePersonalGoalPointsCC1() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(9, game.updatePersonalGoalPoints(gameRules));
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 4/6 tiles")
    void updatePersonalGoalPointsCC2() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(6, game.updatePersonalGoalPoints(gameRules));
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 3/6 tiles")
    void updatePersonalGoalPointsCC3() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(4, game.updatePersonalGoalPoints(gameRules));
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 2/6 tiles")
    void updatePersonalGoalPointsCC4() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(2, game.updatePersonalGoalPoints(gameRules));
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 1/6 tiles")
    void updatePersonalGoalPointsCC5() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(1, game.updatePersonalGoalPoints(gameRules));
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 0/6 tiles")
    void updatePersonalGoalPointsCC6() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 5, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 5, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 5, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 5, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 5, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 4, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        player.setPersonalGoalCard(personalGoalCard);
        assertEquals(0, game.updatePersonalGoalPoints(gameRules));
    }
}