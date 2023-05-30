package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *InfoAndEndGameListener send all the data needed to continue the game, listen to both the game controller and the gameLobby.
 * CASE ALL_INFO:
 * GameController:
 * 1)at the beginning of the game;
 * 2)when the number of connected is equal to 2  (following various disconnections that have led to a number of participants <=1).
 * GameLobby:
 * 1)message is sent to the player who has reconnected and the number of connected participants is greater than 2;
 * 2)this message can be sent even if the player does not disconnect. It is sent after gameLobby receives the something wrong message.
 *
 *
 * CASE END_GAME:
 * GameController: endGame message is sent to all connected players;
 * GameLobby: when all players have received the endGame message the endGame method is called from the game lobby.
 */

public class InfoAndEndGameListener extends EventListener{
    private final GlobalLobby globalLobby;

    /**
     * Constructor InfoAndEndGameListener
     * @param gameLobby
     * @param globalLobby
     */
    public InfoAndEndGameListener(GameLobby gameLobby, GlobalLobby globalLobby) {
        super(gameLobby);
        this.globalLobby = globalLobby;
    }

    /**
     *sends the message: -to all connected players in case of game start, -to two connected players following disconnections ,
     * -only to the user who has just reconnected;
     * @param event:1)start of the game; 2) a player has requested the updated data up to that moment; 3) end of the game;
     * @param playerNickname:next player to play or null in case of game start;
     * @param newValue: modelView updated;
     * @throws IOException
     */

    @Override
    public synchronized void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        //System.out.println("SONO NEL LISTENER STARTGAME e NEL caso something wrong LATO SERVER sto inviando");
        ModelView modelView=(ModelView) newValue;
        switch((TurnPhase)event){
            case ALL_INFO ->{
                if(playerNickname!=null){
                    getGameLobby().sendMessageToSpecificPlayer(creationMessageInfo(playerNickname,modelView),playerNickname) ;

             }else{
                    for(PlayerPointsView nickname: modelView.getPlayerPoints()){
                        getGameLobby().sendMessageToSpecificPlayer(creationMessageInfo(nickname.getNickname(),modelView),nickname.getNickname()) ;
                    }
                }
            }
            /**
             * END_GAME message contains: all the scores -common,-adjacent and -personal points,first player
             * to completely fill the bookshelf.
             * Players are sorted from lowest to highest score;
             */
            case END_GAME ->{
                MessageHeader header=new MessageHeader(MessageType.DATA,null);
                MessagePayload payload=new MessagePayload(TurnPhase.END_GAME);
                payload.put(Data.PERSONAL_POINTS,modelView.getPersonalPoints());
                payload.put(Data.POINTS,modelView.getPlayerPoints());
                payload.put(Data.BOOKSHELF_FULL_PLAYER,modelView.getBookshelfFullPoints());
                Message message=new Message(header,payload);
                getGameLobby().setMessageEndGame(message);
                getGameLobby().sendMessageToAllPlayers(message);
            }
        }
    }

    /**
     *creation of the ALL_INFO message that contains: board, player's bookshelf, commonGoal scores remaining,
     * personalGoal player score, selected tiles of the player whose turn it is, max number of tiles selectable
     * according to the rules of the game (with these rules 3), scores of players, current player, current phase of the game;
     * @param nickname: message addressee;
     * @param modelView: modelView updated;
     * @return message ALL_INFO
     */
    public Message creationMessageInfo(String nickname,ModelView modelView){
        MessageHeader header;
        MessagePayload payload=new MessagePayload(TurnPhase.ALL_INFO);
        BoardBoxView[][] boardView= modelView.getBoardView();
        header=new MessageHeader(MessageType.DATA,nickname);
        payload.put(Data.NEW_BOARD,boardView);
        ItemTileView[][] bookshelfView=modelView.getBookshelfView(nickname);
        payload.put(Data.NEW_BOOKSHELF,bookshelfView);
        payload.put(Data.COMMON_GOAL,modelView.getCommonGoalView());
        PersonalGoalCard personalGoalCard=modelView.getPlayerPersonalGoal(nickname);
        payload.put(Data.PERSONAL_GOAL_CARD,personalGoalCard);
        payload.put(Data.SELECTED_ITEMS,modelView.getSelectedItems());
        payload.put(Data.MAX_SELECTABLE_TILES,modelView.getMAX_SELECTABLE_TILES());
        payload.put(Data.PERSONAL_POINTS,modelView.getPersonalPoint(nickname));
        payload.put(Data.POINTS,modelView.checkWinner());
        payload.put(Data.NEXT_PLAYER,modelView.getTurnNickname());
        payload.put(Data.PHASE,modelView.getTurnPhase());
        Message m=new Message(header,payload);
       return m;
    }

    /**
     *called from the gameLobby when all players have received the endGame message
     */
    public synchronized void endGame(){
        //System.out.println("SONO NELL END GAME");
        try {
            globalLobby.endGameLobbyFromGlobalLobby(getGameLobby().getIdGameLobby());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
