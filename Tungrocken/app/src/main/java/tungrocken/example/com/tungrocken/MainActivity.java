package tungrocken.example.com.tungrocken;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import tungrocken.example.com.tungrocken.domain.HamburgerMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedRespources.getInstance().getUser() == null)
        {
            Intent d = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(d);
        }
        else
        {
            Intent d = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(d);
        }
    }
}
