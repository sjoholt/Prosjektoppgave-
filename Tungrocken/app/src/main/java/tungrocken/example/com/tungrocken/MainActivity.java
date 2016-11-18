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

        // Oppsett av toolbar (uten logoclicklistener)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.toolbarlogo);

        // demoknapp for artikkelvisning
        final Button btn2 = (Button) findViewById(R.id.articlesBtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Intent a = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(a);
            }
        });

        // demoknapp for artikkelvisning
        final Button btn3 = (Button) findViewById(R.id.reg_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Intent a = new Intent(MainActivity.this, RegisterUserActivity.class);
                startActivity(a);
            }
        });

        // demoknapp for login
        final Button btn4 = (Button) findViewById(R.id.login_btn);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Intent a = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(a);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);



        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Håndtering av visning og klikk på hamburgermeny
        HamburgerMenu hm = new HamburgerMenu();
        Intent i = hm.getHamburgerMenu(id, this.getApplicationContext());
        if(i != null) {
            startActivity(i);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
