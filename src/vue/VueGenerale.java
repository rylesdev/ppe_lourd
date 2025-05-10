package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import controleur.Gestion;

public class VueGenerale extends JFrame implements ActionListener {
    private JPanel panelMenu = new JPanel();
    private JPanel panelMenuLigne1 = new JPanel(); // Première ligne du menu
    private JPanel panelMenuLigne2 = new JPanel(); // Deuxième ligne du menu

    private JButton btProfil = new JButton("Profil");
    private JButton btUser = new JButton("Utilisateur");
    private JButton btLivre = new JButton("Livre");
    private JButton btCommande = new JButton("Commande");
    private JButton btAbonnement = new JButton("Abonnement");
    private JButton btCategorie = new JButton("Catégorie");
    private JButton btMaisonEdition = new JButton("Maison d'Édition");
    private JButton btPromotion = new JButton("Promotion");
    private JButton btStats = new JButton("Statistiques");
    private JButton btQuitter = new JButton("Quitter");

    private PanelProfil unPanelProfil;
    private PanelUser unPanelUser = new PanelUser();
    private PanelLivre unPanelLivre = new PanelLivre();
    private PanelCommande unPanelCommande;
    private PanelAbonnement unPanelAbonnement;
    private PanelCategorie unPanelCategorie = new PanelCategorie();
    private PanelMaisonEdition unPanelMaisonEdition = new PanelMaisonEdition();
    private PanelPromotion unPanelPromotion = new PanelPromotion();
    private PanelStats unPanelStats = new PanelStats();

    private int idUser;

    public VueGenerale(int idUser) {
        this.idUser = idUser;

        this.unPanelProfil = new PanelProfil();
        this.unPanelProfil.chargerProfil();

        unPanelCommande = new PanelCommande(this.idUser);
        unPanelAbonnement = new PanelAbonnement(this.idUser);

        this.setTitle("PPE Client Lourd Ryles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.darkGray);
        this.setLayout(null);
        this.setBounds(50, 50, 1000, 600);

        // Configuration du panel principal du menu
        this.panelMenu.setBackground(Color.darkGray);
        this.panelMenu.setBounds(50, 10, 900, 80); // Augmenté la hauteur pour deux rangées
        this.panelMenu.setLayout(new GridLayout(2, 1)); // 2 lignes, 1 colonne

        // Configuration du panel de la première ligne
        this.panelMenuLigne1.setBackground(Color.darkGray);
        this.panelMenuLigne1.setLayout(new GridLayout(1, 5)); // 1 ligne, 5 colonnes
        this.panelMenuLigne1.add(this.btProfil);
        this.panelMenuLigne1.add(this.btUser);
        this.panelMenuLigne1.add(this.btLivre);
        this.panelMenuLigne1.add(this.btCommande);
        this.panelMenuLigne1.add(this.btAbonnement);

        // Configuration du panel de la deuxième ligne
        this.panelMenuLigne2.setBackground(Color.darkGray);
        this.panelMenuLigne2.setLayout(new GridLayout(1, 5)); // 1 ligne, 5 colonnes
        this.panelMenuLigne2.add(this.btCategorie);
        this.panelMenuLigne2.add(this.btMaisonEdition);
        this.panelMenuLigne2.add(this.btPromotion);
        this.panelMenuLigne2.add(this.btStats);
        this.panelMenuLigne2.add(this.btQuitter);

        // Ajout des deux lignes au panel principal du menu
        this.panelMenu.add(this.panelMenuLigne1);
        this.panelMenu.add(this.panelMenuLigne2);

        this.add(this.panelMenu);

        // Ajout des écouteurs d'événements aux boutons
        this.btProfil.addActionListener(this);
        this.btUser.addActionListener(this);
        this.btLivre.addActionListener(this);
        this.btCommande.addActionListener(this);
        this.btAbonnement.addActionListener(this);
        this.btCategorie.addActionListener(this);
        this.btMaisonEdition.addActionListener(this);
        this.btPromotion.addActionListener(this);
        this.btStats.addActionListener(this);
        this.btQuitter.addActionListener(this);

        // Ajout des panels au JFrame
        this.add(unPanelProfil);
        this.add(unPanelUser);
        this.add(unPanelLivre);
        this.add(unPanelCommande);
        this.add(unPanelAbonnement);
        this.add(unPanelCategorie);
        this.add(unPanelMaisonEdition);
        this.add(unPanelPromotion);
        this.add(unPanelStats);

        this.afficherPanel(1);
        this.setVisible(true);
    }

    public PanelProfil getPanelProfil() {
        return this.unPanelProfil;
    }

    private void afficherPanel(int choix) {
        unPanelProfil.setVisible(false);
        unPanelUser.setVisible(false);
        unPanelLivre.setVisible(false);
        unPanelCommande.setVisible(false);
        unPanelAbonnement.setVisible(false);
        unPanelCategorie.setVisible(false);
        unPanelMaisonEdition.setVisible(false);
        unPanelPromotion.setVisible(false);
        unPanelStats.setVisible(false);

        switch (choix) {
            case 1:
                unPanelProfil.setVisible(true);
                unPanelProfil.chargerProfil(); // Rechargement à chaque affichage
                break;
            case 2:
                unPanelUser.setVisible(true);
                break;
            case 3:
                unPanelLivre.setVisible(true);
                break;
            case 4:
                unPanelCommande.setVisible(true);
                break;
            case 5:
                unPanelAbonnement.setVisible(true);
                break;
            case 6:
                unPanelCategorie.setVisible(true);
                break;
            case 7:
                unPanelMaisonEdition.setVisible(true);
                break;
            case 8:
                unPanelPromotion.setVisible(true);
                break;
            case 9:
                unPanelStats.setVisible(true);
                PanelStats.actualiser();
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String choix = e.getActionCommand();
        switch (choix) {
            case "Quitter":
                Gestion.rendreVisibleVueConnexion(true);
                Gestion.creerVueGenerale(false);
                break;
            case "Profil":
                this.afficherPanel(1);
                break;
            case "Utilisateur":
                this.afficherPanel(2);
                break;
            case "Livre":
                this.afficherPanel(3);
                break;
            case "Commande":
                this.afficherPanel(4);
                break;
            case "Abonnement":
                this.afficherPanel(5);
                break;
            case "Catégorie":
                this.afficherPanel(6);
                break;
            case "Maison d'Édition":
                this.afficherPanel(7);
                break;
            case "Promotion":
                this.afficherPanel(8);
                break;
            case "Statistiques":
                this.afficherPanel(9);
                break;
        }
    }
}