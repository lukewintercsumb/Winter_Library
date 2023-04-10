import java.time.LocalDate;
import java.util.Objects;

/**
 * Name: Luke Winter
 * Date: Feb 5
 * Description: This is a class Book, which stores information about a book
 */

public class Book {
    public static final Integer ISBN_ = 0;
    public static final Integer TITLE_ = 1;
    public static final Integer SUBJECT_ = 2;
    public static final Integer PAGE_COUNT_ = 3;
    public static final Integer AUTHOR_ = 4;
    public static final Integer DUE_DATE_ = 5;
    private String isbn;
    private String title;
    private String subject;
    private Integer pageCount;
    private String author;
    private LocalDate dueDate;

    public Book(String isbn, String title, String subject, Integer pageCount, String author, LocalDate dueDate) {
        this.isbn = isbn;
        this.title = title;
        this.subject = subject;
        this.pageCount = pageCount;
        this.author = author;
        this.dueDate = dueDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(subject, book.subject) && Objects.equals(pageCount, book.pageCount) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, subject, pageCount, author);
    }

    public String toString() {
        return title + " by " + author + " ISBN: " + isbn;
    }
}
