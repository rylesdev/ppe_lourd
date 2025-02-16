package controleur;

public class User {
    private int idUser;
    private String emailUser, mdpUser, adresseUser, roleUser;

    public User(int idUser, String emailUser, String mdpUser, String adresseUser, String roleUser) {
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.mdpUser = mdpUser;
        this.adresseUser = adresseUser;
        this.roleUser = roleUser;
    }

    public User(String emailUser, String mdpUser, String adresseUser, String roleUser) {
        this.idUser = 0;
        this.emailUser = emailUser;
        this.mdpUser = mdpUser;
        this.adresseUser = adresseUser;
        this.roleUser = roleUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
}
