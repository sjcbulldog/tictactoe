//
// The interface a display object must implement to be a valid 
// display object.
//
public interface IDisplay {
    //
    // Inform the player we are starting a new game
    //
    // Arguments:
    //    which - which game are we on (1 - N)
    //    count - the total number of games requested
    // 
    void newGame(int which, int total) ;

    //
    // Update the board display to reflect state of the board given.  The strategy
    // for this display module is to just print the new board contents.
    //
    // Arguments:
    //    b - object reflecting the current state of the board
    //
    void updateBoardDisplay(Board b) ;

    //
    // Display a message to the user.  It is expected that only a single message
    // must be displayed at a time.
    //
    // Arguments:
    //    msg - the message to display
    //
    void displayMessage(String str) ;

    //
    // If true, this tells the GameMgr that we want to update the board
    // on every move.
    //
    boolean alwaysUpdateBoard() ;

    //
    // Tell the user who won (or tied)
    //
    // Arguments:
    //    info - information about who won and where
    //
    void showWon(Board.GameWonInfo won) ;

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
    void showTotal(boolean done, int xwon, int owon, int tied);

    //
    // Get the next move from a human player
    //
    // Arguments:
    //    board - the board to evaluate
    //
    BoardPosition getMove(Board b) ;
}
