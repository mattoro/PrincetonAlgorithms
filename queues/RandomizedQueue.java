/* *****************************************************************************
 *  Name: Matthew Santoro
 *  Date:
 *  Description: Creating a RandomizedQueue data structure
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] qArray;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        qArray = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        checkNull(item);
        if (size == qArray.length) resize(2 * qArray.length);
        qArray[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmptyRemove();
        int randIndex = StdRandom.uniform(size);
        // keep item for return
        Item item = qArray[randIndex];

        // replace with last index
        qArray[randIndex] = qArray[size - 1];
        qArray[size - 1] = null; // avoids loitering

        // decrease size and resize array
        size--;
        if (size > 0 && size == qArray.length / 4) resize(qArray.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkEmptyRemove();
        int randIndex = StdRandom.uniform(size);

        return qArray[randIndex];
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        Item[] shuffleArray = shuffle();

        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No items left to iterate");
            return shuffleArray[i++];
        }
    }


    // Throw an IllegalArgumentException if the client calls either
    // addFirst() or addLast() with a null argument.
    private void checkNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add NULL to the list");
        }
    }

    // Throw a java.util.NoSuchElementException if the client calls either
    // removeFirst() or removeLast when the deque is empty.
    private void checkEmptyRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty list");
        }
    }

    // Resizes the array to the given capacity
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = qArray[i];
        }
        qArray = copy;
    }

    // copies the array to up until last value
    private Item[] shuffle() {
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0; i < size; i++) {
            copy[i] = qArray[i];
        }
        StdRandom.shuffle(copy);
        return copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        // empty
        StdOut.println("Empty? " + rQueue.isEmpty());

        // adding
        rQueue.enqueue("one");
        rQueue.enqueue("two");
        rQueue.enqueue("three");
        rQueue.enqueue("four");

        // iterating before removal
        for (String s : rQueue) {
            StdOut.print(s + ", ");
        }
        StdOut.println("\nSize: " + rQueue.size());

        // Sampling
        StdOut.println("Sample: " + rQueue.sample());
        StdOut.println("Sample: " + rQueue.sample());

        // removal
        StdOut.println("Remove: " + rQueue.dequeue());
        StdOut.println("Remove: " + rQueue.dequeue());

        for (String s : rQueue) {
            StdOut.print(s + ", ");
        }
        StdOut.println("\nSize: " + rQueue.size());

    }

}
