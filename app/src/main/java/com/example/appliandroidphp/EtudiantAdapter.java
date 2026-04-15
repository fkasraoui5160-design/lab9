package com.example.appliandroidphp;  // ← REMPLACE PAR TON PACKAGE

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.example.appliandroidphp.beans.Etudiant;  // ← IMPORTANT : adapte ton package

public class EtudiantAdapter extends BaseAdapter {

    private List<Etudiant> etudiants = new ArrayList<>();

    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public Object getItem(int position) {
        return etudiants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_etudiant, parent, false);
        }

        Etudiant e = etudiants.get(position);

        TextView tvNomPrenom = convertView.findViewById(R.id.tvNomPrenom);
        TextView tvVilleSexe = convertView.findViewById(R.id.tdVilleSexe);

        tvNomPrenom.setText(e.getNom() + " " + e.getPrenom());
        tvVilleSexe.setText(e.getVille() + " - " + e.getSexe());

        return convertView;
    }

    public void updateList(List<Etudiant> newList) {
        this.etudiants.clear();
        this.etudiants.addAll(newList);
        notifyDataSetChanged();  // Rafraîchit la liste
    }
}