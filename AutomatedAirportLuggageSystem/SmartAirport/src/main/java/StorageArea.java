package SmartAirport.src.main.java;


import java.util.*;


public class StorageArea {
    private final int id;
    private final String location;
    private final int capacity;
    private final List<Baggage> storedBaggage = new ArrayList<>();

    public StorageArea(int id, String location, int capacity) {
        this.id = id;
        this.location = location;
        this.capacity = capacity;
    }

    public void storeBaggage(Baggage baggage) {
        if (storedBaggage.size() < capacity) storedBaggage.add(baggage);
    }

    public int getStoredCount() { return storedBaggage.size(); }
    public String showStatus() { return "StorageArea " + id + " (" + storedBaggage.size() + " items)"; }
}
