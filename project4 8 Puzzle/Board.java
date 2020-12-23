import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    int[][] tiles; // create a 2d int array,image it is a game board
    int n; // the size of board, nxn, like 3x3, 4x4 etc
    int hamming;  // the sum of each number at the wrong location
    int manhattan; // the sum of each number of how many steps need to move to a correct location
    int blankPos; // the location of blank square


    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        int cnt = 1;
        this.tiles = tiles;
        n = tiles.length;
        //   this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cnt != tiles[i][j]) {
                    hamming++;
                    int tarI = (tiles[i][j] - 1) / n; // get the target row tarI
                    int tarJ = (tiles[i][j] - 1) % n; // get the target column tarJ which the
                    // location of the number should be
                    if (tiles[i][j] != 0) { // remove 0
                        manhattan += Math.abs(tarI - i) + Math.abs(tarJ - j); // calculate how
                    }
                    // many
                    // moves to be the correct location
                }
                cnt++;
                if (tiles[i][j] == 0) {
                    blankPos = i*n + j + 1;
                }
            }
        }
        hamming--;
    }
        // Returns the size of this board.
    public int size() {
        return n;
    }

        // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return this.tiles[i][j];
    }

        // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return this.hamming;
    }

        // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return this.manhattan;
    }

        // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        if (hamming == 0 || manhattan == 0) {
            return true;
        }
        return false;
    }
    // check if isSolvable, if yes return true, else return false
    public boolean isSolvable() {

        Integer[] oneDarray = new Integer[n * n - 1];
        int index = 0, blankrow = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    oneDarray[index] = tiles[i][j]; // transerfer 2d array to 1d array
                    index++;
                } else {
                    blankrow = i;
                }
            }
        }
        long inversions = Inversions.count(oneDarray); // count inversions number
        long sum = -1;
        if (n % 2 == 0) { // even board size
            sum = blankrow + inversions;
            if (sum % 2 == 0) {  // if sum is even number, can not solve
                return false;
            }
        } else {  // odd board size
            sum = inversions;
            if (sum % 2 != 0) { // if inversions is odd number, can not solve
                return false;
            }
        }
        return true;
    }

        // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> q = new LinkedQueue<Board>();
        int blankI = -1;
        int blankJ = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                    break;
                }
            }
        }

        if (blankI + 1 < n) { // south
            int[][] copy = cloneTiles();
            int temp = copy[blankI + 1][blankJ];
            copy[blankI + 1][blankJ] = 0;
            copy[blankI][blankJ] = temp;
            q.enqueue(new Board(copy));
        }

        if (blankI > 0) { // north
            int[][] copy = cloneTiles();
            int temp = copy[blankI - 1][blankJ];
            copy[blankI - 1][blankJ] = 0;
            copy[blankI][blankJ] = temp;
            q.enqueue(new Board(copy));
        }
        if (blankJ + 1 < n) { // east
            int[][] copy = cloneTiles();
            int temp = copy[blankI][blankJ + 1];
            copy[blankI][blankJ + 1] = 0;
            copy[blankI][blankJ] = temp;
            q.enqueue(new Board(copy));
        }

        if (blankJ > 0) { // west
            int[][] copy = cloneTiles();
            int temp = copy[blankI][blankJ - 1];
            copy[blankI][blankJ - 1] = 0;
            copy[blankI][blankJ] = temp;
            q.enqueue(new Board(copy));
        }

        return q;
    }


        // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Board otherBoard = (Board) other;
        if (this.n != otherBoard.n) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != otherBoard.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

        // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

        // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
