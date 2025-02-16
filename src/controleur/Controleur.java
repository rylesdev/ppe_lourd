package controleur;

import modele.Modele;

import java.util.ArrayList;

public class Controleur {
    private static Particulier particulierConnecte;

    /**************** GESTION DES PARTICULIERS ***************/
    public static void insertParticulier(Particulier unParticulier) {
        // Côté sécurité : on vérifie les données avant insertion dans la BDD
        if (verifDonnees(unParticulier)) {
            // Appel du modèle pour insérer le particulier
            Modele.insertParticulier(unParticulier);
        } else {
            System.out.println("Erreur : Données invalides pour l'insertion du particulier.");
        }
    }

    public static ArrayList<Particulier> selectParticulier() {
        return Modele.selectParticulier();
    }

    public static void deleteParticulier(int idUser) {
        Modele.deleteParticulier(idUser);
    }

    public static void updateParticulier(Particulier unParticulier) {
        if (verifDonnees(unParticulier)) {
            Modele.updateParticulier(unParticulier);
        } else {
            System.out.println("Erreur : Données invalides pour la mise à jour du particulier.");
        }
    }

    public static ArrayList<Particulier> selectLikeParticulier(String filtre) {
        return Modele.selectLikeParticulier(filtre);
    }

    public static Particulier selectWhereParticulier(String email, String mdp) {
        return Modele.selectWhereParticulier(email, mdp);
    }

    public static Particulier getParticulierConnecte() {
        return particulierConnecte;
    }

    public static void setParticulierConnecte(Particulier particulier) {
        particulierConnecte = particulier;
    }


    /**************** GESTION DES LIVRES ****************/
    public static ArrayList<Livre> selectLivre() {
        return Modele.selectLivre();
    }

    public static ArrayList<Livre> selectLikeLivre(String filtre) {
        return Modele.selectLikeLivre(filtre);
    }


    /**************** GESTION DES COMMANDES ****************/
    public static ArrayList<Commande> selectCommande(int idUser) {
        return Modele.selectCommande(idUser);
    }

    public static void insertCommande(Commande uneCommande) {
        Modele.insertCommande(uneCommande);
    }

    public static void deleteCommande(int idCommande) {
        Modele.deleteCommande(idCommande);
    }

    /*public static ArrayList<Commande> selectCommandesByUser(int idUser) {
        return Modele.selectCommandesByUser(idUser);
    }

    public static void updateCommande(Commande uneCommande) {
        Modele.updateCommande(uneCommande);
    }
    */


    /**************** GESTION DES ABONNEMENTS ****************/
    public static void insertAbonnement(Abonnement unAbonnement) {
        Modele.insertAbonnement(unAbonnement);
    }


    /**************** VÉRIFICATION DES DONNÉES ***************/
    public static boolean verifDonnees(Particulier unParticulier) {
        // Vérification des champs obligatoires
        if (unParticulier.getNomUser() == null || unParticulier.getNomUser().trim().isEmpty()) {
            return false;
        }
        if (unParticulier.getPrenomUser() == null || unParticulier.getPrenomUser().trim().isEmpty()) {
            return false;
        }
        if (unParticulier.getEmailUser() == null || unParticulier.getEmailUser().trim().isEmpty()) {
            return false;
        }
        if (unParticulier.getMdpUser() == null || unParticulier.getMdpUser().trim().isEmpty()) {
            return false;
        }
        if (unParticulier.getAdresseUser() == null || unParticulier.getAdresseUser().trim().isEmpty()) {
            return false;
        }
        if (unParticulier.getDateNaissanceUser() == null) {
            return false;
        }
        if (unParticulier.getSexeUser() == null || unParticulier.getSexeUser().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean verifDonnees(ArrayList<String> lesChamps) {
        // Vérification des champs obligatoires
        for (String champ : lesChamps) {
            if (champ == null || champ.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /************************ AUTRES MÉTHODES ************************/
    public static int count(String table) {
        return Modele.count(table);
    }

    public static ArrayList<Listing> selectListing(){
        return Modele.selectListing();
    }
}