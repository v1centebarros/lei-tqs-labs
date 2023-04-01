package pt.ua.deti.tqs.lab7_3book.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.ua.deti.tqs.lab7_3book.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    Book findByAuthor(String author);




}
