import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    int moves; // minmum steps to move
    LinkedStack<Board> solution; // create a stack of type Board to put solution

    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {
        if (board == null) {
            throw new NullPointerException("board is null");
        }
        if (!board.isSolvable()) {
            throw new IllegalArgumentException("board is unsolvable");
        }
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(); //  creat a data structure MinPQ named pq
        pq.insert(new SearchNode(board, 0, null)); // insert SearchNode to pq
        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin(); // delete the smallest searchNode and assign to node
            if (node.board.isGoal()) { // check if current node is the goal board
                moves = node.moves;
                solution = new LinkedStack<Board>(); // creat a stack to save possible solution
                while (node.previous != null) {
                    solution.push(node.board); // put possible solution to stack
                    node = node.previous; // keep checking, so we need set node to previous one.
                }
                break;
            }
            for (Board neighbor : node.board.neighbors()) {
                Board prev = null; // second optimization(caching technique)
                if (node.previous != null) {
                    prev = node.previous.board;
                }
                if (prev == null || !neighbor.equals(prev)) { // critical optimization(avoid add
                    // same board twice)
                    pq.insert(new SearchNode(neighbor, node.moves + 1, node));
                }

            }
        }
    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        return moves;
    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        return solution;
    }

    // A data type that represents a search node in the grame tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {
        Board board; // create a Board`s  object name board
        int moves; // how many moves
        SearchNode previous; // a searchnode type of variable named previous

        // Constructs a new search node.
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            int thisPrio = this.board.manhattan() + this.moves;
            int otherPrio = other.board.manhattan() + other.moves;
            return thisPrio - otherPrio;
            // priority = manhattan + # of moves; calculate the priority and compare board`s
            // priority; and using the result to decide the shortest path.
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
