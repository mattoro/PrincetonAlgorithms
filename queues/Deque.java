/* *****************************************************************************
 *  Name: Matthew Santoro
 *  Date:
 *  Description: Creating a double ended queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int size = 0;

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);
        // create new node
        Node<Item> newFirst = new Node<Item>();
        newFirst.item = item;
        // append to front
        newFirst.next = first;
        // check if list was empty
        if (isEmpty()) {
            // reset first and last
            first = newFirst;
            first.prev = null;
            last = first;
        }
        else {
            first.prev = newFirst;
            first = newFirst;
        }
        // increase size
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNull(item);
        // normal queue enqueue
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last; // edge case for empty queue
        else oldLast.next = last;
        size++; // increase size
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkEmptyRemove();
        // normal queue dequeue
        size--;
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkEmptyRemove();
        // decrease size
        --size;
        // preserve item
        Item item = last.item;
        // move last back
        last = last.prev;
        // change last.next if not empty
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No items left to iterate");
            Item item = current.item;
            current = current.next;
            return item;
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

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        // empty
        StdOut.println("Empty? " + deque.isEmpty());

        // seperate testing
        Deque<Integer> deque1 = new Deque<>();
        deque1.addFirst(1);
        deque1.removeLast();
        StdOut.println("PASSED TEST");

        // adding
        deque.addFirst("First");
        deque.addLast("Last");
        deque.addFirst("First Annoying guy");
        deque.addLast("Last Annoying guy");

        // iterating before removal
        for (String s : deque) {
            StdOut.print(s + ", ");
        }
        StdOut.println("\nSize: " + deque.size());

        // removal
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());

        for (String s : deque) {
            StdOut.print(s + ", ");
        }
        StdOut.println("\nSize: " + deque.size());
    }

}
