package tungrocken.example.com.tungrocken;

import android.content.Context;
import android.content.Intent;
import java.util.Date;
import tungrocken.example.com.tungrocken.domain.User;

/**
 * Created by Team Tungrocken on 28.10.2016.
 */

public class HamburgerMenu {

    public HamburgerMenu() {
    }

    public Intent getHamburgerMenu(int id, Context c) {

        Intent result = null;

        switch(id){
            case R.id.action_menu0:
                Intent i0 = new Intent(c, MainActivity.class);
                result = i0;
                break;

            case R.id.action_menu1:
                Intent i1 = new Intent(c, OmTungrocken.class);
                result = i1;
                break;

            case R.id.action_menu2:
                Intent i2 = new Intent(c, MyPageActivity.class);
                i2.putExtra("bruker", getTestUser());               // <---- getTestUser må endres til den reelle metoden for å hente ut pålogget bruker
                result = i2;
                break;
        }

        return result;
    }

    // metoder for å lage en fiktiv bruker, kun for testing. Må fjernes når skikkelig metode for å hente ut bruker er laget
    public User getTestUser() {
        User u = new User(Long.valueOf(123456), "sindre@sjoholt.net", "password", "Sindre", "Sjøholt", true, true, getDateCreated());
        return u;
    }

    public Date getDateCreated() {
        Date d = new Date();
        return d;
    }
}