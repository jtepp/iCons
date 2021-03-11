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

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        String category = getIntent().getStringExtra("CATEGORY");

        expandableListView = findViewById(R.id.itemExpandableListView);



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

                ArrayList<String> subs = new ArrayList<>();
                ArrayList<Item> allItems = new ArrayList<>();

                for (DocumentSnapshot d : value.getDocuments()){
//                    Log.d("Document", d.getData().toString());
                    if (d.getString("category").equalsIgnoreCase(category) || category.equalsIgnoreCase("all")) {
                        Item doc = d.toObject(Item.class);
                        doc.setID(d.getId());

                        allItems.add(doc);

                        if (!subs.contains(doc.getSub())) {
                            subs.add(doc.getSub()+(category.equalsIgnoreCase("all") ? " - "+doc.getCategory() : "" ));
                        }
//                        Log.d("TAG", d.getId() + " => " + doc);



                    }
                }
                refreshELV(subs, allItems);
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

    void refreshELV(ArrayList<String> subs, ArrayList<Item> allItems){
        ArrayList<String> listGroup = new ArrayList<>();
        HashMap<String, ArrayList<Item>> listChild = new HashMap<>();

        ItemList context = ItemList.this;

        for (int g=0; g<subs.size(); g++) {
            listGroup.add(subs.get(g));

            ArrayList<Item> arrayList = new ArrayList<>();
            for (int c=0; c<allItems.size(); c++){
                if (allItems.get(c).getSub().equalsIgnoreCase(subs.get(g).split(" - ")[0])) {
                    arrayList.add(allItems.get(c));
                }
            }
            listChild.put(listGroup.get(g), arrayList);
        }

        adapter = new MainAdapter(context, listGroup, listChild);

        expandableListView.setAdapter(adapter);
    }
}

