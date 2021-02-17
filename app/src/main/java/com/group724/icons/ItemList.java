package com.group724.icons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ItemList extends AppCompatActivity {


    CollectionReference ref = FirebaseFirestore.getInstance().collection("items");

    ExpandableListView expandableListView;
    ArrayList<String> listGroup = new ArrayList<>();
    HashMap<String, ArrayList<String>> listChild = new HashMap<>();
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        String category = getIntent().getStringExtra("CATEGORY");

        expandableListView = findViewById(R.id.itemExpandableListView);

        for (int g=0; g<=10; g++) {
            listGroup.add("Group "+g);

            ArrayList<String> arrayList = new ArrayList<>();
            for (int c=0; c<=5; c++){
                arrayList.add("Item "+c);
            }
            listChild.put(listGroup.get(g), arrayList);
        }

        adapter = new MainAdapter(listGroup, listChild);

        expandableListView.setAdapter(adapter);


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


//        List<Item> items = new ArrayList<Item>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        LinearLayout itemLayout = findViewById(R.id.scrollLayout);

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
//                itemLayout.removeAllViews();
                for (DocumentSnapshot d : value.getDocuments()){
//                    Log.d("Document", d.getData().toString());
                    if (d.getString("category").equalsIgnoreCase(category) || category.equalsIgnoreCase("all")) {
                        Item doc = d.toObject(Item.class);
                        doc.setID(d.getId());
                        Log.d("TAG", d.getId() + " => " + doc);
//                        itemLayout.addView(returnBtn(doc));
                    }
                }
            }
        });



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

