package com.group724.icons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ItemInfo extends AppCompatActivity {

    FirebaseFirestore ref = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        String itemID = getIntent().getStringExtra("ID");
        TextView name = findViewById(R.id.itemName), category = findViewById(R.id.itemCategory), remaining = findViewById(R.id.remaining);
        Button request = findViewById(R.id.req);

        ref.document("items/"+itemID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot doc) {
             Item item = doc.toObject(Item.class);
                item.setID(doc.getId());
             name.setText(item.getName());
             category.setText(item.getCategory());
             remaining.setText(item.getAvailable()+" remaining");
             if (item.getAvailable() > 0) {
                 request.setEnabled(false);
             } else {
                 request.setEnabled(true);
             }
            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ItemInfo.this, Confirmation.class);
                in.putExtra("ID", itemID);
                startActivity(in);
                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
            }
        });
    }
}