package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("iconsPref", Context.MODE_PRIVATE);
        String[] cartid = sharedPref.getString("cartid","").split(",");
        String[] cartname = sharedPref.getString("cartname","").split(",");
        String[] cartq = sharedPref.getString("cartq","").split(",");
        LinearLayout cardLayout = findViewById(R.id.cardLayout);
        Log.d("Tag ", join(cartid));
        Log.d("Tag ", join(cartname));
        Log.d("Tag ", join(cartq));

        if (!sharedPref.getString("cartid","").equalsIgnoreCase("")) {
            for (int i = 0; i < cartid.length; i++) {
                Log.d("Tag ", String.valueOf(i));

                cardLayout.addView(returnCard(cartname[i], cartid[i], cartq[i], i));
            }
        }
    }

    CardView returnCard(String n, String i, String q, int Index){
        CardView card = new CardView(getApplicationContext());
        ViewGroup.MarginLayoutParams p = new ViewGroup.MarginLayoutParams(900, 140);
        p.setMargins(0,0,0,50);
        card.setLayoutParams(p);
        card.setContentPadding(40,40,40,40);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            card.setElevation(10);
        }
        card.setRadius(25);

        TextView name = new TextView(this);
        name.setText(n);
        name.setTextColor(Color.BLACK);
        name.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        card.addView(name);

        TextView quantity = new TextView(this);
        quantity.setText(q);
        quantity.setTextColor(Color.BLACK);
        quantity.setGravity(Gravity.END);
        quantity.setPadding(0,0,100,0);
        card.addView(quantity);

        ImageView trash = new ImageView(this);
        trash.setImageResource(R.drawable.trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete from all arrays


                // apply to arrays

                // remove view

            }
        });


        return card;
    }
    String join(String[] input){
        if (input.length > 0) {
            String out = "";
            for (String s : input) {
                out += s + ",";
            }
            return out.substring(0, out.length() - 1);
        } return "";
    }


}