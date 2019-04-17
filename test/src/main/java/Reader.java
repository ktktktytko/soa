import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "reader")
public class Reader {
    @Id
    @GeneratedValue
    @Column(name="id_reader", nullable = false)
    private int id_reader;

    @Column (name="name_reader", nullable = false)
    private String name_reader;

    @Column (name="surname_reader", nullable=false)
    private String surname_reader;

//    @OneToMany(mappedBy = "id_borrow", cascade = {CascadeType.ALL})
//    private Set<Borrow> borrows;

    public Reader (String name, String surname) {
        this.name_reader = name;
        this.surname_reader = surname;

    }

    public Reader() {};

    public int getId_reader() {
        return id_reader;
    }

    public void setId_reader(int id_reader) {
        this.id_reader = id_reader;
    }

    public String getName_reader() {
        return name_reader;
    }

    public void setName_reader(String name_reader) {
        this.name_reader = name_reader;
    }

    public String getSurname_reader() {
        return surname_reader;
    }

    public void setSurname_reader(String surname_redaer) {
        this.surname_reader = surname_redaer;
    }


}
