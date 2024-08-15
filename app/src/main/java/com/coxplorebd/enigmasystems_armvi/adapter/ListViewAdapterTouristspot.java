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
import com.coxplorebd.enigmasystems_armvi.model.TouristspotModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arm_AVI on 1/30/2019.
 */

public class ListViewAdapterTouristspot extends ArrayAdapter<TouristspotModel> {

    public ArrayList<TouristspotModel> MainList;
    public ArrayList<TouristspotModel> UserListTemp;
    public UserDataFilter userDataFilter ;

    public ListViewAdapterTouristspot(Context context, int id, ArrayList<TouristspotModel> userArrayList) {

        super(context, id, userArrayList);
        this.UserListTemp = new ArrayList<TouristspotModel>();
        this.UserListTemp.addAll(userArrayList);
        this.MainList = new ArrayList<TouristspotModel>();
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
        TextView name, description, imageTwo, imageThree, latitude, longitude;
        ImageView imageHolder;
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_item_touristspot, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.imageHolder = (ImageView) convertView.findViewById(R.id.imageHolder);
            holder.imageTwo = (TextView) convertView.findViewById(R.id.imageTwo);
            holder.imageThree = (TextView) convertView.findViewById(R.id.imageThree);
            holder.latitude = (TextView) convertView.findViewById(R.id.latitude);
            holder.longitude = (TextView) convertView.findViewById(R.id.longitude);
            
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TouristspotModel touristspotModel = UserListTemp.get(position);

        holder.name.setText(touristspotModel.getName());
        holder.latitude.setText(touristspotModel.getLatitude());
        holder.longitude.setText(touristspotModel.getLongitude());
        holder.description.setText(Html.fromHtml(touristspotModel.getDescription()));
//        holder.description.setText(transportModel.getDescription());
        Picasso.get().load(touristspotModel.getImage()).resize(800,800).into(holder.imageHolder);
//
        if(touristspotModel.getImage().toString().contains("jpg") || touristspotModel.getImage().toString().contains("png") || touristspotModel.getImage().toString().contains("jpeg") ){
            Picasso.get().load(touristspotModel.getImage()).resize(800,800).into(holder.imageHolder);
        }
        else{
//            Picasso.with(getContext()).load(R.drawable.dummy).resize(800,800).into(holder.imageHolder);
            holder.imageHolder.setImageResource(R.drawable.dummy);
        }

        return convertView;
    }

    private class UserDataFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<TouristspotModel> arrayList1 = new ArrayList<TouristspotModel>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    TouristspotModel touristspotModel = MainList.get(i);
                    if(touristspotModel.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(touristspotModel);
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
            UserListTemp = (ArrayList<TouristspotModel>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = UserListTemp.size(); i < l; i++)
                add(UserListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }



}
