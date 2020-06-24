import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size_;

    public Deque() {
        first = null;
        last = null;
        size_ = 0;
    }

    private class Node {
        Item item;
        Node previous;
        Node next;

        public Node(Item item) {
            this.item = item;
            previous = null;
            next = null;
        }
    }

    public boolean isEmpty() {
        return size_ == 0;
    }

    public int size() {
        return size_;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node node = new Node(item);
            node.next = first;
            first.previous = node;
            first = node;

        }
        size_++;

    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node node = new Node(item);
            node.previous = last;
            last.next = node;
            last = node;

        }
        size_++;

    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }
        size_--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size_--;
        return item;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }


    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(3);
        deque.addLast(4);
        System.out.println(deque.size());
        for (int i : deque) {
            System.out.println(i);
        }
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
    }
}
