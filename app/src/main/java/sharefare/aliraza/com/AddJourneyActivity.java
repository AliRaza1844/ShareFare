package sharefare.aliraza.com;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddJourneyActivity extends AppCompatActivity {

    EditText pinLocation,destination,time,date,vehicle,seats,fare;
    Button addJourney;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_journey_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  Adding a New Journey");
        actionBar.setDisplayHomeAsUpEnabled(true);

        pinLocation = (EditText) findViewById(R.id.etPinLoaction);
        destination = (EditText) findViewById(R.id.etDestination);
        time = (EditText) findViewById(R.id.etTime);
        date = (EditText) findViewById(R.id.etDate);
        vehicle = (EditText) findViewById(R.id.etVehicle);
        seats = (EditText) findViewById(R.id.etSeats);
        fare = (EditText) findViewById(R.id.etExpectedFare);
        addJourney = (Button) findViewById(R.id.btnAddJourney);
        dialog = new ProgressDialog(this);

        addJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((pinLocation.getText().toString().trim().isEmpty()) || (destination.getText().toString().trim().isEmpty())
                        || (time.getText().toString().trim().isEmpty()) || (date.getText().toString().trim().isEmpty())
                        || (vehicle.getText().toString().trim().isEmpty()) || (seats.getText().toString().trim().isEmpty())
                        || (fare.getText().toString().trim().isEmpty())){
                    Toast.makeText(AddJourneyActivity.this,"Please fill all the fields then press Add", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.setMessage("Adding journey please wait...");
                    dialog.show();
                    Journey journey = new Journey();
                    journey.setPin_location(pinLocation.getText().toString().trim());
                    journey.setDate(date.getText().toString().trim());
                    journey.setDestination(destination.getText().toString().trim());
                    journey.setFare(Float.valueOf(fare.getText().toString().trim()));
                    journey.setSeats(Integer.valueOf(seats.getText().toString().trim()));
                    journey.setTime(time.getText().toString().trim());
                    journey.setVehicle(vehicle.getText().toString().trim());

                    DatabaseReference journeyRef= FirebaseDatabase.getInstance().getReference("journeys");

                    journeyRef.child(User.getUser().account.getUsername().replace(".","_")).setValue(journey);

                    dialog.hide();
                    Toast.makeText(AddJourneyActivity.this,"Journey has been added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
