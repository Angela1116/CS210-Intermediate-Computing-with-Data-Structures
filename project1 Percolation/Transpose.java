import stdlib.StdArrayIO;

public class Transpose {
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        double[][] x = StdArrayIO.readDouble2D();
        StdArrayIO.print(transpose(x));
    }

    // Returns a new matrix that is the transpose of x.
    private static double[][] transpose(double[][] x) {
        // Create a new 2D matrix t (for transpose) with dimensions n x m, where m x n are the
        // dimensions of x.
        if (x.length < 0 || x[0].length < 0) {
            return x;
        }
        int m = x.length;
        int n = x[0].length;
  //      if(m < 0 || n <0) return x;

        double [][] t = new double[n][m];
      //  if(m < 0 || n <0) return t;
        if (m < 0 || n < 0) {
            return t;
        }
        // For each 0 <= i < m and 0 <= j < n, set t[j][i] to x[i][j].
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                t[j][i] = x[i][j];
            }
        }

        // Return t.
        return t;
    }
}
