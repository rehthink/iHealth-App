package com.example.iHealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.iHealth.Utils.SessionManager;
import com.example.iHealth.databinding.ActivityUserInfoBinding;

import java.util.Objects;

public class UserInfoActivity extends AppCompatActivity {

    ActivityUserInfoBinding binding;
    SessionManager sessionManager = new SessionManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!Objects.equals(getIntent().getStringExtra("main"), "open")) {
            if (sessionManager.fetchUserName(UserInfoActivity.this) != null) {
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            binding.logout.setVisibility(View.VISIBLE);
        }

        if (sessionManager.fetchUserName(UserInfoActivity.this) != null) {
            binding.name.setText(sessionManager.fetchUserName(UserInfoActivity.this));
        }

        if (sessionManager.fetchPhoneNumber(UserInfoActivity.this) != null) {
            binding.phoneNum.setText(sessionManager.fetchPhoneNumber(UserInfoActivity.this));
        }

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.saveLogin(UserInfoActivity.this, "false");
                Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                intent.putExtra("main", "open");
                startActivity(intent);
                finish();

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.name.length()<=0) {
                    binding.name.setError("Input Details");
                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(UserInfoActivity.this, "Details Saved!", Toast.LENGTH_SHORT).show();
                    sessionManager.saveUserName(UserInfoActivity.this, binding.name.getText().toString());
                    sessionManager.savePhoneNumber(UserInfoActivity.this, binding.phoneNum.getText().toString());

                    Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}