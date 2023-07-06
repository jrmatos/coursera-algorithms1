import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int count = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private class DequeIterator implements Iterator<Item> {
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
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");

        Node oldFirst = first;

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = oldFirst;
        newNode.previous = null;

        if (isEmpty()) {
            last = newNode;
        }

        first = newNode;
        count++;
    }


    // add the item to the back
    public void addLast(Item item) {
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

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is already empty");

        Node oldLast = last;
        last = last.previous;
        last.next = null;
        count--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

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
