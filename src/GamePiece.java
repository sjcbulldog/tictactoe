
//
// This enumerated type reprents the the contents of a square on the
// board.  This enumerated type contains a single integer value.  This value
// was chosen carefully so that any line of squares that are filled with X or
// O will sum to -3 or 3.  Any line of squares that are have two game pieces and
// any empty spot, will sum to -2 or 2.
//
public enum GamePiece {
    X(1),                       // X game piece
    O(-1),                        // O game piece
    EMPTY(0);                   // EMPTY (no game piece)

    public final int value ;      // The numerical value for the game piece

    //
    // Constructor for a game piece
    //
    private GamePiece(int v) {
        this.value = v ;
    }
} ;
