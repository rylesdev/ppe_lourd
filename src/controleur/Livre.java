package controleur;

public class Livre {
    private int idLivre, idCategorie, idMaisonEdition;
    private String nomLivre, auteurLivre, imageLivre, nomCategorie;
    private int exemplaireLivre;
    private float prixLivre;

    public Livre(int idLivre, String nomLivre, String auteurLivre, String imageLivre, int exemplaireLivre, float prixLivre, int idCategorie, int idMaisonEdition, String nomCategorie) {
        this.idLivre = idLivre;
        this.nomLivre = nomLivre;
        this.auteurLivre = auteurLivre;
        this.imageLivre = imageLivre;
        this.exemplaireLivre = exemplaireLivre;
        this.prixLivre = prixLivre;
        this.idCategorie = idCategorie;
        this.idMaisonEdition = idMaisonEdition;
        this.nomCategorie = nomCategorie;
    }

    public Livre(String nomLivre, String auteurLivre, String imageLivre, int exemplaireLivre, float prixLivre, int idCategorie, int idMaisonEdition, String nomCategorie) {
        this.idLivre = 0;
        this.nomLivre = nomLivre;
        this.auteurLivre = auteurLivre;
        this.imageLivre = imageLivre;
        this.exemplaireLivre = exemplaireLivre;
        this.prixLivre = prixLivre;
        this.idCategorie = idCategorie;
        this.idMaisonEdition = idMaisonEdition;
        this.nomCategorie = nomCategorie;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public String getNomLivre() {
        return nomLivre;
    }

    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public String getAuteurLivre() {
        return auteurLivre;
    }

    public void setAuteurLivre(String auteurLivre) {
        this.auteurLivre = auteurLivre;
    }

    public String getImageLivre() {
        return imageLivre;
    }

    public void setImageLivre(String imageLivre) {
        this.imageLivre = imageLivre;
    }

    public int getExemplaireLivre() {
        return exemplaireLivre;
    }

    public void setExemplaireLivre(int exemplaireLivre) {
        this.exemplaireLivre = exemplaireLivre;
    }

    public float getPrixLivre() {
        return prixLivre;
    }

    public void setPrixLivre(float prixLivre) {
        this.prixLivre = prixLivre;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdMaisonEdition() {
        return idMaisonEdition;
    }

    public void setIdMaisonEdition(int idMaisonEdition) {
        this.idMaisonEdition = idMaisonEdition;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}
