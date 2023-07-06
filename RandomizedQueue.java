import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private Integer N = 0;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final boolean[] alreadyRead = new boolean[N];
        private int amountOfAlreadyRead = 0;

        @Override
        public boolean hasNext() {
            return amountOfAlreadyRead < N;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items");

            while (true) {
                int uniformIntegerIndex = StdRandom.uniformInt(0, N);

                if (!alreadyRead[uniformIntegerIndex]) {
                    alreadyRead[uniformIntegerIndex] = true;
                    amountOfAlreadyRead++;
                    return items[uniformIntegerIndex];
                }
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not implemented");
        }
    }

    // construct an empty deque
    public RandomizedQueue() {
        items = ((Item[]) new Object[1]);
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the back
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item is null");
        if (N == items.length) resize(2 * items.length);
        items[N++] = item;
    }

    public Item dequeue() {
        Item item = items[--N];
        items[N] = null;

        if (N > 0 && N == items.length / 4) resize(items.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("It is empty");
        return items[StdRandom.uniformInt(0, N)];
    }

    private void resize(Integer capacity) {
        Item[] copy = ((Item[]) new Object[capacity]);

        for (int i = 0; i < N; i++) {
            copy[i] = items[i];
        }

        items = copy;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);

        System.out.println(randomizedQueue.sample());

        Iterator<Integer> iterator = randomizedQueue.iterator();

//        System.out.println(iterator.hasNext());
//        System.out.println(iterator.next());
//        System.out.println(iterator.hasNext());
//        System.out.println(iterator.next());
//        System.out.println(iterator.hasNext());
//        System.out.println(iterator.next());
//        System.out.println(iterator.hasNext());
//        System.out.println(iterator.next());
    }
}
