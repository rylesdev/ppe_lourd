package controleur;

import java.util.Date;

public class Promotion {
    private int idPromotion;
    private String nomPromotion;
    private Date dateDebutPromotion, dateFinPromotion;
    private double reductionPromotion;

    public Promotion(int idPromotion, String nomPromotion, Date dateDebutPromotion,
                     Date dateFinPromotion, double reductionPromotion) {
        this.idPromotion = idPromotion;
        this.nomPromotion = nomPromotion;
        this.dateDebutPromotion = dateDebutPromotion;
        this.dateFinPromotion = dateFinPromotion;
        this.reductionPromotion = reductionPromotion;
    }

    public Promotion(String nomPromotion, Date dateDebutPromotion,
                     Date dateFinPromotion, double reductionPromotion) {
        this.idPromotion = 0;
        this.nomPromotion = nomPromotion;
        this.dateDebutPromotion = dateDebutPromotion;
        this.dateFinPromotion = dateFinPromotion;
        this.reductionPromotion = reductionPromotion;
    }

    public int getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getNomPromotion() {
        return nomPromotion;
    }

    public void setNomPromotion(String nomPromotion) {
        this.nomPromotion = nomPromotion;
    }

    public Date getDateDebutPromotion() {
        return dateDebutPromotion;
    }

    public void setDateDebutPromotion(Date dateDebutPromotion) {
        this.dateDebutPromotion = dateDebutPromotion;
    }

    public Date getDateFinPromotion() {
        return dateFinPromotion;
    }

    public void setDateFinPromotion(Date dateFinPromotion) {
        this.dateFinPromotion = dateFinPromotion;
    }

    public double getReductionPromotion() {
        return reductionPromotion;
    }

    public void setReductionPromotion(double reductionPromotion) {
        this.reductionPromotion = reductionPromotion;
    }
}
