package controleur;

import modele.Modele;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controleur {
    private static User userConnecte;



    /************************ GESTION DES USERS ************************/
    public static boolean emailExiste(String email) {
        return Modele.emailExiste(email);
    }

    public static void updateUser(User unUser) {
        Modele.updateUser(unUser);
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
    public static String insertParticulier(Particulier unParticulier) {
        return Modele.insertParticulier(unParticulier);
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



    /**************** GESTION DES ENTREPRISES ****************/
    public static String insertEntreprise(Entreprise uneEntreprise) {
        return Modele.insertEntreprise(uneEntreprise);
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



    /**************** GESTION DES LIVRES ****************/
    public static ArrayList<Livre> selectLivre() {
        return Modele.selectLivre();
    }

    public static Livre selectWhereLivre(int idLivre) {
        return Modele.selectWhereLivre(idLivre);
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

    public static Commande selectWhereCommande(int idCommande) {
        return Modele.selectWhereCommande(idCommande);
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



    /**************** GESTION DES DONNÉES ***************/
    public static boolean verifDonnees(ArrayList<String> lesChamps) {
        for (String champ : lesChamps) {
            if (champ == null || champ.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static String sha1Hash(String input) {
        return Modele.sha1Hash(input);
    }



    /************************ AUTRES MÉTHODES ************************/
    public static int count(String table) {
        return Modele.count(table);
    }

    public static ArrayList<Listing> selectListing(){
        return Modele.selectListing();
    }

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