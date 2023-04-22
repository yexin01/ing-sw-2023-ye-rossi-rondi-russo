package it.polimi.ingsw.model;

import it.polimi.ingsw.json.GameRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

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

    @Test
    @DisplayName("differentNickname: 2nd player has 1st player nickname")
    void differentNickname() throws Exception {
        Game game = new Game();
        game.addPlayer("player1");
        assertFalse(game.differentNickname("player1"));
    }

    @Test
    @DisplayName("differentNickname: 2nd player nickname different from the first one's")
    void differentNicknameCC1() throws Exception {
        Game game = new Game();
        game.addPlayer("player1");
        assertTrue(game.differentNickname("player2"));
    }

    @Test
    @DisplayName("differentNickname: 3rd player nickname different from the ones of the other two")
    void differentNicknameCC2() throws Exception {
        Game game = new Game();
        game.addPlayer("player1");
        game.addPlayer("player2");
        assertTrue(game.differentNickname("player3"));
    }

    @Test
    @DisplayName("createCommonGoalCard: generic check")
    void createCommonGoalCard() throws Exception {
        Game game = new Game();
        GameRules gameRules = new GameRules();
        game.createCommonGoalCard(gameRules);
        assertEquals(2, game.getCommonGoalCards().size());
    }

    @Test
    @DisplayName("updateAdjacentPoints: one 5 tiles group")
    void updateAdjacentPoints() throws Exception {
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
    @DisplayName("updatePointsCommonGoals: 1st player to fulfill the first common goal (2P)")
    void updatePointsCommonGoals() throws Exception {
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player1.setBookshelf(bookshelf);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.createCommonGoalPlayer(gameRules);
        ArrayList<CommonGoalCard> commonGoalCards = new ArrayList<>();
        commonGoalCards.add(new CommonGoalCard8());
        commonGoalCards.add(new CommonGoalCard9());
        game.setCommonGoalCards(commonGoalCards);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID);
        assertEquals(8, game.updatePointsCommonGoals());
    }

    @Test
    @DisplayName("updatePersonalGoalPoints: 6/6 tiles")
    void updatePersonalGoalPoints() throws Exception {
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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
        GameRules gameRules = new GameRules();
        Game game = new Game();
        Player player = new Player("player1");
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