package controleur;

public class Listing {
    private String nom, prenom, designation, description, dateInter ;

    public Listing(String nom, String prenom, String designation, String description, String dateInter) {

        this.nom = nom;
        this.prenom = prenom;
        this.designation = designation;
        this.description = description;
        this.dateInter = dateInter;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateInter() {
        return dateInter;
    }

    public void setDateInter(String dateInter) {
        this.dateInter = dateInter;
    }



}
