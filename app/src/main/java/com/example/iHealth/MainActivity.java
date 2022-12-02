package com.example.iHealth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iHealth.Utils.SessionManager;
import com.example.iHealth.databinding.ActivityMainBinding;
import com.google.android.material.card.MaterialCardView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static {
        if (OpenCVLoader.initDebug()) {
            Log.d("MainActivity: ", "Opencv is loaded");
        } else {
            Log.d("MainActivity: ", "Opencv failed to load");
        }
    }

    SessionManager sessionManager = new SessionManager();
    int SELECT_PICTURE = 200;
    imageClassification imageClassification;
    private MaterialCardView gallery_button;
    ActivityMainBinding binding;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gallery_button = findViewById(R.id.gallery_button);

        if (sessionManager.fetchResult(MainActivity.this) != null) {

        }

        try {
            int mInputSize = 256;
            imageClassification = new imageClassification(getAssets(), "Cancermobilenetmodel.tflite", mInputSize);
            Log.d("TAG", "Model is loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, binding.result.getText().toString() + " Detected.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        gallery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("main", "open");
                startActivity(intent);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri imageUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Mat mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);
                imageClassification.recognizeImage(getApplicationContext(),mat);
                binding.progressBar.setVisibility(View.GONE);
                if (sessionManager.fetchResult(MainActivity.this) != null) {
                    binding.result.setText(sessionManager.fetchResult(MainActivity.this));

                    if (Objects.equals(sessionManager.fetchResult(MainActivity.this), "No Cancer")) {
                        binding.resultLayout.setStrokeColor(Color.parseColor("#448347"));
                        binding.result.setTextColor(Color.parseColor("#448347"));
                        binding.resultLayout.setStrokeWidth(5);
                    }

                    if (Objects.equals(sessionManager.fetchResult(MainActivity.this), "Benign")) {
                        binding.resultLayout.setStrokeColor(Color.RED);
                        binding.result.setTextColor(Color.RED);
                        binding.resultLayout.setStrokeWidth(5);
                    }

                    if (Objects.equals(sessionManager.fetchResult(MainActivity.this), "Malignant")) {
                        binding.resultLayout.setStrokeColor(Color.RED);
                        binding.result.setTextColor(Color.RED);
                        binding.resultLayout.setStrokeWidth(5);
                    }
                }

            }
        }else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

}