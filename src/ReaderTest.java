import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Luke Winter
 * Date: Feb 11
 * Description: This is the Unit test program for the Reader.java class
 */
class ReaderTest {

    @BeforeEach
    void setUp() {
        System.out.println("Starting");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Ending");
    }

    @Test
    void addBook() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");
        assertEquals(reader.addBook(book), Code.SUCCESS);
        assertNotEquals(reader.addBook(book), Code.SUCCESS);
        assertEquals(reader.addBook(book), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
    }

    @Test
    void removeBook() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");
        assertEquals(reader.removeBook(book), Code.READER_DOESNT_HAVE_BOOK_ERROR);
        reader.addBook(book);
        assertEquals(reader.removeBook(book), Code.SUCCESS);

    }

    @Test
    void hasBook() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");
        assertFalse(reader.hasBook(book));
        reader.addBook(book);
        assertTrue(reader.hasBook(book));
    }

    @Test
    void getBookCount() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");
        assertEquals(reader.getBookCount(), 0);
        reader.addBook(book);
        assertEquals(reader.getBookCount(), 1);
        reader.removeBook(book);
        assertEquals(reader.getBookCount(), 0);
    }

    @Test
    void getBooks() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        reader.addBook(book);
        assertEquals(Arrays.asList(book), reader.getBooks());
    }

    @Test
    void setBooks() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        reader.setBooks(Arrays.asList(book));
        assertEquals(Arrays.asList(book), reader.getBooks());
    }

    @Test
    void getCardNumber() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        assertEquals(reader.getCardNumber(), 1);
    }

    @Test
    void setCardNumber() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        assertEquals(reader.getCardNumber(), 1);
        reader.setCardNumber(2);
        assertEquals(reader.getCardNumber(), 2);
    }

    @Test
    void getName() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        assertEquals(reader.getName(), "John");
    }

    @Test
    void setName() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        assertEquals(reader.getName(), "John");
        reader.setName("Michael");
        assertEquals(reader.getName(), "Michael");
    }

    @Test
    void getPhone() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        assertEquals(reader.getPhone(), "123");
    }

    @Test
    void setPhone() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");

        assertEquals(reader.getPhone(), "123");
        reader.setPhone("1234");
        assertEquals(reader.getPhone(), "1234");
    }

    @Test
    void testEquals() {
        Book book = new Book("A", "B", "C", 1, "D", LocalDate.ofYearDay(2023,1));
        Reader reader = new Reader(1, "John", "123");
        Reader reader2 = new Reader(2, "John2", "1234");
        Reader reader3 = new Reader(1, "John", "123");
        assertEquals(reader,reader3);

    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}