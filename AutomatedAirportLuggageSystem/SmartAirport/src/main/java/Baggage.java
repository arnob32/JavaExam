package SmartAirport.src.main.java;



public class Baggage {
    private final int id;
    private final String destination;
    private String status = "Pending";

    public Baggage(int id, String destination) {
        this.id = id;
        this.destination = destination;
    }

    public int getId() { return id; }
    public String getDestination() { return destination; }

    public void updateStatus(String newStatus) { this.status = newStatus; }
    public String getStatus() { return status; }
}

