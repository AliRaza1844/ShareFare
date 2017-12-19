package sharefare.aliraza.com;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddedJourney extends AppCompatActivity {
Button btnaddnewjourney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.added_journey_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  Added Journeys");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnaddnewjourney = (Button) findViewById(R.id.btnaddnewjourney);

        btnaddnewjourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddedJourney.this,AddJourneyActivity.class);
                startActivity(intent);
            }
        });


    }
}
