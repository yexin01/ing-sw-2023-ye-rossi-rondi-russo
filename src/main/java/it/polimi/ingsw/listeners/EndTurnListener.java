package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.persistence.SaveGame;

import java.io.IOException;

/**
 * The EndTurnListener class is an event listener that listens to the game controller.
 * It is awakened when a player's turn is over or when the turn player has disconnected.
 */
public class EndTurnListener extends EventListener{
    /**
     * Constructs an EndTurnListener with the specified game lobby.
     * @param gameLobby The game lobby associated with the listener.
     */
    public EndTurnListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    /**
     * Sends the end of turn message to all connected players.
     * The message contains the updated model view and information to continue the game.
     * @param event The end of turn event.
     * @param playerNickname The nickname of the next connected player.
     * @param newValue The updated model view.
     * @throws IOException If an I/O error occurs while sending the end of turn message.
     */

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        System.out.println("FINE TURNO");

        ModelView model=(ModelView) newValue;
        BoardBoxView[][] b= model.getBoardView();
        System.out.println("END TURN  GAME LOBBY 7,3 "+b[7][3].getType()+" 7 4 "+b[7][4].getType()+" 7 5  "+b[7][5].getType());
        Boolean[] activePlayers=model.getActivePlayers();
        for(PlayerPointsView nickname: model.getPlayerPoints()){
            if(activePlayers[model.getIntegerValue(nickname.getNickname())]){
                getGameLobby().sendMessageToSpecificPlayer(creationMessageEndTurn(nickname.getNickname(),model),nickname.getNickname()) ;
            }
        }
        getGameLobby().getGameLobbyInfo().setGameLobbyState(getGameLobby());
        SaveGame.saveGame(getGameLobby().getGameLobbyInfo());
        System.out.println("FINE TURNO TOTALE");
    }

    /**
     * Creates the end of turn message.
     * @param nickname The message addressee.
     * @param modelView The updated model view.
     * @return The end of turn message.
     */
    public Message creationMessageEndTurn(String nickname,ModelView modelView){
        BoardBoxView[][] boardView= modelView.getBoardView();
        MessageHeader header=new MessageHeader(MessageType.DATA,nickname);
        MessagePayload payload=new MessagePayload(TurnPhase.END_TURN);
        payload.put(Data.NEW_BOARD,boardView);
        payload.put(Data.NEXT_PLAYER,modelView.getTurnNickname());
        payload.put(Data.TOKEN,modelView.getToken());
        payload.put(Data.POINTS,modelView.checkWinner());
        payload.put(Data.COMMON_GOAL, modelView.getCommonGoalView());
        payload.put(Data.PERSONAL_POINTS,modelView.getPersonalPoint(nickname));
        payload.put(Data.WHO_CHANGE,modelView.findPreviousPlayer().getNickname());
        Message m=new Message(header,payload);
        return m;
    }
}
