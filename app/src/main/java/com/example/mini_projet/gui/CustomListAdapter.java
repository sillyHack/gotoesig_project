package com.example.mini_projet.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mini_projet.R;
import com.example.mini_projet.models.Trajet;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Trajet> listData;
    private LayoutInflater layoutInflater;
    public CustomListAdapter(Context aContext, ArrayList<Trajet> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.trajet_row_item, null);
            holder = new ViewHolder();
            holder.uPtDepart = (TextView) v.findViewById(R.id.ptDepart);
            holder.uDateEtHeure = (TextView) v.findViewById(R.id.dateEtHeure);
            holder.uEtatTrajet = (TextView) v.findViewById(R.id.etatTrajet);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uPtDepart.setText("Départ: "+listData.get(position).getPtDepart());
        holder.uDateEtHeure.setText("Date: "+listData.get(position).getDate() + " à " + listData.get(position).getHeure());
        holder.uEtatTrajet.setText(listData.get(position).getEtatTrajet());
        return v;
    }
    static class ViewHolder {
        TextView uPtDepart;
        TextView uDateEtHeure;
        TextView uEtatTrajet;
    }
}
