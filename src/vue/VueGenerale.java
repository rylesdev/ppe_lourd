package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import controleur.Gestion;

public class VueGenerale extends JFrame implements ActionListener {
    private JPanel panelMenu = new JPanel();

    private JButton btProfil = new JButton("Profil");
    private JButton btUser = new JButton("Utilisateur");
    private JButton btLivre = new JButton("Livre");
    private JButton btCommande = new JButton("Commande");
    private JButton btAbonnement = new JButton("Abonnement");
    private JButton btStats = new JButton("Stats");
    private JButton btQuitter = new JButton("Quitter");

    private PanelProfil unPanelProfil;
    private PanelUser unPanelUser = new PanelUser();
    private PanelLivre unPanelLivre = new PanelLivre();
    private PanelCommande unPanelCommande;
    private PanelAbonnement unPanelAbonnement;
    private PanelStats unPanelStats = new PanelStats();

    private int idUser;

    public VueGenerale(int idUser) {
        this.idUser = idUser;

        // Initialisation du PanelProfil AVANT les autres composants
        this.unPanelProfil = new PanelProfil();
        this.unPanelProfil.chargerProfil(); // Chargement immédiat du profil

        unPanelCommande = new PanelCommande(this.idUser);
        unPanelAbonnement = new PanelAbonnement(this.idUser);

        this.setTitle("PPE Client Lourd Ryles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.darkGray);
        this.setLayout(null);
        this.setBounds(50, 50, 1000, 600);

        // Configuration du menu...
        this.panelMenu.setBackground(Color.darkGray);
        this.panelMenu.setBounds(50, 10, 900, 40);
        this.panelMenu.setLayout(new GridLayout(1, 7));
        this.panelMenu.add(this.btProfil);
        this.panelMenu.add(this.btUser);
        this.panelMenu.add(this.btLivre);
        this.panelMenu.add(this.btCommande);
        this.panelMenu.add(this.btAbonnement);
        this.panelMenu.add(this.btStats);
        this.panelMenu.add(this.btQuitter);
        this.add(this.panelMenu);

        // Ajout des listeners...
        this.btProfil.addActionListener(this);
        this.btUser.addActionListener(this);
        this.btLivre.addActionListener(this);
        this.btCommande.addActionListener(this);
        this.btAbonnement.addActionListener(this);
        this.btStats.addActionListener(this);
        this.btQuitter.addActionListener(this);

        // Ajout des panels...
        this.add(unPanelProfil);
        this.add(unPanelUser);
        this.add(unPanelLivre);
        this.add(unPanelCommande);
        this.add(unPanelAbonnement);
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
            case "Stats":
                this.afficherPanel(6);
                break;
        }
    }
}