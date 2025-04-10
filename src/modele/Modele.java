package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import controleur.*;

public class Modele {
    private static Connexion uneConnexion = new Connexion("localhost:8889", "ppe_lourd", "root", "root");


    /************************ GESTION DES USERS ************************/
    public static void insertUser(User unUser) {
        String requete = "insert into user values ("
                + "'" + unUser.getEmailUser() + "', "
                + "'" + unUser.getMdpUser() + "', "
                + "'" + unUser.getAdresseUser() + "', "
                + "'" + unUser.getRoleUser() + "');";

        executerRequete(requete);
    }

    public static ArrayList<User> selectUser() {
        ArrayList<User> lesUsers = new ArrayList<User>();
        String requete = "select * from user;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                User unUser = new User(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser")
                );
                lesUsers.add(unUser);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesUsers;
    }

    public static void deleteUser(int idUser) {
        String requete = "delete from user where idUser = " + idUser + ";";
        executerRequete(requete);
    }

    public static void updateUser(User unUser) {
        String requete = "update user set "
                + "emailUser = '" + unUser.getEmailUser() + "', "
                + "mdpUser = '" + unUser.getMdpUser() + "', "
                + "adresseUser = '" + unUser.getAdresseUser() + "', "
                + "roleUser = '" + unUser.getRoleUser() + "' "
                + "where idUser = " + unUser.getIdUser() + ";";
        executerRequete(requete);
    }

    public static ArrayList<User> selectLikeUser(String filtre) {
        ArrayList<User> lesUsers = new ArrayList<User>();
        String requete = "select * from user where "
                + "emailUser like '%" + filtre + "%' or "
                + "adresseUser like '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                User unUser = new User(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser")
                );
                lesUsers.add(unUser);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
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
                        unResultat.getInt("idUser"),
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
        return unUser;
    }

    public static User selectWhereUser(String email, String mdp, String role) {
        String requete = "select * from user where emailUser = '" + email + "' and mdpUser = '" + mdp + "' and roleUser = '" + role + "';";
        User unUser = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                unUser = new User(
                        unResultat.getInt("idUser"),
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
        return unUser;
    }


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
        String requete =    "select * from particulier p " +
                            "inner join user u " +
                            "on p.idUser=u.idUser;";
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
        String requete =    "update particulier set "
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
        String requete =    "select * from particulier where "
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
        String requete =    "select u.idUser, u.emailUser, u.mdpUser, u.adresseUser, u.roleUser, p.nomUser, p.prenomUser, p.dateNaissanceUser, p.sexeUser " +
                            "from user u " +
                            "left join particulier p " +
                            "on u.idUser = p.idUser " +
                            "where u.emailUser = '" + email + "' and u.mdpUser = '" + mdp + "';";
        Particulier unParticulier = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if(unResultat.next()) {
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
        String requete =    "SELECT l.idLivre, l.nomLivre, l.auteurLivre, l.imageLivre, l.exemplaireLivre, l.prixLivre, l.idCategorie, l.idMaisonEdition, l.idPromotion "
                            + "FROM livre l "
                            + "INNER JOIN categorie c "
                            + "ON l.idCategorie = c.idCategorie;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Livre unLivre = new Livre(
                        lesResultats.getInt("idLivre"),
                        lesResultats.getString("nomLivre"),
                        lesResultats.getString("auteurLivre"),
                        lesResultats.getString("imageLivre"),
                        lesResultats.getInt("exemplaireLivre"),
                        lesResultats.getFloat("prixLivre"),
                        lesResultats.getInt("idCategorie"),
                        lesResultats.getInt("idMaisonEdition"),
                        lesResultats.getInt("idPromotion")
                );
                lesLivres.add(unLivre);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return lesLivres;
    }

    public static ArrayList<Livre> selectLikeLivre(String filtre) {
        ArrayList<Livre> lesLivres = new ArrayList<>();
        String requete =    "select * from livre where nomLivre like '%" + filtre + "%' " +
                            "or auteurLivre like '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Livre unLivre = new Livre(
                        lesResultats.getInt("idLivre"),
                        lesResultats.getString("nomLivre"),
                        lesResultats.getString("auteurLivre"),
                        lesResultats.getString("imageLivre"),
                        lesResultats.getInt("exemplaireLivre"),
                        lesResultats.getFloat("prixLivre"),
                        lesResultats.getInt("idCategorie"),
                        lesResultats.getInt("idMaisonEdition"),
                        lesResultats.getInt("idPromotion")
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

    public static void insertLivre(Livre livre) {
        String requete = "insert into livre values (null,'"
                + livre.getNomLivre() + "', '"
                + livre.getAuteurLivre() + "', '"
                + livre.getImageLivre() + "', "
                + livre.getExemplaireLivre() + ", "
                + livre.getPrixLivre() + ", "
                + livre.getIdCategorie() + ", "
                + livre.getIdMaisonEdition() + ", "
                + livre.getIdPromotion() + ");";
        executerRequete(requete);
    }

    public static void deleteLivre(int idLivre) {
        String requete = "delete from livre where idLivre = " + idLivre + ";";
        executerRequete(requete);
    }

    public static void updateLivre(Livre unLivre) {
        String requete = "update livre set nomLivre = '" + unLivre.getNomLivre() + "', "
                + "auteurLivre = '" + unLivre.getAuteurLivre() + "', "
                + "imageLivre = '" + unLivre.getImageLivre() + "', "
                + "exemplaireLivre = " + unLivre.getExemplaireLivre() + ", "
                + "prixLivre = " + unLivre.getPrixLivre() + ", "
                + "idCategorie = " + unLivre.getIdCategorie() + ", "
                + "idMaisonEdition = " + unLivre.getIdMaisonEdition() + ", "
                + "idPromotion = " + unLivre.getIdPromotion() + " "
                + "where idLivre = " + unLivre.getIdLivre() + ";";
        executerRequete(requete);
    }

    public static int selectIdCategorie(String nomCategorie) {
        String requete =    "select idCategorie " +
                            "from categorie " +
                            "where nomCategorie = '" + nomCategorie + "';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet resultat = unStat.executeQuery(requete);
            if (resultat.next()) {
                return resultat.getInt("idCategorie");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return -1;
    }

    public static int selectIdMaisonEdition(String nomMaisonEdition) {
        String requete =    "select idMaisonEdition " +
                            "from maisonEdition " +
                            "where nomMaisonEdition = '" + nomMaisonEdition + "';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet resultat = unStat.executeQuery(requete);
            if (resultat.next()) {
                return resultat.getInt("idMaisonEdition");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return -1;
    }

    public static int selectIdPromotion(String nomPromotion) {
        String requete =    "select idPromotion " +
                            "from promotion " +
                            "where nomPromotion = '" + nomPromotion + "';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet resultat = unStat.executeQuery(requete);
            if (resultat.next()) {
                return resultat.getInt("idPromotion");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return -1;
    }

    public static String selectNomCategorie(int idCategorie) {
        String requete =    "select nomCategorie " +
                            "from categorie " +
                            "where idCategorie = " + idCategorie + ";";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet resultat = unStat.executeQuery(requete);
            if (resultat.next()) {
                return resultat.getString("nomCategorie");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return null;
    }

    public static String selectNomMaisonEdition(int idMaisonEdition) {
        String requete =    "select nomMaisonEdition " +
                            "from maisonEdition " +
                            "where idMaisonEdition = " + idMaisonEdition + ";";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet resultat = unStat.executeQuery(requete);
            if (resultat.next()) {
                return resultat.getString("nomMaisonEdition");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return null;
    }

    public static String selectNomPromotion(int idPromotion) {
        String requete =    "select nomPromotion " +
                            "from promotion " +
                            "where idPromotion = " + idPromotion + ";";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet resultat = unStat.executeQuery(requete);
            if (resultat.next()) {
                return resultat.getString("nomPromotion");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return null;
    }


    /************************ GESTION DES COMMANDES ************************/
    public static ArrayList<Commande> selectCommande() {
        ArrayList<Commande> lesCommandes = new ArrayList<>();
        String requete = "select * from commande;";
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

    public static ArrayList<Commande> selectLikeCommande(String filtre) {
        ArrayList<Commande> lesCommandes = new ArrayList<>();
        String requete =    "select * from commande where idCommande like '%" + filtre + "%' " +
                            "or dateCommande like '%" + filtre + "%' " +
                            "or statutCommande like '%" + filtre + "%' " +
                            "or dateLivraisonCommande like '%" + filtre + "%' " +
                            "or idUser like '%" + filtre + "%';";
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCommande = dateFormat.format(uneCommande.getDateCommande());
        String dateLivraisonCommande = dateFormat.format(uneCommande.getDateLivraisonCommande());

        String requete =    "insert into commande values (null, '" +
                            dateCommande + "', '" +
                            uneCommande.getStatutCommande() + "', '" +
                            dateLivraisonCommande + "', " +
                            uneCommande.getIdUser() + ");";
        executerRequete(requete);
    }

    public static void deleteCommande(int idCommande) {
        String requete = "DELETE FROM commande WHERE idCommande = " + idCommande + ";";
        executerRequete(requete);
    }

    public static void updateCommande(Commande uneCommande) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCommande = dateFormat.format(uneCommande.getDateCommande());
        String dateLivraisonCommande = dateFormat.format(uneCommande.getDateLivraisonCommande());

        String requete =    "update commande set " +
                            "dateCommande = '" + dateCommande + "', " +
                            "statutCommande = '" + uneCommande.getStatutCommande() + "', " +
                            "dateLivraisonCommande = '" + dateLivraisonCommande + "', " +
                            "idUser = '" + uneCommande.getIdUser() + "' " +
                            "where idCommande = " + uneCommande.getIdCommande() + ";";
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
        ArrayList<Listing> lesListings = new ArrayList<>();
        String requete = "select * from user;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Listing unListing = new Listing(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser")
                );
                lesListings.add(unListing);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return lesListings;
    }
}