import java.util.Scanner;

//
// A very dump console display that implements the IDisplay interface
//
public class ConsoleDisplay implements IDisplay {
    private Scanner sc_ ;                               // Scanner for human player input

    //
    // Create the display
    //
    public ConsoleDisplay() {
        sc_ = new Scanner(System.in);
    }

    //
    // Show who won (or tied) by printing a message to the console
    //
    // Arguments:
    //    info - information about who won and where
    //
    public void showWon(Board.GameWonInfo info, Board board) {
        if (info.who == GamePiece.EMPTY) {
            System.out.println("  It was a tie") ;
        }
        else {
            System.out.println("  Player '" + info.toString() + "' won");
        }
    }

    //
    // Show the current total number of games won and tied by printing a message
    // to the console.  Note, the done value is false for all but the last game.  If you
    // only want to show the total at the end of all games, this value can be used to
    // determine this case.
    //    
    // Arguments:
    //    done - if true, all games requested are done, if false, we are in the middle of the games
    //    xwon - the number of games won by X
    //    owon - the number of games won by O
    //    tied - the number of games that were tied
    //
    public void showTotal(boolean done, int xwon, int owon, int tied) {
        if (done) {
            System.out.println("------------------------------------------------------------") ;
            System.out.printf("X: %d, O: %d, tied: %d\n", xwon, owon, tied);
        }
    }

    //
    // Get the next move
    //
    // Arguments:
    //    board - the board to evaluate
    //
    public BoardPosition getMove(Board b) {
        BoardPosition pos = null ;

        while (true) {
            //
            // Print a message prompting the user
            //
            System.out.print("Enter row and column for you move (seperated by a space): ");
            try {
                //
                // Get the row and column
                //
                int row = sc_.nextInt() ;
                int col = sc_.nextInt() ;

                pos = new BoardPosition(row, col) ;
                break ;
            }
            catch(Exception ex) {
                System.out.println("The input you provided was invalid.") ;
                sc_.nextLine();
                continue ;
            }
        }   

        return pos ;
    }

    //
    // Print a message stating we are starting a new game
    //
    // Arguments:
    //    which - which game are we on (1 - N)
    //    count - the total number of games requested
    //    
    public void newGame(int which, int count) {
        System.out.println("Game " + which + " of " + count);
    }

    //
    // If true, this tells the GameMgr that we want to updated the board
    // on every move. We return false so the board is only updated when a 
    // player wants it updated.
    //
    public boolean alwaysUpdateBoard() {
        return false ;
    }

    //
    // Display a message to the user
    //
    // Arguments:
    //    msg - the message to display
    //
    public void displayMessage(String msg) {
        System.out.println(msg) ;
    }

    //
    // Update the board display to reflect state of the board given.  The strategy
    // for this display module is to just print the new board contents.
    //
    // Arguments:
    //    b - object reflecting the current state of the board
    //
    public void updateBoardDisplay(Board b) {
        System.out.println("        col") ;
        System.out.println("     0   1   2") ;
        System.out.println("row") ;
        for(int row = 0 ; row < 3 ; row++) {
            if (row == 1 || row == 2) {
                System.out.println("    -----------") ;
            }
            System.out.printf("%-4d", row) ;
            for(int col = 0 ; col < 3 ; col++) {
                if (col != 0) {
                    System.out.print("|") ;
                }

                String str = "   " ;
                switch(b.getPiece(new BoardPosition(row, col)))
                {
                    case X:
                        str = " X " ;
                        break ;
                    case O:
                        str = " O " ;
                        break;
                    case EMPTY:
                        str = "   " ;
                        break ;
                }

                System.out.print(str) ;
            }
            System.out.println() ;
        }
    }
}
