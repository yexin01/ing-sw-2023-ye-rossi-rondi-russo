package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.Random;

public class CommonGoalCard3 extends CommonGoalCard{
    /*
      Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo
      (non necessariamente come mostrato in figura).
      Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf){
        int N=6, M=5;
        int goals,near,oldnear;
        int newi,newj;
        int [][] mat = { //dovrei averne 3
                { 1, 1, 3, 4, 5 },
                { 1, 3, 4, 5, 6 },
                { 1, 2, 3, 4, 5 },
                { 2, 3, 4, 5, 5 },
                { 1, 6, 3, 4, 5 },
                { 6, 6, 6, 3, 4 }
        };
        int [][] checkable = {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
        };
        ArrayList<Integer> posgoal = new ArrayList<>(); // un array (x,y,x,y ecc) per salvarmi man mano le posizioni di quelli che sto guardando e solo
        // se troverò un goal allora segno come uncheckable quelli appena trovati e quelli vicini a questi
        int x; // lo uso per spostarmi sulla matrice posgoal

        // stampo matrice
        System.out.println("Matrice: ");
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println(" ");
        }

        //controllo il goal
        goals=0;
        for(int i=0; i<N-1 && goals<4; i++){
            for(int j=0; j<M-1 && goals<4; j++){

                while(checkable[i][j]==0 && (j<M-1)){
                    j++;
                }
                checkable[i][j]=0; //segno come uncheckable quello su cui sono
                posgoal.add(i);
                posgoal.add(j);
                x=0;
                newi=i;
                newj=j;
                near=2;
                do{
                    oldnear=near;
                    posgoal=checknear(posgoal,mat,newi,newj,N,M);
                    near = posgoal.size();
                    newi=posgoal.get(near-2);
                    newj=posgoal.get(near-1);
                    x++;
                }while( (near<8 && (i<N-1)&&(j<M-1) ) && near>oldnear );

                if(near>=8){
                    goals++;
                    //metto uncheckable quelli del gruppo trovato e quelli vicini
                    x=0;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1), N,M);
                    x=x+2;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1), N,M);
                    x=x+2;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1), N,M);
                    x=x+2;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1), N,M);
                }

                for(int s=near-1; s>=0; s--){
                    posgoal.remove(s);
                }
            }
        }

        System.out.println("gruppi da 4 adiacenti trovati: "+goals+" o più");
        if(goals>=4){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList checknear (ArrayList posgoal, int [][] mat, int a, int b, int N, int M){
        //dato il vet posgoal, controlla sotto e di fianco e restituisce le posizioni di dove ha trovato quelli uguali a quello della mat che stavo controllando
        if(a+1<N && mat[a][b]==mat[a+1][b]){
            posgoal.add(a+1);
            posgoal.add(b);
        }
        if(b-1>=0 && mat[a][b]==mat[a][b-1]){
            posgoal.add(a);
            posgoal.add(b-1);
        }
        if(b+1<M && mat[a][b]==mat[a][b+1]){
            posgoal.add(a);
            posgoal.add(b+1);
        }
        return posgoal;
    }

    public int [][] allNearUncheckable (int [][] checkable, int x, int y, int N, int M){
        checkable[x][y]=0;
        if(x-1>=0){
            checkable[x-1][y]=0;
        }
        if(x+1<N){
            checkable[x+1][y]=0;
        }
        if(x-1>=0 && y-1>=0){
            checkable[x-1][y-1]=0;
        }
        if(x-1>=0 && y+1<M){
            checkable[x-1][y+1]=0;
        }
        if(y-1>=0){
            checkable[x][y-1]=0;
        }
        if(y+1<M){
            checkable[x][y+1]=0;
        }
        if(x+1<N && y-1>=0){
            checkable[x+1][y-1]=0;
        }
        if(x+1<N && y+1<M){
            checkable[x+1][y+1]=0;
        }
        return checkable;
    }




    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
