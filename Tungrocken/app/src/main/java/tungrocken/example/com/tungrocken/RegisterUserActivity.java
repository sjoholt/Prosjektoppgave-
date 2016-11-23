package tungrocken.example.com.tungrocken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import tungrocken.example.com.tungrocken.Loaders.LoadUsers;
import tungrocken.example.com.tungrocken.domain.Server;
import tungrocken.example.com.tungrocken.domain.User;

/**
 * Created by Team Tungrocken
 */

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Server s = new Server();
        final String ip = s.serverUrl();

        TextView info = (TextView)findViewById(R.id.register_info);
        info.setText(Html.fromHtml("<h2>Velkommen til brukerregistreringen. <br>Husk at <strong>alle</strong> feltene må være utfylt!</h2>"));

        // Oppsett av toolbar - Må brukes av alle aktiviteter utenom hovedsiden
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.toolbarlogo);

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
                    Toast.makeText(RegisterUserActivity.this, "Alle feltene må fylles ut!",
                            Toast.LENGTH_LONG).show();

                }else {
                    String Data = "?email="+email+"&password="+password+"&firstName="+firstName+"&lastName="+lastName+"";
                    new LoadUsers(new LoadUsers.Callback() {
                        @Override
                        public void update(final List<User> users) {
                            if(users.isEmpty() == false) {
                                Intent a = new Intent(RegisterUserActivity.this, LoginActivity.class);
                                User u = users.get(0);
                                a.putExtra("bruker", u);
                                startActivity(a);
                            } else {
                                Toast toast = Toast.makeText(RegisterUserActivity.this, "Det er allerede registrert en bruker med denne epost-addressen!",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                                toast.show();
                            }
                        }
                    }).execute(ip+"/services/app/adduser"+Data+"");
                }
            }
        });
    }
}
