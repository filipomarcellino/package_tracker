package cmpt213.assignment4.packagedeliveries.control;

import cmpt213.assignment4.packagedeliveries.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment4.packagedeliveries.model.Book;
import cmpt213.assignment4.packagedeliveries.model.Electronic;
import cmpt213.assignment4.packagedeliveries.model.Package;
import cmpt213.assignment4.packagedeliveries.model.Perishable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class PackageDeliveriesTracker accesses the array list from a json file, stores it, and sends it back to list.json
 */
public class PackageManager {
    private static ArrayList<Package> packages;
    private final Gson myGson;
    private static HttpURLConnection connection;

    /**
     * default constructor for a PackageManger (creates a myGson)
     */
    public PackageManager(){
        RuntimeTypeAdapterFactory<Package> packageAdapterFactory = RuntimeTypeAdapterFactory.of(Package.class, "type");
        packageAdapterFactory.registerSubtype(Book.class, "Book");
        packageAdapterFactory.registerSubtype(Perishable.class, "Perishable");
        packageAdapterFactory.registerSubtype(Electronic.class, "Electronic");

        myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }
                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).registerTypeAdapterFactory(packageAdapterFactory).setPrettyPrinting().create();


    }

    /**
     * method to communicate request with server, the request is list all
     */
    public ArrayList<Package> requestListAll() {
        BufferedReader reader;
        try{
            URL url = new URL("http://localhost:8080/listAll");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNullElseGet(packages, ArrayList::new);
    }

    /**
     * method to communicate request with server, the request is add book
     */
    public ArrayList<Package> requestAddBook(String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String packageType, String author) {
        BufferedReader reader;
        String jsonInputString = "{\"author\": " + "\"" + author + "\"" + ", \"type\": " + "\"" + packageType + "\"" + ", \"name\": "  + "\"" + name  + "\"" + ", \"notes\": "  + "\"" + notes  + "\"" + ", \"price\": " + price + ", \"weight\": " + weight + ", \"delivered\": false, \"expectedDeliveryDate\": "  + "\"" + expectedDeliveryDate  + "\"" + "}";
        try{
            URL url = new URL("http://localhost:8080/addBook");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }


            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method to communicate request with server, the request is add perishable
     */
    public ArrayList<Package> requestAddPerishable(String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String packageType, LocalDateTime expiryDate) {
        BufferedReader reader;
        String jsonInputString = "{\"expiryDate\": " + "\"" + expiryDate + "\"" + ", \"type\": " + "\"" + packageType + "\"" + ", \"name\": "  + "\"" + name  + "\"" + ", \"notes\": "  + "\"" + notes  + "\"" + ", \"price\": " + price + ", \"weight\": " + weight + ", \"delivered\": false, \"expectedDeliveryDate\": "  + "\"" + expectedDeliveryDate  + "\"" + "}";

        try{
            URL url = new URL("http://localhost:8080/addPerishable");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }


            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method to communicate request with server, the request is add electronic
     */
    public ArrayList<Package> requestAddElectronic(String name, String notes, double price, double weight, LocalDateTime expectedDeliveryDate, String packageType, double fee) {
        BufferedReader reader;
        String jsonInputString = "{\"fee\": " + fee + ", \"type\": " + "\"" + packageType + "\"" + ", \"name\": "  + "\"" + name  + "\"" + ", \"notes\": "  + "\"" + notes  + "\"" + ", \"price\": " + price + ", \"weight\": " + weight + ", \"delivered\": false, \"expectedDeliveryDate\": "  + "\"" + expectedDeliveryDate  + "\"" + "}";
        try{
            URL url = new URL("http://localhost:8080/addElectronic");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }


            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method to communicate request with server, the request is remove a package
     */
    public ArrayList<Package> requestRemove(long id) {
        BufferedReader reader;
        String command = "http://localhost:8080/removePackage" + "/" + id;
        try{
            URL url = new URL(command);
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method to communicate request with server, the request is to list all upcoming packages
     */
    public ArrayList<Package> requestListUpcoming() {
        BufferedReader reader;
        try{
            URL url = new URL("http://localhost:8080/listUpcomingPackage");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method to communicate request with server, the request is to list all overdue packages
     */
    public ArrayList<Package> requestListOverdue() {
        BufferedReader reader;
        try{
            URL url = new URL("http://localhost:8080/listOverduePackage");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method to communicate request with server, the request is to mark package as delivered or undelivered
     */
    public ArrayList<Package> requestMarkAsDelivered(long id) {
        BufferedReader reader;
        String command = "http://localhost:8080/markPackageAsDelivered" + "/" + id;
        try{
            URL url = new URL(command);
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packages == null){
            return new ArrayList<>();
        }
        return packages;
    }

    /**
     * method that's called when program exits (serializes object to JSON)
     */
    public void exitProgram() {
        BufferedReader reader;
        try{
            URL url = new URL("http://localhost:8080/exit");
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packages =  myGson.fromJson(reader, packageListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

