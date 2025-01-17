package controleur;

import java.util.Date;

public class User {
    private int idUser;
    private String nomUser, prenomUser, emailUser, mdpUser, adresseUser, roleUser;
    private Date dateInscriptionUser;

    public User(int idUser, String nomUser, String prenomUser, String emailUser, String mdpUser, String adresseUser, String roleUser, Date dateInscriptionUser) {
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.emailUser = emailUser;
        this.mdpUser = mdpUser;
        this.adresseUser = adresseUser;
        this.roleUser = roleUser;
        this.dateInscriptionUser = dateInscriptionUser;
    }

    public User(String nomUser, String prenomUser, String emailUser, String mdpUser, String adresseUser, String roleUser, Date dateInscriptionUser) {
        this.idUser = 0;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.emailUser = emailUser;
        this.mdpUser = mdpUser;
        this.adresseUser = adresseUser;
        this.roleUser = roleUser;
        this.dateInscriptionUser = dateInscriptionUser;
    }

    // Getters et setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getMdpUser() {
        return mdpUser;
    }

    public void setMdpUser(String mdpUser) {
        this.mdpUser = mdpUser;
    }

    public String getAdresseUser() {
        return adresseUser;
    }

    public void setAdresseUser(String adresseUser) {
        this.adresseUser = adresseUser;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public Date getDateInscriptionUser() {
        return dateInscriptionUser;
    }

    public void setDateInscriptionUser(Date dateInscriptionUser) {
        this.dateInscriptionUser = dateInscriptionUser;
    }
}
