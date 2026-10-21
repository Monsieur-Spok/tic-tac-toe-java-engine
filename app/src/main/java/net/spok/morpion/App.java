package net.spok.morpion;





public class App {


    public String getGreeting() {
        return "The app is launching...";
    }


    public static void main(String[] args) {
        int[][] board = {{1, 1, 1}, 
                         {0, 0, 1}, 
                         {10, 1, 1}};
        
        //board test = new board(board);      //no need for the board argument

        game game = new game(true, false);

        game.start();

        
    }
}
