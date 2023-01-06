package cmpt213.assignment4.packagedeliveries.webappserver.control;

import cmpt213.assignment4.packagedeliveries.webappserver.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Book;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Electronic;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Package;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Perishable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class PackageManager accesses the array list from a json file, stores it, sends it back and forth with the client side,  and sends it back to list.json
 */
public class PackageManager {
    private static ArrayList<Package> packages;
    private final Gson myGson;

    /**
     * default constructor for a PackageManger (deserializes JSON to object)
     */
    public PackageManager(){
        RuntimeTypeAdapterFactory<Package> packageAdapterFactory = RuntimeTypeAdapterFactory.of(Package.class, "type");
        packageAdapterFactory.registerSubtype(Book.class, "Book");
        packageAdapterFactory.registerSubtype(Perishable.class, "Perishable");
        packageAdapterFactory.registerSubtype(Electronic.class, "Electronic");

        File theFile = new File("list.json");
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
        if(theFile.exists()){
            try (FileReader reader = new FileReader(theFile)) {
                // Convert JSON File to Java Object
                Type packageListType = new TypeToken<ArrayList<Package>>(){}.getType();
                packages =  myGson.fromJson(reader, packageListType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            packages = new ArrayList<>();
        }


    }

    /**
     * method that's called when program exits (serializes object to JSON and stores it in list.json)
     */
    public void exitProgram(){
        try (FileWriter writer = new FileWriter("list.json")) {
            myGson.toJson(packages, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the ArrayList stored
     * @return packages
     */
    public  ArrayList<Package> getArrayList(){
        return packages;
    }
}
