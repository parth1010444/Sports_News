package com.example.sportsnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InformationArrayAdoptor extends ArrayAdapter<Information> {

    ArrayList<Information> information;

    public InformationArrayAdoptor(Context context, int resource,  ArrayList<Information> objects ) {
        super(context, resource, objects);
        information = objects;
    }





    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.information_list_view,parent,false);
        }

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(information.get(position).getTitle());

        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(information.get(position).getDesrciption());

        TextView authorTextView = view.findViewById(R.id.author);
        authorTextView.setText(information.get(position).getAuthor());

        String []datetime=information.get(position).getDateTime().split("T");
        TextView time = view.findViewById(R.id.timeTextView);
        time.setText(datetime[1].split("Z")[0].substring(0,5));

        ImageView img = view.findViewById(R.id.imageView);
        img.setImageBitmap(information.get(position).getImg());



        return view;
    }
}
