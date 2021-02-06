package com.group724.icons;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ItemList extends AppCompatActivity {


    CollectionReference ref = FirebaseFirestore.getInstance().collection("items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String category = getIntent().getStringExtra("CATEGORY");

//        if (getIntent().getExtras().containsKey("confirmed")){
//            Snackbar result;
//            if (response.toString().equals("success")){
//                result = Snackbar.make(findViewById(android.R.id.content), "Order sent, check your email soon to see if your order was accepted", Snackbar.LENGTH_SHORT);
//                result.show();
//
//            } else {
//                result = Snackbar.make(findViewById(android.R.id.content), "Error sending request. Check your network connection and try again", Snackbar.LENGTH_SHORT);
//                result.show();
//            }
//        }

        setContentView(R.layout.activity_item_list);
        List<Item> items = new ArrayList<Item>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LinearLayout itemLayout = findViewById(R.id.scrollLayout);

        FloatingActionButton cart = findViewById(R.id.toCartitemlist);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Cart.class);
                startActivity(in);
            }
        });

        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                itemLayout.removeAllViews();
                for (DocumentSnapshot d : value.getDocuments()){
//                    Log.d("Document", d.getData().toString());
                    if (d.getString("category").equalsIgnoreCase(category) || category.equalsIgnoreCase("all")) {
                        Item doc = d.toObject(Item.class);
                        doc.setID(d.getId());
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


    }
    Button returnBtn(Item i){
        Button btn = new Button(getApplicationContext());
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setText(i.getName()+" | "+i.getAvailable()+" available");
        btn.setTag(i);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLCJSDFKLDSJ", "ID"+i.getID());
                Intent move = new Intent(ItemList.this, ItemInfo.class);
                move.putExtra("ID",i.getID());
                startActivity(move);

            }
        });
        return btn;
    }
}

