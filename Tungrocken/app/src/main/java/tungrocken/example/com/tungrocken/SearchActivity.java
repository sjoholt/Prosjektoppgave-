package tungrocken.example.com.tungrocken;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.List;
import tungrocken.example.com.tungrocken.Loaders.LoadArticles;
import tungrocken.example.com.tungrocken.adapters.SearchAdapter;
import tungrocken.example.com.tungrocken.domain.Article;
import tungrocken.example.com.tungrocken.domain.HamburgerMenu;
import tungrocken.example.com.tungrocken.domain.Server;

/**
 * Created by Team Tungrocken
 */

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResult(query);
        }
    }

    public void showResult(String query){
        Server s = new Server();
        final String ip = s.serverUrl();

        // Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listw);
        recyclerView.setLayoutManager(layoutManager);

        new LoadArticles(new LoadArticles.Callback() {
            @Override
            public void update(final List<Article> articles) {
                if(articles.size()==0){
                    Toast toast = Toast.makeText(SearchActivity.this, "Vi beklager, men vi kunne ikke finne noe på ditt søk...", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    toast.show();
                }else {
                    SearchAdapter test = new SearchAdapter(getApplicationContext(), articles);
                    recyclerView.setAdapter(test);
                    test.setOnClickListener((SearchAdapter.OnClickListener) position -> {
                        Log.i("this", articles.get(position).getArticleId().toString());
                        Intent i = new Intent(SearchActivity.this, ArticleActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putLong("aID", articles.get(position).getArticleId());
                        i.putExtras(bundle);
                        startActivity(i);
                    });
                }
            }
        }).execute(ip + "/services/app/findarticle?search="+query+"");
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