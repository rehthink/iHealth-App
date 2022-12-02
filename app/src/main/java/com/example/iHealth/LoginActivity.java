package com.example.iHealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.iHealth.Fragments.UserInfoFragment;
import com.example.iHealth.Utils.SessionManager;
import com.example.iHealth.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    SessionManager sessionManager = new SessionManager();
    ActivityLoginBinding binding;
    String user[] = {"rehan", "hamdard", "ali", "guest"};
    String pass[] = {"123456"};
    private boolean isAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Used for Disabling Dark Mode Theme!
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (sessionManager.readLogin(LoginActivity.this).equals("true")) {
            Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
            startActivity(intent);
            finish();
        }


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.name.length() != 0 && binding.phoneNum.length() != 0 && binding.privacyCheck.isChecked()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    if (binding.name.getText().toString().equals(user[0]) && binding.phoneNum.getText().toString().equals(pass[0])) {
                        sessionManager.saveLogin(LoginActivity.this, "true");
                        Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.name.setError("Input Details");
                }
            }
        });
    }

}