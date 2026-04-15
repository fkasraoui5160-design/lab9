package com.example.appliandroidphp;  // ← REMPLACE PAR TON PACKAGE

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.appliandroidphp.beans.Etudiant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListEtudiant extends AppCompatActivity {

    private ListView listView;
    private EtudiantAdapter adapter;
    private RequestQueue requestQueue;
    private List<Etudiant> etudiants = new ArrayList<>();

    private static final String loadUrl = "http://10.0.2.2/projet/ws/loadEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        listView = findViewById(R.id.listViewEtudiants);
        adapter = new EtudiantAdapter();
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        chargerEtudiants();
    }

    private void chargerEtudiants() {
        StringRequest request = new StringRequest(Request.Method.GET, loadUrl,
                response -> {
                    Log.d("LISTE", "Réponse : " + response);
                    try {
                        Type type = new TypeToken<List<Etudiant>>(){}.getType();
                        List<Etudiant> etudiants = new Gson().fromJson(response, type);
                        adapter.updateList(etudiants);
                        Toast.makeText(this, etudiants.size() + " étudiants chargés", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("LISTE", "Erreur JSON : " + e.getMessage());
                        Toast.makeText(this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("LISTE", "Volley erreur : " + error.getMessage());
                    Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }
}