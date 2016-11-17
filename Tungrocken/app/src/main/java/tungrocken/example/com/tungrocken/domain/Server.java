package tungrocken.example.com.tungrocken.domain;

/**
 * Created by Team Tungrocken
 */

public class Server {
    String serverUrl = "http://10.16.5.58:8080/Projectserver";
    //String serverUrl = "http://158.38.22.11:8080/Projectserver";
//10.16.5.58     158.38.195.15      158.38.22.23
    public Server() {
    }

    public String serverUrl() {
        return serverUrl;
    }
}
