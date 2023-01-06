package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;
/**
 * class Electronic is a subclass of Package with fee as additional information
 */
public class Electronic extends Package {
    private double fee;

    /**
     * construct a Electronic object
     */
    public Electronic(){
        setType("Electronic");
    }

    /**
     * set fee
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * @return fee
     */
    public double getFee() {
        return fee;
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
     * @param fee
     */
    public Electronic(String type, String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, double fee){
        super(type, name, notes, price, weight, expectedDeliveryDate);
        this.fee = fee;
    }

    /**
     * @return a string to display the information of an Electronic object
     */
    @Override
    public String toString() {
        return "Pakage Type: Electronic \n" + super.toString() + "Environmental handling Fee: " + fee + "\n";
    }
}