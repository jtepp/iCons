package com.group724.icons;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ArrayList<Item> items = new ArrayList<Item>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LinearLayout itemLayout = findViewById(R.id.itemLayout);

        db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                               items.add(new Item(document.getString("name"), (int) Math.round(document.getDouble("available")),document.getString("category")));
                            }

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        String category = getIntent().getStringExtra("CATEGORY");

        TextView listHeader = findViewById(R.id.listHeader);
        listHeader.setText(category);
        listHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FOIFJSDFJDS:KFDS:I", items.toString());
            }
        });

        ListView itemList = findViewById(R.id.itemList);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_item_list, R.id.textView, items);
        itemList.setAdapter(arrayAdapter);
    }
}



class Item {
    String name, category;
    int available;

    Item(String name, int available, String category){
        this.name = name;
        this.available = available;
        this.category = category;
    }
    };