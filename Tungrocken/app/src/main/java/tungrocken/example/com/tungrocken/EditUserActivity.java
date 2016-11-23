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
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import tungrocken.example.com.tungrocken.Loaders.LoadUsers;
import tungrocken.example.com.tungrocken.domain.HamburgerMenu;
import tungrocken.example.com.tungrocken.domain.Server;
import tungrocken.example.com.tungrocken.domain.User;

/**
 * Created by Team Tungrocken
 */

public class EditUserActivity extends AppCompatActivity {

    Server s = new Server();
    final String ip = s.serverUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Oppsett av toolbar - Må brukes av alle aktiviteter utenom hovedsiden
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.toolbarlogo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Tar i mot hvilken bruker som er pålogget, sendt gjennom intenten
        Intent intent = getIntent();
        User u = (User) intent.getSerializableExtra("bruker");

        EditText fname = (EditText)findViewById(R.id.editfirstName);
        fname.setText(Html.fromHtml(u.getFirstName()));
        EditText lname = (EditText)findViewById(R.id.editlastName);
        lname.setText(Html.fromHtml(u.getLastName()));
        EditText pword = (EditText)findViewById(R.id.editpassword);
        TextView editInfo = (TextView)findViewById(R.id.editregister_info);
        editInfo.setText(Html.fromHtml("<h2>Her kan du endre dine brukerdata.</h2><br>Alle feltene <strong>må</strong> være utfylte." +
                "<br>Du <strong>må</strong> skrive inn passord, enten ditt gamle - eller et nytt!"));

        final Button editButton = (Button) findViewById(R.id.edit_u_btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                String email = u.getEmail();
                String password = pword.getText().toString();

                if((firstName.isEmpty()|| lastName.isEmpty() || password.isEmpty())){
                    Toast.makeText(EditUserActivity.this, "Alle feltene må fylles ut!",
                            Toast.LENGTH_LONG).show();

                }else {
                    String Data = "?email="+email+"&firstname="+firstName+"&lastname="+lastName+"&password="+password+"";
                    new LoadUsers(new LoadUsers.Callback() {
                        @Override
                        public void update(final List<User> users) {
                            Intent a = new Intent(EditUserActivity.this, MyPageActivity.class);
                            User u = users.get(0);
                            SharedRespources.getInstance().setUser(u);
                            startActivity(a);
                        }
                    }).execute(ip+"/services/app/edituser"+Data+"");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
