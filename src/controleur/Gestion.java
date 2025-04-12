package controleur;

import vue.VueConnexion;
import vue.VueGenerale;

import javax.swing.*;

public class Gestion {
    private static VueConnexion uneVueConnexion;
    private static VueGenerale uneVueGenerale;

    public static void setUserConnecte(User unUser) {
        Controleur.setUserConnecte(unUser);
    }

    public static User getUserConnecte() {
        return Controleur.getUserConnecte();
    }

    public static void creerVueGenerale(boolean action) {
        if (action) {
            User user = Controleur.getUserConnecte();
            if (user != null) {
                uneVueGenerale = new VueGenerale(user.getIdUser());
                uneVueGenerale.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Aucun utilisateur connecté",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (uneVueGenerale != null) {
                uneVueGenerale.dispose();
            }
        }
    }

    public static void rendreVisibleVueConnexion(boolean action) {
        if (uneVueConnexion != null) {
            uneVueConnexion.setVisible(action);
        }
    }

    public static void main(String[] args) {
        uneVueConnexion = new VueConnexion();
    }
}