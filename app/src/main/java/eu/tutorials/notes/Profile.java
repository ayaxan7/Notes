package eu.tutorials.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
CircleImageView circleImageView;
//TextInputLayout textInputLayout;
Button update_profile;
TextInputEditText displayNameEditText;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        circleImageView = findViewById(R.id.circleImageView);
//        textInputLayout = findViewById(R.id.textInputLayout);
        update_profile=findViewById(R.id.update_profile);
        displayNameEditText = findViewById(R.id.displayNameEditText);
        progressBar = findViewById(R.id.progressBar);
    }

    public void updateProfile(View view) {
        progressBar.setVisibility(View.VISIBLE);
        update_profile.setVisibility(View.INVISIBLE);
        // Perform profile update logic here
    }
}