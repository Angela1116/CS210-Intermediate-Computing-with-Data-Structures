import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

public class Percolation {
    int n;   //  int
    boolean [][] open; //  boolean
    int openSites;  //  opensite
    WeightedQuickUnionUF uf; //  union
    WeightedQuickUnionUF uf2; // union
    int source = 0;  //  top
    int sink; //  bottom

    // Constructs an n x n percolation system, with all sites blocked.
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }

        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
        uf = new WeightedQuickUnionUF(n*n + 2);
        uf2 = new WeightedQuickUnionUF(n*n+2);
        sink = n*n +1;

        for (int j = 0; j < n; j++) {
            uf.union(source, encode(0, j)); //  top

            uf2.union(source, encode(0, j));
        }
        for (int i = 0; i < n; i++) {
            uf.union(encode(n-1, i), sink);
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || j < 0 || i > n-1 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            open[i][j] = true;
            openSites++;
            if (i - 1 >= 0 && open[i - 1][j]) {
                uf.union(encode(i, j), encode(i - 1, j));
                uf2.union(source, encode(i - 1, j));
            }
            if (i + 1 < n && open[i + 1][j]) {
                uf.union(encode(i, j), encode(i + 1, j));
                uf2.union(source, encode(i + 1, j));
            }
            if (j - 1 >= 0 && open[i][j - 1]) {
                uf.union(encode(i, j), encode(i, j - 1));
                uf2.union(source, encode(i, j - 1));
            }

            if (j + 1 < n && open[i][j + 1]) {
                uf.union(encode(i, j), encode(i, j + 1));
                uf2.union(source, encode(i, j + 1));
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || j < 0 || i > n-1 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || j < 0 || i > n-1 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j] && uf2.connected(encode(i, j), source);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return uf.connected(source, sink);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return i*n + j + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}
