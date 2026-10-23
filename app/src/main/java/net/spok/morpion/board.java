package net.spok.morpion;
import java.util.Map;
import java.util.HashMap;


public class board {



    private int[][] morpion = {{1,1,1},        //0 for a O and 10 for an X everything else for an empty case
                               {1,1,1},
                               {1,1,1}};


    board(){
            //pointless but i can't remove it
    }
    board(int [][] board){
        for (int c = 0; c < 3; c++) {
            this.morpion[c] = board[c].clone();
        } 
    }


    public void set(int[][] board){
        for (int c = 0; c < 3; c++) {   //why c?
            this.morpion[c] = board[c].clone();
        }
    }
    public void set(int x, int y, int value){
        this.morpion[x][y] = value;
    }

    public int get(int x, int y) {
        return this.morpion[x][y];
    }


    public void print(){
            String stringBoard[][] = {{" "," "," "},{" "," "," "},{" "," "," "}};


        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (morpion[i][j] == 0){
                    stringBoard[i][j] = "O";
                }else if (morpion[i][j] == 10){
                    stringBoard[i][j] = "X";
                }
            }
        }

        System.out.println(stringBoard[0][0] + "|" + stringBoard[0][1] + "|" + stringBoard[0][2]);
        System.out.println("-+-+-");
        System.out.println(stringBoard[1][0] + "|" + stringBoard[1][1] + "|" + stringBoard[1][2]);
        System.out.println("-+-+-");
        System.out.println(stringBoard[2][0] + "|" + stringBoard[2][1] + "|" + stringBoard[2][2]);

    }
    /* LOG 
    private void printIndex(int index){         //LOG
        String stringBoard[][] = {{" "," "," "},{" "," "," "},{" "," "," "}};


        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (morpion[i][j] == 0){
                    stringBoard[i][j] = "O";
                }else if (morpion[i][j] == 10){
                    stringBoard[i][j] = "X";
                }
            }
        }
        
        for (int i = 0; i < index; i++) {
            System.out.print("    ");
        }
        System.out.println(stringBoard[0][0] + "|" + stringBoard[0][1] + "|" + stringBoard[0][2]);
        for (int i = 0; i < index; i++) {
            System.out.print("    ");
        }
        System.out.println("-+-+-");
        for (int i = 0; i < index; i++) {
            System.out.print("    ");
        }
        System.out.println(stringBoard[1][0] + "|" + stringBoard[1][1] + "|" + stringBoard[1][2]);
        for (int i = 0; i < index; i++) {
            System.out.print("    ");
        }
        System.out.println("-+-+-");
        for (int i = 0; i < index; i++) {
            System.out.print("    ");
        }
        System.out.println(stringBoard[2][0] + "|" + stringBoard[2][1] + "|" + stringBoard[2][2]);

    }
    */


    public int freeSpace() { // passed to public because i need to access it in the game thing for it to be easier to code
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.morpion[i][j] == 1) {
                    counter++;
                }
            }
        }

        return counter;
    }


    public boolean isWining(int side){
        int[][] corners = {{0,0},{0,2},{2,0},{2,2}};
        int[] startCorner = {3,3};
        int trueCorner = 0;

        for (int[] i : corners){
            if (morpion[i[0]][i[1]] == side){     //check the corner
                startCorner = i;
                trueCorner++;
            }
        }

        if (trueCorner == 0 && morpion[1][1] != side) {   //si y'a pas de coin ni de milieu
            return false;


        } else if (trueCorner <= 1){        // si y'a le mid mais pas les coins on regarde si on a les truc à 90°
            return (morpion[1][1] == side && ((morpion[0][1] == side && morpion[2][1] == side) || (morpion[1][0] == side && morpion[1][2] == side)));
        
        
        }else{
            switch (trueCorner) {
                case 2:     //unoptimize shit but the other block are kinda great with almost only one check for every part     work with everything tho
                    if (morpion[1][1] == side && ((morpion[0][0] == side && morpion[2][2] == side) || (morpion[0][2] == side && morpion[2][0] == side)) ){
                        return true;
                    }
                    int[] counter = {0,0, 0, 0, 0, 0};

                    for (int i = 0; i < 3; i++){
                        for (int j = 0; j < 3; j++){
                            if(morpion[i][j] == side){
                                counter[i]++;
                                counter[j+3]++;
                            }
                        }                        
                    }
                    for (int i : counter){
                        if (i == 3){
                            return true;
                        }
                    }


                    return false;

                case 3:
                    int[] falseCorner = {3,3};
                    int[][] loop = getOtherThree(startCorner);
                    for (int[] i : loop){
                        if (morpion[i[0]][i[1]] == side && morpion[ (i[0]+startCorner[0])/2 ] [ ((i[1]+startCorner[1])/2) ] == side){
                            return true;
                        }else if (morpion[i[0]][i[1]] != side){
                            falseCorner = i;
                        }
                    }
                    if (falseCorner[0] == startCorner[0]){
                        int reverse = startCorner[0] == 2 ? 0 : 2;
                        return morpion[reverse][1] == side;
                    }else if(falseCorner[1] == startCorner[1]){
                        int reverse = startCorner[1] == 2 ? 0 : 2;
                        return morpion[1][reverse] == side;
                    }
                    return false;

                case 4:
                    return morpion[1][0]==side || morpion[1][2]==side || morpion[0][1]==side || morpion[2][1]==side || morpion[1][1]==side;
            
                default:
                    break;
            }
        }



        return false;       // on sait jamais
    }

    //help for iswinning right above
    private static int[][] getOtherThree(int[] coordinate){      //get the 3 adjacent block from the corner
        int X = coordinate[1] == 0 ? 2 : 0;     //on inverse des choses
        int Y = coordinate[0] == 0 ? 2 : 0;


        int [][] array =  {{coordinate[0], X},{Y, X}, {Y, coordinate[1]}};      // j'ai pas le droit de definir un array multi dimenesions dans un return ni de mettre des ternary operator dans la definition d'un array

        return array;
    }


    public void generate(){int[][] allBoard = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};     generate(0, 0, 0, allBoard);   }
    private void generate(int row, int col, int xCount, int[][] allBoard){      //the test is intended for debugging only and is mostly vibe coded
        if (xCount > 5) {
            return; // Exceeds maximum X's
        }


        if (row == 3) {
            if (xCount >= 1 && xCount <= 5) {
                // Save a deep copy of the board
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++){
                        this.morpion[i][j] = allBoard[i][j];
                    }
                }
                
                System.out.println(this.isWining(10));
                this.print();
                System.out.println("");
            }
            return;
        }


        int nextRow = (col == 2) ? row + 1 : row;
        int nextCol = (col == 2) ? 0 : col + 1;

        // Option 1: Leave the cell empty (1)
        allBoard[row][col] = 1;
        generate(nextRow, nextCol, xCount, allBoard);

        // Option 2: Place an X (10)
        allBoard[row][col] = 10;
        generate(nextRow, nextCol, xCount + 1, allBoard);

        // Backtrack: Reset the cell to 1
        allBoard[row][col] = 1;


    }
    

    private int[][] getLegalMoves(){         //30 min pour cette merde btw
        int freeSpace = this.freeSpace();
        int[][] moves = new int[freeSpace][2] ;
        int i =0 ;
        
        for (int k = 0; k < 9; k++) {
            if (this.morpion[k/3][k%3]!=10 && this.morpion[k/3][k%3]!=0) {
                moves[i][0] = k/3;
                moves[i][1] = k%3;
                i++;
            }   

        }
        return moves;
    }

    /*log used in best move computation
    public void log(int side, int freeSpace, int score){
        this.printIndex(4-freeSpace);       //LOG
            for (int i = 0; i < 4-freeSpace; i++) {         //LOG
                System.out.print("    ");
            }
            System.out.println(score +" "+side);
            System.out.println("");
    }
    */

    public board[] rotate(){
        board[] univers = new board[4];     //univers est une liste qui contient les 4 positions que peut prendre le morpion
        univers[0] = new board(this.morpion);
        for (int i = 1; i < 4; i++) {
            univers[i] = new board();

            univers[i].set(0,0, univers[i-1].morpion[2][0]);        //on rotate les coins
            univers[i].set(0,2, univers[i-1].morpion[0][0]);
            univers[i].set(2,2, univers[i-1].morpion[0][2]);
            univers[i].set(2,0, univers[i-1].morpion[2][2]);

            univers[i].set(0, 1, univers[i-1].morpion[1][0]);       //on rotate les angles
            univers[i].set(1, 2, univers[i-1].morpion[0][1]);
            univers[i].set(2, 1, univers[i-1].morpion[1][2]);
            univers[i].set(1, 0, univers[i-1].morpion[2][1]);

            univers[i].set(1, 1, univers[i-1].morpion[1][1]);       //et le centre qui ne rotate pas mais bon il existe qd meme
        }
        return univers;
    }




    //  The important code
    public void bestMove(int side){

        //des variables 
        int[][] moves = this.getLegalMoves();           //la liste des coups possibles
        int[] score = new int[moves.length];            //la liste des valeurs pour chaque coup
        board computation = new board(this.morpion);    //une nouvelle planche de jeu pour pas avoir de pbm avec les addresses memoires

        //on itere sur tout les coups
        for (int i = 0; i < moves.length; i++) {
            computation.set(this.morpion);      //on joue le coup suivant sur une copie du morpion
            computation.set(moves[i][0], moves[i][1], side);

            Map<board, Integer> memo = new HashMap<>();

            score[i] = computation.bestMoveComputation(side == 10 ? 0 : 10, memo);
            //System.out.println(score[i]+ " score "+ i);       //LOG
        }

        int bestIndex = 0;      //on itere sur tout la liste de score pour trouver celui qui est le meilleur
        for (int i = 1; i < score.length; i++) {
            if (score[bestIndex] < score[i]) {
                bestIndex = i;  
            }
        }

        this.morpion[moves[bestIndex][0]][moves[bestIndex][1]] = side;
        this.print();
    }



    private int bestMoveComputation(int side, Map<board,Integer> memo){
        int freeSpace = this.freeSpace();
        
        //les cas par défaut
        if (this.isWining(side == 10 ? 0 : 10)){      //quelqu'un a gagné
            //log(side, freeSpace, (freeSpace+1));
            return (freeSpace+1);
        }        
        if (freeSpace == 0){                          //le morpion est rempli
            //log(side, freeSpace, 0);
            return 0;
        }

        if (memo.containsKey(this)){
            return memo.get(this);
        }


        //des variables 
        int[][] moves = this.getLegalMoves();       //la liste des coups possibles
        int[] movesValue = new int[moves.length];   //la liste des valeurs pour chaque coup
        board boardCopy = new board(this.morpion);  //une nouvelle planche de jeu pour pas avoir de pbm avec les addresses memoires

        for (int i = 0; i < movesValue.length; i++) {       //on itere dans tout l'array et on test toutes les possibilitées.
            boardCopy.set(this.morpion);
            boardCopy.set(moves[i][0], moves[i][1], side);
            
            movesValue[i] = boardCopy.bestMoveComputation(side == 10 ? 0 : 10, memo);     //RECURSIVITE ici
        }

        int result = movesValue[0];         //on selectionne la mois pure des options
        for (int i = 1; i < movesValue.length; i++) {
            if (result < movesValue[i]) {
                result = movesValue[i];
            }
        }

        board[] newMemoValue = this.rotate();
        for (board board : newMemoValue) {
            memo.put(board, -result);
        }

        //log(side, freeSpace, -result);
        return -result;
    }



}
