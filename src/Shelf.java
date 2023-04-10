import java.util.HashMap;
import java.util.Objects;

/**
 * Author: Luke Winter
 * Project: Project 01 Part 03/04: Shelf.java
 * Date: March 4th 2023
 * Description: Implementation of the Shelf junit Tests
 */

public class Shelf {
    //TODO: figure out the initial values for finals
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;
    private HashMap<Book, Integer> books;
    private String subject;
    private int shelfNumber;

    public Shelf(String subject, int shelfNumber) {
        books = new HashMap<>();
        this.subject = subject;
        this.shelfNumber = shelfNumber;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shelf shelf)) return false;
        return shelfNumber == shelf.shelfNumber && Objects.equals(subject, shelf.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, shelfNumber);
    }

    @Override
    public String toString() {
        return shelfNumber + " : " + subject;
    }

    public Integer getBookCount(Book book) {
        return books.getOrDefault(book, -1);
    }

    public Code addBook(Book book) {
        if(books.containsKey(book)) {
            books.put(book, books.get(book) + 1);
            return Code.SUCCESS;
        } else if(book.getSubject().equals(this.subject)) {
            books.put(book, 1);
            return Code.SUCCESS;
        } else {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }
    }

    public Code removeBook(Book book) {
        if(!books.containsKey(book)) {
            System.out.println(book.getTitle() + " is not on shelf " + this.subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else if(books.get(book) == 0) {
            System.out.println("No copies of " + book.getTitle() + " remain on shelf " + this.subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            books.put(book, books.get(book) - 1);
            System.out.println(book.getTitle() + " successfully removed from shelf " + this.subject);
            return Code.SUCCESS;
        }
    }

    public String listBooks() {
        int count = 0;
        for (int val : books.values()) {
            count += val;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(count);
        if(count == 1){
            sb.append(" book on shelf: ").append(this.shelfNumber).append(": ").append(this.subject);
        }else{
            sb.append(" books on shelf: ").append(this.shelfNumber).append(": ").append(this.subject);
        }

        for (Book book : books.keySet()){
            sb.append("\n").append(book.toString()).append(" ").append(books.get(book));
        }

        return sb.toString();
    }
}
