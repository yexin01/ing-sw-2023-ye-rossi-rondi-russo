package it.polimi.ingsw.model;

public class CommonGoalCard12 extends CommonGoalCard{
    /*
        Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna
        a sinistra o a destra, ogni colonna successiva deve essere formata da una tessera in più.
        Le tessere possono essere di qualsiasi tipo.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf) {
        BookshelfBox[][] mat = bookshelf.getMatrix();
        boolean verified;
        int i;
        // lavoro solo con occupied, non mi interessa che tipo di tile c'è
        int [][] occupied = { //da adattare con getoccupied
                { 0, 0, 0, 0, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 1, 1, 1 },
                { 0, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
        };
        // stampo matrice
        System.out.println("Matrice occupied: ");
        for (int x=0; x<mat.length; x++) {
            for (int j=0; j<mat[x].length; j++) {
                if(mat[x][j].getItemTile()!=null){
                    System.out.println("1 ");
                }else{
                    System.out.println("0 ");
                }
            }
            System.out.println(" ");
        }
        //controllo il goal
        //caso decrescente
        i=0;
        if (mat[0][0].getItemTile()==null){
            i++;
        }
        verified=true;
        for (int a=0; a<mat[i].length && verified; a++) {
            if (mat[i+a][a].getItemTile()==null){
                verified=false;
            } else if (a+1<mat[i].length && mat[i+a][a+1].getItemTile()!=null){
                verified=false;
            }
        }
        if(verified){
            System.out.println("Trovate colonne decrescenti.");
            return true;
        }
        //caso crescente
        i=0;
        if (mat[0][mat[i].length-1].getItemTile()==null){
            i++;
        }
        verified=true;
        for (int a=mat[i].length-1; a>=0 && verified; a--) {
            if (mat[i-a+mat[i].length-1][a].getItemTile()==null){
                verified=false;
            } else if (a-1>=0 && mat[i-a+mat[i].length-1][a-1].getItemTile()!=null) {
                verified=false;
            }
        }
        if (verified){
            System.out.println("Trovate colonne crescenti.");
            return true;
        }
        System.out.println("Non trovate colonne crescenti o decrescenti.");
        return false;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
