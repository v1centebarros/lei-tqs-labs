package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;
public class TqsStack<T> implements IStack<T> {

    LinkedList<T> collection;

    TqsStack() {
        collection = new LinkedList<>();
    }

    @Override
    public T pop() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException();
        }
        return collection.removeLast();
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public T peek() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException();
        }
        return collection.getLast();
    }

    @Override
    public void push(T value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        collection.addLast(value);
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }
}