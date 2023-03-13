package org.example;

public class Bookshelf_box {
    private Item_tile tile;
    private boolean occupied;



    public Item_tile getTile(){
        return tile;
    }

    public boolean getOccupied(){
        return occupied;
    }


    public void changeOccupied(){
        if(occupied=false)
            occupied=true;
        //aggiungere l'eccezione nel caso fosse già
        //occupata,significa che il metodo insert_column ha sbagliato la posizione
    }
}
