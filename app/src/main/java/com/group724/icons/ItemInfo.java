package com.group724.icons;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.Object;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
        TextView request = findViewById(R.id.req);
        TextView quantityText = findViewById(R.id.quantityText);
        quantityText.setText("0");

        FloatingActionButton cart = findViewById(R.id.toCartiteminfo);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Cart.class);
                startActivity(in);
            }
        });

        ref.document("items/"+itemID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                Item item = doc.toObject(Item.class);
                item.setID(doc.getId());
                name.setText(item.getName());
                category.setText(item.getCategory());
                remaining.setText(item.getAvailable()+" remaining");
                request.setEnabled(false);
//                if (item.getAvailable() > 0) {
//                    request.setEnabled(true);
//                } else {
//                    request.setEnabled(false);
//                }
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
                                } else {
                                    request.setEnabled(false);
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

                request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Integer num = Integer.parseInt(String.valueOf(quantityText.getText()));
                            Context context = getApplicationContext();
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    "iconsPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            String cartid = sharedPref.getString("cartid", "");
                            String cartname = sharedPref.getString("cartname", "");
                            String cartq = sharedPref.getString("cartq", "");

                            if (cartid.contains(doc.getId())) {
                                String[] acartid = cartid.split(",");
//                            String[] acartname = cartname.split(",");
                                String[] acartq = cartq.split(",");

                                Integer index = Arrays.asList(acartid).indexOf(doc.getId());
                                acartq[index] = String.valueOf(Integer.parseInt(String.valueOf(quantityText.getText())));
                                editor.putString("cartq", join(acartq));



                            } else {
                                String[] acartid = cartid.split(",");
                                String[] acartname = cartname.split(",");
                                String[] acartq = cartq.split(",");
                                editor.putString("cartid", joinAdd(acartid, doc.getId()));
                                editor.putString("cartname", joinAdd(acartname, item.getName()));
                                editor.putString("cartq", joinAdd(acartq, String.valueOf(Integer.parseInt(String.valueOf(quantityText.getText())))));
                                editor.apply();

                            }

                            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), ("Cart now contains " + item.getName() + " x" + String.valueOf(num)), Snackbar.LENGTH_LONG);
                            View view = snack.getView();
                            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                            params.gravity = Gravity.TOP;
                            view.setLayoutParams(params);
                            snack.show();
                        } catch (Error e){
                            Log.d("ERROR", e.toString());
                        }

//                Intent in = new Intent(ItemInfo.this, Confirmation.class);
//                in.putExtra("ID", itemID);
//                in.putExtra("itemName", name.getText());
//                startActivity(in);
//                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
                    }
                });

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

    String joinAdd(String[] input, String extra) {
        String joined = join(input);
        return joined + (joined.length() == 0 ? "" : ",") + extra;
    }
}


