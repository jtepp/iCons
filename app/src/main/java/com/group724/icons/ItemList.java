package com.group724.icons;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class ItemList extends AppCompatActivity {


    private CollectionReference ref = FirebaseFirestore.getInstance().collection("items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String category = getIntent().getStringExtra("CATEGORY");
        setContentView(R.layout.activity_item_list);
        List<Item> items = new ArrayList<Item>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LinearLayout itemLayout = findViewById(R.id.itemLayout);
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot d : value.getDocuments()){
//                    Log.d("Document", d.getData().toString());
                    if (d.getString("category").equalsIgnoreCase(category) || category.equalsIgnoreCase("all")) {
                        Item doc = d.toObject(Item.class);
                        Log.d("TAG", d.getId() + " => " + doc);
                        itemLayout.addView(returnBtn(doc));
                    }
                }
            }
        });
//        db.collection("items")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                Item doc = document.toObject(Item.class);
////                                Log.d("TAG", document.getId() + " => " + doc);
//                                itemLayout.addView(returnBtn(doc));
//                            }
//
//                        } else {
//                            Log.w("TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });



        TextView listHeader = findViewById(R.id.listHeader);
        listHeader.setText(category);
        listHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                for (Item i : items) {
//                    Button btn = new Button(getApplicationContext());
//                    btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    btn.setText("BUTTON: " + i.getName());
//                    btn.setTag(i);
//                    btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("CLCJSDFKLDSJ", i.getCategory());
//                        }
//                    });
//                    itemLayout.addView(btn);
//                }

            }
        });












    }
    Button returnBtn(Item i){
        Button btn = new Button(getApplicationContext());
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setText(i.getName()+" | "+i.getAvailable()+" available");
        btn.setTag(i);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLCJSDFKLDSJ", i.getCategory());
            }
        });
        return btn;
    }
}

