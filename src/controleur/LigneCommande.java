package controleur;

public class LigneCommande {
    private int idLigneCommande;
    private int idCommande;  // Clé étrangère vers Commande
    private int idLivre;     // Clé étrangère vers Livre
    private int quantiteLigneCommande;

    // Constructeur complet (pour chargement BDD)
    public LigneCommande(int idLigneCommande, int idCommande,
                         int idLivre, int quantiteLigneCommande) {
        this.idLigneCommande = idLigneCommande;
        this.idCommande = idCommande;
        this.idLivre = idLivre;
        this.quantiteLigneCommande = quantiteLigneCommande;
    }

    // Constructeur simplifié (pour création)
    public LigneCommande(int idCommande, int idLivre, int quantiteLigneCommande) {
        this(0, idCommande, idLivre, quantiteLigneCommande);
    }

    // Getters
    public int getIdLigneCommande() { return idLigneCommande; }
    public int getIdCommande() { return idCommande; }
    public int getIdLivre() { return idLivre; }
    public int getQuantiteLigneCommande() { return quantiteLigneCommande; }

    // Setters
    public void setIdLigneCommande(int idLigneCommande) {
        this.idLigneCommande = idLigneCommande;
    }
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
    public void setQuantiteLigneCommande(int quantiteLigneCommande) {
        if(quantiteLigneCommande > 0) {
            this.quantiteLigneCommande = quantiteLigneCommande;
        }
    }
}