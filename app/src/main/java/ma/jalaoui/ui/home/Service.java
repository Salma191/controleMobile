package ma.jalaoui.ui.home;

import java.util.Date;

public class Service {

    private int id;
    private static int lastId = 0;
    private String nom;

    public Service(String nom) {
        this.id = ++lastId;
        this.nom = nom;

    }
    public Service(int id,String nom) {
        this.id = ++lastId;
        this.nom = nom;

    }

    public Service() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "\nNom: " + nom +  "\n";
    }
}
