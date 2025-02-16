package controleur;

import java.util.Date;

public class Admin {
    private int idAdmin, idUser;
    private String niveauAdmin;

    public Admin(int idAdmin, int idUser, String niveauAdmin) {
        this.idAdmin = idAdmin;
        this.idUser = idUser;
        this.niveauAdmin = niveauAdmin;
    }

    public Admin(int idUser, String niveauAdmin) {
        this.idAdmin = 0;
        this.idUser = idUser;
        this.niveauAdmin = niveauAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNiveauAdmin() {
        return niveauAdmin;
    }
    public void setNiveauAdmin(String niveauAdmin) {
        this.niveauAdmin = niveauAdmin;
    }
}
