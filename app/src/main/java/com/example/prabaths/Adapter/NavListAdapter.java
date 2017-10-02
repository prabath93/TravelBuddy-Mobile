package com.example.prabaths.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prabaths.models.Item;
import com.example.prabaths.TravelBuddy.R;

import java.util.List;

/**
 * Created by prabath s on 3/31/2016.
 */
public class NavListAdapter extends ArrayAdapter<Item> {

    Context context;
    int resLayout;
    List<Item> listNavItems;

    public NavListAdapter(Context context, int resLayout, List<Item> listNavItems) {
        super(context, resLayout, listNavItems);
        this.context=context;
        this.resLayout=resLayout;
        this.listNavItems=listNavItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(context,resLayout,null);

        TextView tvTitle=(TextView)v.findViewById(R.id.title);
        TextView tvSubTitle=(TextView)v.findViewById(R.id.subtitle);
        ImageView navIcon=(ImageView)v.findViewById(R.id.nav_icon);

        Item item=listNavItems.get(position);
        if(item.isSection()) {
            tvTitle.setText(item.getTitle());
            tvSubTitle.setText(item.getSubTitle());
            //v.setOnClickListener(null);
            //v.setOnLongClickListener(null);
           // v.setLongClickable(false);
            navIcon.setImageResource(item.getResIcon());
            v.setBackgroundColor(Color.GRAY);
            

        }
        else{
            tvTitle.setText(item.getTitle());
            tvSubTitle.setText(item.getSubTitle());
            navIcon.setImageResource(item.getResIcon());
        }
        return v;
    }
}
