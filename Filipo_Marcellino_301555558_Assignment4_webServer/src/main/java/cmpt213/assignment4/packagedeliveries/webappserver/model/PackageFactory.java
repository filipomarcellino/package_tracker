package cmpt213.assignment4.packagedeliveries.webappserver.model;


import java.time.LocalDateTime;

/**
 * Class Menu is used for determining and returning a subclass instance of Package
 */
public class PackageFactory {

    /**
     * a method to return a Book object
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param expectedDeliveryDate
     * @param packageType
     * @param author
     * @return a Book object which attributes corresponding the function parameters
     */
    public static Package getInstance(String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String packageType, String author){
        if(packageType == null){
            return null;
        }
        return new Book(packageType, name, notes, price, weight, expectedDeliveryDate, author);
    }

    /**
     * a method to return an Electronic object
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param expectedDeliveryDate
     * @param packageType
     * @param fee
     * @return an Electronic object which attributes corresponding the function parameters
     */
    public static Package getInstance(String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String packageType, double fee){
        if(packageType == null){
            return null;
        }
        return new Electronic(packageType, name, notes, price, weight, expectedDeliveryDate, fee);
    }

    /**
     * a method to return a Perishable object
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param expectedDeliveryDate
     * @param packageType
     * @param expiryDate
     * @return a Perishable object which attributes corresponding the function parameters
     */
    public static Package getInstance(String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String packageType, LocalDateTime expiryDate){
        if(packageType == null){
            return null;
        }

        return new Perishable(packageType, name, notes, price, weight, expectedDeliveryDate, expiryDate);
    }
}
