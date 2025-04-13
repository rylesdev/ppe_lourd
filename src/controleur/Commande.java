package controleur;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int idCommande;
    private Date dateCommande;
    private String statutCommande;
    private Date dateLivraisonCommande;
    private int idUser;
    private List<LigneCommande> lesLignesCommande;

    // Constructeur complet (pour chargement depuis BDD)
    public Commande(int idCommande, Date dateCommande, String statutCommande,
                    Date dateLivraisonCommande, int idUser) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statutCommande = statutCommande;
        this.dateLivraisonCommande = dateLivraisonCommande;
        this.idUser = idUser;
        this.lesLignesCommande = new ArrayList<>();
    }

    // Constructeur simplifié (pour création nouvelle commande)
    public Commande(Date dateCommande, String statutCommande, int idUser) {
        this(0, dateCommande, statutCommande, null, idUser);
    }

    // Méthodes de gestion des lignes
    public void ajouterLigneCommande(LigneCommande ligneCommande) {
        ligneCommande.setIdCommande(this.idCommande);
        this.lesLignesCommande.add(ligneCommande);
    }

    // Getters
    public int getIdCommande() { return idCommande; }
    public Date getDateCommande() { return dateCommande; }
    public String getStatutCommande() { return statutCommande; }
    public Date getDateLivraisonCommande() { return dateLivraisonCommande; }
    public int getIdUser() { return idUser; }
    public List<LigneCommande> getLesLignesCommande() { return lesLignesCommande; }

    // Setters
    public void setIdCommande(int idCommande) { this.idCommande = idCommande; }
    public void setDateLivraisonCommande(Date dateLivraisonCommande) {
        this.dateLivraisonCommande = dateLivraisonCommande;
    }
    public void setStatutCommande(String statutCommande) {
        this.statutCommande = statutCommande;
    }
}