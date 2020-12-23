import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    int m;     //  int
    double[] x;      //  d

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        this.m = m;
        x = new double[m];

        for (int t = 0; t < m; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int i = StdRandom.uniform(0, n);
                int j = StdRandom.uniform(0, n);
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                }
            }
            x[t] = (1.0*p.numberOfOpenSites())/(n*n);


        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - (1.96*stddev()/Math.sqrt(m));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + (1.96*stddev()/Math.sqrt(m));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, m);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}

