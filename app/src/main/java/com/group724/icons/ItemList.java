package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);


        String category = getIntent().getStringExtra("CATEGORY");
        TextView listHeader = findViewById(R.id.listHeader);
        listHeader.setText(category);
        listHeader.setTypeface(null, Typeface.BOLD);
    }
}