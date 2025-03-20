package controleur;

import java.util.Date;

public class Entreprise extends User {
    private String siretUser;
    private String raisonSocialeUser;
    private double capitalSocialUser;

    public Entreprise(int idUser, String siretUser, String raisonSocialeUser, float capitalSocialUser,
                      String emailUser, String mdpUser, String adresseUser, String roleUser) {
        super(idUser, emailUser, mdpUser, adresseUser, roleUser);
        this.siretUser = siretUser;
        this.raisonSocialeUser = raisonSocialeUser;
        this.capitalSocialUser = capitalSocialUser;
    }

    public String getSiretUser() {
        return siretUser;
    }

    public void setSiretUser(String siretUser) {
        this.siretUser = siretUser;
    }

    public String getRaisonSocialeUser() {
        return raisonSocialeUser;
    }

    public void setRaisonSocialeUser(String raisonSocialeUser) {
        this.raisonSocialeUser = raisonSocialeUser;
    }

    public double getCapitalSocialUser() {
        return capitalSocialUser;
    }

    public void setCapitalSocialUser(double capitalSocialUser) {
        this.capitalSocialUser = capitalSocialUser;
    }
}