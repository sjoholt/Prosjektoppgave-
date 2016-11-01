package tungrocken.example.com.tungrocken;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




// under må tilpasses

        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.listw);


        new LoadArticles(new LoadArticles.Callback() {
            @Override
            public void update(final List<LoadArticles.Article> articles) {
                // Update ui
                //ArrayAdapter<LoadUsers.User> arrayAdapter = new ArrayAdapter<LoadUsers.User>( android.R.layout.simple_list_item_1, users);
               // ArticleAdapter ar = new ArticleAdapter(ArticleActivity.this,articles);
                //lv.setAdapter(arrayAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Log.i("this", articles.get(position).title.toString());
                    }
                });

            }
        }).execute("http://10.16.5.95:8080/Projectserver/services/app/articles");



    }

}
