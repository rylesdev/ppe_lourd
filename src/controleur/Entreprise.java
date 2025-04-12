package controleur;

import java.util.Date;

public class Entreprise extends User {
    private String siretUser, raisonSocialeUser;
    private float capitalSocialUser;

    public Entreprise(int idUser, String emailUser, String mdpUser, String adresseUser, String roleUser,
                      String siretUser, String raisonSocialeUser, float capitalSocialUser) {
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

    public float getCapitalSocialUser() {
        return capitalSocialUser;
    }

    public void setCapitalSocialUser(float capitalSocialUser) {
        this.capitalSocialUser = capitalSocialUser;
    }
}