package sharefare.aliraza.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText cnic;
    EditText dob;
    EditText password;
    EditText email;
    EditText phoneNumber;
    TextView message;
    Button next;
    RadioGroup gender;
    RadioButton rbGender;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public User user = User.getUser();
    public static final String MY_PREFS_NAME = "sharefare.aliraza.com.account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        firstName = (EditText) findViewById(R.id.etFirstName);
        lastName = (EditText) findViewById(R.id.etLastName);
        address = (EditText) findViewById(R.id.etAddress);
        cnic = (EditText) findViewById(R.id.etCNIC);
        dob = (EditText) findViewById(R.id.etDOB);
        password = (EditText) findViewById(R.id.etPassword);
        email = (EditText) findViewById(R.id.etEmail);
        message = (TextView) findViewById(R.id.tvMessage);
        phoneNumber = (EditText) findViewById(R.id.etPhone_Number);
        next = (Button) findViewById(R.id.btnNext);
        gender = (RadioGroup) findViewById(R.id.rgGender);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((firstName.getText().toString().trim().isEmpty()) || (lastName.getText().toString().trim().isEmpty())
                        || (address.getText().toString().trim().isEmpty()) || (cnic.getText().toString().trim().isEmpty())
                        || (password.getText().toString().trim().isEmpty()) || (email.getText().toString().trim().isEmpty())
                        || (dob.getText().toString().trim().isEmpty()) || phoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Please fill all the fields then press next", Toast.LENGTH_SHORT).show();
                }
                else {
                    user.profile.setCnic(cnic.getText().toString().trim());
                    user.profile.setFirstName(firstName.getText().toString().trim());
                    user.profile.setLastName(lastName.getText().toString().trim());
                    user.profile.setAddress(address.getText().toString().trim());
                    user.profile.setDob(dob.getText().toString().trim());
                    user.account.setUsername(email.getText().toString().trim());
                    user.account.setPassword(password.getText().toString().trim());
                    user.profile.setPhoneNumber(phoneNumber.getText().toString().trim());
                    user.setProfile_id(cnic.getText().toString().trim());
                    rbGender = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                    if(rbGender.getText().toString().trim().equals("Male")){
                        user.profile.setGender(0);
                    }else{
                        user.profile.setGender(1);
                    }
                    createUser(user.account.getPassword(),user.account.getUsername());

                }
            }
        });

    }

    public void createUser(String password, String email){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

                            myRef.child(user.account.getUsername().replace(".","_")).child("profile_id").setValue(user.getProfile_id());
                            myRef.child(user.account.getUsername().replace(".","_")).child("ranking").setValue(0);

                            DatabaseReference profileRef= FirebaseDatabase.getInstance().getReference("profiles");

                            profileRef.child(user.getProfile_id()).setValue(user.profile);

                            DatabaseReference walletRef= FirebaseDatabase.getInstance().getReference("wallet");

                            Wallet wallet = new Wallet();
                            walletRef.child(user.account.getUsername().replace(".","_")).setValue(wallet);

                            SharedPreferences.Editor editor = getSharedPreferences(StartPageActivity.MY_PREFS_NAME,MODE_PRIVATE).edit();
                            editor.putString("new_account","true");
                            editor.putString("sign_in","true");
                            editor.commit();
                            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

}
