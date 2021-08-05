package com.schneewittchen.rosandroid.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.schneewittchen.rosandroid.databinding.ActivityMainBinding;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.main.MainFragment;
import com.schneewittchen.rosandroid.ui.fragments.main.OnBackPressedListener;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding actBinding;
    private static final int LOCATION_PERM = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(actBinding.getRoot());

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance())
                    .commitNow();
        }

        this.requestPermissions();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (fragment instanceof OnBackPressedListener) {
            OnBackPressedListener listener = (OnBackPressedListener) fragment;

            if (listener.onBackPressed()) {
                return;
            }
        }

        super.onBackPressed();

    }

    private void requestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERM);
    }
}
