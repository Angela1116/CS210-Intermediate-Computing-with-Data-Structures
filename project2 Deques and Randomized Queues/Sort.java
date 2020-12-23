import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedStack<String> s = new LinkedStack<>();
        LinkedDeque<String> d = new LinkedDeque<>();
        String w = " ";
        while (!StdIn.isEmpty()) {
            w = StdIn.readString();
            if (d.isEmpty()) {
                d.addFirst(w);
            } else if (less(w, d.peekFirst())) {
                d.addFirst(w);
            } else if (!less(w, d.peekLast())) {
                d.addLast(w);
            } else {
                while (less(d.peekFirst(), w)) {
                    s.push(d.removeFirst());
                }
                d.addFirst(w);
                while (!s.isEmpty()) {
                    d.addFirst(s.pop());
                }
            }
        }
        for (String words : d) {
            StdOut.println(d.removeFirst());
        }
    }

        // Returns true if v is less than w according to their lexicographic order, and false
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }

}