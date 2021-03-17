package id.co.npad93.pm.f5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    FirebaseAuth firebaseAuth;
    TextView lomgin;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextTextPassword);
        lomgin = findViewById(R.id.textView2);
        signup = findViewById(R.id.button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                if (em.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Masukkan E-mail", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }

                String pw = pass.getText().toString();
                if (pw.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Masukkan sandi", Toast.LENGTH_SHORT).show();
                    pass.requestFocus();
                    return;
                }

                Task<AuthResult> result = firebaseAuth.createUserWithEmailAndPassword(em, pw);
                result.addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, LomginActivity.class));
                            Toast.makeText(MainActivity.this, "Pendaftaran berhasil. Silahkan login.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                result.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        lomgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LomginActivity.class));
            }
        });
    }
}
