import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@ManagedBean(name="LibManager")
public class LibManager{

    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static EntityTransaction et;


    public LibManager() {
        factory = Persistence.createEntityManagerFactory("NewPersistenceUnit"); //.createEntityManager();
        em = factory.createEntityManager();
        et = em.getTransaction();
    }

    private int id;
    private String authorName;
    private List<Borrow> borrows;


    private int editId;
    private String title;
    private int amount;
    private Integer reader;
    private String startDate;
    private String endDate;

    public static List getAllBooks() {
        String jpql = "SELECT b FROM Book2 b";
        TypedQuery<Book2> query = em.createQuery(jpql, Book2.class);
        List<Book2> list= query.getResultList();
        System.out.println("Ksiazki: ");
        for(Book2 b : list) {
            System.out.println(b.getId_author()+ ""+ b.getTitle_book());
        }
        return list;
    }

    public static List getAllBooksAvailable() {
        String jpql = "SELECT b FROM Book2 b";
        TypedQuery<Book2> query = em.createQuery(jpql, Book2.class);
        return query.getResultList();
    }

    public static List getAllUsers() {
        String jpql = "SELECT b FROM Reader b";
        TypedQuery<Reader> query = em.createQuery(jpql, Reader.class);
        return query.getResultList();
    }

    public String editBookDetailsById(int id) {
        editId = id;
        return "editBook.xhtml";
    }

    public static List <Borrow> getAllBorrows() {
        String jpql = "SELECT b FROM Borrow b";
        TypedQuery<Borrow> query = em.createQuery(jpql, Borrow.class);
        return query.getResultList();
    }


    public String editBook() throws NoResultException {
        if (!et.isActive()) {
                et.begin();
        }

        String jpql = "SELECT a FROM Author a WHERE a.name_author=:authorName";
        TypedQuery<Author> query = em.createQuery(jpql, Author.class).setParameter("authorName", authorName);
        try {
            Author author = query.getSingleResult();
            String jpql2 = "SELECT b FROM Book2 b WHERE b.id_book=:id";
            TypedQuery<Book2> query2 = em.createQuery(jpql2, Book2.class).setParameter("id", editId);
            Book2 book = query2.getSingleResult();
            book.setAuthor_book(author);
            book.setTitle_book(title);
            em.merge(book);
            et.commit();
        }
        catch (Exception e){
            Author author1 = new Author(authorName);
            String jpql3 = "SELECT b FROM Book2 b WHERE b.id_book=:id";
            TypedQuery<Book2> query3 = em.createQuery(jpql3, Book2.class).setParameter("id", editId);
            Book2 book = query3.getSingleResult();
            //em.persist(author1);
            em.persist(author1);

            book.setAuthor_book(author1);
            book.setTitle_book(title);
            em.merge(book);
            et.commit();
        }
        finally {
            return "indexWypozyczenia.xhtml";
        }

    }



    public String addBook() throws NoResultException {
        if (!et.isActive()) {
            et.begin();
        }
        String jpql = "SELECT a FROM Author a WHERE a.name_author=:authorName";
        TypedQuery<Author> query = em.createQuery(jpql, Author.class).setParameter("authorName", authorName);
        try {
            Author author = query.getSingleResult();
            Book2 newBook = new Book2(author, title);
            em.persist(newBook);
            et.commit();
        }
        catch (Exception e){
            Author author1= new Author(authorName);
            em.persist(author1);
            et.commit();
            et.begin();
            Book2 newBook = new Book2(author1, title);
            em.persist(newBook);
            et.commit();
        }
        finally {
            return "indexWypozyczenia.xhtml";
        }
    }


    public String addBorrow() throws ParseException {
        if (!et.isActive()) {
            et.begin();
        }

        String jpql = "SELECT b FROM Book2 b WHERE b.title_book=:title";
        TypedQuery<Book2> query = em.createQuery(jpql, Book2.class).setParameter("title", title);

        System.out.println(title);
        System.out.println(reader);

        String jpql2 = "SELECT r FROM Reader r WHERE r.id_reader=:id";
        TypedQuery<Reader> query2 = em.createQuery(jpql2, Reader.class).setParameter("id", reader);
        Book2 book = query.getSingleResult();
        Reader reader = query2.getSingleResult();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date startD = sdf1.parse(startDate);
        java.sql.Date sqlStartDate = new java.sql.Date(startD.getTime());
        java.util.Date endD = sdf1.parse(endDate);
        java.sql.Date sqlEndDate = new java.sql.Date(endD.getTime());

        Borrow borrow = new Borrow (book, reader, sqlStartDate, sqlEndDate);
        em.persist(borrow);
        et.commit();

        et.begin();
        em.merge(book);
        et.commit();
        return "indexWypozyczenia.xhtml";

    }


        public static String deleteBook(Integer id) {
            if (!et.isActive()) {
                et.begin();
            }

            String jpql = "SELECT b FROM Book2 b WHERE b.id_book=:id";
            TypedQuery<Book2> query = em.createQuery(jpql, Book2.class)
                    .setParameter("id", id);
            Book2 book = query.getSingleResult();
                em.remove(em.merge(book));
                et.commit();

            return "booksList.xhtml?faces-redirect=true";
        }


        public static String deleteBorrow(Integer id) {
            if (!et.isActive()) {
                et.begin();
            }

            String jpql = "SELECT l FROM Borrow l WHERE l.id_borrow=:id";
            TypedQuery<Borrow> query = em.createQuery(jpql, Borrow.class).setParameter("id", id);
            Borrow borrow = query.getSingleResult();
            Book2 book = borrow.getId_book();

            em.merge(book);
            em.remove(borrow);
            et.commit();

            return "booksList.xhtml?faces-redirect=true";
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Borrow> getBorrows() {
            return borrows;
        }

        public void setBorrows(List<Borrow> borrows) {
            this.borrows = borrows;
        }

        public Integer getReader() {
            return reader;
        }

        public void setReader(Integer reader) {
            this.reader = reader;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getEditId() {
        return editId;
    }

        public void setEditId(int editId) {
        this.editId = editId;
    }

}
