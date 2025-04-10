package controleur;

public class LigneCommande {
    private int idLigneCommande, idCommande, idLivre, quantiteLigneCommande;

    public LigneCommande(int idLigneCommande, int idCommande, int idLivre, int quantiteLigneCommande) {
        this.idLigneCommande = idLigneCommande;
        this.idCommande = idCommande;
        this.idLivre = idLivre;
        this.quantiteLigneCommande = quantiteLigneCommande;
    }

    public LigneCommande(int idCommande, int idLivre, int quantiteLigneCommande) {
        this.idLigneCommande = 0;
        this.idCommande = idCommande;
        this.idLivre = idLivre;
        this.quantiteLigneCommande = quantiteLigneCommande;
    }

    public int getIdLigneCommande() {
        return idLigneCommande;
    }

    public void setIdLigneCommande(int idLigneCommande) {
        this.idLigneCommande = idLigneCommande;
    }

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
