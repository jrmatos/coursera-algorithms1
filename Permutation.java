import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        Integer k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readLine());
        }

        Iterator<String> randomizedQueueIterator = randomizedQueue.iterator();

        for (int i = 0; i < k; i++) System.out.println(randomizedQueueIterator.next());
    }
}
