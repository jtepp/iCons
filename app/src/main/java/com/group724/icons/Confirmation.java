package com.group724.icons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
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
        Date d = new Date();


        String req = getIntent().getStringExtra("itemName");

        String html = "<h1>Incoming order from Room 220</h1><p>From: "+sharedPref.getString("name",null)+"</p><p>Request: <b>"+req+"</b></p><p>Date: "+d.toString()+"</p><a href='https://iconsportal.netlify.app/response?id=[\""+itemID+"\"]%26date="+d.toString()+"%26room="+room+"%26mail="+sharedPref.getString("mail",null)+"%26name="+sharedPref.getString("name",null)+"'>Click to accept order on the iCons Portal</a>";


        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://iconsportal.netlify.app/.netlify/functions/email?s=Order%20from%20room%20"+room+"&h="+html+"";
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                    Intent back = new Intent(getApplicationContext(), CategoryPicking.class);
                    back.putExtra("response", response);
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
