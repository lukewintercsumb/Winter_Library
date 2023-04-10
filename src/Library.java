import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

/**
 * Author: Luke Winter
 * Project: Project 01 Part 04/04: Library.java
 * Date: March 26th 2023
 * Description: Implementation of the Library class
 */
public class Library {
    //---MEMBERS---
    public static final int LENDING_LIMIT = 5;
    private String name;
    private static int libraryCard;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    //---INITIALIZERS---
    //TODO: finish the init method
    public Library(String name){
        this.name = name;
        readers = new ArrayList<>();
        shelves = new HashMap<>();
        books = new HashMap<>();
    }
    public Code init(String filename){
        File f = new File(filename);
        Scanner fileIn;

        try {
            fileIn = new Scanner(f);
        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        //INITIALIZE BOOKS
        //TODO: figure out what value to pass for the second Code parameter
        int recordCount = convertInt(fileIn.nextLine(), Code.BOOK_COUNT_ERROR);
        //TODO: maybe it is less then or equal to 0 on the next line
        if(recordCount < 0){
            return getCodeFromInt(recordCount);
        }

        //Parse through the books
        initBooks(recordCount, fileIn);

        listBooks();

        //INITIALIZE SHELVES
        recordCount = convertInt(fileIn.nextLine(), Code.SHELF_COUNT_ERROR);
        //TODO: maybe it is less then or equal to 0 on the next line
        if(recordCount < 0){
            return getCodeFromInt(recordCount);
        }

        //Parse through the shelves
        //TODO: initShelves returns a Code but I don't do anything with the Code object
        initShelves(recordCount, fileIn);
        listShelves(true);

        //INITIALIZE READERS
        recordCount = convertInt(fileIn.nextLine(), Code.READER_COUNT_ERROR);
        if(recordCount < 0){
            return getCodeFromInt(recordCount);
        }
        //Parse through the readers
        initReader(recordCount, fileIn);
        listReaders(true);
        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner fileIn){
        if(bookCount < 1){
            return Code.LIBRARY_ERROR;
        }

        /**
         * Iterate through the records in the CSV file converting each line to a book.
         * Use the constant fields in the Book object to identify the indexes of the relevant data from the CSV file.
         */
        for(int i = 0; i < bookCount; i++){
            String[] details = fileIn.nextLine().split(",");
            String isbn = details[Book.ISBN_];
            String title = details[Book.TITLE_];
            String subject = details[Book.SUBJECT_];
            String author = details[Book.AUTHOR_];
            int pageCount = convertInt(details[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR);
            if(pageCount < 0){
                return Code.PAGE_COUNT_ERROR;
            }
            //TODO: Figure out if the variable below should be a Date or LocalDate
            LocalDate date = convertDate(details[Book.DUE_DATE_], Code.DUE_DATE_ERROR);
            if(date == null){
                return Code.DATE_CONVERSION_ERROR;
            }
            addBook(new Book(isbn, title, subject, pageCount, author, date));
        }
        return Code.SUCCESS;
    }

    private Code initShelves(int shelfCount, Scanner fileIn){
        if(shelfCount < 1){
            return Code.SHELF_COUNT_ERROR;
        }

        for(int i = 0; i < shelfCount; i++){
            String[] details = fileIn.nextLine().split(",");
            int shelveNumber = convertInt(details[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR);
            if(shelveNumber < 0){
                return Code.SHELF_NUMBER_PARSE_ERROR;
            }
            String subject = details[Shelf.SUBJECT_];
            addShelf(new Shelf(subject, shelveNumber));
        }

        if(shelves.size() == shelfCount){
            return Code.SUCCESS;
        }else{
            System.out.println("Number of shelves doesn't match expected");
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }
    }

    private Code initReader(int readerCount, Scanner fileIn){
        if(readerCount <= 0){
            return Code.READER_COUNT_ERROR;
        }
        for(int i = 0; i < readerCount; i++){
            String[] details = fileIn.nextLine().split(",");
            int cardNumber = convertInt(details[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR);
            String name = details[Reader.NAME_];
            String phone = details[Reader.PHONE_];
            Reader reader = new Reader(cardNumber, name, phone);
            readers.add(reader);
            int bookCount = convertInt(details[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR);
            if(bookCount < 0){
                return Code.BOOK_COUNT_ERROR;
            }
            //TODO: it might be k += 2 and not k++ because I want to get the ISBNS and skip the DUE DATES
            for(int k = Reader.BOOK_START_; k < Reader.BOOK_COUNT_ + bookCount; k += 2){
                Book b = getBookByISBN(details[k]);
                if(b == null){
                    System.out.println("ERROR");
                    continue;
                }
                LocalDate date = convertDate(details[k + 1], Code.DATE_CONVERSION_ERROR);
                b.setDueDate(date);
                checkOutBook(reader, b);
            }
        }
        return Code.SUCCESS;
    }

    public Shelf getShelf(Integer shelfNumber){
        for(Shelf s: shelves.values()){
            if(s.getShelfNumber() == shelfNumber){
                return s;
            }
        }
        System.out.println("No shelf Number " + shelfNumber + " found");
        return null;
    }

    public Shelf getShelf(String subject){
        for(String s: shelves.keySet()){
            if(s.equals(subject)){
                return shelves.get(s);
            }
        }
        System.out.println("No shelf for " + subject + " books");
        return null;
    }

    //---OTHER METHODS---
    public static int convertInt(String recordCountString, Code code){
        //TODO: not sure if error message is printed regardless of exception
        try{
            return Integer.parseInt(recordCountString);
        }catch(NumberFormatException e){
            System.out.println("Value which caused the error: " + recordCountString);
            switch (code) {
                case SHELF_COUNT_ERROR:
                    System.out.println("Error message: Error: Could not read number of shelves");
                case READER_COUNT_ERROR:
                    System.out.println("Error message: Error: Could not read number of readers");
                case BOOK_COUNT_ERROR:
                    System.out.println("Error message: Error: Could not read number of books");
                case PAGE_COUNT_ERROR:
                    System.out.println("Error message: Error: could not parse page count");
                case DATE_CONVERSION_ERROR:
                    System.out.println("Error message: Error: Could not parse date component");
                default:
                    System.out.println("Error message: Error: Unknown conversion error");
            }
            return code.getCode();
        }

    }

    //TODO: figure out why I am passing a Code to convertDate method
    public static LocalDate convertDate(String date, Code errorCode){
        if(date.equals("0000")){
            return LocalDate.of(1970, 1, 1);
        }

        String[] dateSplit = date.split("-");
        if(dateSplit.length != 3){
            System.out.println("ERROR: date conversion error, could not parse " + date);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }

        int year = convertInt(dateSplit[0], Code.DATE_CONVERSION_ERROR);
        int month = convertInt(dateSplit[1], Code.DATE_CONVERSION_ERROR);
        int day = convertInt(dateSplit[2], Code.DATE_CONVERSION_ERROR);

        boolean badDate = false;

        if(year < 0){
            System.out.println("Error converting date: Year " + year);
            badDate = true;
        }

        if(month < 0){
            System.out.println("Error converting date: Month " + month);
            badDate = true;
        }

        if(day < 0){
            System.out.println("Error converting date: Day " + day);
            badDate = true;
        }

        if(badDate){
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }

        return LocalDate.of(year, month, day);
    }

    public Code addBook(Book newBook){
        if(books.containsKey(newBook)){
            books.put(newBook, books.get(newBook) + 1);
            System.out.println(books.get(newBook) + " copies of " + newBook.getTitle() + " in the stacks");
        }else{
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }

        if(shelves.containsKey(newBook.getSubject())){
            //TODO: this might be an error, because I am not sure if I have to explicitly use the put method
            shelves.get(newBook.getSubject()).addBook(newBook);
            return Code.SUCCESS;
        }
        System.out.println("No shelf for " + newBook.getSubject() + " books");
        return Code.SHELF_EXISTS_ERROR;
    }

    //TODO: Not very sure about this method
    public Code addShelf(Shelf newShelf){
        //TODO: Figure out the condition
        if(shelves.containsKey(newShelf.getSubject())){
            System.out.println("ERROR: Shelf already exists " + newShelf.toString());
            return Code.SHELF_EXISTS_ERROR;
        }

        //Figuring out the shelf Number of the new shelf
        int largestNumber = 0;
        for(Shelf s: shelves.values()){
            if(s.getShelfNumber() > largestNumber){
                largestNumber = s.getShelfNumber();
            }
        }
        newShelf.setShelfNumber(largestNumber++);

        //Adding all the books with matching subject;
        for(Book b: books.keySet()){
            int amount = books.get(b);
            //TODO: potentially I want to make sure that amount > 0 in the condition below.
            if(b.getSubject().equals(newShelf.getSubject())){
                //If the shelf does not already contain this book, create the key and set to 0
                if(!newShelf.getBooks().containsKey(b)){
                    newShelf.getBooks().put(b, amount);
                }else{
                    newShelf.getBooks().put(b, newShelf.getBooks().get(b) + amount);
                }
            }
        }
        shelves.put(newShelf.getSubject(), newShelf);
        return Code.SUCCESS;
    }

    public Code addShelf(String shelfSubject){
        Shelf shelf = new Shelf(shelfSubject, shelves.size() + 1);
        addShelf(shelf);
        return Code.SUCCESS;
    }

    public Code checkOutBook(Reader reader, Book book){
        if(!readers.contains(reader)){
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }else if(reader.getBooks().size() >= LENDING_LIMIT){
            System.out.println(reader.getName() + " has reached the lending limit, (" + LENDING_LIMIT + ")");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }else if(!books.containsKey(book)){
            System.out.println("ERROR: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }else if(!shelves.containsKey(book.getSubject())){
            System.out.println("no shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }else if(shelves.get(book.getSubject()).getBooks().get(book) < 1){
            System.out.println("ERROR: no copies of " + book + " remain");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        //TODO: I might want to use the .equals method instead of !=
        Code c = reader.addBook(book);
        if(c != Code.SUCCESS){
            System.out.println("Couldn't checkout " + book);
            return c;
        }
        //TODO: I might need to use the put method again
        c = shelves.get(book.getSubject()).removeBook(book);
        if(c == Code.SUCCESS){
            System.out.println(book + " checked out successfully");
        }else{
            System.out.println("Something went horribly wrong :<");
        }
        return c;
    }

    public Code returnBook(Reader reader, Book book){
        if(!reader.getBooks().contains(book)){
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }else if(!books.containsKey(book)){
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        System.out.println(reader.getName() + " is returning " + book.toString());
        Code c = reader.removeBook(book);
        if(c == Code.SUCCESS){
            return returnBook(book);
        }
        System.out.println("Could not return " + book.toString());
        return c;
    }

    public Code returnBook(Book book){
        if(!shelves.containsKey(book.getSubject())){
            System.out.println("No shelf for " + book.toString());
            return Code.SHELF_EXISTS_ERROR;
        }
        shelves.get(book.getSubject()).addBook(book);
        return Code.SUCCESS;
    }

    public int listBooks(){
        int total = 0;
        for(Book book: books.keySet()){
            int value = books.get(book);
            total += value;
            System.out.println(value + " copies of " + book.getTitle() + " by " + book.getAuthor() + " ISBN: " + book.getIsbn());
        }
        return total;
    }

    public Code listShelves(boolean showBooks){
        for(Shelf s: shelves.values()){
            if(showBooks){
                s.listBooks();
            }else{
                s.toString();
            }
        }
        return Code.SUCCESS;
    }



    public int listReaders(){
        int numberOfReaders = 0;
        for(Reader r: readers){
            System.out.println(r.toString());
            numberOfReaders++;
        }
        return numberOfReaders;
    }

    public int listReaders(boolean showBooks){
        int numberOfReaders = 0;
        if(showBooks){
            for(Reader r: readers){
                numberOfReaders++;
                System.out.println(r.getName() + " (#" + (numberOfReaders + 1) + ")  has the following books:");
                StringBuilder booksString = new StringBuilder();
                booksString.append("[");
                for(Book b: r.getBooks()){
                    booksString.append(b.getTitle()).append(" by ").append(b.getAuthor()).append(" ISBN:").append(b.getIsbn()).append(", ");
                }
                if(booksString.length() >= 2){
                    booksString.deleteCharAt(booksString.length() - 1);
                    booksString.deleteCharAt(booksString.length() - 2);
                }
                booksString.append("]");
                System.out.println(booksString.toString());
            }
        }else{
            for(Reader r: readers){
                numberOfReaders++;
                System.out.println(r.toString());
            }
        }
        return numberOfReaders;
    }


    public static Code getCodeFromInt(int codeValue) {
        for(Code c: Code.values()){
            if(c.getCode() == codeValue){
                return c;
            }
        }
        return Code.UNKNOWN_ERROR;
    }

    public Book getBookByISBN(String isbn){
        for(Book b: books.keySet()){
            if(b.getIsbn().equals(isbn)){
                return b;
            }
        }
        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    public Reader getReaderByCard(int cardNumber){
        for(Reader r: readers){
            if(r.getCardNumber() == cardNumber){
                return r;
            }
        }
        System.out.println("Could not find reader with card number #" + cardNumber);
        return null;
    }

}
