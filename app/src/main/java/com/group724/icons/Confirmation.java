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
import com.google.firebase.auth.FirebaseAuth;

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
        String dn = sharedPref.getString("name", "");
        String em = sharedPref.getString("mail", "");
        String html = "&h=<h1>Incoming order from Room "+room+"</h1><p>From: "+dn;
        html += "</p><p>Request: <b>"+itemNQ()+"</b></p><p>Date: "+d;
        html += "</p><a href='https://iconsportal.netlify.app/response?id=["+itemIDs()+"]%26quantities=["+sharedPref.getString("cartq","")+"]%26date="+d;
        html += "%26room="+room+"%26mail="+em;
        html += "%26name="+dn;
        html += "'>Click to accept order on the iCons Portal</a>";
        String url = "https://iconsportal.netlify.app/.netlify/functions/email?s=Order from room "+room+html;


        RequestQueue rq = Volley.newRequestQueue(this);
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
                Context context = getApplicationContext();
                SharedPreferences sharedPref = (context.getSharedPreferences("iconsPref", Context.MODE_PRIVATE));
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("cartid", "");
                editor.putString("cartname", "");
                editor.putString("cartq", "");
                editor.apply();


            }
        });
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
    String itemNQ(){
        String out = "";
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "iconsPref", Context.MODE_PRIVATE);
        String[] cartid = sharedPref.getString("cartid","").split(",");
        String[] cartname = sharedPref.getString("cartname","").split(",");
        String[] cartq = sharedPref.getString("cartq","").split(",");
        for (int i = 0; i<cartid.length; i++){
            out += cartname[i]+" x"+cartq[i]+", ";
        }
        return out.substring(0, out.length()-2);
    }
    String itemIDs(){
        String out = "";
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "iconsPref", Context.MODE_PRIVATE);
        String[] cartid = sharedPref.getString("cartid","").split(",");
        String[] preout = cartid.clone();
        for (int i = 0; i<cartid.length; i++){
            preout[i] = "\""+cartid[i]+"\"";
        }
        return join(preout);
    }
}
