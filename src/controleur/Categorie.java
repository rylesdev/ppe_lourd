package controleur;

public class Categorie {
    private int idCategorie;
    private String nomCategorie;

    // Constructeur avec tous les champs
    public Categorie(int idCategorie, String nomCategorie) {
        this.idCategorie = idCategorie;
        this.nomCategorie = nomCategorie;
    }

    // Constructeur sans idCategorie (par exemple, pour une nouvelle catégorie non enregistrée)
    public Categorie(String nomCategorie) {
        this.idCategorie = 0; // Valeur par défaut pour indiquer qu'elle n'est pas encore définie
        this.nomCategorie = nomCategorie;
    }

    // Getter pour idCategorie
    public int getIdCategorie() {
        return idCategorie;
    }

    // Setter pour idCategorie
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    // Getter pour nomCategorie
    public String getNomCategorie() {
        return nomCategorie;
    }

    // Setter pour nomCategorie
    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}
