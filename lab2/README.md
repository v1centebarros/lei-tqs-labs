# Lab 2 - Mocking dependencies (for unit testing)

## What is Mocking Dependencies?

In software development, dependencies refer to other components or services that a module or class relies on to function properly. In unit testing, it is common to test a class in isolation from its dependencies. This is because dependencies can be complex, slow, or difficult to set up, which can make testing the class as a whole more difficult.

Mocking is the process of creating fake objects that mimic the behavior of real dependencies, without actually using the real dependencies. A mock object is created to simulate the behavior of the dependency, which allows the code to be tested in isolation from the real dependency. This makes unit testing easier, faster, and more reliable.

Mocking is commonly used in test-driven development (TDD) and behavior-driven development (BDD) to test individual units of code in isolation, without worrying about the behavior of external dependencies.
Importance of Mocking Dependencies

Mocking dependencies is important for several reasons:

- **Isolation**: By mocking dependencies, you can isolate the code being tested and ensure that any failures or errors are not caused by external factors.

- **Speed**: Mocking dependencies can speed up the testing process because you can avoid the overhead of setting up real dependencies.

- **Control**: With mocking, you have more control over the behavior of the dependencies, allowing you to test edge cases and error conditions more easily.

- **Flexibility**: By using mock objects, you can test different scenarios without worrying about the availability or consistency of real dependencies.

- **Maintainability**: Mocking dependencies can make the code easier to maintain, because it allows you to test individual units of code in isolation, making it easier to identify and fix bugs.

## Mockito

Mocking is a way to test the functionality of a class in isolation. Mocking does not require a database connection or properties file read or file server read to test a functionality. Mock objects do the mocking of the real service. A mock object returns a dummy data corresponding to some dummy input passed to it.

Mockito facilitates creating mock objects seamlessly. It uses Java Reflection in order to create mock objects for a given interface. Mock objects are nothing but proxy for actual implementations.

Consider a case of Stock Service which returns the price details of a stock. During development, the actual stock service cannot be used to get real-time data. So we need a dummy implementation of the stock service. Mockito can do the same very easily, as its name suggests.

## Advantages of Mockito



- **No Handwriting** − No need to write mock objects on your own.

- **Refactoring Safe** − Renaming interface method names or reordering parameters will not break the test code as Mocks are created at runtime.

- **Return value support** − Supports return values.

- **Exception support** − Supports exceptions.

- O**rder check support** − Supports check on order of method calls.

- **Annotation support** − Supports creating mocks using annotation.

## Annotations

|Annotation |	Description |
| --- | --- |
| @Mock|	Creates a mock object of a class or interface. This is used to replace real dependencies with mock objects in unit tests.|
| @Spy	|Creates a spy object that wraps around a real object, allowing you to verify its behavior and modify its output.|
| @InjectMocks	|Automatically injects mocks and spies into the object being tested. This is used to simplify the setup of unit tests.|
| @Captor | Captures the arguments passed to a mock object method. This is used to verify the inputs to a method.|
| @MockitoAnnotations | Initializes mock objects annotated with @Mock or @Spy. This is typically used in conjunction with the @Before method in JUnit.|
| @MockSettings | Allows you to specify settings for a mock object, such as its behavior and whether it is a strict or lenient mock.|
| @MockitoJUnitRunner |	Runs a test class with the Mockito test runner. This is used to simplify the setup of unit tests with Mockito.|
| @Rule |	Provides a way to add additional behavior to test cases using custom rules. A rule is defined as a field of a test class with the @Rule annotation.|

## Usage of Mockito

### 1. Creating Mocks

```java
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository; // Mock object
    
    @InjectMocks
    private UserService userService; // Object to be tested
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize the mock objects
    }
    
    @Test
    public void testFindUserById() {
        // Set up the mock behavior
        User expectedUser = new User("123", "John");
        Mockito.when(userRepository.findById("123")).thenReturn(expectedUser);
        
        // Call the method under test
        User actualUser = userService.findUserById("123");
        
        // Verify the results
        assertEquals(expectedUser, actualUser);
    }
    
    // ... More test cases
}
```



## Useful Links

- [Mockito](https://site.mockito.org/)

- [Mockito Tutorial](https://www.tutorialspoint.com/mockito/index.htm)

- [Mockito Cheat Sheet](https://github.com/cuijfboy/MockitoCheatSheet)


## Hamcrest

Hamcrest is a framework for writing matcher objects allowing ‘match’ rules to be defined declaratively. There are a number of situations where matchers are invaluable, such as UI validation or data filtering, but it is in the area of writing flexible tests that matchers are most commonly used. This tutorial shows you how to use Hamcrest for unit testing.

When writing tests it is sometimes difficult to get the balance right between over specifying the test (and making it brittle to changes), and not specifying enough (making the test less valuable since it continues to pass even when the thing being tested is broken). Having a tool that allows you to pick out precisely the aspect under test and describe the values it should have, to a controlled level of precision, helps greatly in writing tests that are “just right”. Such tests fail when the behaviour of the aspect under test deviates from the expected behaviour, yet continue to pass when minor, unrelated changes to the behaviour are made.

## Setup

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-all</artifactId>
    <version>1.3</version>
</dependency>
```


## Example

```java
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class BiscuitTest {
  @Test 
  public void testEquals() { 
    Biscuit theBiscuit = new Biscuit("Ginger"); 
    Biscuit myBiscuit = new Biscuit("Ginger"); 
    assertThat(theBiscuit, equalTo(myBiscuit)); 
  } 
} 
```

## Useful Links


- [Hamcrest](https://hamcrest.org/JavaHamcrest/)
- [Testing with Hamcrest](https://www.baeldung.com/hamcrest-library)