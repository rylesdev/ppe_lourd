package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import controleur.Controleur;
import controleur.Listing;
import controleur.Tableau;

public class PanelStats extends PanelPrincipal {
    private static JPanel panelCount = new JPanel();
    private static JLabel lbParticulier = new JLabel("Nombre de Clients : " + Controleur.count("particulier"));
    private static JLabel lbLivre = new JLabel("Nombre de Livres : " + Controleur.count("livre"));
    private static JLabel lbCommande = new JLabel("Nombre de Commandes : " + Controleur.count("commande"));
    private static JLabel lbAbonnement = new JLabel("Nombre de Abonnements : " + Controleur.count("abonnement"));

    private JTable tableStats;
    private Tableau tableauStats;

    public PanelStats() {
        super("Gestion des Statistiques");

        Color customColor = new Color(100, 140, 180);
        this.setBackground(customColor);

        // Panel pour les statistiques
        panelCount.setBounds(50, 20, 800, 100);
        panelCount.setLayout(new GridLayout(1, 4));
        panelCount.setBackground(customColor);

        panelCount.add(lbParticulier);
        panelCount.add(lbLivre);
        panelCount.add(lbCommande);
        panelCount.add(lbAbonnement);

        this.add(panelCount);

        // Créer un graphique à barres
        CategoryChart chart = new CategoryChartBuilder()
                .width(400)
                .height(300)
                .title("Statistiques")
                .xAxisTitle("Catégorie")
                .yAxisTitle("Nombre")
                .build();

        // Personnaliser le graphique
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);

        // Préparer les données
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Livre");
        categories.add("Commande");
        categories.add("Abonnement");
        categories.add("Particulier");

        ArrayList<Integer> values = new ArrayList<>();
        values.add(Controleur.count("livre"));
        values.add(Controleur.count("commande"));
        values.add(Controleur.count("abonnement"));
        values.add(Controleur.count("particulier"));

        // Ajouter les données au graphique
        chart.addSeries("Statistiques", categories, values);

        // Afficher le graphique (45% de largeur)
        JPanel chartPanel = new XChartPanel<>(chart);
        chartPanel.setBounds(50, 130, 450, 300);
        this.add(chartPanel);

        // Tableau des statistiques (30% de largeur)
        String entetes[] = {"Id Livre", "Nom", "Note Moyenne"};
        this.tableauStats = new Tableau(this.obtenirDonnees(), entetes);
        this.tableStats = new JTable(this.tableauStats);

        this.tableStats.setBackground(Color.WHITE);
        this.tableStats.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                return c;
            }
        });

        JScrollPane uneScroll = new JScrollPane(this.tableStats);
        uneScroll.setBounds(520, 130, 300, 300);
        uneScroll.getViewport().setBackground(customColor);
        this.add(uneScroll);
    }

    public static void actualiser() {
        lbParticulier.setText("Nombre de clients : " + Controleur.count("particulier"));
        lbLivre.setText("Nombre de Livres : " + Controleur.count("livre"));
        lbCommande.setText("Nombre de Commandes : " + Controleur.count("commande"));
        lbAbonnement.setText("Nombre de Abonnements : " + Controleur.count("abonnement"));
    }

    public Object[][] obtenirDonnees() {
        ArrayList<Listing> lesListings = Controleur.selectListing();

        Object matrice[][] = new Object[lesListings.size()][3];
        int i = 0;
        for (Listing unListing : lesListings) {
            matrice[i][0] = unListing.getIdLivre();
            matrice[i][1] = unListing.getNomLivre();
            matrice[i][2] = unListing.getNoteMoyenne();
            i++;
        }
        return matrice;
    }
}