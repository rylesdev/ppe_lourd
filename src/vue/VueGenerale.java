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
    private JButton btParticulier = new JButton("Particulier");
    private JButton btLivre = new JButton("Livre");
    private JButton btCommande = new JButton("Commande");
    private JButton btAbonnement = new JButton("Abonnement");
    private JButton btStats = new JButton("Stats");
    private JButton btQuitter = new JButton("Quitter");

    private static PanelProfil unPanelProfil = new PanelProfil();
    private static PanelParticulier unPanelParticulier = new PanelParticulier();
    private static PanelLivre unPanelLivre = new PanelLivre();
    private static PanelCommande unPanelCommande;
    private static PanelAbonnement unPanelAbonnement;
    private static PanelStats unPanelStats = new PanelStats();

    private int idUser;

    public VueGenerale(int idUser) {
        this.idUser = idUser;

        unPanelCommande = new PanelCommande(this.idUser);
        unPanelAbonnement = new PanelAbonnement(this.idUser);

        this.setTitle("PPE Client Lourd Ryles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.darkGray);
        this.setLayout(null);
        this.setBounds(50, 50, 1000, 600);

        this.panelMenu.setBackground(Color.darkGray);
        this.panelMenu.setBounds(50, 10, 900, 40);
        this.panelMenu.setLayout(new GridLayout(1, 7));
        this.panelMenu.add(this.btProfil);
        this.panelMenu.add(this.btParticulier);
        this.panelMenu.add(this.btLivre);
        this.panelMenu.add(this.btCommande);
        this.panelMenu.add(this.btAbonnement);
        this.panelMenu.add(this.btStats);
        this.panelMenu.add(this.btQuitter);
        this.add(this.panelMenu);

        this.btProfil.addActionListener(this);
        this.btParticulier.addActionListener(this);
        this.btLivre.addActionListener(this);
        this.btCommande.addActionListener(this);
        this.btAbonnement.addActionListener(this);
        this.btStats.addActionListener(this);
        this.btQuitter.addActionListener(this);

        this.add(unPanelProfil);
        this.add(unPanelParticulier);
        this.add(unPanelLivre);
        this.add(unPanelCommande);
        this.add(unPanelAbonnement);
        this.add(unPanelStats);

        this.afficherPanel(1);

        this.setVisible(true);
    }

    private void afficherPanel(int choix) {
        unPanelProfil.setVisible(false);
        unPanelParticulier.setVisible(false);
        unPanelLivre.setVisible(false);
        unPanelCommande.setVisible(false);
        unPanelAbonnement.setVisible(false);
        unPanelStats.setVisible(false);
        switch (choix) {
            case 1:
                unPanelProfil.setVisible(true);
                break;
            case 2:
                unPanelParticulier.setVisible(true);
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
            case "Particulier":
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