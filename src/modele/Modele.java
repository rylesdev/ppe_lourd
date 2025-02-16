package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.*;

public class Modele {
    private static Connexion uneConnexion = new Connexion("localhost:8889", "ppe-lourd", "root", "root");

    /************************ GESTION DES PARTICULIERS **********************/
    public static void insertParticulier(Particulier unParticulier) {
        String requete = "insert into particulier (nomUser, prenomUser, dateNaissanceUser, sexeUser, emailUser, mdpUser, adresseUser, roleUser) values ("
                + "'" + unParticulier.getNomUser() + "', "
                + "'" + unParticulier.getPrenomUser() + "', "
                + "'" + unParticulier.getDateNaissanceUser() + "', "
                + "'" + unParticulier.getSexeUser() + "', "
                + "'" + unParticulier.getEmailUser() + "', "
                + "'" + unParticulier.getMdpUser() + "', "
                + "'" + unParticulier.getAdresseUser() + "', "
                + "'" + unParticulier.getRoleUser() + "');";

        executerRequete(requete);
    }

    public static ArrayList<Particulier> selectParticulier() {
        ArrayList<Particulier> lesParticuliers = new ArrayList<Particulier>();
        String requete = "select * from particulier;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Particulier unParticulier = new Particulier(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("nomUser"),
                        lesResultats.getString("prenomUser"),
                        lesResultats.getDate("dateNaissanceUser"),
                        lesResultats.getString("sexeUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser")
                );
                // On ajoute le particulier dans l'ArrayList
                lesParticuliers.add(unParticulier);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesParticuliers;
    }

    public static void deleteParticulier(int idUser) {
        String requete = "delete from particulier where idUser = " + idUser + ";";
        executerRequete(requete);
    }

    public static void updateParticulier(Particulier unParticulier) {
        String requete = "update particulier set "
                + "nomUser = '" + unParticulier.getNomUser() + "', "
                + "prenomUser = '" + unParticulier.getPrenomUser() + "', "
                + "dateNaissanceUser = '" + unParticulier.getDateNaissanceUser() + "', "
                + "sexeUser = '" + unParticulier.getSexeUser() + "', "
                + "emailUser = '" + unParticulier.getEmailUser() + "', "
                + "mdpUser = '" + unParticulier.getMdpUser() + "', "
                + "adresseUser = '" + unParticulier.getAdresseUser() + "', "
                + "roleUser = '" + unParticulier.getRoleUser() + "' "
                + "where idUser = " + unParticulier.getIdUser() + ";";

        executerRequete(requete);
    }

    public static ArrayList<Particulier> selectLikeParticulier(String filtre) {
        ArrayList<Particulier> lesParticuliers = new ArrayList<Particulier>();
        String requete = "select * from particulier where "
                + "nomUser like '%" + filtre + "%' or "
                + "prenomUser like '%" + filtre + "%' or "
                + "dateNaissanceUser like '%" + filtre + "%' or "
                + "sexeUser like '%" + filtre + "%' or "
                + "emailUser like '%" + filtre + "%' or "
                + "adresseUser like '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Particulier unParticulier = new Particulier(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("nomUser"),
                        lesResultats.getString("prenomUser"),
                        lesResultats.getDate("dateNaissanceUser"),
                        lesResultats.getString("sexeUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser")
                );
                // On ajoute le particulier dans l'ArrayList
                lesParticuliers.add(unParticulier);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesParticuliers;
    }

    public static Particulier selectWhereParticulier(int idUser) {
        String requete = "select * from particulier where idUser = " + idUser + ";";
        Particulier unParticulier = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                unParticulier = new Particulier(
                        unResultat.getInt("idUser"),
                        unResultat.getString("nomUser"),
                        unResultat.getString("prenomUser"),
                        unResultat.getDate("dateNaissanceUser"),
                        unResultat.getString("sexeUser"),
                        unResultat.getString("emailUser"),
                        unResultat.getString("mdpUser"),
                        unResultat.getString("adresseUser"),
                        unResultat.getString("roleUser")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return unParticulier;
    }

    public static Particulier selectWhereParticulier(String email, String mdp) {
        String requete ="select * from user where email ='"+email+"' and mdp ='"+mdp+"';";
        Particulier unParticulier = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if(unResultat.next()) {
                //instanciation du client
                unParticulier = new Particulier(
                        unResultat.getInt("idUser"),
                        unResultat.getString("nomUser"),
                        unResultat.getString("prenomUser"),
                        unResultat.getDate("dateNaissanceUser"),
                        unResultat.getString("sexeUser"),
                        unResultat.getString("emailUser"),
                        unResultat.getString("mdpUser"),
                        unResultat.getString("adresseUser"),
                        unResultat.getString("roleUser")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        }
        catch(SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return unParticulier;
    }


    /************************ GESTION DES LIVRES ************************/
    public static ArrayList<Livre> selectLivre() {
        ArrayList<Livre> lesLivres = new ArrayList<>();
        String requete = "SELECT * FROM livre;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Livre unLivre = new Livre(
                        lesResultats.getInt("idLivre"),
                        lesResultats.getString("nomLivre"),
                        lesResultats.getString("categorieLivre"),
                        lesResultats.getString("auteurLivre"),
                        lesResultats.getString("imageLivre"),
                        lesResultats.getInt("exemplaireLivre"),
                        lesResultats.getFloat("prixLivre")
                );
                lesLivres.add(unLivre);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesLivres;
    }

    public static ArrayList<Livre> selectLikeLivre(String filtre) {
        ArrayList<Livre> lesLivres = new ArrayList<>();
        String requete = "SELECT * FROM livre WHERE nomLivre LIKE '%" + filtre + "%' OR auteurLivre LIKE '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Livre unLivre = new Livre(
                        lesResultats.getInt("idLivre"),
                        lesResultats.getString("nomLivre"),
                        lesResultats.getString("categorieLivre"),
                        lesResultats.getString("auteurLivre"),
                        lesResultats.getString("imageLivre"),
                        lesResultats.getInt("exemplaireLivre"),
                        lesResultats.getFloat("prixLivre")
                );
                lesLivres.add(unLivre);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesLivres;
    }


    /************************ GESTION DES COMMANDES ************************/
    public static ArrayList<Commande> selectCommande(int idUser) {
        ArrayList<Commande> lesCommandes = new ArrayList<>();
        String requete = "SELECT * FROM commande WHERE idUser = " + idUser + ";";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Commande uneCommande = new Commande(
                        lesResultats.getInt("idCommande"),
                        lesResultats.getDate("dateCommande"),
                        lesResultats.getString("statutCommande"),
                        lesResultats.getDate("dateLivraisonCommande"),
                        lesResultats.getInt("idUser")
                );
                lesCommandes.add(uneCommande);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesCommandes;
    }

    public static void insertCommande(Commande uneCommande) {
        String requete = "INSERT INTO commande (dateCommande, statutCommande, dateLivraisonCommande, idUser) VALUES ("
                + "'" + uneCommande.getDateCommande() + "', "
                + "'" + uneCommande.getStatutCommande() + "', "
                + "'" + uneCommande.getDateLivraisonCommande() + "', "
                + uneCommande.getIdUser() + ");";
        executerRequete(requete);
    }

    public static void deleteCommande(int idCommande) {
        String requete = "DELETE FROM commande WHERE idCommande = " + idCommande + ";";
        executerRequete(requete);
    }


    /************************ GESTION DES ABONNEMENTS ************************/
    public static void insertAbonnement(Abonnement unAbonnement) {
        String requete = "INSERT INTO abonnement (idUser, dateDebutAbonnement, dateFinAbonnement, pointAbonnement) VALUES ("
                + unAbonnement.getIdUser() + ", "
                + "'" + unAbonnement.getDateDebutAbonnement() + "', "
                + "'" + unAbonnement.getDateFinAbonnement() + "', "
                + unAbonnement.getPointAbonnement() + ");";
        executerRequete(requete);
    }


    /************************ AUTRES MÉTHODES ************************/
    private static void executerRequete(String requete) {
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            unStat.execute(requete);
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
    }

    public static int count(String table) {
        int nb = 0;
        String requete = " select count(*) as nb from  "+table + ";";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat =  unStat.executeQuery(requete);
            if (unResultat.next()) {
                nb = unResultat.getInt("nb");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        }
        catch(SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }

        return nb ;
    }

    public static ArrayList<Listing> selectListing() {
        ArrayList<Listing> lesListings = new ArrayList<Listing>();
        String requete ="select * from listing;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while(lesResultats.next()) {
                //instanciation d'un client
                Listing unListing = new Listing(
                        lesResultats.getString("nom"),
                        lesResultats.getString("prenom"),lesResultats.getString("designation"),
                        lesResultats.getString("description"), lesResultats.getString("dateInter")
                );
                //on ajoute le client dans l'ArrayList
                lesListings.add(unListing);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        }
        catch(SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesListings;
    }
}