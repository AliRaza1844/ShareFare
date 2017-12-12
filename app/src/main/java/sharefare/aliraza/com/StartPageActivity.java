package sharefare.aliraza.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartPageActivity extends AppCompatActivity {

    Button btnSignIn;
    Button btnSignUp;
    public static final String MY_PREFS_NAME = "sharefare.aliraza.com.account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page_activity);

        // Populating the data base
        this.populoateDatebase();



        // If the user was already sign in just go to the Home page...
        User user = User.getUser();
        SharedPreferences pref = getSharedPreferences(StartPageActivity.MY_PREFS_NAME,MODE_PRIVATE);
        if(pref.getString("username",null) != null){
            user.account.setUsername(pref.getString("username",null));
            user.account.setPassword(pref.getString("password",null));
            Intent intent = new Intent(StartPageActivity.this, NavigationDrawer.class);
            startActivity(intent);
        }


        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPageActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPageActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Intent intent = new Intent(this,StartPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void populoateDatebase(){
        try {
            // First populating the accounts database...
            AccountsTable accounts = new AccountsTable(this);
            accounts.open();
            accounts.createEntry("aliraza","1234");
            accounts.createEntry("adnan","1234");
            accounts.createEntry("sameer","1234");
            accounts.createEntry("fahad","1234");
            accounts.createEntry("ali","1234");
            accounts.close();

            // Now populating the profile database...

        }catch (SQLException e){
            Toast.makeText(StartPageActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{
            ProfileTable profile = new ProfileTable(this);
            profile.open();
            profile.createEntry("3410116155589", "ali", "raza", "1-6-1997", "20", "03048683771", "abcd", null);
            profile.createEntry("3410116155588", "ali", "raza", "1-6-1997", "20", "03048683771", "abcd", null);
            profile.createEntry("3410116155587", "ali", "raza", "1-6-1997", "20", "03048683771", "abcd", null);
            profile.createEntry("3410116155586", "ali", "raza", "1-6-1997", "20", "03048683771", "abcd", null);
            profile.createEntry("3410116155585", "ali", "raza", "1-6-1997", "20", "03048683771", "abcd", null);
            profile.close();
        }catch (SQLException e){
            Toast.makeText(StartPageActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
