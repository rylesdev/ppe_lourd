package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controleur.Particulier;
import controleur.Controleur;
import controleur.Listing;
import controleur.Tableau;

public class PanelStats extends PanelPrincipal
{
    private static JPanel panelCount = new JPanel();
    private static JLabel lbParticulier = new JLabel("Nombre de Clients : " + Controleur.count("particulier"));
    private static JLabel lbLivre = new JLabel("Nombre de Livres : " + Controleur.count("livre"));
    private static JLabel lbCommande = new JLabel("Nombre de Commandes : " + Controleur.count("commande"));
    private static JLabel lbAbonnement = new JLabel("Nombre de Abonnements : " + Controleur.count("abonnement"));

    private JTable tableStats ;

    private Tableau tableauStats ;

    public PanelStats() {

        super ("Gestion des  Statistiques ");

        panelCount.setBackground(Color.cyan);
        panelCount.setBounds(50, 100, 400, 250);
        panelCount.setLayout(new GridLayout(2,2));

        panelCount.add(lbParticulier);
        panelCount.add(lbLivre);
        panelCount.add(lbCommande);
        panelCount.add(lbAbonnement);

        this.add(panelCount);
        String entetes[] = {"Nom C", "Prénom C", "Designation", "Descritpion","Date Inter"};
        this.tableauStats = new Tableau (this.obtenirDonnees(), entetes);
        this.tableStats = new JTable(this.tableauStats);
        JScrollPane uneScroll = new JScrollPane(this.tableStats);
        uneScroll.setBounds(480, 100, 400, 250);
        this.add(uneScroll);


    }
    public static void actualiser () {
        lbParticulier.setText("Nombre de clients : " + Controleur.count("particulier"));
        lbLivre.setText("Nombre de Livres : " + Controleur.count("livre"));
        lbCommande.setText("Nombre de Commandes : " + Controleur.count("commande"));
        lbAbonnement.setText("Nombre de Abonnements : " + Controleur.count("abonnement"));
    }
    public Object [][] obtenirDonnees()
    {
        ArrayList <Listing>  lesListings = Controleur.selectListing();

        Object matrice[][] = new Object[lesListings.size()][5];
        int i = 0;
        for (Listing unListing : lesListings) {
            matrice[i][0] = unListing.getIdUser();
            matrice[i][1] = unListing.getEmailUser();
            matrice[i][2] = unListing.getMdpUser();
            matrice[i][3] = unListing.getAdresseUser();
            matrice[i][4] = unListing.getRoleUser();
            i++;
        }
        return matrice ;
    }
}
