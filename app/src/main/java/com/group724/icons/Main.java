package com.group724.icons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Map;

public class Main extends AppCompatActivity  {
        FirebaseAuth firebaseAuth;
    OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");

    void signIn(){
        Log.d("FUNC", "RUNNING");
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
    if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.d("TOP SUCCESS", "success");
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    // The OAuth ID token can also be retrieved:
                                    // authResult.getCredential().getIdToken().
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //handle failure
                                    Log.d("TOP FAILURE", "fail");
                                }
                            });
        } else {
        firebaseAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Map<String, Object> p = authResult.getAdditionalUserInfo().getProfile();
                                Log.d("BOTTOM SUCCESS", "success");
                                Intent i = new Intent(Main.this, HomeScreen.class);
                                i.putExtra("NAME", p.get("displayName").toString());

                                Context context = getApplicationContext();
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        "iconsPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("mail", p.get("mail").toString());
                                editor.putString("name", p.get("displayName").toString());
                                editor.apply();
                                Log.d("FSDILDFJDSJSLKJLFKJDLF", "MAIL"+sharedPref.getString("mail",null));
                                startActivity(i);
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                // authResult.getCredential().getAccessToken().
                                // The OAuth ID token can also be retrieved:
                                // authResult.getCredential().getIdToken().
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                                Log.d("BOTTOM FAILURE", e.toString());
                            }
                        });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "iconsPref", Context.MODE_PRIVATE);
    if (sharedPref.getString("name", null) != null) {
        Intent i = new Intent(Main.this, CategoryPicking.class);
        i.putExtra("NAME", sharedPref.getString("name", null));
        startActivity(i);
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

    }
}