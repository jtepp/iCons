package com.group724.icons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CategoryPicking extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_picking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = CategoryPicking.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(CategoryPicking.this, R.color.red));

    Bundle e = getIntent().getExtras();

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



//        FloatingActionButton cart = findViewById(R.id.toCartcategorypicking);
//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(getApplicationContext(), Cart.class);
//                startActivity(in);
//            }
//        });

        FloatingActionButton cart = findViewById(R.id.toCartcp);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Cart.class);
                startActivity(in);
            }
        });

        TextView all = findViewById(R.id.toAll);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "All");
                    startActivity(in);
            }
        });

        TextView textbooks = findViewById(R.id.toTextbooks);
        textbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Textbooks");
                startActivity(in);
            }
        });

        TextView supplies = findViewById(R.id.toSupplies);
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Supplies");
                startActivity(in);
            }
        });
        TextView cables = findViewById(R.id.toCables);
        cables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Cables");
                startActivity(in);
            }
        });

        TextView chargers = findViewById(R.id.toChargers);
        chargers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Chargers");
                startActivity(in);
            }
        });

        TextView workbooks = findViewById(R.id.toWorkbooks);
        workbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Workbooks");
                startActivity(in);
            }
        });

        TextView book = findViewById(R.id.toBooklets);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Booklets");
                startActivity(in);
            }
        });





    }

}