package com.coxplorebd.enigmasystems_armvi.adapter;

import android.content.Context;
//import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coxplorebd.enigmasystems_armvi.R;
import com.coxplorebd.enigmasystems_armvi.model.FoodModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arm_AVI on 1/27/2019.
 */

public class ListViewAdapterFood extends ArrayAdapter<FoodModel> {
    public ArrayList<FoodModel> MainList;
    public ArrayList<FoodModel> UserListTemp;
    public UserDataFilter userDataFilter ;

    public ListViewAdapterFood(Context context, int id, ArrayList<FoodModel> userArrayList) {

        super(context, id, userArrayList);
        this.UserListTemp = new ArrayList<FoodModel>();
        this.UserListTemp.addAll(userArrayList);
        this.MainList = new ArrayList<FoodModel>();
        this.MainList.addAll(userArrayList);
    }

    @Override
    public Filter getFilter() {

        if (userDataFilter == null){

            userDataFilter  = new UserDataFilter();
        }
        return userDataFilter;
    }

    public class ViewHolder {
        TextView name, phone, description, web, latitude, longitude;
        ImageView imageHolder;
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_item_food, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.description = (TextView) convertView.findViewById(R.id.description);

            holder.imageHolder = (ImageView) convertView.findViewById(R.id.imageHolder);
            holder.web = (TextView) convertView.findViewById(R.id.web);
            holder.latitude = (TextView) convertView.findViewById(R.id.latitude);
            holder.longitude = (TextView) convertView.findViewById(R.id.longitude);


//            holder.phone = (TextView) convertView.findViewById(R.id.phone);
//            holder.description = (TextView) convertView.findViewById(R.id.description);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FoodModel foodModel = UserListTemp.get(position);
        holder.name.setText(foodModel.getName());
        holder.phone.setText(foodModel.getPhone());
//        holder.description.setText(transportModel.getDescription());

        holder.latitude.setText(foodModel.getLatitude());
        holder.longitude.setText(foodModel.getLongitude());

        holder.description.setText(Html.fromHtml(foodModel.getDescription()));
        Picasso.get().load(foodModel.getImage()).resize(600,600).into(holder.imageHolder);
//
        if(foodModel.getImage().toString().contains("jpg") || foodModel.getImage().toString().contains("png") || foodModel.getImage().toString().contains("jpeg") ){
            Picasso.get().load(foodModel.getImage()).resize(600,600).into(holder.imageHolder);
        }
        else{
//            Picasso.with(getContext()).load(R.drawable.dummy).resize(800,800).into(holder.imageHolder);
            holder.imageHolder.setImageResource(R.drawable.dummy);
        }

//        Picasso.with(getContext()).load(transportModel.getImage()).resize(800,800).into(holder.imageHolder);

//        holder.address.setText(workerModel.getAddress());
//        holder.phone.setText(transportModel.getPhone());
//        holder.description.setText(transportModel.getDescription());


        return convertView;
    }

    private class UserDataFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<FoodModel> arrayList1 = new ArrayList<FoodModel>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    FoodModel foodModel = MainList.get(i);
                    if(foodModel.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(foodModel);
                }
                filterResults.count = arrayList1.size();
                filterResults.values = arrayList1;
            }
            else {
                synchronized(this) {
                    filterResults.values = MainList;
                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            UserListTemp = (ArrayList<FoodModel>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = UserListTemp.size(); i < l; i++)
                add(UserListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}
