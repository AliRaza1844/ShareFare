package sharefare.aliraza.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView message;
    Button btnSignIn;
    TextView forgetPassword;
    public User user = User.getUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        email = (EditText) findViewById(R.id.tvEmail);
        password = (EditText) findViewById(R.id.tvPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        forgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
        message = (TextView) findViewById(R.id.tvMessage);
        message.setVisibility(View.INVISIBLE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnSignIn(view)) {
                    email.setText("");
                    password.setText("");
                    Intent intent = new Intent(SignInActivity.this, NavigationDrawer.class);
                    startActivity(intent);
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    public boolean btnSignIn(View v){
        String email_ = email.getText().toString().trim();
        String password_ = password.getText().toString().trim();

        try{
            AccountsTable accountsTable = new AccountsTable(this);
            accountsTable.open();
            if(email_.equals("") || password_.equals("")){
                message.setText(" Please enter both email and password first!");
                message.setVisibility(View.VISIBLE);
            }
            else if(!accountsTable.validateUser(email_,password_)){
                message.setText(" Email or password is not correct!");
                message.setVisibility(View.VISIBLE);
            }
            else {
                user.account.setPassword(password_);
                user.account.setUsername(email_);
                SharedPreferences.Editor editor = getSharedPreferences(StartPageActivity.MY_PREFS_NAME,MODE_PRIVATE).edit();
                editor.putString("username",email_);
                editor.putString("password",password_);
                editor.commit();
                return true;
            }

            accountsTable.close();

        }catch (SQLException e){
            Toast.makeText(SignInActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
