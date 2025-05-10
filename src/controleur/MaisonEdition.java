package controleur;

public class MaisonEdition {
    private int idMaisonEdition;
    private String nomMaisonEdition;

    // Constructeur avec tous les champs
    public MaisonEdition(int idMaisonEdition, String nomMaisonEdition) {
        this.idMaisonEdition = idMaisonEdition;
        this.nomMaisonEdition = nomMaisonEdition;
    }

    // Constructeur sans idMaisonEdition (par exemple, pour une nouvelle maison d'édition non enregistrée)
    public MaisonEdition(String nomMaisonEdition) {
        this.idMaisonEdition = 0; // Valeur par défaut pour indiquer qu'elle n'est pas encore définie
        this.nomMaisonEdition = nomMaisonEdition;
    }

    // Getter pour idMaisonEdition
    public int getIdMaisonEdition() {
        return idMaisonEdition;
    }

    // Setter pour idMaisonEdition
    public void setIdMaisonEdition(int idMaisonEdition) {
        this.idMaisonEdition = idMaisonEdition;
    }

    // Getter pour nomMaisonEdition
    public String getNomMaisonEdition() {
        return nomMaisonEdition;
    }

    // Setter pour nomMaisonEdition
    public void setNomMaisonEdition(String nomMaisonEdition) {
        this.nomMaisonEdition = nomMaisonEdition;
    }
}
