package net.spok.morpion;

import java.util.Scanner;

public class game {
    private board board = new board();

    private boolean joueur1 = true;         //true c'est un joueur false c'est un bot
    private boolean joueur2 = false;
    Scanner input = new Scanner(System.in);

    game (){
        // ...
    }
    game(boolean J1, boolean J2){
        joueur1 = J1;
        joueur2 = J2;
    }


    public void start(){
        int side = 10;
        boolean joueur = joueur1;
        board.print();

        
        while (!board.isWining(0) && !board.isWining(10) && board.freeSpace()!=0) {
            if (side == 10) {
                joueur = joueur1;
                System.out.println("tour du Joueur 1 : ");
            }else{
                joueur = joueur2;
                System.out.println("tour du Joueur 2 : ");
            }

            
            if (!joueur) {
                board.bestMove(side);
            } else{
                int coup;
                
                do{
                    coup = input.nextInt();
                }while ((coup < 1 || coup > 9) || board.get((coup-1)/3, (coup-1)%3)!=1);

                coup--;
                board.set(coup/3, coup%3, side);
                board.print();

            }

            side = (side == 10) ? 0 : 10;
            System.out.println("");


        }

        if(board.freeSpace()!=0){
            System.out.println("La team " + ((side==10) ? "X" : "O") + " a gagné.");
        }else{
            System.out.println("égalité");
        }

    }
}
