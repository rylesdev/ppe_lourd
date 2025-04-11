package controleur;

import java.util.Date;

public class Abonnement {
    private int idAbonnement, idUser, pointAbonnement;
    private Date dateDebutAbonnement;
    private Date dateFinAbonnement;

    public Abonnement(int idAbonnement, int idUser, Date dateDebutAbonnement, Date dateFinAbonnement, int pointAbonnement) {
        this.idAbonnement = idAbonnement;
        this.idUser = idUser;
        this.dateDebutAbonnement = dateDebutAbonnement;
        this.dateFinAbonnement = dateFinAbonnement;
        this.pointAbonnement = pointAbonnement;
    }

    public Abonnement(int idUser, Date dateDebutAbonnement, Date dateFinAbonnement, int pointAbonnement) {
        this.idAbonnement = 0;
        this.idUser = idUser;
        this.dateDebutAbonnement = dateDebutAbonnement;
        this.dateFinAbonnement = dateFinAbonnement;
        this.pointAbonnement = pointAbonnement;
    }

    public int getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateDebutAbonnement() {
        return dateDebutAbonnement;
    }

    public void setDateDebutAbonnement(Date dateDebutAbonnement) {
        this.dateDebutAbonnement = dateDebutAbonnement;
    }

    public Date getDateFinAbonnement() {
        return dateFinAbonnement;
    }

    public void setDateFinAbonnement(Date dateFinAbonnement) {
        this.dateFinAbonnement = dateFinAbonnement;
    }

    public int getPointAbonnement() {
        return pointAbonnement;
    }

    public void setPointAbonnement(int pointAbonnement) {
        this.pointAbonnement = pointAbonnement;
    }
}