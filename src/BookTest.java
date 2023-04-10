/**
 * Name: Luke Winter
 * Date: Feb 5
 * Description: This is a Junit Test for Book.Java
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @BeforeEach
    void setUp() {
        System.out.println("Starting");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Ending");
    }

    @Test
    void Book(){
        Book book = null;
        assertEquals(null, book);
        String isbn = "A";
        String title = "B";
        String subject = "C";
        Integer pageCount = 1;
        String author = "D";
        LocalDate dueDate = LocalDate.ofYearDay(2023,1);
        book = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertNotEquals(null, book);
    }

    @Test
    void getIsbn() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals("A", book.getIsbn());
    }

    @Test
    void setIsbn() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        book.setIsbn("A2");
        assertEquals("A2", book.getIsbn());
    }

    @Test
    void getTitle() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals("B", book.getTitle());
    }

    @Test
    void setTitle() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        book.setTitle("B2");
        assertEquals("B2", book.getTitle());
    }

    @Test
    void getSubject() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals("C", book.getSubject());
    }

    @Test
    void setSubject() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        book.setSubject("C2");
        assertEquals("C2", book.getSubject());
    }

    @Test
    void getPageCount() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals(1, book.getPageCount());
    }

    @Test
    void setPageCount() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        book.setPageCount(2);
        assertEquals(2, book.getPageCount());
    }

    @Test
    void getAuthor() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals("D", book.getAuthor());
    }

    @Test
    void setAuthor() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        book.setAuthor("D2");
        assertEquals("D2", book.getAuthor());
    }

    @Test
    void getDueDate() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals(LocalDate.ofYearDay(2023,1), book.getDueDate());
    }

    @Test
    void setDueDate() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        book.setDueDate(LocalDate.ofYearDay(2023,2));
        assertEquals(LocalDate.ofYearDay(2023,2), book.getDueDate());
    }

    @Test
    void testEquals() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Book book2 = new Book("A2", "B2", "C2", 2, "D2", LocalDate.ofYearDay(2023,2));
        Book book3 = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        assertEquals(book,book3);
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}