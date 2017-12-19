package sharefare.aliraza.com;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    //private DatabaseReference SFDatabase;
    private FirebaseDatabase SFDatabase = FirebaseDatabase.getInstance();
    private String value;
    private String userid;

    User user = User.getUser();

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.navigation_drawer_activity);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();



            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            displaySelectedScreen(R.id.home);


            editor = getSharedPreferences(StartPageActivity.MY_PREFS_NAME,MODE_PRIVATE).edit();
            SharedPreferences pref = getSharedPreferences(StartPageActivity.MY_PREFS_NAME,MODE_PRIVATE);

            if(!pref.getString("new_account","").equals("true")) {
                if (pref.getString("sign_in", "").equals("true")) {
                    user.account.setUsername(pref.getString("username", ""));
                    user.account.setPassword(pref.getString("password", ""));
                    user.setProfile_id(pref.getString("profile_id", ""));
                    user.setRanking(pref.getFloat("ranking", 0));
                    user.profile.setAddress(pref.getString("address", ""));
                    user.profile.setAge(pref.getInt("age", 0));
                    user.profile.setCnic(pref.getString("cnic", ""));
                    user.profile.setDob(pref.getString("dob", ""));
                    user.profile.setFirstName(pref.getString("firstName", ""));
                    user.profile.setLastName(pref.getString("lastName", ""));
                    user.profile.setGender(pref.getInt("gender", -1));
                    user.profile.setPhoneNumber(pref.getString("phoneNumber", ""));
                    user.profile.setImage(pref.getString("image", ""));
                    user.wallet.setPaymentMethod(pref.getString("paymentMethod",""));
                    user.wallet.setBalance(pref.getFloat("balance",0));
                } else {
                    userid = User.getUser().account.getUsername().replace(".", "_");
                    getProfileId();
                    getWallet();
                    if (user.getProfile_id() != null)
                        getProfileFromDB();
                }
            }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        TextView text = (TextView) findViewById(R.id.tvHeaderName);
        TextView email = (TextView) findViewById(R.id.tvHeaderEmail);
        ImageView image = (ImageView) findViewById(R.id.ivHeaderImage);
        User user = User.getUser();

        text.setText(user.profile.getFirstName() + " " + user.profile.getLastName());
        email.setText(user.account.getUsername());
//        int id;
//        if(user.profile.getImage() != null) {
//            id = getId(user.profile.getImage(),R.drawable.class);
//        }
//        else {
//            id = getId("ali", R.drawable.class);
//        }
//
//        image.setImageResource(id);
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(200,200);
//        image.setLayoutParams(parms);
        return true;
    }

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.profileid:
                fragment = new ViewProfileFragment();
                break;
            case R.id.wallet:
                fragment = new WalletActivity();
                break;
            case R.id.reportuser:
                fragment = new ReportUser();
                break;

            case R.id.about:
                fragment = new About();
                break;

            case R.id.signout:
                editor.clear().commit();
                Intent intent = new Intent(HomeActivity.this,StartPageActivity.class);
                startActivity(intent);
                break;
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void getWallet(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("wallet_activity");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().trim().equals(userid)){
                        user.wallet.setBalance(ds.child("balance").getValue(float.class));
                        user.wallet.setPaymentMethod(ds.child("paymentMethod").getValue(String.class));
                        editor.putFloat("balance",user.wallet.getBalance());
                        editor.putString("paymentMethod",user.wallet.getPaymentMethod());
                        editor.commit();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public void getProfileId(){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().trim().equals(userid)){
                        user.setRanking(ds.child("ranking").getValue(float.class));
                        user.setProfile_id(ds.child("profile_id").getValue(String.class));
                        editor.putString("profile_id",user.getProfile_id());
                        editor.putFloat("ranking",user.getRanking());
                        editor.commit();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
    public void getProfileFromDB(){
        DatabaseReference profileRef= FirebaseDatabase.getInstance().getReference("profiles");
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    if(data.getKey().toString().trim().equals(user.getProfile_id())) {
                        user.getUser().profile = data.getValue(UserProfile.class);
                        editor.putString("firstName",user.profile.getFirstName());
                        editor.putString("lastName",user.profile.getLastName());
                        editor.putInt("age",user.profile.getAge());
                        editor.putString("dob",user.profile.getDob());
                        editor.putString("phoneNumber",user.profile.getPhoneNumber());
                        editor.putString("image",user.profile.getImage());
                        editor.putString("cnic",user.profile.getCnic());
                        editor.putString("address",user.profile.getAddress());
                        editor.putInt("gender",user.profile.getGender());
                        editor.putString("sign_in","true");
                        editor.commit();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

}