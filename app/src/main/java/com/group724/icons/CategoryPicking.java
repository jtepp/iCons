package com.group724.icons;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CategoryPicking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_picking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    Bundle e = getIntent().getExtras();
        if (e.containsKey("NAME")){
            Snackbar welc = Snackbar.make(findViewById(android.R.id.content), "Welcome, " + e.getString("NAME"), Snackbar.LENGTH_LONG);
            welc.show();
        }


        TextView categoryHeader = findViewById(R.id.categoryHeader);
        categoryHeader.setTypeface(null, Typeface.BOLD);

        Button all = findViewById(R.id.toAll);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "All");
                    startActivity(in);
            }
        });

        Button textbooks = findViewById(R.id.toText);
        textbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Textbooks");
                startActivity(in);
            }
        });

        Button supplies = findViewById(R.id.toSupplies);
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Supplies");
                startActivity(in);
            }
        });
        Button chargers = findViewById(R.id.Chargers);
        chargers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ItemList.class);
                in.putExtra("CATEGORY", "Chargers");
                startActivity(in);
            }
        });
    }
}