package com.example.appliandroidphp.beans;

public class Etudiant {

    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;

    // Constructeur vide (nécessaire pour Gson)
    public Etudiant() {
    }

    // Constructeur avec tous les champs
    public Etudiant(int id, String nom, String prenom, String ville, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.sexe = sexe;
    }

    // Getters (pour lire les valeurs)
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getVille() {
        return ville;
    }

    public String getSexe() {
        return sexe;
    }

    // Setters (pour modifier les valeurs)
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    // Pour afficher un étudiant dans les logs
    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ville='" + ville + '\'' +
                ", sexe='" + sexe + '\'' +
                '}';
    }
}