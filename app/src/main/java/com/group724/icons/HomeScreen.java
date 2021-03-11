package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        FloatingActionButton cart = findViewById(R.id.toCarthome);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Cart.class);
                startActivity(in);
            }
        });

        Bundle e = getIntent().getExtras();
        if (e.containsKey("NAME")){
            Snackbar welc = Snackbar.make(findViewById(android.R.id.content), "Welcome, " + e.getString("NAME"), Snackbar.LENGTH_LONG);
            welc.show();
        }
        if (e.containsKey("response")){
            Snackbar result;
            if (e.getString("response").equals("success")){
                result = Snackbar.make(findViewById(android.R.id.content), "Order sent, check your email soon to see if your order was accepted", Snackbar.LENGTH_SHORT);
                result.show();

            } else {
                result = Snackbar.make(findViewById(android.R.id.content), "Error sending request. Check your network connection and try again", Snackbar.LENGTH_SHORT);
                result.show();
            }
        }

        Button RequestItems = findViewById(R.id.RequestItems);
        RequestItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), CategoryPicking.class);
                in.putExtra("NAME", e.getString("NAME", null));
                startActivity(in);

            }
        });

        Button meet = findViewById(R.id.MeetTheIcons);
        meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://icon.engsoc.queensu.ca/hestia-front/team/"));
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
    @Override
    public void onBackPressed() {

    }
}