package sharefare.aliraza.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText cnic;
    EditText dob;
    EditText password;
    EditText reEnteredPassword;
    TextView message;
    Button next;
    RadioGroup gender;
    RadioButton rbGender;

    public User user = User.getUser();
    public static final String MY_PREFS_NAME = "sharefare.aliraza.com.account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        firstName = (EditText) findViewById(R.id.etFirstName);
        lastName = (EditText) findViewById(R.id.etLastName);
        address = (EditText) findViewById(R.id.etAddress);
        cnic = (EditText) findViewById(R.id.etCNIC);
        dob = (EditText) findViewById(R.id.etDOB);
        password = (EditText) findViewById(R.id.etPassword);
        reEnteredPassword = (EditText) findViewById(R.id.etReenterPassword);
        message = (TextView) findViewById(R.id.tvMessage);
        next = (Button) findViewById(R.id.btnNext);
        gender = (RadioGroup) findViewById(R.id.rgGender);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().toString().trim().equals(reEnteredPassword.getText().toString().trim())){
                    Toast.makeText(SignUpActivity.this,"Password does not match", Toast.LENGTH_SHORT).show();
                }
                else if((firstName.getText().toString().trim() == "") || (lastName.getText().toString().trim() == "")
                        || (address.getText().toString().trim() == "") || (cnic.getText().toString().trim() == "")
                        || (password.getText().toString().trim() == "") || (reEnteredPassword.getText().toString().trim() == "")
                        || (dob.getText().toString().trim() == "")){
                    Toast.makeText(SignUpActivity.this,"Please fill all the fields then press next", Toast.LENGTH_SHORT).show();
                }
                else {
                    user.profile.setCnic(cnic.getText().toString().trim());
                    user.profile.setFirstName(firstName.getText().toString().trim());
                    user.profile.setLastName(lastName.getText().toString().trim());
                    user.profile.setAddress(address.getText().toString().trim());
                    user.profile.setDob(dob.getText().toString().trim());
                    user.account.setPassword(password.getText().toString().trim());
                    rbGender = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                    if(rbGender.getText().toString().trim().equals("Male")){
                        user.profile.setGender(0);
                    }else{
                        user.profile.setGender(1);
                    }
                    Intent intent = new Intent(SignUpActivity.this,EmailActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
