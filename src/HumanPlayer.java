//
// A human player object
//
public class HumanPlayer extends Player {

    //
    // Create the human player
    //
    // Arguments:
    //    p - the game piece for this player (X or O)
    //
    public HumanPlayer(GamePiece p) throws IllegalArgumentException {
        super(p) ;
    }

    //
    // If true, we want to show the board before nextMove() is called.  Since a human player
    // will want to see the board to see the computer's move, we return true.
    //
    public boolean showBoard() {
        return true ;
    }

    //
    // Get the next move from the human player.
    //
    // Arguments:
    //    board - the board to evaluate for the next move
    //
    public BoardPosition nextMove(Board board) {
        BoardPosition pos = null ;

        while (true) {
            //
            // Ask the display module for a board position
            //
            pos = getDisplay().getMove(board);

            if (board.getPiece(pos) == GamePiece.EMPTY) {
                break ;
            }

            getDisplay().displayMessage("That square is already taken") ;
        }

        return pos ;
    }
}
