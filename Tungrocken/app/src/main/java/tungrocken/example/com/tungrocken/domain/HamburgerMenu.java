package tungrocken.example.com.tungrocken.domain;

import android.content.Context;
import android.content.Intent;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import tungrocken.example.com.tungrocken.LoginActivity;
import tungrocken.example.com.tungrocken.MainActivity;
import tungrocken.example.com.tungrocken.MyPageActivity;
import tungrocken.example.com.tungrocken.OmTungrocken;
import tungrocken.example.com.tungrocken.R;
import tungrocken.example.com.tungrocken.SharedRespources;

/**
 * Created by Team Tungrocken on 28.10.2016.
 */

public class HamburgerMenu {

    Server s = new Server();
    final String ip = s.serverUrl();


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
                i2.putExtra("bruker", SharedRespources.getInstance().getUser());
                result = i2;
                break;

            case R.id.action_menu3:
                logoutMethod();
                Intent i3 = new Intent(c, MainActivity.class);
                result = i3;
                break;
        }
        return result;
    }

    public void logoutMethod(){
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "".toCharArray());
            }
        });
        SharedRespources.getInstance().setUser(null);
    }
}