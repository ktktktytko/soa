import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book2 {
    @Id
    @GeneratedValue
    @Column(name="id_book", nullable = false)
    private int id_book;


    @Column(nullable = false)
    private String title_book;

    @ManyToOne
    @JoinColumn(name="id_author", nullable=false)
    private Author id_author;


    public Book2(Author author, String title) {
        this.id_author = author;
        this.title_book = title;
    }

    public Book2(){}
    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public String getTitle_book() {
        return title_book;
    }

    public void setTitle_book(String title_book) {
        this.title_book = title_book;
    }

    public Author getAuthor_book() {
        return id_author;
    }

    public void setAuthor_book(Author author_book) {
        this.id_author = author_book;
    }

    public Author getId_author() {
        return id_author;
    }

    public void setId_author(Author id_author) {
        this.id_author = id_author;
    }

}
