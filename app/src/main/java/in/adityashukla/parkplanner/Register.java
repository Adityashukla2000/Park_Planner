package in.adityashukla.parkplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {

    EditText userName,userEmail,userPass,checkPass;
    Button regBtn, loginBtn;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.emailId);
        userPass = findViewById(R.id.passwordIn);
        checkPass = findViewById(R.id.conPass);
        regBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.finalLoginBtn);

        mAuth = FirebaseAuth.getInstance();


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validUserCheck();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,LoginActivity.class));
          finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    private void validUserCheck() {

        String emailID,passwordID,currentPassID;

        emailID = userEmail.getText().toString();
        passwordID = userPass.getText().toString();
        currentPassID = checkPass.getText().toString();

        if(emailID.isEmpty() || passwordID.isEmpty() || currentPassID.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all fields correct", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            mAuth.signInWithEmailAndPassword(emailID,passwordID)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                 updateUser();
                             }else{
                                 Toast.makeText(Register.this, "Something error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                             }
                        }
                    });
        }
    }

    private void updateUser() {
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(String.valueOf(userName))
                .build();

        mAuth.getCurrentUser().updateProfile(changeRequest);
        mAuth.signOut();
        
        openLogin();

    }

    private void openLogin() {
        startActivity(new Intent(Register.this,MainActivity.class));
        finish();
    }


}