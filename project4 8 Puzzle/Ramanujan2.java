import dsa.MinPQ;
import stdlib.StdOut;

public class Ramanujan2 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        MinPQ<Pair> pq = new MinPQ<Pair>();
        int c = 1;
        for (int i = 1; i*i*i < n; i++) {
            pq.insert(new Pair(i, i + 1));
        }

        Pair previous = new Pair(0, 0);
        while (!pq.isEmpty()) {

            Pair current = pq.delMin();
            if (previous.sumOfCubes == current.sumOfCubes && previous.sumOfCubes <= n) {
                c++;
                if (c == 2) {
                    StdOut.print(previous.sumOfCubes + " = " + previous.i + "^3 + " +
                            previous.j + "^3");
                }
                StdOut.print(" = " + current.i + "^3 + " + current.j + "^3");
            } else {
                if (c > 1) {
                    StdOut.println();
                    c = 1;

                }
            }

            previous = current;
            if (current.sumOfCubes < n) {
                pq.insert(new Pair(current.i, current.j + 1));
            }
        }
        if (c > 1) {
            StdOut.println();
        }
    }



    // A data type that encapsulates a pair of numbers (i, j) and the sum of their cubes.
    private static class Pair implements Comparable<Pair> {
        public int i;          // first number in the pair
        public int j;          // second number in the pair
        public int sumOfCubes; // i^3 + j^3

        // Constructs a pair (i, j).
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
            this.sumOfCubes = i * i * i + j * j * j;
        }

        // Returns a comparison of pairs this and other based on their sum-of-cubes values.
        public int compareTo(Pair other) {
            return sumOfCubes - other.sumOfCubes;
        }
    }
}
