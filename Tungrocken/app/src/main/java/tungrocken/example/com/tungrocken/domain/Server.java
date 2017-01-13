package tungrocken.example.com.tungrocken.domain;

/**
 * Created by Team Tungrocken
 */

public class Server {

    // AKTIVER RIKTIG IP-ADRESSE TIL KJØRENDE SERVER!

    //String serverUrl = "http://158.38.141.3:8080/Projectserver";        // Thomas Ethernet
    //String serverUrl = "http://10.16.5.58:8080/Projectserver";        // Thomas Wireless
    String serverUrl = "http://10.16.5.167:8080/Projectserver";        // Thomas Wireless

    public Server() {
    }

    public String serverUrl() {
        return serverUrl;
    }
}
