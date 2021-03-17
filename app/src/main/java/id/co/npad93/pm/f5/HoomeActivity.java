package id.co.npad93.pm.f5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HoomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoome);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Lomgin dulu!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void lomgout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
