package controleur;

public class MaisonEdition {
    private int idMaisonEdition;
    private String nomMaisonEdition;

    public MaisonEdition(int idMaisonEdition, String nomMaisonEdition) {
        this.idMaisonEdition = idMaisonEdition;
        this.nomMaisonEdition = nomMaisonEdition;
    }

    public MaisonEdition(String nomMaisonEdition) {
        this.idMaisonEdition = 0;
        this.nomMaisonEdition = nomMaisonEdition;
    }


    public int getIdMaisonEdition() {
        return idMaisonEdition;
    }
    public void setIdMaisonEdition(int idMaisonEdition) {
        this.idMaisonEdition = idMaisonEdition;
    }

    public String getNomMaisonEdition() {
        return nomMaisonEdition;
    }
    public void setNomMaisonEdition(String nomMaisonEdition) {
        this.nomMaisonEdition = nomMaisonEdition;
    }
}
