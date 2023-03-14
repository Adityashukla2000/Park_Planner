package in.adityashukla.parkplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private TextView name ,email;
    Button signOut;

   private FirebaseAuth mAuth;
   private FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.userText);
        email = findViewById(R.id.userIdText);

        signOut = findViewById(R.id.signout);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

if (mUser != null){
    name.setText(mUser.getDisplayName());
    email.setText(mUser.getEmail());
}

   signOut.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           mAuth.signOut();
           startActivity(new Intent(MainActivity.this,LoginActivity.class));
           finish();
       }
   });

    }

    protected void onStart() {
        super.onStart();
        if(mUser == null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }
}