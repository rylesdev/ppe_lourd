package controleur;

import java.util.Date;

public class Abonnement {
    private int idAbonnement, idUser, pointAbonnement;
    private Date dateDebutAbonnement, dateFinAbonnement;

    public Abonnement(int idAbonnement, int idUser, int pointAbonnement, Date dateDebutAbonnement, Date dateFinAbonnement) {
        this.idAbonnement = idAbonnement;
        this.idUser = idUser;
        this.pointAbonnement = pointAbonnement;
        this.dateDebutAbonnement = dateDebutAbonnement;
        this.dateFinAbonnement = dateFinAbonnement;
    }

    public Abonnement(int idUser, int pointAbonnement, Date dateDebutAbonnement, Date dateFinAbonnement) {
        this.idAbonnement = 0;
        this.idUser = idUser;
        this.pointAbonnement = pointAbonnement;
        this.dateDebutAbonnement = dateDebutAbonnement;
        this.dateFinAbonnement = dateFinAbonnement;
    }

    public int getIdAbonnement() {
        return idAbonnement;
    }
    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public int getPointAbonnement() {
        return pointAbonnement;
    }
    public void setPointAbonnement(int pointAbonnement) {
        this.pointAbonnement = pointAbonnement;
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
}
