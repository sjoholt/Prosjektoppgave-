package tungrocken.example.com.tungrocken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import tungrocken.example.com.tungrocken.adapters.ArticleAdapter;
import tungrocken.example.com.tungrocken.domain.Article;
import tungrocken.example.com.tungrocken.domain.HamburgerMenu;
import tungrocken.example.com.tungrocken.domain.Server;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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



        final ListView lv = (ListView) findViewById(R.id.listw);


        new LoadArticles(new LoadArticles.Callback() {
            @Override
            public void update(final List<Article> articles) {
                // Update ui

                //ArrayAdapter<Article> arrayAdapter = new ArrayAdapter<Article>(HomeActivity.this, android.R.layout.simple_list_item_1, articles);
                ArticleAdapter arrayAdapter = new ArticleAdapter(getApplicationContext(), articles);
                lv.setAdapter(arrayAdapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Log.i("this", articles.get(position).getArticleId().toString());
                        Intent i = new Intent(HomeActivity.this, ArticleActivity.class);

                        // bundle sender over info fra en activity til en annen
                        Bundle bundle = new Bundle();
                        bundle.putLong("aID",articles.get(position).getArticleId());
                        i.putExtras(bundle);

                        startActivity(i);
                    }
                });
            }
        }).execute(ip+ "/services/app/articles");

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
