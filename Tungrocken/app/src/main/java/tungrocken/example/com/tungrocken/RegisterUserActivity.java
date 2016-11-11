package tungrocken.example.com.tungrocken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import tungrocken.example.com.tungrocken.Loaders.LoadUsers;
import tungrocken.example.com.tungrocken.domain.HamburgerMenu;
import tungrocken.example.com.tungrocken.domain.Server;
import tungrocken.example.com.tungrocken.domain.User;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Server s = new Server();
        final String ip = s.serverUrl();


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

        // knapp for å registrere en bruker
        final Button btn3 = (Button) findViewById(R.id.reg_u_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                String firstName;
                String lastName;
                String email;
                String password;
                EditText fname = (EditText)findViewById(R.id.firstName);
                firstName = fname.getText().toString();
                EditText lname = (EditText)findViewById(R.id.lastName);
                lastName = lname.getText().toString();
                EditText e_mail = (EditText)findViewById(R.id.email);
                email = e_mail.getText().toString();
                EditText pword = (EditText)findViewById(R.id.password);
                password = pword.getText().toString();

                if((firstName.isEmpty()||lastName.isEmpty()||email.isEmpty()||password.isEmpty())==true){

                    Toast.makeText(RegisterUserActivity.this, "one of the fields are empty",
                            Toast.LENGTH_LONG).show();

                }else {

                    String Data = "?email="+email+"&password="+password+"&firstName="+firstName+"&lastName="+lastName+"";

                    new LoadUsers(new LoadUsers.Callback() {
                        @Override
                        public void update(final List<User> users) {
                            // Update ui

                            if(users.isEmpty() ==false) {
                                Intent a = new Intent(RegisterUserActivity.this, RegisterUserActivity.class);
                                startActivity(a);
                                // tenker å sende brukeren til home activity, eller mypage vi får se.
                            }

                        }
                    }).execute(ip+"/services/app/adduser"+Data+"");

                }
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
