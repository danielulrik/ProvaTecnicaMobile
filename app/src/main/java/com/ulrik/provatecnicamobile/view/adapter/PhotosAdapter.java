package com.ulrik.provatecnicamobile.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Photo;

import java.util.List;

public class PhotosAdapter extends ArrayAdapter<Photo> {
    private Context context;
    private int layoutResourceId;
    private List<Photo> data;

    public PhotosAdapter(Context context, int layoutResourceId, List<Photo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = row.findViewById(R.id.text);
            holder.image = row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Photo item = data.get(position);
        holder.imageTitle.setText(item.getTitle());
        Glide.with(row).load(item.getThumbnailUrl()).into(holder.image);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    public List<Photo> getData() {
        return data;
    }
}
