package it.polimi.ingsw.view;

//TODO will change

public interface ClientInterface  {


    ClientView getClientView();
    void waitingRoom();
    void displayToken(int num, String nickname);
    void askCoordinates();
    void askOrder() ;
    void askColumn() ;
    void displayError(String error);
    void displayMessage(String error);
    void askNicknameAndConnection();
    void askLobbyDecision();
    void endGame(int[] personalPoints,String playerBookshelfFull);
    void onlyPlayer();



}
