package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.User;
import controleur.Livre;
import controleur.Commande;
import controleur.LigneCommande;

public class Modele {
    private static Connexion uneConnexion = new Connexion("localhost:8889", "ppe", "root", "root");

    /************************ GESTION DES USERS **********************/
    public static void insertUser(User unUser) {
        String requete = "insert into user values (null, '" + unUser.getNomUser()
                + "', '" + unUser.getPrenomUser()
                + "', '" + unUser.getEmailUser()
                + "', '" + unUser.getMdpUser()
                + "', '" + unUser.getAdresseUser()
                + "', '" + unUser.getRoleUser()
                + "', '" + unUser.getDateInscriptionUser() + "');";

        executerRequete(requete);
    }

    public static ArrayList<User> selectAllUsers() {
        ArrayList<User> lesUsers = new ArrayList<User>();
        String requete = "select * from user;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                User unUser = new User(
                        lesResultats.getInt("idUser"), lesResultats.getString("nomUser"),
                        lesResultats.getString("prenomUser"), lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"), lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser"), lesResultats.getDate("dateInscriptionUser")
                );
                lesUsers.add(unUser);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesUsers;
    }

    public static void deleteUser(int idUser) {
        String requete = "delete from user where idUser = " + idUser + ";";
        executerRequete(requete);
    }

    public static void updateUser(User unUser) {
        String requete = "update user set nomUser = '" + unUser.getNomUser()
                + "', prenomUser ='" + unUser.getPrenomUser()
                + "', emailUser='" + unUser.getEmailUser()
                + "', mdpUser ='" + unUser.getMdpUser()
                + "', adresseUser='" + unUser.getAdresseUser()
                + "', roleUser='" + unUser.getRoleUser()
                + "', dateInscriptionUser='" + unUser.getDateInscriptionUser()
                + "' where idUser = " + unUser.getIdUser() + ";";

        executerRequete(requete);
    }

    public static ArrayList<User> selectLikeUsers(String filtre) {
        ArrayList<User> lesUsers = new ArrayList<User>();
        String requete = "select * from user where " +
                "nomUser like '%" + filtre + "%' or " +
                "prenomUser like '%" + filtre + "%' or " +
                "emailUser like '%" + filtre + "%' or " +
                "adresseUser like '%" + filtre + "%'; ";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                User unUser = new User(
                        lesResultats.getInt("idUser"), lesResultats.getString("nomUser"),
                        lesResultats.getString("prenomUser"), lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"), lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser"), lesResultats.getDate("dateInscriptionUser")
                );
                lesUsers.add(unUser);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesUsers;
    }

    public static User selectWhereUser(int idUser) {
        String requete = "select * from user where idUser = " + idUser + ";";
        User unUser = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                unUser = new User(
                        unResultat.getInt("idUser"), unResultat.getString("nomUser"),
                        unResultat.getString("prenomUser"), unResultat.getString("emailUser"),
                        unResultat.getString("mdpUser"), unResultat.getString("adresseUser"),
                        unResultat.getString("roleUser"), unResultat.getDate("dateInscriptionUser")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return unUser;
    }

    public static User selectWhereUser(String emailUser, String mdpUser) {
        String requete = "select * from user where " +
                "emailUser = '" + emailUser +
                "'and mdpUser = " + mdpUser + ";";
        User unUser = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                unUser = new User(
                        unResultat.getInt("idUser"), unResultat.getString("nomUser"),
                        unResultat.getString("prenomUser"), unResultat.getString("emailUser"),
                        unResultat.getString("mdpUser"), unResultat.getString("adresseUser"),
                        unResultat.getString("roleUser"), unResultat.getDate("dateInscriptionUser")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return unUser;
    }

    /************************ GESTION DES LIVRES **********************/
    public static void insertLivre(Livre unLivre) {
        String requete = "insert into livre values (null, '" + unLivre.getNomLivre()
                + "', '" + unLivre.getCategorieLivre()
                + "', '" + unLivre.getAuteurLivre()
                + "', '" + unLivre.getImageLivre()
                + "', " + unLivre.getExemplaireLivre()
                + ", " + unLivre.getPrixLivre() + ");";

        executerRequete(requete);
    }

    public static ArrayList<Livre> selectAllLivres() {
        ArrayList<Livre> lesLivres = new ArrayList<Livre>();
        String requete = "select * from livre;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Livre unLivre = new Livre(
                        lesResultats.getInt("idLivre"), lesResultats.getString("nomLivre"),
                        lesResultats.getString("categorieLivre"), lesResultats.getString("auteurLivre"),
                        lesResultats.getString("imageLivre"), lesResultats.getInt("exemplaireLivre"),
                        lesResultats.getFloat("prixLivre")
                );
                lesLivres.add(unLivre);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesLivres;
    }

    public static void deleteLivre(int idLivre) {
        String requete = "delete from livre where idLivre = " + idLivre + ";";
        executerRequete(requete);
    }

    public static void updateLivre(Livre unLivre) {
        String requete = "update livre set nomLivre = '" + unLivre.getNomLivre() +
                "', categorieLivre ='" + unLivre.getCategorieLivre() +
                "', auteurLivre='" + unLivre.getAuteurLivre() +
                "', imageLivre ='" + unLivre.getImageLivre() +
                "', exemplaireLivre=" + unLivre.getExemplaireLivre() +
                ", prixLivre=" + unLivre.getPrixLivre() +
                " where idLivre = " + unLivre.getIdLivre() + ";";

        executerRequete(requete);
    }

    public static ArrayList<Livre> selectLikeLivres(String filtre) {
        ArrayList<Livre> lesLivres = new ArrayList<Livre>();
        String requete = "select * from livre where nomLivre like '%" + filtre + "%' or " +
                "categorieLivre like '%" + filtre + "%' or " +
                "auteurLivre like '%" + filtre + "%' or " +
                "imageLivre like '%" + filtre + "%' or " +
                "exemplaireLivre like '%" + filtre + "%' or " +
                "prixLivre like '%" + filtre + "%' ;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Livre unLivre = new Livre(
                        lesResultats.getInt("idLivre"), lesResultats.getString("nomLivre"),
                        lesResultats.getString("categorieLivre"), lesResultats.getString("auteurLivre"),
                        lesResultats.getString("imageLivre"), lesResultats.getInt("exemplaireLivre"),
                        lesResultats.getFloat("prixLivre")
                );
                lesLivres.add(unLivre);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesLivres;
    }

    public static Livre selectWhereLivre(int idLivre) {
        String requete = "select * from livre where idLivre = " + idLivre + ";";
        Livre unLivre = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                unLivre = new Livre(
                        unResultat.getInt("idLivre"), unResultat.getString("nomLivre"),
                        unResultat.getString("categorieLivre"), unResultat.getString("auteurLivre"),
                        unResultat.getString("imageLivre"), unResultat.getInt("exemplaireLivre"),
                        unResultat.getFloat("prixLivre")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return unLivre;
    }


    /************************ GESTION DES COMMANDES **********************/
    public static void insertCommande(Commande uneCommande) {
        String requete = "insert into commande values (null, '" + uneCommande.getDateCommande()
                + ", '" + uneCommande.getStatutCommande()
                + ", '" + uneCommande.getDateLivraisonCommande()
                + "', " + uneCommande.getIdUser() + "');";
        executerRequete(requete);
    }

    public static ArrayList<Commande> selectAllCommandes() {
        ArrayList<Commande> lesCommandes = new ArrayList<Commande>();
        String requete = "select * from commande;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Commande uneCommande = new Commande(
                        lesResultats.getInt("idCommande"), lesResultats.getDate("dateCommande"),
                        lesResultats.getString("statutCommande"), lesResultats.getDate("dateLivraisonCommande"),
                        lesResultats.getInt("idUser")
                );
                lesCommandes.add(uneCommande);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesCommandes;
    }

    public static void deleteCommande(int idCommande) {
        String requete = "delete from commande where idCommande = " + idCommande + ";";
        executerRequete(requete);
    }

    public static void updateCommande(Commande uneCommande) {
        String requete = "update commande set dateCommande = '" + uneCommande.getDateCommande() +
                "', dateLivraisonCommande ='" + uneCommande.getDateLivraisonCommande() +
                "', statutCommande ='" + uneCommande.getStatutCommande() +
                " where idCommande = " + uneCommande.getIdCommande() + ";";

        executerRequete(requete);
    }

    public static ArrayList<Commande> selectLikeCommandes(String filtre) {
        ArrayList<Commande> lesCommandes = new ArrayList<Commande>();
        String requete = "select * from commande where dateCommande like '%" + filtre + "%' or " +
                "dateLivraisonCommande like '%" + filtre + "%' or " +
                "statutCommande like '%" + filtre + "%' ;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Commande uneCommande = new Commande(
                        lesResultats.getInt("idCommande"), lesResultats.getDate("dateCommande"),
                        lesResultats.getString("statutCommande"), lesResultats.getDate("dateLivraisonCommande"),
                        lesResultats.getInt("idUser")
                );
                lesCommandes.add(uneCommande);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesCommandes;
    }

    public static Commande selectWhereCommande(int idCommande) {
        String requete = "select * from commande where idCommande = " + idCommande + ";";
        Commande uneCommande = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                uneCommande = new Commande(
                        unResultat.getInt("idCommande"), unResultat.getDate("dateCommande"),
                        unResultat.getString("statutCommande"), unResultat.getDate("dateLivraisonCommande"),
                        unResultat.getInt("idUser")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return uneCommande;
    }


    /**************** GESTION DES LIGNESCOMMANDE ****************/
    public static void insertLigneCommande(LigneCommande uneLigneCommande) {
        String requete =    "insert into ligneCommande values (" + uneLigneCommande.getIdCommande() +
                            "', '" + uneLigneCommande.getIdLivre() +
                            "', '" + uneLigneCommande.getQuantiteLigneCommande() + "');";

        executerRequete(requete);
    }

    public static ArrayList<LigneCommande> selectAllLignesCommande() {
        ArrayList<LigneCommande> lesLignesCommande = new ArrayList<LigneCommande>();
        String requete =    "select * from ligneCommande;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                LigneCommande uneLigneCommande = new LigneCommande (
                        lesResultats.getInt("idCommande"), lesResultats.getInt("idLivre"),
                        lesResultats.getInt("quantiteLigneCommande")
                );
                lesLignesCommande.add(uneLigneCommande);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesLignesCommande;
    }

    public static void deleteLigneCommande(int idCommande) {
        String requete =    "delete from ligneCommande where idCommande = " + idCommande + ";";

        executerRequete(requete);
    }

    public static void updateLigneCommande(LigneCommande uneLigneCommande) {
        String requete =    "update ligneCommande set idLivre = '" + uneLigneCommande.getIdLivre() +
                            "', quantiteLigneCommande ='" + uneLigneCommande.getQuantiteLigneCommande() +
                            "' where idCommande = " + uneLigneCommande.getIdCommande() + ";";

        executerRequete(requete);
    }

    public static ArrayList<LigneCommande> selectLikeLignesCommande(String filtre) {
        ArrayList<LigneCommande> lesLignesCommande = new ArrayList<LigneCommande>();
        String requete =    "select * from ligneCommande where " +
                            "idCommande like '%" + filtre + "%' or " +
                            "idLivre like '%" + filtre + "%' or " +
                            "quantiteLigneCommande like '%" + filtre + "%' ;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                LigneCommande uneLigneCommande = new LigneCommande(
                        lesResultats.getInt("idCommande"), lesResultats.getInt("idLivre"),
                        lesResultats.getInt("quantiteLigneCommande")
                );
                lesLignesCommande.add(uneLigneCommande);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return lesLignesCommande;
    }

    public static LigneCommande selectWhereLigneCommande(int idCommande) {
        String requete =    "select * from ligneCommande where idCommande = " + idCommande + ";";
        LigneCommande uneLigneCommande = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                uneLigneCommande = new LigneCommande(
                        unResultat.getInt("idCommande"), unResultat.getInt("idLivre"),
                        unResultat.getInt("quantiteLigneCommande")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
        return uneLigneCommande;
    }


    /************************** Autres méthodes *******************/
    public static void executerRequete (String requete) {
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            unStat.execute(requete);
            unStat.close();
            uneConnexion.seDeConnecter();
        }
        catch(SQLException exp) {
            System.out.println("Erreur d'execution de la requete : " + requete);
        }
    }
}