package Service;

import Requests.Request;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JsonInfo
{
    private ArrayList<String> womanName;
    private ArrayList<String> manName;
    private ArrayList<String> surnames;
    private ArrayList<Place> locations;

    JsonInfo()
    {
        womanName = new ArrayList<String>();
        manName = new ArrayList<String>();
        surnames = new ArrayList<String>();
        locations = new ArrayList<Place>();
        loadNames(womanName, "json/fnames.json");
        loadNames(manName, "json/mnames.json");
        loadNames(surnames, "json/snames.json");
        loadLocations(locations, "json/locations.json");
        System.out.println(locations.toString());
    }

    private void loadNames(ArrayList<String> nameType, String fileUrl)
    {
        try
        {
            File file = new File(fileUrl);
            FileReader fileRead = new FileReader(file);
            JsonReader jsonReader = new JsonReader(fileRead);
            jsonReader.beginObject();
            String name = jsonReader.nextName();

            if (!name.equals("data"))
            {
                throw new IOException("Incorrect head type");
            }
            jsonReader.beginArray();
            while (jsonReader.peek() == JsonToken.STRING)
            {
                nameType.add(jsonReader.nextString());
            }
        } catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }

    private void loadLocations(ArrayList<Place> locations, String fileUrl)
    {

        assert locations != null && fileUrl != null;
        try
        {
            File file = new File(fileUrl);
            FileReader fileRead = new FileReader(file);
            JsonReader jsonReader = new JsonReader(fileRead);
            jsonReader.beginObject();
            String name = jsonReader.nextName();
            if (!name.equals("data"))
            {
                throw new IOException("Incorrect head type");
            }
            jsonReader.beginArray();


            while (jsonReader.peek() == JsonToken.BEGIN_OBJECT)
            {
                jsonReader.beginObject();
                Place place = new Place();
                if (!jsonReader.nextName().equals("country"))
                {
                    throw new IOException("error");
                }
                place.setCountry(jsonReader.nextString());
                if (!jsonReader.nextName().equals("city"))
                {
                    throw new IOException("error");
                }
                place.setCity(jsonReader.nextString());
                if (!jsonReader.nextName().equals("latitude"))
                {
                    throw new IOException("error");
                }
                place.setLatitude(jsonReader.nextDouble());
                if (!jsonReader.nextName().equals("longitude"))
                {
                    throw new IOException("error");
                }
                place.setLongitude(jsonReader.nextDouble());
                locations.add(place);
                jsonReader.endObject();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }
}
