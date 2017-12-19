package sharefare.aliraza.com;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BookJourney extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_journey);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  Book a Journey");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
