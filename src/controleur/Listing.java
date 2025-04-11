package controleur;

public class Listing {
    private int idLivre;
    private String nomLivre;
    private double noteMoyenne;

    public Listing(int idLivre, String nomLivre, double noteMoyenne) {
        this.idLivre = idLivre;
        this.nomLivre = nomLivre;
        this.noteMoyenne = noteMoyenne;
    }

    // Getters
    public int getIdLivre() {
        return idLivre;
    }

    public String getNomLivre() {
        return nomLivre;
    }

    public double getNoteMoyenne() {
        return noteMoyenne;
    }

    // Setters
    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public void setNoteMoyenne(double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }
}