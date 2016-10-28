package tungrocken.example.com.tungrocken;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Team Tungrocken on 28.10.2016.
 */

public class hamburgerMenu {

    public hamburgerMenu() {
    }

    public Intent getHamburgerMenu(int id, Context c) {

        Intent result = null;

        switch(id){
            case R.id.action_menu0:
                Intent i1 = new Intent(c, MainActivity.class);
                result = i1;
                break;

            case R.id.action_menu1:
                Intent i2 = new Intent(c, OmTungrocken.class);
                result = i2;
                break;
        }

        return result;
    }
}
