package controleur;

import vue.VueConnexion;
import vue.VueGenerale;

public class Gestion {

    // Instanciation de la classe VueConnexion
    private static VueConnexion uneVueConnexion;
    private static VueGenerale uneVueGenerale;
    private static User userConnecte;

    // Méthode pour définir l'utilisateur connecté
    public static void setUserConnecte(User unUser) {
        userConnecte = unUser;
    }

    // Méthode pour récupérer l'utilisateur connecté
    public static User getUserConnecte() {
        return userConnecte;
    }

    // Méthode pour créer ou détruire la VueGenerale
    public static void creerVueGenerale(boolean action) {
        if (action) {
            // Récupérer l'idUser de l'utilisateur connecté
            int idUser = userConnecte.getIdUser(); // Supposons que User a une méthode getIdUser()
            // Créer une instance de VueGenerale avec l'idUser
            uneVueGenerale = new VueGenerale(idUser);
            uneVueGenerale.setVisible(true);
        } else {
            uneVueGenerale.dispose();
        }
    }

    // Méthode pour rendre visible ou invisible la VueConnexion
    public static void rendreVisibleVueConnexion(boolean action) {
        uneVueConnexion.setVisible(action);
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        uneVueConnexion = new VueConnexion();
    }
}