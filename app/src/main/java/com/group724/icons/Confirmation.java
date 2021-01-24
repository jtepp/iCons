package com.group724.icons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        String itemID = getIntent().getStringExtra("ID");
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "iconsPref", Context.MODE_PRIVATE);
        String room = "225";

        String html = "<h1>Incoming order from Room 220</h1><p>Request: 1 Mac charger</p><p>Date: 5:30 PM Jan 24, 2021</p><a href='https://iconsportal.netlify.app/response?info={id:["+itemID+"],date:Date().toString(),room:`"+room+"`,mail:`"+sharedPref.getString("mail",null)+"`' >Click to accept order on the iCons Portal</a>";

        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://allpurpose.netlify.app/.netlify/functions/email?s=Incoming%20Order&h="+html+"";
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                Intent back = new Intent(getApplicationContext(), ItemList.class);
                back.putExtra("confirmed", response.toString().equals("success"));
                startActivity(back);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        });


        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setEnabled(false);
                rq.add(sr);

            }
        });
    }
}