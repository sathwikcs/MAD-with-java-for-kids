package com.cssun.p4;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView wallpaperImageView;
    private TextView instructionsTextView;
    private Button setWallpaperButton;
    private CardView wallpaperCardView;

    private List<Drawable> wallpapers;
    private int currentWallpaperIndex = 0;

    private Random random;
    private Handler handler;
    private Runnable wallpaperChangeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        wallpaperImageView = findViewById(R.id.wallpaperImageView);
        instructionsTextView = findViewById(R.id.instructionsTextView);
        setWallpaperButton = findViewById(R.id.setWallpaperButton);
        wallpaperCardView = findViewById(R.id.wallpaperCardView);

        // Initialize wallpapers
        wallpapers = new ArrayList<>();
        wallpapers.add(getDrawable(R.drawable.wallpaper1));
        wallpapers.add(getDrawable(R.drawable.wallpaper2));
        wallpapers.add(getDrawable(R.drawable.wallpaper3));

        // Initialize random
        random = new Random();

        // Set initial wallpaper
        setWallpaper(currentWallpaperIndex);

        // Set click listener for changing the wallpaper
        wallpaperCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change wallpaper
                currentWallpaperIndex = (currentWallpaperIndex + 1) % wallpapers.size();
                setWallpaper(currentWallpaperIndex);
            }
        });

        // Set click listener for the "Set Wallpaper" button
        setWallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the image as wallpaper
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                try {
                    Drawable drawable = wallpaperImageView.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize handler and runnable for wallpaper change timer
        handler = new Handler();
        wallpaperChangeRunnable = new Runnable() {
            @Override
            public void run() {
                // Change wallpaper
                currentWallpaperIndex = (currentWallpaperIndex + 1) % wallpapers.size();
                setWallpaper(currentWallpaperIndex);

                // Schedule the next wallpaper change after 30 seconds
                handler.postDelayed(this, 30 * 1000);
            }
        };

        // Start the wallpaper change timer
        handler.postDelayed(wallpaperChangeRunnable, 30 * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the wallpaper change timer when the activity is destroyed
        handler.removeCallbacks(wallpaperChangeRunnable);
    }

    private void setWallpaper(int index) {
        // Set wallpaper image
        wallpaperImageView.setImageDrawable(wallpapers.get(index));

        // Show/hide instructions TextView
        if (index == 0) {
            instructionsTextView.setVisibility(View.VISIBLE);
        } else {
            instructionsTextView.setVisibility(View.GONE);
        }
    }
}
