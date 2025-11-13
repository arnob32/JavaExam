package SmartAirport.src.main.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AGV {

    private final int id;
    private final String name;
    private double batteryLevel = 100.0;
    private Baggage carryingBaggage;
    private boolean available = true;
    private final LogService logService;
    
 // Thread pool to run AGVs concurrently
    private ExecutorService agvExecutor = Executors.newFixedThreadPool(5);

    public AGV(int id, String name, LogService logService) {
        this.id = id;
        this.name = name;
        this.logService = logService;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

 
    public void moveToDestination(String destination) {
        logService.writeRecord("agv", "agv" + id, name + " moving to " + destination);

     
        batteryLevel -= 20.0;
        if (batteryLevel < 0) batteryLevel = 0;

     
        if (batteryLevel <= 0) {
            available = false;
            logService.writeRecord("agv", "agv" + id,
                    name + " battery depleted to 0%. Marked as unavailable.");
        }

        logService.writeRecord("agv", "agv" + id,
                name + " reached " + destination + " | Battery: " + batteryLevel + "%");
    }

  
    
    public void loadBaggage(Baggage baggage) {
        carryingBaggage = baggage;
        available = false;
        logService.writeRecord("agv", "agv" + id, name + " loaded baggage " + baggage.getId());
    }

   
    public void unloadBaggage(StorageArea storage) {
        if (carryingBaggage != null) {
            storage.storeBaggage(carryingBaggage);
            logService.writeRecord("agv", "agv" + id,
                    name + " unloaded baggage " + carryingBaggage.getId() + " into storage.");
            carryingBaggage = null;
        }
        available = true;
    }

   
    public void chargeBattery(ChargingStation station) {
        logService.writeRecord("agv", "agv" + id,
                name + " sent for charging at " + station.showStatus());
        station.chargeAGV(this);
        available = true;
    }

  
    public String showStatus() {
        String status = available ? "Free" : "Busy";
        if (batteryLevel <= 0) status = "Needs Charging ⚠️";
        return name + " | Battery: " + String.format("%.0f", batteryLevel) + "% | Status: " + status;
    }

  
    public void setBatteryLevel(double level) {
        this.batteryLevel = Math.max(0, Math.min(100, level));
        if (this.batteryLevel <= 0) {
            this.available = false;
            logService.writeRecord("agv", "agv" + id,
                    name + " battery reached 0%. AGV set to unavailable.");
        } else if (this.batteryLevel == 100 && !available) {
            this.available = true;
            logService.writeRecord("agv", "agv" + id,
                    name + " fully charged. Now available again.");
        }
    }

   
    public double getBatteryLevel() {
        return batteryLevel;
    }
}