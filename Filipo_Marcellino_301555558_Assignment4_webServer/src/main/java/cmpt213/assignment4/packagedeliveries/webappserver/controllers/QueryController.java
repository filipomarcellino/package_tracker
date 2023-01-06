package cmpt213.assignment4.packagedeliveries.webappserver.controllers;
import cmpt213.assignment4.packagedeliveries.webappserver.control.PackageManager;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Book;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Electronic;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Package;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Perishable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class QueryController manages all the requests coming from the client side and responds accordingly
 */
@RestController
public class QueryController {
    private static PackageManager packageManager = new PackageManager();
    private static ArrayList<Package> packages = packageManager.getArrayList();
    private AtomicLong nextId = new AtomicLong();

    /**
     * method that responds a ping with a string
     */
    @GetMapping("/ping")
    public String getPingMessage(){
        return "System is up!";
    }

    /**
     * get endpoint that returns the list of all the pacakages in the array list
     */
    @GetMapping("/listAll")
    public ArrayList<Package> listAllPackages(){
        Collections.sort(packages);
        return packages;
    }

    /**
     * post endpoint that creates a book and adds it to the arraylist
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addBook")
    public ArrayList<Package> createNewPackage(@RequestBody Book book){
        book.setId(nextId.incrementAndGet());
        packages.add(book);
        Collections.sort(packages);
        return packages;
    }

    /**
     * post endpoint that creates a perishable and adds it to the arraylist
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addPerishable")
    public ArrayList<Package> createNewPackage(@RequestBody Perishable perishable){
        perishable.setId(nextId.incrementAndGet());
        packages.add(perishable);
        Collections.sort(packages);
        return packages;
    }

    /**
     * post endpoint that creates an electronic and adds it to the arraylist
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addElectronic")
    public ArrayList<Package> createNewPackage(@RequestBody Electronic electronic){
        electronic.setId(nextId.incrementAndGet());
        packages.add(electronic);
        Collections.sort(packages);
        return packages;
    }

    /**
     * post endpoint that creates an electronic and adds it to the arraylist
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/removePackage/{id}")
    public ArrayList<Package> removePackage(@PathVariable("id") long packageId){
        Collections.sort(packages);
        for (int i = 0; i < packages.size(); i++){
            if(packages.get(i).getId() == packageId){
                packages.remove(i);
                return packages;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * get endpoint that returns an array list of all overdue packages
     */
    @GetMapping("/listOverduePackage")
    public ArrayList<Package> listOverduePackages(){
        Collections.sort(packages);
        ArrayList<Package> overdue = new ArrayList<>();
        for (Package aPackage : packages) {
            if (aPackage.getExpectedDeliveryDate().isBefore(LocalDateTime.now()) && !aPackage.getDelivered()) {
                overdue.add(aPackage);
            }
        }
        return overdue;
    }

    /**
     * get endpoint that returns an array list of all upcoming packages
     */
    @GetMapping("/listUpcomingPackage")
    public ArrayList<Package> listUpcomingPackage(){
        Collections.sort(packages);
        ArrayList<Package> upcoming = new ArrayList<>();
        for (Package aPackage : packages) {
            if (aPackage.getExpectedDeliveryDate().isAfter(LocalDateTime.now()) && !aPackage.getDelivered()) {
                upcoming.add(aPackage);
            }
        }
        return upcoming;
    }

    /**
     * get endpoint that responds to window closing and saves array list to list.json
     */
    @GetMapping("/exit")
    public void exitProgram(){
        packageManager.exitProgram();
    }

    /**
     * post endpoint that marks delivered as true
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/markPackageAsDelivered/{id}")
    public ArrayList<Package> markAsDelivered(@PathVariable("id") long packageId){
        Collections.sort(packages);
        //get the package first
        for (Package aPackage : packages){
            if(aPackage.getId() == packageId){
                if(aPackage.getDelivered()){
                    aPackage.markAsUndelivered();
                }
                else {
                    aPackage.markAsDelivered();
                }
                return packages;
            }
        }
        throw new IllegalArgumentException();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {

    }


}
