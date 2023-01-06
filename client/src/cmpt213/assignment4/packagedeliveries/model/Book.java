package cmpt213.assignment4.packagedeliveries.model;

import java.time.LocalDateTime;

/**
 * class Book is a subclass of Package with author as additional information
 */
public class Book extends Package {
    private String author;

    /**
     * construct a Book object
     */
    public Book() {
        setType("Book");
    }

    /**
     * @return author
     */
//    public String getAuthor() {
//        return author;
//    }

    /**
     * method to set author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * construct a Book object
     *
     * @param type
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param expectedDeliveryDate
     * @param author
     */
    public Book(String type, String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String author){
        super(type, name, notes, price, weight, expectedDeliveryDate);
        this.author = author;
    }

    /**
     * @return a string to display the information of a Book object
     */
    @Override
    public String toString() {
        return "Package Type: Book \n" + super.toString() + "Author: " + author + "\n";
    }
}

