package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * class Perishable is a subclass of Package with expiryDate as additional information
 */
public class Perishable extends Package {
    private LocalDateTime expiryDate;

    /**
     * construct a Book object
     */
    public Perishable(){
        setType("Perishable");
    }

    /**
     * set expiry date
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return expiry date
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
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
     * @param expiryDate
     */
    public Perishable(String type, String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, LocalDateTime expiryDate){
        super(type, name, notes, price, weight, expectedDeliveryDate);
        this.expiryDate = expiryDate;
    }

    /**
     * @return a string to display the information of a Book object
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Pakage Type: Perishable \n" + super.toString() + "Expiry date: " + dtf.format(expiryDate) + "\n";
    }
}
