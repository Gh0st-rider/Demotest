package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.StatesModel;

import java.util.List;

public class StateAdapter extends ArrayAdapter<StatesModel.Result> {

    Context context;
    List<StatesModel.Result> dataBeans;

    public StateAdapter(@NonNull Context context, int resource, List<StatesModel.Result> dataBeans) {
        super(context, resource ,dataBeans);
    this.context = context;
    this.dataBeans.addAll(dataBeans);

    }

    public void setList( List<StatesModel.Result> dataBeans){
        this.dataBeans.addAll(dataBeans);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return  dataBeans.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        StatesModel.Result rowItem = getItem(position);

        View row = inflater.inflate(R.layout.raw_item_layout, parent, false);

        TextView txtTitle = (TextView) row.findViewById(R.id.tv_location_name);

        if (position==0){

            txtTitle.setTextSize(11);


        }else {
            txtTitle.setTextSize(14);

        }


        txtTitle.setText(rowItem.name);
        txtTitle.setPadding(0,0,0,0);
        txtTitle.setTextSize(15);
        View devider = row.findViewById(R.id.view_devider);
        devider.setVisibility(View.GONE);



        return row;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        StatesModel.Result rowItem = getItem(position);

        View row = inflater.inflate(R.layout.raw_item_layout, parent, false);

        TextView txtTitle = (TextView) row.findViewById(R.id.tv_location_name);

      /*  if (position==0){
            txtTitle.setText(rowItem.getName());

        }else {
            txtTitle.setText(rowItem.getName()+"\n"+rowItem.getCode());

        }*/
        txtTitle.setText(rowItem.name);

        txtTitle.setPadding(50,50,50,50);
        View devider = row.findViewById(R.id.view_devider);
        devider.setVisibility(View.VISIBLE);
        return row;
    }
}/*extends ArrayAdapter<String>{

    private List<String> objects;
    private Context context;

    public CustomArrayAdapter(Context context, int resourceId,
                              List<String> objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.spinner_item, parent, false);
        TextView label=(TextView)row.findViewById(R.id.spItem);
        label.setText(objects.get(position));

        if (position == 0) {//Special style for dropdown header
            label.setTextColor(context.getResources().getColor(R.color.text_hint_color));
        }

        return row;
    }

}
*/