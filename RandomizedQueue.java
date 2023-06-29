import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    int count = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException("Deque is empty");

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public RandomizedQueue() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the back
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;

        if (isEmpty()) {
            first = newNode;
            newNode.previous = null;
        } else {
            last.next = newNode;
            newNode.previous = last;
        }

        last = newNode;
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is already empty");

        Node oldFirst = first;
        first = first.next;
        count--;
        return oldFirst.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> deque = new RandomizedQueue<>();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());

        System.out.println(deque.isEmpty());
        System.out.println(deque.size());


        for (Integer item : deque) {
            System.out.println("Item ->" + item);
        }

        Iterator<Integer> iterator = deque.iterator();

        System.out.println(iterator.next());
        System.out.println(iterator.next());
    }
}
