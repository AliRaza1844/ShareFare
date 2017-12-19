package sharefare.aliraza.com;

/**
 * Created by Hina Ghafoor on 12/17/2017.
 */

public class Journey {
    private String pin_location;
    private String destination;
    private String time;
    private String date;
    private String vehicle;
    private int seats;
    private float fare;

    public Journey(){}

    public Journey(String pin_location, String destination, String time, String date, String vehicle, int seats, float fare) {
        this.pin_location = pin_location;
        this.destination = destination;
        this.time = time;
        this.date = date;
        this.vehicle = vehicle;
        this.seats = seats;
        this.fare = fare;
    }

    public void setPin_location(String pin_location) {
        this.pin_location = pin_location;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public String getPin_location() {
        return pin_location;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getVehicle() {
        return vehicle;
    }

    public int getSeats() {
        return seats;
    }

    public float getFare() {
        return fare;
    }
}
