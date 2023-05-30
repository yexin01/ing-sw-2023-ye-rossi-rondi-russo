package it.polimi.ingsw.view;

//TODO will change

public interface ClientInterface  {


    ClientView getClientView();
    void waitingRoom() throws Exception;
    void displayToken(int num, String nickname);
    void askCoordinates() throws Exception;
    void askOrder() throws Exception;
    void askColumn() throws Exception;
    void displayError(String error);
    void displayMessage(String error);
    void askNicknameAndConnection() throws Exception;
    void askLobbyDecision() throws Exception;
    void endGame(int[] personalPoints,String playerBookshelfFull) throws Exception;
    void onlyPlayer();



}
