package eu.tutorials.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login_Register extends AppCompatActivity {
    Button btn_reg;
    FirebaseAuth auth;

    private static final String TAG = "Login_Register";

    // Declare an ActivityResultLauncher
    private ActivityResultLauncher<Intent> signInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // Initialize UI components
        btn_reg = findViewById(R.id.btn_reg);
        auth = FirebaseAuth.getInstance();

        // Initialize the ActivityResultLauncher
        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // User is signed in
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                                // This is a new user
                                Toast.makeText(this, "Welcome New User", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Welcome Back User", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(Login_Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Sign in failed
                        IdpResponse response = IdpResponse.fromResultIntent(result.getData());
                        if (response == null) {
                            Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                        } else {
                            Log.d(TAG, "onActivityResult: " + response.getError().getMessage());
                        }
                    }
                });

        // Check if user is already signed in
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(Login_Register.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Button click listener
        btn_reg.setOnClickListener(view -> handleLoginRegister(view));
    }

    public void handleLoginRegister(View view) {
        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setLogo(R.drawable.notes_icon) // Set your logo
                .build();

        // Use the launcher instead of startActivityForResult
        signInLauncher.launch(intent);
    }
}
