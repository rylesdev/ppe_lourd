package controleur;

import modele.Modele;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controleur {
    private static User userConnecte;


    /************************ GESTION DES USERS ************************/
    public static void insertUser(User unUser) {
        Modele.insertUser(unUser);
    }

    public static ArrayList<User> selectUser() {
        return Modele.selectUser();
    }

    public static void deleteUser(int idUser) {
        Modele.deleteUser(idUser);
    }

    public static void updateUser(User unUser) {
        Modele.updateUser(unUser);
    }

    public static ArrayList<User> selectLikeUser(String filtre) {
        return Modele.selectLikeUser(filtre);
    }

    public static User selectWhereUser(int idUser) {
        return Modele.selectWhereUser(idUser);
    }

    public static User selectWhereUser(String email, String mdp, String role) {
        return Modele.selectWhereUser(email, mdp, role);
    }

    public static User getUserConnecte() {
        return userConnecte;
    }

    public static void setUserConnecte(User unUser) {
        userConnecte = unUser;
    }


    /**************** GESTION DES PARTICULIERS ***************/
    public static void insertParticulier(Particulier unParticulier) {
        /*if (!unParticulier.getRoleUser().equals("client") &&
                !unParticulier.getRoleUser().equals("admin") &&
                !unParticulier.getRoleUser().equals("gestionnaire")) {
            JOptionPane.showMessageDialog(null,
                    "Erreur. Rôle de l'utilisateur non valide.", "Rôle invalide",
                    JOptionPane.ERROR_MESSAGE);
        } else {*/
            Modele.insertParticulier(unParticulier);
            /*JOptionPane.showMessageDialog(null, "Insertion réussie du particulier.",
                    "Insertion Particulier", JOptionPane.INFORMATION_MESSAGE);
        }*/
    }

    public static ArrayList<Particulier> selectParticulier() {
        return Modele.selectParticulier();
    }

    public static void deleteParticulier(int idUser) {
        Modele.deleteParticulier(idUser);
    }

    public static void updateParticulier(Particulier unParticulier) {
            Modele.updateParticulier(unParticulier);
    }

    public static ArrayList<Particulier> selectLikeParticulier(String filtre) {
        return Modele.selectLikeParticulier(filtre);
    }

    public static Particulier selectWhereParticulier(String email, String mdp) {
        return Modele.selectWhereParticulier(email, mdp);
    }

    /*public static Particulier getParticulierConnecte() {
        return particulierConnecte;
    }

    public static void setParticulierConnecte(Particulier particulier) {
        particulierConnecte = particulier;
    }*/



    /**************** GESTION DES ENTREPRISES ****************/
    public static void insertEntreprise(Entreprise uneEntreprise) {
        Modele.insertEntreprise(uneEntreprise);
    }

    public static ArrayList<Entreprise> selectEntreprise() {
        return Modele.selectEntreprise();
    }

    public static void deleteEntreprise(int idUser) {
        Modele.deleteEntreprise(idUser);
    }

    public static void updateEntreprise(Entreprise uneEntreprise) {
            Modele.updateEntreprise(uneEntreprise);
    }

    public static ArrayList<Entreprise> selectLikeEntreprise(String filtre) {
        return Modele.selectLikeEntreprise(filtre);
    }

    public static Entreprise selectWhereEntreprise(String email, String mdp) {
        return Modele.selectWhereEntreprise(email, mdp);
    }



    /**************** GESTION DES LIVRES ****************/
    public static ArrayList<Livre> selectLivre() {
        return Modele.selectLivre();
    }

    public static ArrayList<Livre> selectLikeLivre(String filtre) {
        return Modele.selectLikeLivre(filtre);
    }

    public static void insertLivre(Livre livre) {
        Modele.insertLivre(livre);
    }

    public static void deleteLivre(int idLivre) {
        Modele.deleteLivre(idLivre);
    }

    public static void updateLivre(Livre unLivre) {
        Modele.updateLivre(unLivre);
    }

    public static int selectIdCategorie(String nomCategorie) {
        return Modele.selectIdCategorie(nomCategorie);
    }

    public static int selectIdMaisonEdition(String nomMaisonEdition) {
        return Modele.selectIdMaisonEdition(nomMaisonEdition);
    }

    public static int selectIdPromotion(String nomPromotion) {
        return Modele.selectIdPromotion(nomPromotion);
    }

    public static String selectNomCategorie(int idCategorie) {
        return Modele.selectNomCategorie(idCategorie);
    }

    public static String selectNomMaisonEdition(int idMaisonEdition) {
        return Modele.selectNomMaisonEdition(idMaisonEdition);
    }

    public static String selectNomPromotion(int idPromotion) {
        return Modele.selectNomPromotion(idPromotion);
    }


    /**************** GESTION DES COMMANDES ****************/
    public static ArrayList<Commande> selectCommande() {
        return Modele.selectCommande();
    }

    public static ArrayList<Commande> selectLikeCommande(String filtre) {
        return Modele.selectLikeCommande(filtre);
    }

    public static void insertCommande(Commande uneCommande) {
        Modele.insertCommande(uneCommande);
    }

    public static void deleteCommande(int idCommande) {
        Modele.deleteCommande(idCommande);
    }

    public static void updateCommande(Commande uneCommande) {
        Modele.updateCommande(uneCommande);
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

    public static ArrayList<Abonnement> selectAbonnement() {
        return Modele.selectAbonnement();
    }

    public static void deleteAbonnement(int idAbonnement) {
        Modele.deleteAbonnement(idAbonnement);
    }

    public static void updateAbonnement(Abonnement unAbonnement) {
        Modele.updateAbonnement(unAbonnement);
    }

    public static ArrayList<Abonnement> selectLikeAbonnement(String filtre) {
        return Modele.selectLikeAbonnement(filtre);
    }


    /**************** VÉRIFICATION DES DONNÉES ***************/
    /*public static boolean verifDonnees(Particulier unParticulier) {
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
    }*/

    public static boolean verifDonnees(ArrayList<String> lesChamps) {
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

    /*public static List<LivreNote> selectLivresMieuxNotes() {
        return Modele.selectLivresMieuxNotes();
    }*/

    public static String getRoleUserConnecte() {
        return userConnecte.getRoleUser();
    }

    public static void actualiserUserConnecte() {
        User user = getUserConnecte();
        if (user != null) {
            userConnecte = selectWhereUser(user.getEmailUser(), user.getMdpUser(), user.getRoleUser());
        }
    }
}