package stack;

public class BoundedStack<T> extends TqsStack<T> {

        private final int maxSize;

        BoundedStack(int maxSize) {
            super();
            this.maxSize = maxSize;
        }

        @Override
        public void push(T value) {
            if (size() == maxSize) {
                throw new IllegalStateException();
            }
            super.push(value);
        }
}
