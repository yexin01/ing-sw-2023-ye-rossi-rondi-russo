package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;

import java.io.IOException;

/**
 * The InfoAndEndGameListener class is responsible for sending necessary game data and handling end game events.
 * It listens to both the game controller and the game lobby.
 * For the "ALL_INFO" event:
 * In the game controller, it is triggered at the beginning of the game or when the number of connected players is equal to 2
 * after various disconnections have led to a number of participants <= 1.
 * In the game lobby, it is triggered when a player has reconnected and the number of connected participants is greater than 2.
 * It can also be triggered without player disconnection, after the game lobby receives a "something wrong" message.
 * For the "END_GAME" event:
 * In the game controller, it sends an "endGame" message to all connected players.
 * In the game lobby, it is triggered when all players have received the "endGame" message, and it calls the "endGame" method
 * in the game lobby to finalize the game.
 */

public class InfoAndEndGameListener extends EventListener{
    private final GlobalLobby globalLobby;

    /**
     * Constructs an InfoAndEndGameListener.
     * @param gameLobby The game lobby associated with the listener.
     * @param globalLobby The global lobby.
     */
    public InfoAndEndGameListener(GameLobby gameLobby, GlobalLobby globalLobby) {
        super(gameLobby);
        this.globalLobby = globalLobby;
    }

    /**
     * Fires the specified event and notifies the appropriate listeners.
     * - For the "ALL_INFO" event, it sends the message containing the updated model view to the appropriate players.
     * - For the "END_GAME" event, it sends the "endGame" message to all connected players in the game lobby.
     * @param event The event to fire.
     * @param playerNickname The nickname of the next player or null for game start.
     * @param newValue The updated model view or relevant data.
     * @throws IOException If an I/O error occurs while sending the message.
     */

    @Override
    public synchronized void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
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
     * Creates an "ALL_INFO" message containing the relevant game data to continue the game:
     * @param nickname The nickname of the message recipient.
     * @param modelView The updated model view.
     * @return The "ALL_INFO" message.
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
     * Called from the game lobby when all players have received the "endGame" message.
     */
    public synchronized void endGame(){
        try {
            globalLobby.endGameLobbyFromGlobalLobby(getGameLobby().getIdGameLobby());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
