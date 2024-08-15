package com.coxplorebd.enigmasystems_armvi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coxplorebd.enigmasystems_armvi.R;
import com.coxplorebd.enigmasystems_armvi.model.Model;

import java.util.ArrayList;

/**
 * Created by Oclemy on 7/2/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Model> models;

    public CustomAdapter(Context c, ArrayList<Model> spacecrafts) {
        this.c = c;
        this.models = spacecrafts;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.grid_model,viewGroup,false);
        }

        final Model s= (Model) this.getItem(i);

        ImageView img= (ImageView) view.findViewById(R.id.spacecraftImg);
        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);

        //BIND
        nameTxt.setText(s.getName());
        img.setImageResource(s.getImage());

        return view;
    }
}








