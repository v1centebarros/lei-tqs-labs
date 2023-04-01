package pt.ua.deti.tqs.lab7_3book;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.deti.tqs.lab7_3book.entities.Book;
import pt.ua.deti.tqs.lab7_3book.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTests {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13.2")
            .withDatabaseName("book")
            .withUsername("postgres")
            .withPassword("postgres");


    @Autowired
    private BookRepository bookRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @Test
    @Order(1)
    void createBookTest() {
        Book b = new Book("Lord of the Rings", "J.R.R. Tolkien", "978-0544003415", "Allen & Unwin", 1954, 10);
        bookRepository.save(b);
        assertThat(bookRepository.findByAuthor("J.R.R. Tolkien")).isNotNull();
        assertThat(bookRepository.findByAuthor("J.R.R. Tolkien")).isInstanceOf(Book.class);
        assertThat(bookRepository.findByAuthor("J.R.R. Tolkien").getAuthor()).isEqualTo("J.R.R. Tolkien");
    }


    @Test
    @Order(2)
    void checkWhatBookIsOlder() {
        Book b2 = new Book("The Hobbit", "J.R.R. Tolkien", "978-0544003415", "Allen & Unwin", 1937, 10);
        bookRepository.save(b2);
        assertThat(bookRepository.findByTitle("The Hobbit").getYear()).isLessThan(bookRepository.findByTitle("Lord of the Rings").getYear());
    }

    @Test
    @Order(3)
    void updateBookTest() {
        Book b = bookRepository.findByTitle("Lord of the Rings");
        b.setAuthor("Frodo Baggins");
        bookRepository.save(b);
        assertThat(bookRepository.findByTitle("Lord of the Rings").getAuthor()).isEqualTo("Frodo Baggins");
        }

    @Test
    @Order(4)
    void deleteBookTest() {
        bookRepository.delete(bookRepository.findByTitle("Lord of the Rings"));
        assertThat(bookRepository.findByTitle("Lord of the Rings")).isNull();
    }
}
