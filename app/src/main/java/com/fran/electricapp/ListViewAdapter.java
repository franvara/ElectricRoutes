package com.fran.electricapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Vehicle> vehiclelist = null;
    private ArrayList<Vehicle> arraylist;
    private String garage;


    public ListViewAdapter(Context context,
                           List<Vehicle> vehiclelist) {
        this.context = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Vehicle>();
        this.arraylist.addAll(vehiclelist);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView marca;
        TextView modelo;
        ImageView image;
    }

    @Override
    public int getCount() {
        return vehiclelist.size();
    }

    @Override
    public Object getItem(int position) {
        return vehiclelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.vehicle_card, null);
            // Locate the TextViews in listview_item.xml
            holder.marca = (TextView) view.findViewById(R.id.marca);
            holder.modelo = (TextView) view.findViewById(R.id.modelo);
            // Locate the ImageView in listview_item.xml
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.marca.setText(vehiclelist.get(position).getMarca());
        holder.modelo.setText(vehiclelist.get(position).getModelo());
        // Set the results into ImageView
        imageLoader.DisplayImage(vehiclelist.get(position).getImage(),
                holder.image);
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.put("garage", vehiclelist.get(position).getModelo());
                garage = vehiclelist.get(position).getModelo();
                currentUser.saveInBackground();
                //Cerramos la activity y retornamos el poder a la activity para que act�e
                Intent i = ((Activity)context).getIntent();

                //Devolvemos los datos que ha introducido el usuario delegando la tarea
                i.putExtra("garage", garage);

                //Cerramos la pantalla indicando que ha ido bien
                ((Activity)context).setResult(((Activity)context).RESULT_OK, i);

                ((Activity)context).finish();

            }
        });
        return view;
    }

}
