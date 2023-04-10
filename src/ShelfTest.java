import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Luke Winter
 * Project: Project 01 Part 03/04: Shelf.java
 * Date: March 4th 2023
 * Description: Implementation of the Shelf junit Tests
 */

class ShelfTest {

    Shelf shelfTest = null;
    Book bookDrama = null;
    Book bookNotDrama = null;
    String defaultSubject = "Drama";
    int defaultShelfNumber = 1;
    @BeforeEach
    void setUp() {
        shelfTest = new Shelf(defaultSubject, defaultShelfNumber);
        bookDrama = new Book("1234", "Harry", "Drama", 500, "JK", LocalDate.now());
        bookNotDrama = new Book("4321", "Lord", "NotDrama", 500, "JK", LocalDate.now());
    }

    @Test
    void getBooks() {
        HashMap<Book, Integer> hashTest = new HashMap<>();
        assertEquals(hashTest, shelfTest.getBooks());
        shelfTest.addBook(bookDrama);
        hashTest.put(bookDrama, 1);
        assertEquals(hashTest, shelfTest.getBooks());
    }

    @Test
    void setBooks() {
        HashMap<Book, Integer> hashTest = new HashMap<>();
        hashTest.put(bookDrama, 1);
        shelfTest.setBooks(hashTest);
        assertEquals(hashTest, shelfTest.getBooks());
    }

    @Test
    void getSubject() {
        assertEquals(defaultSubject, shelfTest.getSubject());
    }

    @Test
    void setSubject() {
        String subject = "Comedy";
        shelfTest.setSubject(subject);
        assertEquals(subject, shelfTest.getSubject());
    }

    @Test
    void getShelfNumber() {
        assertEquals(defaultShelfNumber, shelfTest.getShelfNumber());
    }

    @Test
    void setShelfNumber() {
        int shelfNumber = 2;
        shelfTest.setShelfNumber(shelfNumber);
        assertEquals(shelfNumber, shelfTest.getShelfNumber());
    }

    @Test
    void testEquals() {
        Shelf shelf2 = new Shelf(defaultSubject + "a", defaultShelfNumber - 1);
        assertNotEquals(shelfTest, shelf2);
        shelf2 = new Shelf(defaultSubject, defaultShelfNumber);
        assertEquals(shelfTest, shelf2);
    }

    @Test
    void testToString() {
        assertEquals("1 : Drama", shelfTest.toString());
    }

    @Test
    void getBookCount() {
        Random random = new Random();
        int amount = random.nextInt(1,100);
        for(int i = 0; i < amount ; i++){
            shelfTest.addBook(bookDrama);
        }

        assertEquals(amount, shelfTest.getBookCount(bookDrama));
        assertEquals(Code.SUCCESS, shelfTest.removeBook(bookDrama));
        assertEquals(amount - 1, shelfTest.getBookCount(bookDrama));
    }

    @Test
    void addBook() {
        assertEquals(Code.SUCCESS, shelfTest.addBook(bookDrama));
        assertEquals(Code.SUCCESS, shelfTest.addBook(bookDrama));
        assertEquals(2, shelfTest.getBookCount(bookDrama));
    }

    @Test
    void removeBook() {
        assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, shelfTest.removeBook(bookDrama));
        assertEquals(Code.SUCCESS, shelfTest.addBook(bookDrama));
        assertEquals(1, shelfTest.getBookCount(bookDrama));
        assertEquals(Code.SUCCESS, shelfTest.removeBook(bookDrama));
        assertEquals(0, shelfTest.getBookCount(bookDrama));
    }

    @Test
    void listBooks() {
        assertEquals("0 books on shelf: 1: Drama", shelfTest.listBooks());
        shelfTest.addBook(bookDrama);
        assertEquals("1 book on shelf: 1: Drama\n" +
                "Harry by JK ISBN: 1234 1", shelfTest.listBooks());
    }
}