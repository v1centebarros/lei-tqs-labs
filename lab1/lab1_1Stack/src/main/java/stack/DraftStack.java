package stack;

import java.util.LinkedList;

public class DraftStack<T> implements IStack<T> {

    LinkedList<T> collection;

    DraftStack() {
        collection = new LinkedList<>();
    }

    @Override
    public T pop() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public void push(T value) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}