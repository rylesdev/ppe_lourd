package controleur;

import vue.VueConnexion;
import vue.VueGenerale;

public class Gestion {

    private static VueConnexion uneVueConnexion;
    private static VueGenerale uneVueGenerale;
    private static User userConnecte;

    public static void setUserConnecte(User unUser) {
        userConnecte = unUser;
    }

    public static User getUserConnecte() {
        return userConnecte;
    }

    public static void creerVueGenerale(boolean action) {
        if (action) {
            int idUser = userConnecte.getIdUser();
            uneVueGenerale = new VueGenerale(idUser);
            uneVueGenerale.setVisible(true);
        } else {
            uneVueGenerale.dispose();
        }
    }

    public static void rendreVisibleVueConnexion(boolean action) {
        uneVueConnexion.setVisible(action);
    }

    public static void main(String[] args) {
        uneVueConnexion = new VueConnexion();
    }
}