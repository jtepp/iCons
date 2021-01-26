package com.group724.icons;

import androidx.annotation.Nullable;
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

        ref.document("items/"+itemID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                Item item = doc.toObject(Item.class);
                item.setID(doc.getId());
                name.setText(item.getName());
                category.setText(item.getCategory());
                remaining.setText(item.getAvailable()+" remaining");
                if (item.getAvailable() <= 0) {
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
                in.putExtra("itemName", name.getText());
                startActivity(in);
                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
            }
        });
    }
}