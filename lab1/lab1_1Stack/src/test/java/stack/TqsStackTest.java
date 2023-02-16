package stack;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.NoSuchElementException;


public class TqsStackTest {
    IStack<String> wordStack;

    @BeforeEach
    public void setUp() {
        wordStack = new TqsStack<>();
    }

    @DisplayName("A stack is empty on construction.")
    @Test
    public void testEmptyStack() {
        assertTrue(wordStack.isEmpty());
        assertEquals(0, wordStack.size());
    }

    @DisplayName("A stack has size 0 on construction")
    @Test
    public void testEmptyStackSize() {
        assertEquals(0, wordStack.size());
    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    public void testNPushes() {
        wordStack.push("Hello");
        wordStack.push("World");
        wordStack.push("!");

        assertFalse(wordStack.isEmpty());
        assertEquals(3, wordStack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x.")
    @Test
    public void testPop () {
        wordStack.push("Hello");
        assertEquals("Hello", wordStack.pop());
        assertTrue(wordStack.isEmpty());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    @Test
    public void testPeek () {
        wordStack.push("Hello");
        assertEquals("Hello", wordStack.peek());
        assertFalse(wordStack.isEmpty());
        assertEquals(1, wordStack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
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

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    @Test
    public void testPopEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            wordStack.pop();
        });
    }

    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    @Test
    public void testPeekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            wordStack.peek();
        });
    }
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    @Test
    public void testPushFullStack() {
        IStack<String> boundedStack = new BoundedStack<>(1);
        boundedStack.push("Hello");
        assertThrows(IllegalStateException.class, () -> {
            boundedStack.push("World");
        });
    }
}
