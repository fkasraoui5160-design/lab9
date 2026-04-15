package com.example.appliandroidphp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.example.appliandroidphp.beans.Etudiant;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {

    // Déclaration des éléments du formulaire
    private EditText nom, prenom;
    private Spinner ville;
    private RadioGroup sexeGroup;
    private RadioButton homme, femme;
    private Button btnAjouter;

    // File d'attente des requêtes Volley
    private RequestQueue requestQueue;

    // URL du service web PHP (pour l'émulateur)
    private static final String insertUrl = "http://10.0.2.2/projet/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        // Liaison des éléments du layout
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        sexeGroup = findViewById(R.id.sexeGroup);
        homme = findViewById(R.id.homme);
        femme = findViewById(R.id.femme);
        btnAjouter = findViewById(R.id.btnAjouter);

        // Initialisation de la file d'attente Volley
        requestQueue = Volley.newRequestQueue(this);

        // Écouteur du bouton
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAjouter) {
            envoyerEtudiant();
        }
    }

    private void envoyerEtudiant() {
        // Récupération des valeurs saisies
        final String nomValue = nom.getText().toString().trim();
        final String prenomValue = prenom.getText().toString().trim();
        final String villeValue = ville.getSelectedItem().toString();

        // Récupération du sexe sélectionné
        String sexeValue = "homme"; // par défaut
        if (femme.isChecked()) {
            sexeValue = "femme";
        }
        final String sexeFinal = sexeValue;

        // Validation : nom et prénom ne doivent pas être vides
        if (nomValue.isEmpty() || prenomValue.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir le nom et le prénom", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création de la requête Volley
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Succès : on affiche la réponse dans Logcat
                        Log.d("SUCCES", "Réponse du serveur : " + response);

                        // Transformation du JSON en objets Etudiant avec Gson
                        try {
                            Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                            Collection<Etudiant> etudiants = new Gson().fromJson(response, type);

                            for (Etudiant e : etudiants) {
                                Log.d("ETUDIANT", e.toString());
                            }

                            Toast.makeText(AddEtudiant.this, "Étudiant ajouté avec succès !", Toast.LENGTH_SHORT).show();

                            // Optionnel : fermer l'activité après ajout
                            // finish();

                        } catch (Exception e) {
                            Log.e("JSON", "Erreur de parsing : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Erreur : affichage dans Logcat et Toast
                        Log.e("ERREUR", "Volley erreur : " + error.getMessage());
                        Toast.makeText(AddEtudiant.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                // Envoi des paramètres en POST (x-www-form-urlencoded)
                Map<String, String> params = new HashMap<>();
                params.put("nom", nomValue);
                params.put("prenom", prenomValue);
                params.put("ville", villeValue);
                params.put("sexe", sexeFinal);
                return params;
            }
        };

        // Ajout de la requête à la file d'attente
        requestQueue.add(request);
    }
}