package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mymovie.utils.NetworkUtils;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARTY, 1);
        if (jsonObject == null) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}