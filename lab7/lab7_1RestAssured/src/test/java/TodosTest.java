import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.number.OrderingComparison.lessThan;

class TodosTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";

    @Test
    void testAllTodosListAvailable() {
        get(BASE_URL)
                .then().statusCode(200).assertThat();
    }

    @Test
    void testTodo4Title() {
        get(BASE_URL + "/4")
                .then().statusCode(200).assertThat()
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    void testLastTodoIdAndTotalResults() {
        get(BASE_URL)
                .then().statusCode(200).assertThat()
                .body("id", hasItems(198,199));
    }

    @Test
    void testListAllTodosTime() {
        get(BASE_URL)
                .then().statusCode(200).assertThat()
                .time(lessThan(2000L));
    }
}