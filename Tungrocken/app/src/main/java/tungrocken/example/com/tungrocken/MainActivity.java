package tungrocken.example.com.tungrocken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Team Tungrocken
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedRespources.getInstance().getUser() == null)
        {
            Intent d = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(d);
        }
        else
        {
            Intent d = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(d);
        }
    }
}
