package com.example.baladiyanews;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<String> values;
    private final ArrayList<String> id;
    private final ArrayList<String> date;
    private final ArrayList<String> description;
    private final ArrayList<String> numbers;
    private final ArrayList<String> images;

    public ListAdapter(Context context, ArrayList<String> values, ArrayList<String> numbers,ArrayList<String> images,ArrayList<String> date,ArrayList<String> description, ArrayList<String> id){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values = values;
        this.numbers = numbers;
        this.images = images;
        this.id = id;
        this.date = date;
        this.description = description;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);
            viewHolder.txtdescription= (TextView) convertView.findViewById(R.id.txtdescription);
            viewHolder.txtdate= (TextView) convertView.findViewById(R.id.txtdate);
            viewHolder.txtid = (TextView) convertView.findViewById(R.id.idtxt);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            viewHolder.txtimag = (TextView) convertView.findViewById(R.id.imagtxt);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(values.get(position));
        viewHolder.txtVersion.setText("Date : "+numbers.get(position));
        viewHolder.txtid.setText(id.get(position));
        viewHolder.txtdescription.setText(description.get(position));
        viewHolder.txtdate.setText(date.get(position));
        viewHolder.txtimag.setText(images.get(position));
        viewHolder.icon.setImageResource(R.drawable.baladiya);

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtid;
        TextView txtimag;
        TextView txtdescription;
        TextView txtdate;
        TextView txtVersion;
        ImageView icon;

    }




}
