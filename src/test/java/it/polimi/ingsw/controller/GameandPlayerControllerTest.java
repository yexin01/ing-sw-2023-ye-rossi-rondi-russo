package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameandPlayerControllerTest {

    @Test
    void differentNickname() {
        Board board = new Board();
        Game game = new Game(board);
        GameandPlayerController gameandPlayerController = new GameandPlayerController(game);
        ArrayList<Player> players = new ArrayList<>();
        //Set the nickname you want to insert
        players.add(new Player("player1"));
        game.setPlayers(players);
        assertFalse(gameandPlayerController.differentNickname("player1"));
    }

    @Test
    void insertAsSelected() {
    }

    @Test
    void checkBookshelf() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        Player player = new Player("player1");
        player.setBookshelf(bookshelf);
        Board board = new Board();
        Game game = new Game(board);
        game.setTurnPlayer(player);
        //Blank bookshelf set, create and insert tiles as you want
        GameandPlayerController gameandPlayerController = new GameandPlayerController(game);
        //Set the size of selectedTiles
        int size = 1; int tileID = 0;
        for (int i = 0; i<size; i++) {
            gameandPlayerController.selectedTiles().add(new ItemTile(Type.CAT, 0));
            tileID++;
        }
        //Set the column you want to check
        int column = 0;
        assertTrue(gameandPlayerController.checkBookshelf(column));
    }

    @Test
    void insertOnceATime() {
    }

    @Test
    void updateAdjacentPoints() throws Exception {
        GameRules gameRules = new GameRules();
        Board board = new Board();
        Game game = new Game(board);
        GameandPlayerController gameandPlayerController = new GameandPlayerController(game);
        Player player = new Player("player1");
        game.setTurnPlayer(player);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        //Set the tiles you want inside the bookshelf
        int tileID = 0;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID);
        assertEquals(5, gameandPlayerController.updateAdjacentPoints(gameRules));
    }

    @Test
    void updatePointsCommonGoals() throws Exception {
        GameRules gameRules = new GameRules();
        Board board = new Board();
        Game game = new Game(board);
        GameandPlayerController gameandPlayerController = new GameandPlayerController(game);
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        game.setTurnPlayer(player1);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player1.setBookshelf(bookshelf);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        gameandPlayerController.createCommonGoalPlayer(gameRules);
        ArrayList<CommonGoalCard> commonGoalCards = new ArrayList<>();
        //Set the commonGoalCards you want
        commonGoalCards.add(new CommonGoalCard8());
        commonGoalCards.add(new CommonGoalCard1());
        game.setCommonGoalCards(commonGoalCards);
        //Set the tiles inside the bookshelf as you want
        int tileID = 0;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID);
        assertEquals(8, gameandPlayerController.updatePointsCommonGoals());
    }

    @Test
    void updatePersonalGoalPoints() throws Exception {
        GameRules gameRules = new GameRules();
        Board board = new Board();
        Game game = new Game(board);
        GameandPlayerController gameandPlayerController = new GameandPlayerController(game);
        Player player = new Player("player1");
        game.setTurnPlayer(player);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        player.setBookshelf(bookshelf);
        ArrayList<PersonalGoalBox> personalGoalBoxes = new ArrayList<>();
        //Set the PersonalGoal coordinates as you want
        personalGoalBoxes.add(new PersonalGoalBox(Type.CAT, 0, 0));
        personalGoalBoxes.add(new PersonalGoalBox(Type.BOOK, 0, 1));
        personalGoalBoxes.add(new PersonalGoalBox(Type.GAME, 0, 2));
        personalGoalBoxes.add(new PersonalGoalBox(Type.FRAME, 0, 3));
        personalGoalBoxes.add(new PersonalGoalBox(Type.TROPHY, 0, 4));
        personalGoalBoxes.add(new PersonalGoalBox(Type.PLANT, 1, 0));
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalBoxes);
        //Set the tiles inside the bookshelf as you want
        int tileID = 0;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[0][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[0][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.PLANT, tileID);
        assertEquals(12, gameandPlayerController.updatePersonalGoalPoints(gameRules));
    }
}