import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size_;

    public RandomizedQueue() {
        size_ = 0;
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size_ == 0;
    }

    public int size() {
        return size_;
    }

    private void resize(int capacity) {
        Item[] newQueue = (Item[]) new Object[capacity];
        for (int i = 0; i < queue.length; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size_ == queue.length) {
            resize(queue.length * 2);
        }
        queue[size_] = item;
        size_++;
    }

    public Item dequeue() {
        if (size_ == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size_);
        Item item = queue[index];
        System.out.println(item);
        queue[index] = null;
        size_--;
        justiceQueue(queue, index, size_);
        if (size_ == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (size_ == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size_);
        return queue[index];
    }

    private void justiceQueue(Item[] q, int i, int j) {
        if (i == j) return;
        Item tmp = q[i];
        q[i] = q[j];
        q[j] = tmp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] copiedQueue;
        private int currentIndex;

        public RandomizedQueueIterator() {
            copiedQueue = (Item[]) new Object[size_];
            for (int i = 0; i < size_; i++) {
                copiedQueue[i] = queue[i];
            }
            StdRandom.shuffle(copiedQueue);
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < copiedQueue.length;
        }

        @Override
        public Item next() {
            if (currentIndex == copiedQueue.length) {
                throw new NoSuchElementException();
            }
            Item item = copiedQueue[currentIndex];
            currentIndex++;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
//
//    public void remove() {
//        throw new UnsupportedOperationException();
//    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");

        Iterator iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
