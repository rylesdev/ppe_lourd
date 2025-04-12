package controleur;

import java.util.Date;

public class Particulier extends User {
    private String nomUser, prenomUser, sexeUser;
    private Date dateNaissanceUser;

    public Particulier(int idUser, String emailUser, String mdpUser, String adresseUser, String roleUser,
                       String nomUser, String prenomUser, Date dateNaissanceUser, String sexeUser) {
        super(idUser, emailUser, mdpUser, adresseUser, roleUser);
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.dateNaissanceUser = dateNaissanceUser;
        this.sexeUser = sexeUser;
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

    public Date getDateNaissanceUser() {
        return dateNaissanceUser;
    }

    public void setDateNaissanceUser(Date dateNaissanceUser) {
        this.dateNaissanceUser = dateNaissanceUser;
    }

    public String getSexeUser() {
        return sexeUser;
    }

    public void setSexeUser(String sexeUser) {
        this.sexeUser = sexeUser;
    }
}