package stack;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.NoSuchElementException;


public class TqsStackTest {
    IStack<String> wordStack;

    @BeforeEach
    public void setUp() {
        wordStack = new TqsStack<>();

        assertTrue(wordStack.isEmpty());
        assertEquals(0, wordStack.size());
    }

    @Test
    public void testNPushes() {
        wordStack.push("Hello");
        wordStack.push("World");
        wordStack.push("!");

        assertFalse(wordStack.isEmpty());
        assertEquals(3, wordStack.size());
    }

    @Test
    public void testPop () {
        wordStack.push("Hello");
        assertEquals("Hello", wordStack.pop());
        assertTrue(wordStack.isEmpty());
    }

    @Test
    public void testPeek () {
        wordStack.push("Hello");
        assertEquals("Hello", wordStack.peek());
        assertFalse(wordStack.isEmpty());
        assertEquals(1, wordStack.size());
    }

    @Test
    public void testNPops() {
        wordStack.push("Hello");
        wordStack.push("World");
        wordStack.push("!");
        wordStack.pop();
        wordStack.pop();
        wordStack.pop();
        assertTrue(wordStack.isEmpty());
        assertEquals(0, wordStack.size());
    }

    @Test
    public void testPopEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            wordStack.pop();
        });
    }


    @Test
    public void testPeekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            wordStack.peek();
        });
    }

    @Test
    public void testPushFullStack() {
        IStack<String> boundedStack = new BoundedStack<>(1);
        boundedStack.push("Hello");
        assertThrows(IllegalStateException.class, () -> {
            boundedStack.push("World");
        });
    }
}
