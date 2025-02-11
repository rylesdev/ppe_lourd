import controleur.User;

import java.util.Date;

class Particulier extends User {
    private String nomUser, prenomUser, adresseUser;
    private Date dateNaissanceUser;
    private char sexeUser;

    public Particulier(int idUser, String emailUser, String mdpUser, String nomUser, String prenomUser, String adresseUser, Date dateNaissanceUser, char sexeUser) {
        super(idUser, nomUser, prenomUser, emailUser, mdpUser, adresseUser, roleUser, dateInscriptionUser);
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.adresseUser = adresseUser;
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

    public String getAdresseUser() {
        return adresseUser;
    }

    public void setAdresseUser(String adresseUser) {
        this.adresseUser = adresseUser;
    }

    public Date getDateNaissanceUser() {
        return dateNaissanceUser;
    }

    public void setDateNaissanceUser(Date dateNaissanceUser) {
        this.dateNaissanceUser = dateNaissanceUser;
    }

    public char getSexeUser() {
        return sexeUser;
    }

    public void setSexeUser(char sexeUser) {
        this.sexeUser = sexeUser;
    }
}