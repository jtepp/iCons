package com.group724.icons;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ItemInfo extends AppCompatActivity {

    FirebaseFirestore ref = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        String itemID = getIntent().getStringExtra("ID");
        TextView name = findViewById(R.id.itemName), category = findViewById(R.id.itemCategory), remaining = findViewById(R.id.remaining);
        Button request = findViewById(R.id.req);
        TextView quantityText = findViewById(R.id.quantityText);
        quantityText.setText("0");
        ref.document("items/"+itemID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                Item item = doc.toObject(Item.class);
                item.setID(doc.getId());
                name.setText(item.getName());
                category.setText(item.getCategory());
                remaining.setText(item.getAvailable()+" remaining");
                if (item.getAvailable() > 0) {
                    request.setEnabled(true);
                } else {
                    request.setEnabled(false);
                }
                quantityText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (item.getAvailable() > 0 && !String.valueOf(quantityText.getText()).equalsIgnoreCase("")) {
                            try {
                                Integer num = Integer.parseInt(String.valueOf(quantityText.getText()));
                                if (Integer.parseInt(String.valueOf(quantityText.getText())) > 0 && Integer.parseInt(String.valueOf(quantityText.getText())) < item.getAvailable()) {
                                    request.setEnabled(true);
                                }
                            } catch (Error e) {
                                Log.d("ERROR", e.toString());
                                request.setEnabled(false);
                            }

                        } else {
                            request.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });






        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        "iconsPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
//                sharedPref.get

//                Intent in = new Intent(ItemInfo.this, Confirmation.class);
//                in.putExtra("ID", itemID);
//                in.putExtra("itemName", name.getText());
//                startActivity(in);
//                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
            }
        });
    }
}