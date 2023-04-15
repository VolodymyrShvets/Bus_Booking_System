package com.booking.system.utility;

import com.booking.system.model.Bus;
import com.booking.system.repository.BusRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utility {
    // busUniqueName contains 3 digits + uppercase letter
    public static String busUniqueNameGenerator() {
        Random random = new Random();
        int x = random.nextInt(1000);
        char c = (char) (random.nextInt(26) + 'A');
        return String.format("%03d%c", x, c);
    }

    public static void saveBuses(BusRepository repository, String jsonPath) throws Exception {
        clearBusCollection(repository);

        //String jsonPath = "src/main/resources/buses.json";
        List<Bus> busses;

        busses = loadBusesFromJson(jsonPath);

        repository.saveAll(busses);
    }

    public static void saveBuses(BusRepository repository, List<Bus> buses) {
        clearBusCollection(repository);
        trimBusCollection(repository);
        repository.saveAll(buses);
    }

    public static void trimBusCollection(BusRepository repository) {
        repository.deleteAllByDepartureTimeBefore(LocalDateTime.now());
    }

    public static void clearBusCollection(BusRepository repository) {
        repository.deleteAll();
    }

    public static List<Bus> loadBusesFromJson(String jsonPath) throws Exception {
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
            bus.setTicketPrice((Long) jo.get("ticketPrice"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            bus.setDepartureTime(LocalDateTime.parse((String) jo.get("departureTime"), formatter));
            bus.setArrivalTime(LocalDateTime.parse((String) jo.get("arrivalTime"), formatter));
            bus.setTravelTime();

            buses.add(bus);
        }

        return buses;
    }

    public static void writeBusesToJson(String jsonPath) throws Exception {
        List<Bus> buses = generateBuses();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        JSONArray list = new JSONArray();

        FileWriter file = new FileWriter(jsonPath);
        file.write("[");

        for (Bus bus : buses) {
            JSONObject obj = new JSONObject();

            obj.put("name", bus.getName());
            obj.put("departureCity", bus.getDepartureCity());
            obj.put("arrivalCity", bus.getArrivalCity());
            obj.put("departureTime", bus.getDepartureTime().format(formatter));
            obj.put("arrivalTime", bus.getArrivalTime().format(formatter));
            obj.put("ticketPrice", bus.getTicketPrice());
            obj.put("availableSeats", bus.getAvailableSeats());

            //list.add(obj);
            file.write(obj.toJSONString() + ',');
        }

        file.write("]");
    }

    public static List<Bus> generateBuses() {
        List<Bus> list = new ArrayList<>();
        String[] cityPairs = {
                "Kyiv-Kharkiv-2-700-2", "Kyiv-Dnipro-2-690-2", "Kyiv-Mykolaiv-1-950-4", "Kyiv-Rivne-2-370-2", "Kyiv-Lviv-3-470-4",
                "Kharkiv-Kyiv-2-850-2", "Kharkiv-Dnipro-2-500-2",
                "Dnipro-Kharkiv-2-350-2", "Dnipro-Kyiv-2-691-2", "Dnipro-Mykolaiv-2-400-2", "Dnipro-Odessa-2-1040-3",
                "Lviv-Kyiv-3-380-4", "Lviv-Rivne-2-330-1", "Lviv-Odessa-1-690-3",
                "Rivne-Lviv-2-200-1", "Rivne-Kyiv-2-340-2", "Rivne-Odessa-1-580-2",
                "Odessa-Rivne-1-580-2", "Odessa-Lviv-1-600-3", "Odessa-Dnipro-2-990-3-3", "Odessa-Mykolaiv-3-190-1",
                "Mykolaiv-Odessa-3-170-1", "Mykolaiv-Kyiv-1-1030-4", "Mykolaiv-Dnipro-2-430-2"};
        Random rnd = new Random();
        LocalDate today = LocalDate.now();
        Bus bus;

        for (int k = 0; k < 30; k++) {
            for (String cityPair : cityPairs) {
                String[] val = cityPair.split("-");

                for (int j = 0; j < Integer.parseInt(val[2]); j++) {
                    bus = new Bus();

                    bus.setName(busUniqueNameGenerator());

                    bus.setDepartureCity(val[0]);
                    bus.setArrivalCity(val[1]);

                    LocalDateTime[] dates = generateDate(Integer.parseInt(val[2]), j + 1, Integer.parseInt(val[4]), rnd, today);
                    bus.setDepartureTime(dates[0]);
                    bus.setArrivalTime(dates[1]);

                    bus.setAvailableSeats(generateSeats(rnd.nextInt(20) + 1, rnd));

                    bus.setTravelTime();

                    bus.setTicketPrice(generateTicketPrice(Long.parseLong(val[3]), rnd));

                    list.add(bus);
                }
            }
            today = today.plusDays(1);
        }

        return list;
    }

    public static List<Integer> generateSeats(int limit, Random rnd) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < limit; i++) {
            set.add(rnd.nextInt(20) + 1);
        }

        List<Integer> sorted = new ArrayList<>(set);
        Collections.sort(sorted);

        return sorted;
    }

    public static long generateTicketPrice(long num, Random rnd) {
        return (long) (num - num * (rnd.nextFloat(10f) / 100.0f));
    }

    // duration: 1-fast 2-medium 3-long 4-superLong
    // count: 1, 2, 3
    public static LocalDateTime[] generateDate(int count, int order, int duration, Random rnd, LocalDate dateNow) {
        LocalDateTime[] dates = new LocalDateTime[2];
        LocalTime time = LocalTime.now();

        switch (count) {
            case 1 -> time = getRandomTime(LocalTime.of(1, 15), LocalTime.of(23, 55));
            case 2 ->
                    time = order == 1 ? getRandomTime(LocalTime.of(1, 25), LocalTime.of(12, 55)) : getRandomTime(LocalTime.of(13, 0), LocalTime.of(23, 55));
            case 3 -> {
                if (order == 1)
                    time = getRandomTime(LocalTime.of(1, 15), LocalTime.of(8, 35));
                else if (order == 2)
                    time = getRandomTime(LocalTime.of(8, 40), LocalTime.of(16, 20));
                else
                    time = getRandomTime(LocalTime.of(16, 30), LocalTime.of(23, 45));
            }
        }

        dates[0] = LocalDateTime.of(dateNow.getYear(), dateNow.getMonth(), dateNow.getDayOfMonth(), time.getHour(), time.getMinute());
        int timeOnTheRoad = 0;
        switch (duration) {
            case 1 -> timeOnTheRoad = 150;
            case 2 -> timeOnTheRoad = 210;
            case 3 -> timeOnTheRoad = 350;
            case 4 -> timeOnTheRoad = 540;
        }
        dates[1] = dates[0].plusMinutes(rnd.nextInt(60) + timeOnTheRoad);

        return dates;
    }

    public static LocalTime getRandomTime(LocalTime start, LocalTime end) {
        long totalNanos = Math.abs(ChronoUnit.NANOS.between(start, end));
        long randomNanos = ThreadLocalRandom.current().nextLong(totalNanos);

        LocalTime firstTime = end.isAfter(start) ? start : end;

        return firstTime.plusNanos(randomNanos);
    }

    public static String capitalize(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
