package tungrocken.example.com.tungrocken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tungrocken.example.com.tungrocken.domain.Article;

public class ArticleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

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

        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("aID");

        new LoadArticles(new LoadArticles.Callback() {
            @Override
            public void update(final List<Article> articles) {
                // Update ui

                //Pass på at IP er rett
                Article article = articles.get(0);

                ImageView photoUrl = (ImageView) findViewById(R.id.photoUrl);
                Picasso.with(getApplicationContext()).load("http://10.16.5.58:8080/Projectserver" + article.getPhotoUrl()).into(photoUrl);

                TextView title = (TextView)findViewById(R.id.title2);
                title.setText(article.getTitle());
                TextView ingress = (TextView)findViewById(R.id.ingress);
                ingress.setText(article.getIngress());
                TextView content = (TextView)findViewById(R.id.content);
                content.setText(article.getContent());
                TextView youtubeUrl = (TextView)findViewById(R.id.youtubeUrl);
                youtubeUrl.setText(article.getYoutubeUrl());

            }
        }).execute("http://10.16.5.58:8080/Projectserver/services/app/getarticle?id="+id+"");

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


