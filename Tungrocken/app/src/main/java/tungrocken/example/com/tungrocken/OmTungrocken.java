package tungrocken.example.com.tungrocken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.text.Html;

import tungrocken.example.com.tungrocken.domain.HamburgerMenu;

public class OmTungrocken extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_om_tungrocken);

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

        writeAboutContent();
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

    public void writeAboutContent() {
        TextView heading = (TextView)findViewById(R.id.aboutHeader);
        TextView about = (TextView)findViewById(R.id.aboutContent);

        String contentString = "<b>Tungrocken er en fiktiv musikkavis, laget for folk som liker rock av tyngre sort!</b>" +
                "<br><br>Det hele begynte som et gruppeprosjekt i faget Datamodellering og Databaseapplikasjoner på ingeniørstudiet på NTNU i Ålesund, " +
                "der fire unge og lovende studenter lagde et CMS (Content Management System) for Tungrocken." +
                "<br><br>Når det igjen var tid for gruppeprosjekt, denne gangen i faget Mobile og distribuerte applikasjoner, så ble Tungrocken dratt frem fra støvet." +
                "<br>Med andre ord så er dette bare et demo-prosjekt, og innholdet i appen er produsert kun med tanke på å skape en fungerende applikasjon med tilhørende " +
                "serverløsning for henting/publisering av innhold." +
                "<br><br><b>Hvem er vi?</b><br>Dette prosjektet er utarbeidet av:<br>" +
                "Sindre Sjøholt<br>Thomas Robert Tennøy<br>Fredrik Mikael Valderhaug<br>Gaute Hjellbakk Pettersen" +
                "<br><br><b>Lorem ipsum</b><br>" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean id faucibus velit, vel volutpat risus. Mauris ac eros ornare, dictum enim vel, varius risus. " +
                "Duis blandit id sem ac tempus. Curabitur faucibus, tellus eget venenatis feugiat, enim libero dapibus enim, vel ultricies felis orci a mauris. " +
                "Nulla dignissim interdum mauris, eu lobortis sem tempor a. Cras eu malesuada magna. Curabitur ac erat non diam commodo aliquet a tristique lacus. " +
                "Mauris scelerisque imperdiet mi, vitae semper enim sodales a. Nulla faucibus fermentum nulla. Morbi dictum risus neque. Fusce commodo laoreet varius.<br>" +
                "<br>" +
                "<b>Eget odeo posuere</b><br>In blandit leo eget odio posuere fringilla. Cras facilisis placerat leo quis ultricies. Cras et sodales velit. " +
                "In ex elit, dignissim non metus quis, pharetra porta augue. Nullam venenatis pretium lacus a tincidunt. " +
                "Aenean vel massa pharetra eros bibendum viverra ac id nisi. Donec in justo in felis rutrum ornare quis sed nunc. " +
                "Ut rutrum mi a sem placerat, quis consectetur lorem fringilla. Integer posuere, felis vitae malesuada commodo, mi nulla rhoncus est, in rhoncus felis urna eu sapien.";

        heading.setText("Om Tungrocken\n");
        about.setText(Html.fromHtml(contentString));
    }
}
