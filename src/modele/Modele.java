package modele;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
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
        String mdpHashe = sha1Hash(unUser.getMdpUser()); // Hachage du mot de passe
        String requete = "insert into user values (null," + "'" +
                unUser.getEmailUser() + "', '" +
                mdpHashe + "', '" +
                unUser.getAdresseUser() + "', '" +
                unUser.getRoleUser() + "');";
        executerRequete(requete);
    }

    public static boolean emailExiste(String email) {
        String requete = "SELECT COUNT(*) AS count FROM user WHERE emailUser = '" + email + "'";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteUser(int idUser) {
        String requete = "delete from user where idUser = " + idUser + ";";
        executerRequete(requete);
    }

    public static void updateUser(User unUser) {
        String requete =    "update user set " +
                            "emailUser = '" + unUser.getEmailUser() + "', " +
                            "adresseUser = '" + unUser.getAdresseUser() + "', " +
                            "roleUser = '" + unUser.getRoleUser() + "' " +
                            "where idUser = " + unUser.getIdUser() + ";";
        executerRequete(requete);
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

    public static int selectIdUser() {
        int idUser = 0;
        String requete = "select max(idUser) from user;";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);

            if (unResultat.next()) {
                idUser = unResultat.getInt(1);
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return idUser;
    }



    /************************ GESTION DES PARTICULIERS **********************/
    public static String insertParticulier(Particulier unParticulier) {
        String mdpAleatoire = generateRandomPassword();
        unParticulier.setMdpUser(mdpAleatoire);

        Modele.insertUser(unParticulier);
        int idUser = Modele.selectIdUser();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateUser = dateFormat.format(unParticulier.getDateNaissanceUser());

        String requete = "insert into particulier values (" +
                idUser + ", '" +
                unParticulier.getNomUser() + "', '" +
                unParticulier.getPrenomUser() + "', '" +
                dateUser + "', '" +
                unParticulier.getSexeUser() + "');";

        try {
            executerRequete(requete);
            return "OK:" + mdpAleatoire; // Retourne un message de succès avec le mot de passe généré
        } catch (Exception e) {
            return "Erreur lors de l'insertion : " + e.getMessage();
        }
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
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser"),
                        lesResultats.getString("nomUser"),
                        lesResultats.getString("prenomUser"),
                        lesResultats.getDate("dateNaissanceUser"),
                        lesResultats.getString("sexeUser")
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
        Modele.deleteUser(idUser);

        String requete = "delete from particulier where idUser = " + idUser + ";";
        executerRequete(requete);
    }

    public static void updateParticulier(Particulier unParticulier) {
        Modele.updateUser(unParticulier);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateUser = dateFormat.format(unParticulier.getDateNaissanceUser());

        String requete =    "update particulier set " +
                            "nomUser = '" + unParticulier.getNomUser() + "', " +
                            "prenomUser = '" + unParticulier.getPrenomUser() + "', " +
                            "dateNaissanceUser = '" + dateUser + "', " +
                            "sexeUser = '" + unParticulier.getSexeUser() + "' " +
                            "where idUser = " + unParticulier.getIdUser() + ";";
        executerRequete(requete);
    }

    public static ArrayList<Particulier> selectLikeParticulier(String filtre) {
        ArrayList<Particulier> lesParticuliers = new ArrayList<Particulier>();
        String requete =    "select * from user u " +
                            "inner join particulier p " +
                            "on u.idUser=p.idUser where " +
                            "u.idUser like '%" + filtre + "%' or " +
                            "u.emailUser like '%" + filtre + "%' or " +
                            "u.adresseUser like '%" + filtre + "%' or " +
                            "u.roleUser like '%" + filtre + "%' or " +
                            "p.nomUser like '%" + filtre + "%' or " +
                            "p.prenomUser like '%" + filtre + "%' or " +
                            "p.dateNaissanceUser like '%" + filtre + "%' or " +
                            "p.sexeUser like '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Particulier unParticulier = new Particulier(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser"),
                        lesResultats.getString("nomUser"),
                        lesResultats.getString("prenomUser"),
                        lesResultats.getDate("dateNaissanceUser"),
                        lesResultats.getString("sexeUser")
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



    /************************ GESTION DES ENTREPRISES **********************/
    public static String insertEntreprise(Entreprise uneEntreprise) {
        String mdpAleatoire = generateRandomPassword();
        uneEntreprise.setMdpUser(mdpAleatoire);

        Modele.insertUser(uneEntreprise);
        int idUser = Modele.selectIdUser();

        String requete = "insert into entreprise values (" +
                idUser + ", '" +
                uneEntreprise.getSiretUser() + "', '" +
                uneEntreprise.getRaisonSocialeUser() + "', " +
                uneEntreprise.getCapitalSocialUser() + ");";

        try {
            executerRequete(requete);
            return "OK:" + mdpAleatoire; // Retourne un message de succès avec le mot de passe généré
        } catch (Exception e) {
            return "Erreur lors de l'insertion : " + e.getMessage();
        }
    }

    public static ArrayList<Entreprise> selectEntreprise() {
        ArrayList<Entreprise> lesEntreprises = new ArrayList<Entreprise>();
        String requete =    "select * from entreprise e " +
                            "inner join user u " +
                            "on e.idUser=u.idUser;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Entreprise uneEntreprise = new Entreprise(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser"),
                        lesResultats.getString("siretUser"),
                        lesResultats.getString("raisonSocialeUser"),
                        lesResultats.getFloat("capitalSocialUser")
                );
                lesEntreprises.add(uneEntreprise);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesEntreprises;
    }

    public static void deleteEntreprise(int idUser) {
        Modele.deleteUser(idUser);

        String requete = "delete from entreprise where idUser = " + idUser + ";";
        executerRequete(requete);
    }

    public static void updateEntreprise(Entreprise uneEntreprise) {
        Modele.updateUser(uneEntreprise);

        String requete =    "update entreprise set " +
                            "siretUser = '" + uneEntreprise.getSiretUser() + "', " +
                            "raisonSocialeUser = '" + uneEntreprise.getRaisonSocialeUser() + "', " +
                            "capitalSocialUser = " + uneEntreprise.getCapitalSocialUser() + " " +
                            "where idUser = " + uneEntreprise.getIdUser() + ";";
        executerRequete(requete);
    }

    public static ArrayList<Entreprise> selectLikeEntreprise(String filtre) {
        ArrayList<Entreprise> lesEntreprises = new ArrayList<Entreprise>();
        String requete =    "select * from user u " +
                            "inner join entreprise e " +
                            "on u.idUser=e.idUser where " +
                            "u.idUser like '%" + filtre + "%' or " +
                            "u.emailUser like '%" + filtre + "%' or " +
                            "u.adresseUser like '%" + filtre + "%' or " +
                            "u.roleUser like '%" + filtre + "%' or " +
                            "e.siretUser like '%" + filtre + "%' or " +
                            "e.raisonSocialeUser like '%" + filtre + "%' or " +
                            "e.capitalSocialUser like '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Entreprise uneEntreprise = new Entreprise(
                        lesResultats.getInt("idUser"),
                        lesResultats.getString("emailUser"),
                        lesResultats.getString("mdpUser"),
                        lesResultats.getString("adresseUser"),
                        lesResultats.getString("roleUser"),
                        lesResultats.getString("siretUser"),
                        lesResultats.getString("raisonSocialeUser"),
                        lesResultats.getFloat("capitalSocialUser")
                );
                lesEntreprises.add(uneEntreprise);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesEntreprises;
    }



    /************************ GESTION DES LIVRES ************************/
    public static ArrayList<Livre> selectLivre() {
        ArrayList<Livre> lesLivres = new ArrayList<>();
        String requete =    "select * from livre " +
                            "order by idLivre;";
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

    public static Livre selectWhereLivre(int idLivre) {
        String requete = "select * from livre where idLivre = " + idLivre + ";";
        Livre unLivre = null;
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);
            if (unResultat.next()) {
                unLivre = new Livre(
                        unResultat.getInt("idLivre"),
                        unResultat.getString("nomLivre"),
                        unResultat.getString("auteurLivre"),
                        unResultat.getString("imageLivre"),
                        unResultat.getInt("exemplaireLivre"),
                        unResultat.getFloat("prixLivre"),
                        unResultat.getInt("idCategorie"),
                        unResultat.getInt("idMaisonEdition"),
                        unResultat.getInt("idPromotion")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return unLivre;
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
        String requete =    "update livre set " +
                            "nomLivre = '" + unLivre.getNomLivre() + "', " +
                            "auteurLivre = '" + unLivre.getAuteurLivre() + "', " +
                            "imageLivre = '" + unLivre.getImageLivre() + "', " +
                            "exemplaireLivre = " + unLivre.getExemplaireLivre() + ", " +
                            "prixLivre = " + unLivre.getPrixLivre() + ", " +
                            "idCategorie = " + unLivre.getIdCategorie() + ", " +
                            "idMaisonEdition = " + unLivre.getIdMaisonEdition() + ", " +
                            "idPromotion = " + unLivre.getIdPromotion() + " " +
                            "where idLivre = " + unLivre.getIdLivre() + ";";
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
                String reqLignes = "select * from ligneCommande where idCommande = " + uneCommande.getIdCommande();
                Statement statLignes = uneConnexion.getMaConnexion().createStatement();
                ResultSet resLignes = statLignes.executeQuery(reqLignes);
                while (resLignes.next()) {
                    LigneCommande uneLigne = new LigneCommande(
                            resLignes.getInt("idLigneCommande"),
                            resLignes.getInt("idCommande"),
                            resLignes.getInt("idLivre"),
                            resLignes.getInt("quantiteLigneCommande")
                    );
                    uneCommande.ajouterLigneCommande(uneLigne);
                }
                statLignes.close();
                lesCommandes.add(uneCommande);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
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
                        unResultat.getInt("idCommande"),
                        unResultat.getDate("dateCommande"),
                        unResultat.getString("statutCommande"),
                        unResultat.getDate("dateLivraisonCommande"),
                        unResultat.getInt("idUser")
                );
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return uneCommande;
    }

    public static ArrayList<Commande> selectLikeCommande(String filtre) {
        ArrayList<Commande> lesCommandes = new ArrayList<>();
        String requete =    "select * from commande where " +
                            "idCommande like '%" + filtre + "%' " +
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
                String reqLignes = "select * from ligneCommande where idCommande = " + uneCommande.getIdCommande();
                Statement statLignes = uneConnexion.getMaConnexion().createStatement();
                ResultSet resLignes = statLignes.executeQuery(reqLignes);
                while (resLignes.next()) {
                    LigneCommande uneLigne = new LigneCommande(
                            resLignes.getInt("idLigneCommande"),
                            resLignes.getInt("idCommande"),
                            resLignes.getInt("idLivre"),
                            resLignes.getInt("quantiteLigneCommande")
                    );
                    uneCommande.ajouterLigneCommande(uneLigne);
                }
                statLignes.close();
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
        String dateLivraison = uneCommande.getDateLivraisonCommande() != null
                ? dateFormat.format(uneCommande.getDateLivraisonCommande())
                : "null";

        try {
            // 1. Connexion et démarrage transaction (comme dans selectWhereLigneCommande)
            uneConnexion.seConnecter();
            Connection conn = uneConnexion.getMaConnexion();
            conn.setAutoCommit(false); // Ajout spécifique pour les transactions

            // 2. Insertion commande (style identique à votre code original)
            String requete = "insert into commande values (null, '"
                    + dateCommande + "', '"
                    + uneCommande.getStatutCommande() + "', "
                    + (dateLivraison.equals("null") ? "null" : "'" + dateLivraison + "'") + ", "
                    + uneCommande.getIdUser() + ")";

            Statement st = conn.createStatement();
            st.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);

            // 3. Récupération ID (identique à votre code)
            ResultSet rs = st.getGeneratedKeys();
            int idCommande = rs.next() ? rs.getInt(1) : 0;

            // 4. Insertion lignes (style préservé)
            for (LigneCommande uneLigneCommande : uneCommande.getLesLignesCommande()) {
                String reqLigne = "insert into ligneCommande values (null, "
                        + idCommande + ", "
                        + uneLigneCommande.getIdLivre() + ", "
                        + uneLigneCommande.getQuantiteLigneCommande() + ")";
                st.executeUpdate(reqLigne);
            }

            // 5. Déclenchement conditionnel du trigger (inchangé)
            if (uneCommande.getStatutCommande().equals("expédiée")) {
                String requeteTrigger = "update ligneCommande set idCommande = '" + idCommande + "' " +
                                        "where idCommande = " + idCommande;
                st.executeUpdate(requeteTrigger);
            }

            conn.commit(); // Validation transaction

            // 6. Fermeture identique à selectWhereLigneCommande
            st.close();
            uneConnexion.seDeConnecter();

        } catch (SQLException exp) {
            try {
                // Rollback ajouté pour annuler les opérations
                if (uneConnexion.getMaConnexion() != null) {
                    uneConnexion.getMaConnexion().rollback();
                    System.out.println("ROLLBACK effectué. Erreur: " + exp.getMessage());
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors du rollback : " + e.getMessage());
            }
        } finally {
            // Fermeture sécurisée comme dans votre méthode
            try {
                if (uneConnexion.getMaConnexion() != null && !uneConnexion.getMaConnexion().isClosed()) {
                    uneConnexion.getMaConnexion().setAutoCommit(true); // Reset auto-commit
                    uneConnexion.seDeConnecter();
                }
            } catch (SQLException e) {
                System.out.println("Erreur fermeture : " + e.getMessage());
            }
        }
    }

    public static void deleteCommande(int idCommande) {
        String requeteCommande = "delete from commande where idCommande = " + idCommande + ";";
        String requeteLigneCommande = "delete from ligneCommande where idCommande = " + idCommande + ";";
        executerRequete(requeteCommande);
        executerRequete(requeteLigneCommande);
    }

    public static void updateCommande(Commande uneCommande) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCommande = dateFormat.format(uneCommande.getDateCommande());
        String dateLivraison = uneCommande.getDateLivraisonCommande() != null
                ? dateFormat.format(uneCommande.getDateLivraisonCommande())
                : "null";

        try {
            String requete = "update commande set "
                    + "dateCommande = '" + dateCommande + "', "
                    + "statutCommande = '" + uneCommande.getStatutCommande() + "', "
                    + "dateLivraisonCommande = " + (dateLivraison.equals("null") ? "null" : "'" + dateLivraison + "'") + ", "
                    + "idUser = " + uneCommande.getIdUser() + " "
                    + "where idCommande = " + uneCommande.getIdCommande();
            executerRequete(requete);

            for (LigneCommande uneLigneCommande : uneCommande.getLesLignesCommande()) {
                String reqLigne =   "update ligneCommande set " +
                                    "idLivre = " + uneLigneCommande.getIdLivre() + ", " +
                                    "quantiteLigneCommande = " + uneLigneCommande.getQuantiteLigneCommande() + " " +
                                    "where idLigneCommande = " + uneLigneCommande.getIdLigneCommande();
                executerRequete(reqLigne);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }



    /************************ GESTION DES ABONNEMENTS ************************/
    public static void insertAbonnement(Abonnement unAbonnement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutAbonnement = dateFormat.format(unAbonnement.getDateDebutAbonnement());
        String dateFinAbonnement = dateFormat.format(unAbonnement.getDateFinAbonnement());

        String requete = "insert into abonnement values (null, " +
                unAbonnement.getIdUser() + ", '" +
                dateDebutAbonnement + "', '" +
                dateFinAbonnement + "', " +
                unAbonnement.getPointAbonnement() + ");";
        executerRequete(requete);
    }

    public static ArrayList<Abonnement> selectAbonnement() {
        ArrayList<Abonnement> lesAbonnements = new ArrayList<Abonnement>();
        String requete = "select * from abonnement;";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Abonnement unAbonnement = new Abonnement(
                        lesResultats.getInt("idAbonnement"),
                        lesResultats.getInt("idUser"),
                        lesResultats.getDate("dateDebutAbonnement"),
                        lesResultats.getDate("dateFinAbonnement"),
                        lesResultats.getInt("pointAbonnement")
                );
                lesAbonnements.add(unAbonnement);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesAbonnements;
    }

    public static void deleteAbonnement(int idAbonnement) {
        String requete = "delete from abonnement where idAbonnement = " + idAbonnement + ";";
        executerRequete(requete);
    }

    public static void updateAbonnement(Abonnement unAbonnement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutAbonnement = dateFormat.format(unAbonnement.getDateDebutAbonnement());
        String dateFinAbonnement = dateFormat.format(unAbonnement.getDateFinAbonnement());

        String requete =    "update abonnement set " +
                            "idUser = '" + unAbonnement.getIdUser() + "', " +
                            "dateDebutAbonnement = '" + dateDebutAbonnement + "', " +
                            "dateFinAbonnement = '" + dateFinAbonnement + "', " +
                            "pointAbonnement = '" + unAbonnement.getPointAbonnement() + "' " +
                            "where idAbonnement = " + unAbonnement.getIdAbonnement() + ";";
        executerRequete(requete);
    }

    public static ArrayList<Abonnement> selectLikeAbonnement(String filtre) {
        ArrayList<Abonnement> lesAbonnements = new ArrayList<Abonnement>();
        String requete = "select * from abonnement where "
                + "idUser like '%" + filtre + "%' or "
                + "dateDebutAbonnement like '%" + filtre + "%' or "
                + "dateFinAbonnement like '%" + filtre + "%' or "
                + "pointAbonnement like '%" + filtre + "%';";
        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);
            while (lesResultats.next()) {
                Abonnement unAbonnement = new Abonnement(
                        lesResultats.getInt("idAbonnement"),
                        lesResultats.getInt("idUser"),
                        lesResultats.getDate("dateDebutAbonnement"),
                        lesResultats.getDate("dateFinAbonnement"),
                        lesResultats.getInt("pointAbonnement")
                );
                lesAbonnements.add(unAbonnement);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
        }
        return lesAbonnements;
    }



    /**************** GESTION DES DONNÉES ***************/
    public static String sha1Hash(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
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
        ArrayList<Listing> lesStatsLivres = new ArrayList<>();
        String requete = "select * from vLivresMieuxNotes;";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet lesResultats = unStat.executeQuery(requete);

            while (lesResultats.next()) {
                Listing uneStat = new Listing(
                        lesResultats.getInt("idLivre"),
                        lesResultats.getString("nomLivre"),
                        lesResultats.getDouble("noteMoyenne")
                );
                lesStatsLivres.add(uneStat);
            }

            unStat.close();
            uneConnexion.seDeConnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return lesStatsLivres;
    }

    private static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.security.SecureRandom random = new java.security.SecureRandom();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}