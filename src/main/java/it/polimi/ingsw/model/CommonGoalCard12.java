package it.polimi.ingsw.model;

public class CommonGoalCard12 extends CommonGoalCard{
    /*
        Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna
        a sinistra o a destra, ogni colonna successiva deve essere formata da una tessera in più.
        Le tessere possono essere di qualsiasi tipo.
    */

    @Override
    boolean checkGoal() {
        int N=6, M=5;
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
        System.out.println("Matrice: ");
        for (int x=0; x<N; x++) {
            for (int j=0; j<M; j++) {
                System.out.print(occupied[x][j] + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        //caso decrescente
        i=0;
        if (occupied[0][0]==0){
            i++;
        }
        verified=true;
        for (int a=0; a<M && verified; a++) {
            if (occupied[i+a][a]==0){
                verified=false;
            } else if (a+1<M && occupied[i+a][a+1]!=0){
                verified=false;
            }
        }
        if(verified){
            System.out.println("Trovate colonne decrescenti.");
            return true;
        }
        //caso crescente
        i=0;
        if (occupied[0][M-1]==0){
            i++;
        }
        verified=true;
        for (int a=M-1; a>=0 && verified; a--) {
            if (occupied[i-a+M-1][a]==0){
                verified=false;
            } else if (a-1>=0 && occupied[i-a+M-1][a-1]!=0) {
                verified=false;
            }
        }
        if(verified){
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
