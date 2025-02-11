package controleur;

import java.util.Date;

public class Entreprise extends User {
    private String siretUser;
    private String raisonSocialeUser;
    private double capitalSocialUser;

    // Constructeur pour une entreprise
    public Entreprise(int idUser, String emailUser, String mdpUser, Date dateInscriptionUser,
                      String siretUser, String raisonSocialeUser, double capitalSocialUser) {
        super(idUser, emailUser, mdpUser, "entreprise", dateInscriptionUser);
        this.siretUser = siretUser;
        this.raisonSocialeUser = raisonSocialeUser;
        this.capitalSocialUser = capitalSocialUser;
    }

    // Getters et setters spécifiques à Entreprise
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