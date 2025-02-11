package controleur;

import java.util.Date;

public class Commande {
    private int idCommande;
    private Date dateCommande, dateLivraisonCommande;
    private String statutCommande;
    private int idUser;

    public Commande(int idCommande, Date dateCommande, String statutCommande, Date dateLivraisonCommande, int idUser) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statutCommande = statutCommande;
        this.dateLivraisonCommande = dateLivraisonCommande;
        this.idUser = idUser;
    }

    public Commande(Date dateCommande, String statutCommande, Date dateLivraisonCommande, int idUser) {
        this.idCommande = 0;
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