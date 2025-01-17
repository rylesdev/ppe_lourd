package controleur;

public class Livre {
    private int idLivre;
    private String nomLivre, categorieLivre, auteurLivre, imageLivre;
    private int exemplaireLivre;
    private float prixLivre;

    public Livre(int idLivre, String nomLivre, String categorieLivre, String auteurLivre, String imageLivre, int exemplaireLivre, float prixLivre) {
        this.idLivre = idLivre;
        this.nomLivre = nomLivre;
        this.categorieLivre = categorieLivre;
        this.auteurLivre = auteurLivre;
        this.imageLivre = imageLivre;
        this.exemplaireLivre = exemplaireLivre;
        this.prixLivre = prixLivre;
    }

    public Livre(String nomLivre, String categorieLivre, String auteurLivre, String imageLivre, int exemplaireLivre, float prixLivre) {
        this.idLivre = 0;
        this.nomLivre = nomLivre;
        this.categorieLivre = categorieLivre;
        this.auteurLivre = auteurLivre;
        this.imageLivre = imageLivre;
        this.exemplaireLivre = exemplaireLivre;
        this.prixLivre = prixLivre;
    }

    // Getters et setters
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

    public String getCategorieLivre() {
        return categorieLivre;
    }

    public void setCategorieLivre(String categorieLivre) {
        this.categorieLivre = categorieLivre;
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
}