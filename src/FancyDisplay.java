import java.util.Scanner;

//
// A fancy display (motivated by Hollister) that uses ANSI
// escape codes to provide for a better looking display.
//
// The following web page will explain ANSI control codes in more
// detail.
//
// https://en.wikipedia.org/wiki/ANSI_escape_code
//
public class FancyDisplay implements IDisplay {
    //
    // The scanner for human input when needed
    //
    private Scanner sc_ ;

    //
    // Stored values for the number of games won and tied.  This is
    // required because we want to show at a point in time other than when
    // the showTotal() method is called.  In showTotal() we just store the 
    // values and then display them when we update the board display.
    //
    private int xwon_ ;
    private int owon_ ;
    private int tied_ ;

    //
    // A set of ANSI control coles
    //
    private final String cls_ = "\033[2J";                      // Clear the screen
    private final String gotopos_ = "\033[%d;%dH" ;             // Goto row and column (all 1 based, not 0)
    private final String clearline_ = "\033[2K" ;               // Clear the complete line the cursor is on
    private final String green_ = "\033[0;32m" ;                // Display all subsequent text as green
    private final String blue_ = "\033[0;34m" ;                 // Display all subsequent text as blue
    private final String white_ = "\033[0;37m" ;                // Display all subsequent text as white
    private final String yellow_ = "\033[1;33m" ;               // Display all subsequent text as yellow
    private final String red_ = "\033[0;31m" ;                  // Display all subsequent text as red

    //
    // Unicode characters for drawing lines
    //
    private String horizline = "\u2550" ;
    private String intersect = "\u256C" ;
    private String vertline = "\u2551" ;

    //
    // Create the display object
    //
    public FancyDisplay(boolean lines) {
        //
        // The scanner for human input
        //
        sc_ = new Scanner(System.in);

        xwon_ = 0 ;
        owon_ = 0 ;
        tied_ = 0 ;

        if (!lines) {
            horizline = "=" ;
            intersect = "=" ;
            vertline = "|" ;
        }
    }

    //
    // Show who won the game (or a tie if that happened)
    //
    // Arguments:
    //    info - the information about who won the game
    //
    public void showWon(Board.GameWonInfo info) {
        //
        // If X or O won the game, highlight where they won on the board in yellow
        //
        if (info.who != GamePiece.EMPTY) {
            for(int i = 0 ; i < info.where.length ; i++) {
                putsquare(info.where[i].Row, info.where[i].Col, info.who.toString(), yellow_);
            }
        }
        else {

        }

        //
        // Move down below the board and print what happened (who won)
        //
        gotopos(12, 1) ;
        clearline();
        if (info.who == GamePiece.EMPTY) {
            System.out.println("It was a tie") ;
        }
        else {
            System.out.println("Player '" + info.who.toString() + "' won");
        }

        //
        // Finally wait for human input before we move to the next game
        //
        gotopos(14, 1) ;
        System.out.println("Hit enter to continue") ;

        //
        // We call next line twice because the first time will just consume the 
        // new line from the input where the latest row and column were input.
        //
        sc_.nextLine() ;
        sc_.nextLine() ;
    }

    //
    // Show the current total number of games won and tied by printing a message
    // to the console.  Note, the done value is false for all but the last game.  If you
    // only want to show the total at the end of all games, this value can be used to
    // determine this case.
    //
    // Here we just store the values as they are displayed else where in the code
    //    
    // Arguments:
    //    done - if true, all games requested are done, if false, we are in the middle of the games
    //    xwon - the number of games won by X
    //    owon - the number of games won by O
    //    tied - the number of games that were tied
    //
    public void showTotal(boolean done, int xwon, int owon, int tied) {
        xwon_ = xwon ;
        owon_ = owon ;
        tied_ = tied ;
    }

    //
    // Display the stored total wins data that was previously stored.
    //
    private void displayTotal() {
        gotopos(1, 20) ;
        System.out.printf("X: %d, O: %d, tied: %d\n", xwon_, owon_, tied_);
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
            // Move the cursor to the line where we get input and clear the line
            //
            gotopos(11, 1);
            clearline() ;

            //
            // Print a message prompting for new row and column values
            //
            System.out.print("Enter row and column for you move (seperated by a space): ");
            try {
                //
                // If the input is good, create the board position object and break out of the loop
                // to return the value.
                //
                int row = sc_.nextInt() ;
                int col = sc_.nextInt() ;

                pos = new BoardPosition(row, col) ;

                if (pos.isValid())
                    break; 

                displayMessage("The row and column values must be between 0 and 2");
            }
            catch(Exception ex) {
                //
                // Display that the data was not good
                //
                displayMessage("The input you provided was invalid.") ;
                sc_.nextLine();
                continue ;
            }
        }   
        return pos ;
    }

    //
    // Clear the screen
    //
    private void clear() {
        System.out.print(cls_);
    }

    //
    // Turn all subsequent text white
    //
    private void white() {
        System.out.print(white_) ;
    }

    //
    // Turn all subsequent text red
    //    
    private void red() {
        System.out.println(red_);
    }

    //
    // Clear the current line (line containing the cursor)
    //     
    private void clearline() {
        System.out.print(clearline_) ;
    }

    //
    // Go to the given position on the screen
    //
    // Arguments:
    //    row - the row on the screen, the first row is 1
    //    col - the column on the screen, the first column is 1
    //
    private void gotopos(int row, int col) {
        String str = String.format(gotopos_, row, col) ;
        System.out.print(str) ;
    }

    //
    // Put a square on the board
    //
    // Arguments:
    //    row - the row on the board, 0 - 2
    //    col - the column on the board, 0 - 2
    //    sq - the charcter to draw (either 'X' or 'O')
    //    color - the color of the character
    //    
    private void putsquare(int row, int col, String sq, String color) {
        gotopos(row * 2 + 5, col * 4 + 6);
        System.out.print(color) ;
        System.out.print(sq);
        white() ;
    }

    //
    // Print a message stating we are starting a new game
    //
    // Arguments:
    //    which - which game are we on (1 - N)
    //    count - the total number of games requested
    // 
    public void newGame(int which, int count) {
        clear() ;
        gotopos(1, 1) ;
        System.out.println("Game " + which + " of " + count);
        inititalBoardDisplay();
        displayTotal();
    }

    //
    // If true, this tells the GameMgr that we want to updated the board
    // on every move. We return true so the display is updated every turn
    //    
    public boolean alwaysUpdateBoard() {
        return false ;
    }

    //
    // Display a message to the user.  Row 13 is for user messages.  New user messages will
    // overwrite previous user messages but this is ok for this game.
    //
    // Arguments:
    //    msg - the message to display
    //    
    public void displayMessage(String msg) {
        gotopos(13, 1) ;
        clearline() ;
        red() ;
        System.out.println(msg) ;
        white() ;
    }

    //
    // Update the board display to reflect state of the board given.  The strategy
    // for this display class is to just update the any of the nine squares that contain
    // game pieces.
    //
    // Arguments:
    //    b - object reflecting the current state of the board
    //
    public void updateBoardDisplay(Board b) {
        for(int row = 0 ; row < 3 ; row++) {
            for(int col = 0 ; col < 3 ; col++) {
                GamePiece p = b.getPiece(new BoardPosition(row, col)) ;
                if (p != GamePiece.EMPTY) {
                    String color = blue_ ;
                    if (p == GamePiece.O)
                        color = green_ ;
                    putsquare(row, col, p.toString(), color) ;
                }
            }
        }
    }

    //
    // Draw the initial board on the screen at the start of a new game
    // after the screen is cleared.
    //
    private void inititalBoardDisplay() {
        String horizsq = horizline + horizline + horizline  ;
        String horiz = "    " + horizsq + intersect + horizsq + intersect + horizsq ;
        System.out.println("        col") ;
        System.out.println("     0   1   2") ;
        System.out.println("row") ;
        for(int row = 0 ; row < 3 ; row++) {
            if (row == 1 || row == 2) {
                System.out.println(horiz) ;
            }
            System.out.printf("%-4d", row) ;
            for(int col = 0 ; col < 3 ; col++) {
                if (col != 0) {
                    System.out.print(vertline) ;
                }
                System.out.print("   ") ;
            }

            System.out.println() ;
        }
    }    
}
