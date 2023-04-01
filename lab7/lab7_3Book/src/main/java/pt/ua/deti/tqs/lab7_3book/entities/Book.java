package pt.ua.deti.tqs.lab7_3book.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Table(name = "book")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // como o @NonNull obriga a passar o valor do atributo no construtor, o @NoArgsConstructor(force = true) obriga a passar o valor de todos os atributos
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String author;

    @NonNull
    private String isbn;

    @NonNull
    private String publisher;

    @NonNull
    private Integer year;

    @NonNull
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
