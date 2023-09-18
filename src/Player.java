
//
// This abstract class represents the base for an class that represents
// a player in the game.  There are expected to be other player classes that
// extend this class to implement specific behaviours as a player.
//
public abstract class Player {
    private GamePiece p_ ;
    private IDisplay display_ ;

    //
    // Create a new player
    //
    // Arguments:
    //    p - the game piece for this player
    //
    public Player(GamePiece p) throws IllegalArgumentException {
        if (p == GamePiece.EMPTY) {
            throw new IllegalArgumentException("invalid game piece value for a player") ;
        }        
        this.p_ = p ;
    }

    //
    // Return my game piece value
    //
    public GamePiece getMyGamePiece() {
        return this.p_ ;
    }

    //
    // Return my opponents game piece value
    //
    public GamePiece getOpponentGamePiece() {
        return (this.p_ == GamePiece.X) ? GamePiece.O : GamePiece.X ;
    }

    //
    // Set the display associated with this player.  This probably should be in 
    // the constructor.  However, I am too lazy to fix the initiailzation ordering
    // problem in the main class (GameMgr) argument processing.
    //
    // Arguments:
    //    disp - the display for the game
    //
    public void setDisplay(IDisplay disp) {
        display_ = disp ;
    }

    //
    // Get the display assocaited with this player
    //
    protected IDisplay getDisplay() {
        return display_ ;
    }

    //
    // Returns if the player requires the board to be shown before its move.
    // A computer player does not.
    //    
    public abstract boolean showBoard() ;

    //
    // Compute the next move for the computer player.  It is expected that the
    // player objects store NO game state and that the board object provided here
    // is sufficient to compute next moves.
    //
    // Arguments:
    //    board - the board to evalute for the next move
    //
    public abstract BoardPosition nextMove(Board board) ;

}
