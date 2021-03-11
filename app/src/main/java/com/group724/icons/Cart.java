package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("iconsPref", Context.MODE_PRIVATE);
        String cid = sharedPref.getString("cartid","");
        String[] cartid = sharedPref.getString("cartid","").split(",");
        String[] cartname = sharedPref.getString("cartname","").split(",");
        String[] cartq = sharedPref.getString("cartq","").split(",");
        if (cid.length() <= 0 || cid.equalsIgnoreCase("")){
            findViewById(R.id.sendOrder).setEnabled(false);
        } else {
            findViewById(R.id.sendOrder).setEnabled(true);

        }

        findViewById(R.id.sendOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getApplicationContext(), Confirmation.class);
                startActivity(c);
            }
        });



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






    CardView returnCard(String n, String i, String q, int dex){
        CardView card = new CardView(getApplicationContext());
        ViewGroup.MarginLayoutParams p = new ViewGroup.MarginLayoutParams(900, 140);
        p.setMargins(0,0,0,50);
        card.setLayoutParams(p);
        card.setCardBackgroundColor(Color.rgb(6,137,75));
        card.setContentPadding(40,40,40,40);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            card.setElevation(10);
        }
        card.setRadius(25);

        TextView name = new TextView(this);
        name.setText(n);
        name.setTextColor(Color.WHITE);
        name.setTextSize(16);
        name.setTypeface(null, Typeface.BOLD);
        name.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        card.addView(name);

        TextView quantity = new TextView(this);
        quantity.setText(q);
        quantity.setTextColor(Color.WHITE);
        quantity.setTextSize(16);
        quantity.setGravity(Gravity.END);
        quantity.setTypeface(null, Typeface.BOLD);
        quantity.setPadding(0,0,100,0);
        card.addView(quantity);

        ImageView trash = new ImageView(this);
        trash.setImageResource(R.drawable.trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete from all arrays
                eraseAll(dex);

                // remove view
                LinearLayout cardLayout = findViewById(R.id.cardLayout);
                cardLayout.removeView(card);

                Context context = getApplicationContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        "iconsPref", Context.MODE_PRIVATE);
                String cartid = sharedPref.getString("cartid","");

                if (cartid.length() <= 0 || cartid.equalsIgnoreCase("")){
                    findViewById(R.id.sendOrder).setEnabled(false);
                } else {
                    findViewById(R.id.sendOrder).setEnabled(true);

                }
            }
        });

        FrameLayout.LayoutParams pp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        pp.gravity = Gravity.RIGHT;
        trash.setLayoutParams(pp);
        trash.setPadding(-100,0,-100,0);
        card.addView(trash);

        return card;
    }
    String join(String[] input){
        if (input.length > 0) {
            String out = "";
            for (String s : input) {
                out += s + ",";
            }
            return out.substring(0, (out.length() == 0 ? out.length() : out.length() - 1));
        } return "";
    }

    void eraseAll(int dex) {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "iconsPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String[] cartid = sharedPref.getString("cartid","").split(",");
        String[] cartname = sharedPref.getString("cartname","").split(",");
        String[] cartq = sharedPref.getString("cartq","").split(",");

        editor.putString("cartid", joinRemove(cartid, dex));
        editor.putString("cartname", joinRemove(cartname, dex));
        editor.putString("cartq", joinRemove(cartq, dex));
        editor.apply();
    }

    String joinRemove(String[] input, int dex){
        if (input.length > 0) {
            String out = "";
            for (int i = 0; i<input.length; i++) {
                if (i != dex){
                    out += input[i] + ",";
                }

            }
            return out.substring(0, (out.length() == 0 ? out.length() : out.length() - 1));
        } return "";
    }


}