import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author{

    @Id
    @GeneratedValue
    @Column(name="id_author", nullable = false)
    private int id_author;

    @Column(name="name_author", nullable = false)
    private String name_author;

//    @Column(name="surname_author", nullable=false)
//    private String surname_author;

    @OneToMany(mappedBy = "id_author", cascade = {CascadeType.ALL})
    private Set<Book2> books;

    public Author(String name) {
        this.name_author = name;
    }

    public Author(){};

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public String getName_author() {
        return name_author;
    }

    public void setName_author(String name_author) {
        this.name_author = name_author;
    }

}
