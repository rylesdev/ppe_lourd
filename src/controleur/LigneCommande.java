package controleur;

public class LigneCommande {
    private int idCommande;
    private int idLivre;
    private int quantiteLigneCommande;

    public LigneCommande(int idCommande, int idLivre, int quantiteLigneCommande) {
        this.idCommande = idCommande;
        this.idLivre = idLivre;
        this.quantiteLigneCommande = quantiteLigneCommande;
    }

    public LigneCommande(int idLivre, int quantiteLigneCommande) {
        this.idCommande = 0;
        this.idLivre = idLivre;
        this.quantiteLigneCommande = quantiteLigneCommande;
    }

    // Getters et setters
    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public int getQuantiteLigneCommande() {
        return quantiteLigneCommande;
    }

    public void setQuantiteLigneCommande(int quantiteLigneCommande) {
        this.quantiteLigneCommande = quantiteLigneCommande;
    }
}
