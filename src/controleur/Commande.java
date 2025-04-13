package controleur;

import java.util.Date;

public class Commande extends LigneCommande {
    private int idCommande, idUser;
    private Date dateCommande, dateLivraisonCommande;
    private String statutCommande;

    public Commande(int idCommande, int idLigneCommande, int idLivre, int quantiteLigneCommande,
                    Date dateCommande, String statutCommande, Date dateLivraisonCommande, int idUser) {
        super(idLigneCommande, idLivre, quantiteLigneCommande);
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statutCommande = statutCommande;
        this.dateLivraisonCommande = dateLivraisonCommande;
        this.idUser = idUser;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatutCommande() {
        return statutCommande;
    }

    public void setStatutCommande(String statutCommande) {
        this.statutCommande = statutCommande;
    }

    public Date getDateLivraisonCommande() {
        return dateLivraisonCommande;
    }

    public void setDateLivraisonCommande(Date dateLivraisonCommande) {
        this.dateLivraisonCommande = dateLivraisonCommande;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}