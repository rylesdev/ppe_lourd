package controleur;

import java.util.Date;

public class Promotion {
    private int idPromotion, idLivre;
    private float prixPromotion;
    private Date dateDebutPromotion, dateFinpromotion;

    public Promotion(int idPromotion, int idLivre, Date dateDebutPromotion, Date dateFinpromotion, float prixPromotion) {
        this.idPromotion = idPromotion;
        this.idLivre = idLivre;
        this.dateDebutPromotion = dateDebutPromotion;
        this.dateFinpromotion = dateFinpromotion;
        this.prixPromotion = prixPromotion;
    }

    public Promotion(int idLivre, Date dateDebutPromotion, Date dateFinpromotion, float prixPromotion) {
        this.idPromotion = 0;
        this.idLivre = idLivre;
        this.dateDebutPromotion = dateDebutPromotion;
        this.dateFinpromotion = dateFinpromotion;
        this.prixPromotion = prixPromotion;
    }

    public int getIdPromotion() {
        return idPromotion;
    }
    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    public int getIdLivre() {
        return idLivre;
    }
    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public float getPrixPromotion() {
        return prixPromotion;
    }
    public void setPrixPromotion(float prixPromotion) {
        this.prixPromotion = prixPromotion;
    }

    public Date getDateDebutPromotion() {
        return dateDebutPromotion;
    }
    public void setDateDebutPromotion(Date dateDebutPromotion) {
        this.dateDebutPromotion = dateDebutPromotion;
    }

    public Date getDateFinpromotion() {
        return dateFinpromotion;
    }
    public void setDateFinpromotion(Date dateFinpromotion) {
        this.dateFinpromotion = dateFinpromotion;
    }
}
