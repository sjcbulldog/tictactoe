import java.util.List;
import java.util.Random;

//
// This class implements a computer player.  The computer player has various skill levels
// given by a single percentage.  A random number is generated between 0 and 1 and if it is
// below the computer player percentage, then the computer makes a smart move.  If not, the
// computer makes a random move.
//
// Note all percentages are between 0 and 1.
//
// For example, if the computer player percentage is 1.0, then all random numbers will be less
// than 1.0 and the computer player is an expert.
//
// Alternatively, if the computer player percentage is 0.3, then the computer player will make a
// smart move only 30% of the time and this player is a novice.
//
public class ComputerPlayer extends Player {
    private Random r_ ;                             // The random number generator for this player
    private double choose_best_ ;                   // The percentage below which the best move is chosen

    //
    // Create the computer player
    //
    // Arguments:
    //    p - X or O for this player
    //    best - below this percentage and the computer makes its best move
    //
    public ComputerPlayer(GamePiece p, double best) throws IllegalArgumentException {
        super(p) ;

        r_ = new Random() ;
        choose_best_ = best ;
    }

    //
    // Returns if the player requires the board to be shown before its move.
    // A computer player does not.
    //
    public boolean showBoard() {
        return false ;
    }

    //
    // Compute the next move for the computer player
    //
    // Arguments:
    //    board - the board to evalute for the next move
    //
    public BoardPosition nextMove(Board board) {
        BoardPosition ret = null ;

        double r = r_.nextDouble() ;
        if (r <= choose_best_) {
            //
            ret = smartMove(board) ;
        }
        else {
            ret = randomMove(board) ;
        }

        return ret ;
    }

    //
    // Compute a smart move by the computer player
    //
    // Arguments:
    //    board - the board to evaluate for the move
    //
    private BoardPosition smartMove(Board board) {
        BoardPosition pos = null ;

        //
        // Quick strategy evaluation.  If other player went first and the
        // middle is open, grab it.
        //
        if (board.totalPlayed() == 1 && board.getPiece(new BoardPosition(1, 1)) != GamePiece.EMPTY) {
            pos = new BoardPosition(0, 0) ;
            return pos ;
        }

        //
        // TODO: If we really want a smart player, we need to add more special cases in here.
        //

        //
        // Look for a move that wins the game
        //
        pos = board.hasTwo(getMyGamePiece()) ;
        if (pos != null)
            return pos ;

        //
        // Look for a move that blocks the opponent
        //
        pos = board.hasTwo(getOpponentGamePiece()) ;
        if (pos != null)
            return pos ;

        //
        // Pick a random place to play
        //
        List<BoardPosition> spots = board.findEmpty() ;
        int which = r_.nextInt(spots.size()) ;
        return spots.get(which) ;       
    }

    //
    // Pick a random move on the board
    //
    // Arguments:
    //    board - the board to evaluate
    //
    private BoardPosition randomMove(Board board) {
        //
        // Ask the board for a list of open slots
        //
        List<BoardPosition> spots = board.findEmpty() ;

        //
        // Pick on of them at random
        //
        int which = r_.nextInt(spots.size()) ;

        
        return spots.get(which) ;
    }
}
