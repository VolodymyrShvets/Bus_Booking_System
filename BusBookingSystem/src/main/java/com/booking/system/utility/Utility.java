package com.booking.system.utility;

import com.booking.system.model.Bus;
import com.booking.system.repository.BusRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {
    // busUniqueName contains 3 digits + uppercase letter
    public static String busUniqueNameGenerator() {
        Random random = new Random();
        int x = random.nextInt(1000);
        char c = (char) (random.nextInt(26) + 'A');
        return String.format("%03d%c%n", x, c);
    }

    public static void saveBusses(BusRepository repository, String jsonPath) throws Exception {
        clearBusCollection(repository);

        //String jsonPath = "src/main/resources/busses.json";
        List<Bus> busses;

        busses = loadBussesFromJson(jsonPath);

        repository.saveAll(busses);
    }

    public static void clearBusCollection(BusRepository repository) {
        repository.deleteAll();
    }

    public static List<Bus> loadBussesFromJson(String jsonPath) throws Exception {
        JSONArray array = (JSONArray) new JSONParser().parse(new FileReader(jsonPath));

        List<Bus> buses = new ArrayList<>();
        Bus bus;

        for (Object o : array) {
            JSONObject jo = (JSONObject) o;
            bus = new Bus();

            bus.setId((String) jo.get("id"));
            bus.setName((String) jo.get("name"));
            bus.setDepartureCity((String) jo.get("departureCity"));
            bus.setArrivalCity((String) jo.get("arrivalCity"));

            JSONArray seats = (JSONArray) jo.get("availableSeats");
            bus.setAvailableSeats(seats);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            bus.setDepartureTime(LocalDateTime.parse((String) jo.get("departureTime"), formatter));
            bus.setArrivalTime(LocalDateTime.parse((String) jo.get("arrivalTime"), formatter));

            buses.add(bus);
        }

        return buses;
    }
}
