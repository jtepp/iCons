package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button RequestItems = findViewById(R.id.RequestItems);
        RequestItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), CategoryPicking.class);
                startActivity(in);

            }
        });


        Button signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Main.class);
                Context context = getApplicationContext();
                SharedPreferences sharedPref = (context.getSharedPreferences("iconsPref", Context.MODE_PRIVATE));
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("mail", null);
                editor.putString("name", null);
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                startActivity(in);
                editor.putString("cartid", "");
                editor.putString("cartname", "");
                editor.putString("cartq", "");
                editor.apply();
            }
        });
    }
}