package it.polimi.ingsw.model;

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
        Board board = new Board();
        Game game = new Game();
        game.addPlayer("player1");
        assertFalse(game.differentNickname("player1"));
    }

    @Test
    @DisplayName("differentNickname: 2nd player nickname different from the first one's")
    void differentNicknameCC1() throws Exception {
        Board board = new Board();
        Game game = new Game();
        game.addPlayer("player1");
        assertTrue(game.differentNickname("player2"));
    }

    @Test
    @DisplayName("differentNickname: 3rd player nickname different from the ones of the other two")
    void differentNicknameCC2() throws Exception {
        Board board = new Board();
        Game game = new Game();
        game.addPlayer("player1");
        game.addPlayer("player2");
        assertTrue(game.differentNickname("player3"));
    }
}