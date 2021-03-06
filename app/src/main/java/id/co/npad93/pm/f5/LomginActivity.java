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
import com.google.firebase.auth.FirebaseUser;

public class LomginActivity extends AppCompatActivity {

    EditText email, pass;
    FirebaseAuth firebaseAuth;
    Button lomgin;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomgin);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress2);
        pass = findViewById(R.id.editTextTextPassword2);
        lomgin = findViewById(R.id.button2);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser u = LomginActivity.this.firebaseAuth.getCurrentUser();

                if (u != null) {
                    //Toast.makeText(LomginActivity.this, "Sudah lomgin!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LomginActivity.this, HoomeActivity.class));
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void lomgin(View view) {
        String em = email.getText().toString();
        if (em.isEmpty()) {
            Toast.makeText(this, "Masukkan E-mail", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }

        String pw = pass.getText().toString();
        if (pw.isEmpty()) {
            Toast.makeText(this, "Masukkan sandi", Toast.LENGTH_SHORT).show();
            pass.requestFocus();
            return;
        }

        Task<AuthResult> result = firebaseAuth.signInWithEmailAndPassword(em, pw);
        result.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LomginActivity.this, HoomeActivity.class));
                } else {
                    Toast.makeText(LomginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        result.addOnFailureListener(LomginActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LomginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void hmm(View view) {
        String em = email.getText().toString();
        if (em.isEmpty()) {
            Toast.makeText(this, "Masukkan E-mail", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }

        Task<Void> result = firebaseAuth.sendPasswordResetEmail(em);
        result.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LomginActivity.this, "Reset password terkirim ke E-mail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LomginActivity.this, "Reset password gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        result.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LomginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
