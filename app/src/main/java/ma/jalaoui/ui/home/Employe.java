package ma.jalaoui.ui.home;


public class Employe {
    private int id;
    private static int lastId = 0;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private Service service;

    public Employe(String nom, String prenom, String dateNaissance, Service service) {
        this.id = ++lastId;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.service = service;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "\nNom: " + nom + "\n\nPrenom: " + prenom + "\n\nDateNaissance: " + dateNaissance +"\n\nService: " + service.getNom() +  "\n";
    }
}
