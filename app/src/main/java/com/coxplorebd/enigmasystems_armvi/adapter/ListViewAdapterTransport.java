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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coxplorebd.enigmasystems_armvi.R;
import com.coxplorebd.enigmasystems_armvi.model.TransportModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arm_AVI on 4/29/2018.
 */

public class ListViewAdapterTransport extends ArrayAdapter<TransportModel> {

    //Image extension checker

    public ArrayList<TransportModel> MainList;
    public ArrayList<TransportModel> UserListTemp;
    public UserDataFilter userDataFilter ;

    public ListViewAdapterTransport(Context context, int id, ArrayList<TransportModel> userArrayList) {

        super(context, id, userArrayList);
        this.UserListTemp = new ArrayList<TransportModel>();
        this.UserListTemp.addAll(userArrayList);
        this.MainList = new ArrayList<TransportModel>();
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
        TextView name, phone, description, web;
        RelativeLayout image;
        ImageView imageHolder;
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_items_transport, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.description = (TextView) convertView.findViewById(R.id.description);
//            holder.image = (RelativeLayout) convertView.findViewById(R.id.image);
            holder.imageHolder = (ImageView) convertView.findViewById(R.id.imageHolder);
            holder.web = (TextView) convertView.findViewById(R.id.web);


//            holder.phone = (TextView) convertView.findViewById(R.id.phone);
//            holder.description = (TextView) convertView.findViewById(R.id.description);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TransportModel transportModel = UserListTemp.get(position);
        holder.name.setText(transportModel.getName());
        holder.phone.setText(transportModel.getPhone());
//        holder.description.setText(transportModel.getDescription());
        holder.web.setText(transportModel.getWeb());

        holder.description.setText(Html.fromHtml(transportModel.getDescription()));
        Picasso.get().load(transportModel.getImage()).resize(600,600).into(holder.imageHolder);
//
        if(transportModel.getImage().toString().contains("jpg") || transportModel.getImage().toString().contains("png") || transportModel.getImage().toString().contains("jpeg") ){
            Picasso.get().load(transportModel.getImage()).resize(600,600).into(holder.imageHolder);
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
                ArrayList<TransportModel> arrayList1 = new ArrayList<TransportModel>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    TransportModel transportModel = MainList.get(i);
                    if(transportModel.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(transportModel);
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
            UserListTemp = (ArrayList<TransportModel>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = UserListTemp.size(); i < l; i++)
                add(UserListTemp.get(i));
                notifyDataSetInvalidated();
        }
    }
}
