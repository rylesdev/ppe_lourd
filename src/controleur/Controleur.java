package controleur;

import modele.Modele;

import java.util.ArrayList;

public class Controleur {

    /**************** GESTION DES USERS ***************/
    public static void insertUser(User unUser) {
        //Cote securite : on verifie les données avant insertion dans la bdd

        //appel du modele pour inserer le user
        Modele.insertUser(unUser);
    }

    public static ArrayList<User> selectAllUsers (){
        return Modele.selectAllUsers();
    }

    public static void deleteUser(int idUser) {
        Modele.deleteUser(idUser);
    }

    public static void updateUser(User unUser) {
        Modele.updateUser(unUser);
    }

    public static ArrayList<User> selectLikeUsers (String filtre){
        return Modele.selectLikeUsers(filtre);
    }

    public static User selectWhereUser (int idUser) {
        return Modele.selectWhereUser(idUser);
    }

    public static User selectWhereUser(String emailUser, String mdpUser) {
        return Modele.selectWhereUser(emailUser, mdpUser);
    }


    /**************** GESTION DES LIVRES ***************/
    public static void insertLivre(Livre unLivre) {
        Modele.insertLivre(unLivre);
    }

    public static ArrayList<Livre> selectAllLivres (){
        return Modele.selectAllLivres();
    }

    public static void deleteLivre(int idLivre) {
        Modele.deleteLivre(idLivre);
    }

    public static void updateLivre(Livre unLivre) {
        Modele.updateLivre(unLivre);
    }

    public static ArrayList<Livre> selectLikeLivres (String filtre){
        return Modele.selectLikeLivres(filtre);
    }

    public static Livre selectWhereLivre (int idLivre) {
        return Modele.selectWhereLivre(idLivre);
    }


    /**************** GESTION DES COMMANDES ***************/
    public static void insertCommande(Commande uneCommande) {
        Modele.insertCommande(uneCommande);
    }

    public static ArrayList<Commande> selectAllCommandes (){
        return Modele.selectAllCommandes();
    }

    public static void deleteCommande(int idCommande) {
        Modele.deleteCommande(idCommande);
    }

    public static void updateCommande(Commande uneCommande) {
        Modele.updateCommande(uneCommande);
    }

    public static ArrayList<Commande> selectLikeCommandes (String filtre){
        return Modele.selectLikeCommandes(filtre);
    }

    public static Commande selectWhereCommande (int idCommande) {
        return Modele.selectWhereCommande(idCommande);
    }


    /**************** GESTION DES LIGNESCOMMANDE ***************/
    public static void insertLigneCommande(LigneCommande uneLigneCommande) {
        Modele.insertLigneCommande(uneLigneCommande);
    }

    public static ArrayList<LigneCommande> selectAllLignesCommande (){
        return Modele.selectAllLignesCommande();
    }

    public static void deleteLigneCommande(int idCommande) {
        Modele.deleteLigneCommande(idCommande);
    }

    public static void updateLigneCommande(LigneCommande uneLigneCommande) {
        Modele.updateLigneCommande(uneLigneCommande);
    }

    public static ArrayList<LigneCommande> selectLikeLignesCommande (String filtre){
        return Modele.selectLikeLignesCommande(filtre);
    }

    public static LigneCommande selectWhereLigneCommande (int idCommande) {
        return Modele.selectWhereLigneCommande(idCommande);
    }
}

