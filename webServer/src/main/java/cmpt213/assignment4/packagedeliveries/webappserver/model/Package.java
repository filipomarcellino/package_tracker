package cmpt213.assignment4.packagedeliveries.webappserver.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * class Package defines a package with name, notes, price, weight, delivered, and expectedDeliveryDate
 */
public class Package implements Comparable<Package> {
    private long id;
    private String type;
    private String name;
    private String notes;
    private double price;
    private double weight;
    private boolean delivered;
    private LocalDateTime expectedDeliveryDate;

    /**
     * default constructor for a Package object
     */
    public Package(){

    }

    /**
     * construct a Package object
     *
     * @param type
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param expectedDeliveryDate
     */
    public Package(String type, String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate){
        this.type = type;
        this.name = name;
        this.notes = notes;
        this.weight = weight;
        this.price = price;
        this.expectedDeliveryDate = expectedDeliveryDate;
        delivered = false;
    }

    /**
     * returns the expected delivery date of an object
     * @return expectedDeliveryDate
     */
    public LocalDateTime getExpectedDeliveryDate(){
        return expectedDeliveryDate;
    }

    /**
     * returns the id of an object
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * returns the type of an object
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * returns the name of an object
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * returns the notes of an object
     * @return notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * returns the price of an object
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * returns the weight of an object
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * returns the state of delivered of an object
     * @return delivered
     */
    public boolean isDelivered() {
        return delivered;
    }

    /**
     * returns the boolean delivered of an object
     * @return delivered
     */
    public boolean getDelivered(){
        return delivered;
    }

    /**
     * sets delivered
     */
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    /**
     * sets id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * sets type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * sets name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * sets price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * sets weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * sets expected delivery date
     */
    public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    /**
     * set delivered to true
     */
    public void markAsDelivered(){
        this.delivered = true;
    }

    /**
     * set delivered to false
     */
    public void markAsUndelivered(){
        this.delivered = false;
    }

    /**
     *
     * @return a string to display the information of a Package object
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String yesOrNo = delivered ? "Yes" : "No";

        return "Package: " + name + '\n' +
                "Notes: " + notes + '\n' +
                "Price: $ " + price + '\n' +
                "Weight: " + weight + " kg" + '\n' +
                "Expected delivery date: " + dtf.format(expectedDeliveryDate) + '\n' +
                "Delivered? " + yesOrNo + '\n';
    }

    @Override
    public int compareTo(Package object) {
        return this.expectedDeliveryDate.compareTo(object.expectedDeliveryDate);
    }
}

