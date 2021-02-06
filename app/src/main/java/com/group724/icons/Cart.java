package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "iconsPref", Context.MODE_PRIVATE);
        String[] cartid = sharedPref.getString("cartid","").split(",");
        String[] cartname = sharedPref.getString("cartname","").split(",");
        String[] cartq = sharedPref.getString("cartq","").split(",");
    }
}