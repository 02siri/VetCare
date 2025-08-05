package au.edu.rmit.sept.webapp.models;

public class Clinic {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private double distance;

    public Clinic(String name, String address, double latitude, double longitude, double distance) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
}