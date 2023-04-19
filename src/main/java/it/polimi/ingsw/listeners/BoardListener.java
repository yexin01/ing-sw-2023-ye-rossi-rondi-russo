package it.polimi.ingsw.listeners;


import it.polimi.ingsw.model.Board;


public class BoardListener implements EventListener{
/*
    @Override
    public void onEvent(EventType eventType, EventValue<Board> eventValue, String nickname) {
        //Board newBoard = eventValue.getValue();
        System.out.println(nickname + " changed Board");
        System.out.println(eventValue.getValue().getMatrix());

    }
 */


    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        Board newBoard=(Board) newValue;
        System.out.println(nickname +" changed Board");
        newBoard.printMatrix();

    }
}

    /*

}

/*
    @Override
    public void onEvent(EventType eventType, EventValue eventValue, String nickname) {
        Board newBoard = (Board) eventValue.getValue();
        System.out.println(nickname + " changed Board");
        newBoard.printMatrix();
    }
}
/*
public class BoardListener implements EventListener{
    /*
    @Override
    public void onEvent(String eventName, Object newValue, String nickname) {
        Board newBoard=(Board) newValue;
        System.out.println(nickname +" changed Board");
        newBoard.printMatrix();

    }


    @Override
    public void onEvent(EventType eventType, EventValue eventValue, String playerName) {
        Board newBoard=(Board) eventValue.getValue();
        System.out.println(eventValue +" changed Board");
        newBoard.printMatrix();
    }
}*/