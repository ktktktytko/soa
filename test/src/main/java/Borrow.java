import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue
    @Column(name="id_borrow", nullable = false)
    private int id_borrow;

    @Column (name="start_borrow", nullable = false)
    private Date start_borrow;

    @Column (name="end_borrow", nullable = false)
    private Date end_borrow;

//    @ManyToOne
    @OneToOne
    @JoinColumn(name="id_book", nullable = false)
    private Book2 id_book;

//    @ManyToOne
    @OneToOne
    @JoinColumn(name="id_reader", nullable = false)
    private Reader id_reader;

    public Borrow() {}

    public Borrow(Book2 book, Reader reader, Date start, Date end) {
        this.id_book = book;
        this.id_reader = reader;
        this.start_borrow = start;
        this.end_borrow = end;
    }

    public int getId_borrow() {
        return id_borrow;
    }

    public void setId_borrow(int id_borrow) {
        this.id_borrow = id_borrow;
    }

    public Date getStart_borrow() {
        return start_borrow;
    }

    public void setStart_borrow(Date start_borrow) {
        this.start_borrow = start_borrow;
    }

    public Date getEnd_borrow() {
        return end_borrow;
    }

    public void setEnd_borrow(Date end_borrow) {
        this.end_borrow = end_borrow;
    }

    public Book2 getId_book() {
        return id_book;
    }

    public void setId_book(Book2 id_book) {
        this.id_book = id_book;
    }

    public Reader getId_reader() {
        return id_reader;
    }

    public void setId_reader(Reader id_reader) {
        this.id_reader = id_reader;
    }
}
