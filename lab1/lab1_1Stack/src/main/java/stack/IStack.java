package stack;

public interface IStack<T> {

    T pop();
    int size();
    T peek();
    void push(T value);
    boolean isEmpty();

}
