package controleur;

import java.util.Date;

public class Admin extends User {
    private int idAdmin;
    private String niveauAdmin;

    public Admin(int idAdmin, int idUser, String nomUser, String prenomUser, String emailUser, String mdpUser, String adresseUser, String roleUser, Date dateInscriptionUser, String niveauAdmin) {
        super(idUser, nomUser, prenomUser, emailUser, mdpUser, adresseUser, roleUser, dateInscriptionUser);
        this.idAdmin = idAdmin;
        this.niveauAdmin = niveauAdmin;
    }

    public Admin(int idUser, String nomUser, String prenomUser, String emailUser, String mdpUser, String adresseUser, String roleUser, Date dateInscriptionUser, String niveauAdmin) {
        super(idUser, nomUser, prenomUser, emailUser, mdpUser, adresseUser, roleUser, dateInscriptionUser);
        this.idAdmin = 0;
        this.niveauAdmin = niveauAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNiveauAdmin() {
        return niveauAdmin;
    }

    public void setNiveauAdmin(String niveauAdmin) {
        this.niveauAdmin = niveauAdmin;
    }
}
