package tungrocken.example.com.tungrocken;

import tungrocken.example.com.tungrocken.domain.User;

/**
 * Created by Team Tungrocken
 */

public class SharedRespources {
    private static SharedRespources ourInstance;
    private static User user;

    private SharedRespources() {
    }

    public static SharedRespources getInstance() {
        if(ourInstance == null) {
            ourInstance = new SharedRespources();
        }
        return ourInstance;
    }

    public static User getUser()
    {
        if(user == null) {
            return null;
        }
        else {
            return user;
        }
    }

    public static void setUser(User inputUser) {
        user = inputUser;
    }
}
