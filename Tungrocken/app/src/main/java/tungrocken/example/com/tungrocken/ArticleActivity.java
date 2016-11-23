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
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;
import java.util.List;
import tungrocken.example.com.tungrocken.Loaders.LoadArticles;
import tungrocken.example.com.tungrocken.domain.Article;
import tungrocken.example.com.tungrocken.domain.HamburgerMenu;
import tungrocken.example.com.tungrocken.domain.Server;

/**
 * Created by Team Tungrocken
 */

public class ArticleActivity extends AppCompatActivity {

    public static final String KEY = "AIzaSyC3BB6nhsBUlPGCJNRLSqCPg8vgr65Lqqk";  // Activation key for YouTube API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

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

        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("aID");

        new LoadArticles(new LoadArticles.Callback() {
            @Override
            public void update(final List<Article> articles) {
                Article article = articles.get(0);

                ImageView photoUrl = (ImageView) findViewById(R.id.photoUrl);
                Picasso.with(getApplicationContext()).load(ip + article.getPhotoUrl()).into(photoUrl);

                TextView title = (TextView)findViewById(R.id.title2);
                title.setText(Html.fromHtml(article.getTitle()));
                TextView ingress = (TextView)findViewById(R.id.ingress);
                ingress.setText(Html.fromHtml(article.getIngress()));
                TextView content = (TextView)findViewById(R.id.content);
                content.setText(Html.fromHtml(article.getContent()));

                //Henter ut en thumbnail fra youtubeurl med Picasso API.
                ImageView ivImageFromUrl = (ImageView) findViewById(R.id.iv_image_from_url);
                Picasso
                        .with(getApplicationContext()).
                        load("http://img.youtube.com/vi/"+article.getYoutubeUrl()+"/0.jpg").
                        into(ivImageFromUrl);

                //Bruker thumbnailen som knapp til å starte youtube aktiviteten.
                ivImageFromUrl.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        playVideo(article.getYoutubeUrl());
                    }
                });
                youtubeInformation();
            }
        }).execute(ip+"/services/app/secure/getarticle?id="+id+"");
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
        //Using Standalone YouTubeAPI to play a video from YouTube
    public void playVideo(String youtubeID) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, KEY, youtubeID, 0, true, true);
        startActivity(intent);
    }

    public void youtubeInformation(){
        TextView about = (TextView)findViewById(R.id.aboutContent);
        about.setText(Html.fromHtml("<strong>Klikk på bildet for å starte video!</strong>"));
    }
}


