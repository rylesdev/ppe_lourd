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
    private static Connexion uneConnexion = new Connexion("172.20.111.103", "ppe_lourd", "user", "user");

    //private static Connexion uneConnexion = new Connexion("localhost:8889", "ppe_lourd", "root", "root");


    /************************ GESTION DES ADMIN ************************/
    public static String selectNiveauAdminByIdUser(int idUser) {
        String niveauAdmin = null;
        String requete = "SELECT niveauAdmin FROM admin WHERE idUser = " + idUser + ";";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);
            if (rs.next()) {
                niveauAdmin = rs.getString("niveauAdmin");
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return niveauAdmin;
    }

    public static void updateAdmin(User unUser, String niveauAdmin) {
        String requeteUser = "UPDATE user SET " +
                "emailUser = '" + unUser.getEmailUser() + "', " +
                "mdpUser = '" + unUser.getMdpUser() + "', " +
                "adresseUser = '" + unUser.getAdresseUser() + "', " +
                "roleUser = '" + unUser.getRoleUser() + "' " +
                "WHERE idUser = " + unUser.getIdUser() + ";";

        String requeteAdmin = "UPDATE admin SET " +
                "niveauAdmin = '" + niveauAdmin + "' " +
                "WHERE idUser = " + unUser.getIdUser() + ";";

        Connection conn = null;
        try {
            uneConnexion.seConnecter();
            conn = uneConnexion.getMaConnexion();
            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(requeteUser);
                stmt.executeUpdate(requeteAdmin);
                conn.commit();
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                uneConnexion.seDeConnecter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    /************************ GESTION DES USERS ************************/
    public static void insertUser(User unUser) {
        String mdpHashe = sha1Hash(unUser.getMdpUser());
        String requete = "insert into user values (null," + "'" +
                unUser.getEmailUser() + "', '" +
                mdpHashe + "', '" +
                unUser.getAdresseUser() + "', '" +
                unUser.getRoleUser() + "');";
        executerRequete(requete);
    }

    public static boolean emailExiste(String email) {
        String requete = "SELECT COUNT(*) AS count FROM user WHERE emailUser = '" + email + "';";
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
        String requete =    "select u.*, a.*  from user u " +
                "inner join admin a on u.idUser=a.idUser " +
                "where u.emailUser = '" + email + "' and u.mdpUser = '" + mdp + "' and a.niveauAdmin = '" + role + "';";
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
            return "OK:" + mdpAleatoire;
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
            return "OK:" + mdpAleatoire;
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
            exp.printStackTrace();
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

    public static Livre selectLivreByNom(String nomLivre) {
        String requete = "SELECT * FROM livre WHERE nomLivre = '" + nomLivre + "';";
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
            exp.printStackTrace();
        }
        return unLivre;
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
        Integer idPromotion = null;
        if (unLivre.getIdPromotion() == -1) {
            idPromotion = null;
        } else {
            idPromotion = unLivre.getIdPromotion();
        }
        String requete =    "update livre set " +
                "nomLivre = '" + unLivre.getNomLivre() + "', " +
                "auteurLivre = '" + unLivre.getAuteurLivre() + "', " +
                "imageLivre = '" + unLivre.getImageLivre() + "', " +
                "exemplaireLivre = " + unLivre.getExemplaireLivre() + ", " +
                "prixLivre = " + unLivre.getPrixLivre() + ", " +
                "idCategorie = " + unLivre.getIdCategorie() + ", " +
                "idMaisonEdition = " + unLivre.getIdMaisonEdition() + ", " +
                "idPromotion = " + idPromotion + " " +
                "where idLivre = " + unLivre.getIdLivre() + ";";
        executerRequete(requete);
    }

    public static void updateCategorieLivre(Livre unLivre) {
        String requete = "UPDATE livre SET idCategorie = " + unLivre.getIdCategorie() + " WHERE idLivre = " + unLivre.getIdLivre() + ";";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            unStat.executeUpdate(requete);

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
    }

    public static void updateMaisonEditionLivre(Livre unLivre) {
        String requete = "UPDATE livre SET idMaisonEdition = " + unLivre.getIdMaisonEdition() + " WHERE idLivre = " + unLivre.getIdLivre() + ";";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            unStat.executeUpdate(requete);

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
    }

    public static boolean updatePromotionLivre(Livre unLivre) {
        String requete;
        if (unLivre.getIdPromotion() != null) {
            requete = String.format(
                    "UPDATE livre SET idPromotion = %d WHERE idLivre = %d;",
                    unLivre.getIdPromotion(), unLivre.getIdLivre()
            );
        } else {
            requete = String.format(
                    "UPDATE livre SET idPromotion = NULL WHERE idLivre = %d;",
                    unLivre.getIdLivre()
            );
        }

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            int nbLignes = stmt.executeUpdate(requete);
            stmt.close();
            uneConnexion.seDeConnecter();
            return nbLignes > 0;
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la mise à jour de la promotion du livre : " + exp.getMessage());
            return false;
        }
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



    /************************ GESTION DES CATEGORIES ************************/
    public static ArrayList<Categorie> selectCategorie() {
        String requete = "SELECT * from categorie;";
        ArrayList<Categorie> lesCategories = new ArrayList<>();

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);

            while (unResultat.next()) {
                Categorie uneCategorie = new Categorie(
                        unResultat.getInt("idCategorie"),
                        unResultat.getString("nomCategorie")
                );
                lesCategories.add(uneCategorie);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return lesCategories;
    }


    public static ArrayList<Categorie> selectLikeCategorie(String filtre) {
        String requete = "SELECT * FROM categorie WHERE nomCategorie LIKE '%" + filtre + "%';";
        ArrayList<Categorie> lesCategories = new ArrayList<>();

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);

            while (unResultat.next()) {
                Categorie uneCategorie = new Categorie(
                        unResultat.getInt("idCategorie"),
                        unResultat.getString("nomCategorie")
                );
                lesCategories.add(uneCategorie);
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return lesCategories;
    }


    public static Categorie selectCategorieByNom(String nomCategorie) {
        String requete = "SELECT * FROM categorie WHERE nomCategorie = '" + nomCategorie + "';";
        Categorie uneCategorie = null;

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);

            if (unResultat.next()) {
                uneCategorie = new Categorie(
                        unResultat.getInt("idCategorie"),
                        unResultat.getString("nomCategorie")
                );
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return uneCategorie;
    }

    public static Categorie selectCategorieById(int idCategorie) {
        String requete = "SELECT * FROM categorie WHERE idCategorie = " + idCategorie + ";";
        Categorie uneCategorie = null;

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet unResultat = unStat.executeQuery(requete);

            if (unResultat.next()) {
                uneCategorie = new Categorie(
                        unResultat.getInt("idCategorie"),
                        unResultat.getString("nomCategorie")
                );
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution de la requête : " + requete);
            exp.printStackTrace();
        }
        return uneCategorie;
    }

    public static void insertCategorie(Categorie uneCategorie) {
        String requete =    "insert into categorie values (null,'" +
                uneCategorie.getNomCategorie() + "');";
        executerRequete(requete);
    }

    public static void updateCategorie(Categorie uneCategorie) {
        String requete =    "update categorie set " +
                "nomCategorie = '" + uneCategorie.getNomCategorie() + "' " +
                "where idCategorie = " + uneCategorie.getIdCategorie() + ";";
        executerRequete(requete);
    }

    public static void deleteCategorie(int idCategorie) {
        String requete = "delete from categorie where idCategorie = " + idCategorie + ";";
        executerRequete(requete);
    }



    /************************ GESTION DES MAISON D'EDITION ******************/
    public static ArrayList<MaisonEdition> selectMaisonEdition() {
        String requete =    "SELECT * from maisonEdition;";
        ArrayList<MaisonEdition> lesMaisonEditions = new ArrayList<>();

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);

            while (rs.next()) {
                MaisonEdition uneMaisonEdition = new MaisonEdition(
                        rs.getInt("idMaisonEdition"),
                        rs.getString("nomMaisonEdition")
                );
                lesMaisonEditions.add(uneMaisonEdition);
            }
            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la récupération des maisons d'édition : " + e.getMessage());
            e.printStackTrace();
        }
        return lesMaisonEditions;
    }

    public static ArrayList<MaisonEdition> selectLikeMaisonEdition(String filtre) {
        String requete = "SELECT * FROM maisonEdition WHERE nomMaisonEdition LIKE '%" + filtre + "%';";
        ArrayList<MaisonEdition> lesMaisonEditions = new ArrayList<>();

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);

            while (rs.next()) {
                MaisonEdition uneMaisonEdition = new MaisonEdition(
                        rs.getInt("idMaisonEdition"),
                        rs.getString("nomMaisonEdition")
                );
                lesMaisonEditions.add(uneMaisonEdition);
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la récupération des maisons d'édition par filtre : " + e.getMessage());
            e.printStackTrace();
        }
        return lesMaisonEditions;
    }

    public static void insertMaisonEdition(MaisonEdition maisonEdition) {
        String requete = "INSERT INTO maisonEdition (nomMaisonEdition) VALUES ('" + maisonEdition.getNomMaisonEdition() + "');";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            unStat.executeUpdate(requete);

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de l'insertion de la maison d'édition : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateMaisonEdition(MaisonEdition maisonEdition) {
        String requete = "UPDATE maisonEdition SET nomMaisonEdition = '" + maisonEdition.getNomMaisonEdition() + "' WHERE idMaisonEdition = " + maisonEdition.getIdMaisonEdition() + ";";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            unStat.executeUpdate(requete);

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la mise à jour de la maison d'édition : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteMaisonEdition(int idMaisonEdition) {
        String requeteMAJ = "UPDATE livre SET idMaisonEdition = NULL WHERE idMaisonEdition = " + idMaisonEdition + ";";
        String requete = "DELETE FROM maisonEdition WHERE idMaisonEdition = " + idMaisonEdition + ";";

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();

            unStat.executeUpdate(requeteMAJ);
            unStat.executeUpdate(requete);

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la suppression de la maison d'édition : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static MaisonEdition selectMaisonEditionById(int idMaisonEdition) {
        String requete = "SELECT * FROM maisonEdition WHERE idMaisonEdition = " + idMaisonEdition + ";";
        MaisonEdition maisonEdition = null;

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);

            if (rs.next()) {
                maisonEdition = new MaisonEdition(
                        rs.getInt("idMaisonEdition"),
                        rs.getString("nomMaisonEdition")
                );
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la récupération de la maison d'édition par ID : " + e.getMessage());
            e.printStackTrace();
        }

        return maisonEdition;
    }

    public static MaisonEdition selectMaisonEditionByNom(String nomMaisonEdition) {
        String requete = "SELECT * FROM maisonEdition WHERE nomMaisonEdition = '" + nomMaisonEdition + "';";
        MaisonEdition maisonEdition = null;

        try {
            uneConnexion.seConnecter();
            Statement unStat = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);

            if (rs.next()) {
                maisonEdition = new MaisonEdition(
                        rs.getInt("idMaisonEdition"),
                        rs.getString("nomMaisonEdition")
                );
            }

            unStat.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la récupération de la maison d'édition par nom : " + e.getMessage());
            e.printStackTrace();
        }

        return maisonEdition;
    }



    /************************ GESTION DES PROMOTIONS ***********************/
    public static boolean insertPromotion(Promotion unePromotion) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebut = sdf.format(unePromotion.getDateDebutPromotion());
        String dateFin = sdf.format(unePromotion.getDateFinPromotion());

        String requete = String.format(
                "INSERT INTO promotion (nomPromotion, dateDebutPromotion, dateFinPromotion, reductionPromotion) " +
                        "VALUES ('%s', '%s', '%s', %d)",
                unePromotion.getNomPromotion(), dateDebut, dateFin, unePromotion.getReductionPromotion()
        );

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            int nbLignes = stmt.executeUpdate(requete);
            stmt.close();
            uneConnexion.seDeConnecter();
            return nbLignes > 0;
        } catch (SQLException exp) {
            System.out.println("Erreur d'insertion promotion : " + exp.getMessage());
            return false;
        }
    }

    public static boolean updatePromotion(Promotion unePromotion) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebut = sdf.format(unePromotion.getDateDebutPromotion());
        String dateFin = sdf.format(unePromotion.getDateFinPromotion());

        String requete = String.format(
                "UPDATE promotion SET nomPromotion = '%s', dateDebutPromotion = '%s', " +
                        "dateFinPromotion = '%s', reductionPromotion = %d WHERE idPromotion = %d;",
                unePromotion.getNomPromotion(), dateDebut, dateFin,
                unePromotion.getReductionPromotion(), unePromotion.getIdPromotion()
        );

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            int nbLignes = stmt.executeUpdate(requete);
            stmt.close();
            uneConnexion.seDeConnecter();
            return nbLignes > 0;
        } catch (SQLException exp) {
            System.out.println("Erreur de mise à jour promotion : " + exp.getMessage());
            return false;
        }
    }

    public static boolean deletePromotion(int idPromotion) {
        String requeteNullRef = "UPDATE livre SET idPromotion = NULL WHERE idPromotion = " + idPromotion + ";";

        String requete = "DELETE FROM promotion WHERE idPromotion = " + idPromotion + ";";

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            stmt.executeUpdate(requeteNullRef);
            int nbLignes = stmt.executeUpdate(requete);
            stmt.close();
            uneConnexion.seDeConnecter();
            return nbLignes > 0;
        } catch (SQLException exp) {
            System.out.println("Erreur de suppression promotion : " + exp.getMessage());
            return false;
        }
    }

    public static Promotion selectPromotionByNom(String nomPromotion) {
        String requete = "SELECT * FROM promotion WHERE nomPromotion = '" + nomPromotion + "';";
        Promotion unePromotion = null;

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            if (rs.next()) {
                unePromotion = new Promotion(
                        rs.getInt("idPromotion"),
                        rs.getString("nomPromotion"),
                        rs.getDate("dateDebutPromotion"),
                        rs.getDate("dateFinPromotion"),
                        rs.getInt("reductionPromotion")
                );
            }

            stmt.close();
            rs.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la recherche de promotion par nom : " + exp.getMessage());
        }

        return unePromotion;
    }

    public static Promotion selectPromotionById(int idPromotion) {
        String requete = "SELECT * FROM promotion WHERE idPromotion = " + idPromotion + ";";
        Promotion unePromotion = null;

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            if (rs.next()) {
                unePromotion = new Promotion(
                        rs.getInt("idPromotion"),
                        rs.getString("nomPromotion"),
                        rs.getDate("dateDebutPromotion"),
                        rs.getDate("dateFinPromotion"),
                        rs.getInt("reductionPromotion")
                );
            }

            stmt.close();
            rs.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la recherche de promotion par id : " + exp.getMessage());
        }

        return unePromotion;
    }

    public static ArrayList<Promotion> selectPromotion() {
        String requete = "SELECT * FROM promotion;";
        ArrayList<Promotion> lesPromotions = new ArrayList<>();

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                Promotion unePromotion = new Promotion(
                        rs.getInt("idPromotion"),
                        rs.getString("nomPromotion"),
                        rs.getDate("dateDebutPromotion"),
                        rs.getDate("dateFinPromotion"),
                        rs.getInt("reductionPromotion")
                );
                lesPromotions.add(unePromotion);
            }

            stmt.close();
            rs.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la récupération des promotions : " + exp.getMessage());
        }
        return lesPromotions;
    }

    public static ArrayList<Promotion> selectLikePromotion(String filtre) {
        String requete = "SELECT * FROM promotion WHERE nomPromotion LIKE '%" + filtre + "%' ORDER BY nomPromotion;";
        ArrayList<Promotion> lesPromotions = new ArrayList<>();

        try {
            uneConnexion.seConnecter();
            Statement stmt = uneConnexion.getMaConnexion().createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                Promotion unePromotion = new Promotion(
                        rs.getInt("idPromotion"),
                        rs.getString("nomPromotion"),
                        rs.getDate("dateDebutPromotion"),
                        rs.getDate("dateFinPromotion"),
                        rs.getInt("reductionPromotion")
                );
                lesPromotions.add(unePromotion);
            }

            stmt.close();
            rs.close();
            uneConnexion.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la recherche de promotions par filtre : " + exp.getMessage());
        }

        return lesPromotions;
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
                String reqLignes = "select * from ligneCommande where idCommande = " + uneCommande.getIdCommande() + ";";
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
                String reqLignes = "select * from ligneCommande where idCommande = " + uneCommande.getIdCommande() + ";";
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

    public static int insertCommande(Commande uneCommande) {
        int result = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCommande = dateFormat.format(uneCommande.getDateCommande());
        String dateLivraison = dateFormat.format(uneCommande.getDateLivraisonCommande());

        try {
            uneConnexion.seConnecter();
            Connection conn = uneConnexion.getMaConnexion();
            conn.setAutoCommit(false);

            String requete = "insert into commande values (null, '"
                    + dateCommande + "', '"
                    + "en attente', '"
                    + dateLivraison + "', '"
                    + uneCommande.getIdUser() + "');";

            Statement st = conn.createStatement();
            st.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = st.getGeneratedKeys();
            int idCommande = rs.next() ? rs.getInt(1) : 0;

            for (LigneCommande uneLigneCommande : uneCommande.getLesLignesCommande()) {
                String reqLigne = "insert into ligneCommande values (null, '"
                        + idCommande + "', '"
                        + uneLigneCommande.getIdLivre() + "', '"
                        + uneLigneCommande.getQuantiteLigneCommande() + "');";
                st.executeUpdate(reqLigne);
            }

            System.out.println(uneCommande.getStatutCommande());
            if (uneCommande.getStatutCommande().equals("expédiée")) {
                String requeteTriggerCommande = "update commande " +
                        "set statutCommande = 'expédiée' where idCommande = " + idCommande + ";";

                st.executeUpdate(requeteTriggerCommande);
            }

            conn.commit();

            st.close();
            uneConnexion.seDeConnecter();

            result = 1;
        } catch (SQLException exp) {
            try {
                if (uneConnexion.getMaConnexion() != null) {
                    uneConnexion.getMaConnexion().rollback();
                    System.out.println("ROLLBACK effectué. Erreur: " + exp.getMessage());
                    result = 2;
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors du rollback : " + e.getMessage());
                result = 3;
            }
        } finally {
            try {
                if (uneConnexion.getMaConnexion() != null && !uneConnexion.getMaConnexion().isClosed()) {
                    uneConnexion.getMaConnexion().setAutoCommit(true);
                    uneConnexion.seDeConnecter();
                }
            } catch (SQLException e) {
                System.out.println("Erreur fermeture : " + e.getMessage());
            }
        }
        return result;
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
                    + "where idCommande = " + uneCommande.getIdCommande() + ";";
            executerRequete(requete);

            for (LigneCommande uneLigneCommande : uneCommande.getLesLignesCommande()) {
                String reqLigne =   "update ligneCommande set " +
                        "idLivre = " + uneLigneCommande.getIdLivre() + ", " +
                        "quantiteLigneCommande = " + uneLigneCommande.getQuantiteLigneCommande() + " " +
                        "where idLigneCommande = " + uneLigneCommande.getIdLigneCommande() + ";";
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