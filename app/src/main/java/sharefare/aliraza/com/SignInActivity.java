package sharefare.aliraza.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity{
    EditText email;
    EditText password;
    TextView message;
    Button btnSignIn;
    TextView forgetPassword;

    private ProgressDialog dialog;

    private final String TAG = "FB_SIGNIN";
    // TODO: Add Auth members
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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


        // TODO: Get a reference to the Firebase auth object
        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        // TODO: Attach a new AuthListener to detect sign in and out
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
//                    AuthUI.getInstance().signOut(SignInActivity.this);
                    Log.d(TAG,"Signed in " + user.getUid());
                }
                else {
                    Log.d(TAG,"Currently Signed out");
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignIn(view);
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

    @Override
    public void onStart() {
        super.onStart();
        // TODO: add the AuthListener
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: Remove the AuthListener
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    public void btnSignIn(View v){
        final String _email = email.getText().toString().trim();
        final String _password = password.getText().toString().trim();
        boolean notValid = false;
        if (_email.isEmpty()) {
            email.setError("Email Required!");
            notValid = true;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            email.setError("enter a valid email address");
            notValid = true;
        }
        if (_password.isEmpty()){
            password.setError("Password Required");
            notValid = true;
        }
        if(notValid)
            return;
        else{
            dialog.setMessage("Signing in, Please wait");
            dialog.show();
            mAuth.signInWithEmailAndPassword(_email,_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                SharedPreferences.Editor editor = getSharedPreferences(StartPageActivity.MY_PREFS_NAME,MODE_PRIVATE).edit();
                                editor.putString("username",_email);
                                editor.putString("password",_password);

                                editor.commit();
                                user.account.setPassword(_password);
                                user.account.setUsername(_email);
                                Toast.makeText(SignInActivity.this,"User was signed in",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                startActivity(intent);
                                email.setText("");
                                password.setText("");

                            }
                            else{
                                dialog.dismiss();
                                //Toast.makeText(SignInActivity.this,"Account Creation sign in failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e instanceof FirebaseAuthInvalidCredentialsException){
                                message.setText(e.getLocalizedMessage());
                                message.setVisibility(View.VISIBLE);
                            }
                            else if(e instanceof FirebaseAuthInvalidUserException){
                                message.setText(e.getLocalizedMessage());
                                message.setVisibility(View.VISIBLE);
                            }
                            else {
                                Toast.makeText(SignInActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            dialog.dismiss();
        }

    }
}
