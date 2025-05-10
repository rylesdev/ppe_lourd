package controleur;

import java.util.Date;

public class Promotion {
    private int idPromotion;
    private String nomPromotion;
    private Date dateDebutPromotion;
    private Date dateFinPromotion;
    private int reductionPromotion;

    // Constructeur avec tous les champs
    public Promotion(int idPromotion, String nomPromotion, Date dateDebutPromotion, Date dateFinPromotion, int reductionPromotion) {
        this.idPromotion = idPromotion;
        this.nomPromotion = nomPromotion;
        this.dateDebutPromotion = dateDebutPromotion;
        this.dateFinPromotion = dateFinPromotion;
        this.reductionPromotion = reductionPromotion;
    }

    public Promotion(String nomPromotion, Date dateDebutPromotion, Date dateFinPromotion, int reductionPromotion) {
        this.idPromotion = 0;
        this.nomPromotion = nomPromotion;
        this.dateDebutPromotion = dateDebutPromotion;
        this.dateFinPromotion = dateFinPromotion;
        this.reductionPromotion = reductionPromotion;
    }

    // Getter pour idPromotion
    public int getIdPromotion() {
        return idPromotion;
    }

    // Setter pour idPromotion
    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    // Getter pour nomPromotion
    public String getNomPromotion() {
        return nomPromotion;
    }

    // Setter pour nomPromotion
    public void setNomPromotion(String nomPromotion) {
        this.nomPromotion = nomPromotion;
    }

    // Getter pour dateDebutPromotion
    public Date getDateDebutPromotion() {
        return dateDebutPromotion;
    }

    // Setter pour dateDebutPromotion
    public void setDateDebutPromotion(Date dateDebutPromotion) {
        this.dateDebutPromotion = dateDebutPromotion;
    }

    // Getter pour dateFinPromotion
    public Date getDateFinPromotion() {
        return dateFinPromotion;
    }

    // Setter pour dateFinPromotion
    public void setDateFinPromotion(Date dateFinPromotion) {
        this.dateFinPromotion = dateFinPromotion;
    }

    // Getter pour reductionPromotion
    public int getReductionPromotion() {
        return reductionPromotion;
    }

    // Setter pour reductionPromotion
    public void setReductionPromotion(int reductionPromotion) {
        this.reductionPromotion = reductionPromotion;
    }
}
