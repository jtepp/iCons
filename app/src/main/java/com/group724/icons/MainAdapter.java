package com.group724.icons;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.core.content.ContextCompat.startActivity;

public class MainAdapter extends BaseExpandableListAdapter {
    ItemList context;
    ArrayList<String> listGroup;
    HashMap<String, ArrayList<Item>> listChild;


    public MainAdapter(ItemList context, ArrayList<String> listGroup, HashMap<String ,ArrayList<Item>> listChild){
        this.context = context;
        this.listGroup = listGroup;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        String sGroup = String.valueOf(getGroup(groupPosition));

        textView.setText(sGroup);

        textView.setTypeface(null, Typeface.BOLD);

        textView.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_selectable_list_item, parent, false);

        TextView textView = convertView.findViewById(android.R.id.text1);
        Item i = (Item) getChild(groupPosition,childPosition);
        textView.setText(i.getName()+" | "+i.getAvailable()+" remaining");
        textView.setPadding(100,0,0,0);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(context, ItemInfo.class);
                move.putExtra("ID",i.getID());
                startActivity(context, move, null);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
