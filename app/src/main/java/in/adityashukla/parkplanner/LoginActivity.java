package in.adityashukla.parkplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    String emailCheck = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText userEmailId,userPassId;
    Button regBtnID, loginBtnID;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailId = findViewById(R.id.emailCheck);
        userPassId = findViewById(R.id.passwordCheck);

        regBtnID = findViewById(R.id.nextBtn);
        loginBtnID = findViewById(R.id.logBtn);

        mAuth = FirebaseAuth.getInstance();

        regBtnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register.class));
                finish();
            }
        });

        loginBtnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserData();
            }




        });

    }
    private void checkUserData() {
        String email,password;
        email = userEmailId.getText().toString().trim();
        password = userPassId.getText().toString().trim();

        if(email.isEmpty() || !email.matches(emailCheck) ){
            Toast.makeText(LoginActivity.this, "Something missing", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(LoginActivity.this, "Enter correct password", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Something error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}