package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controleur.Abonnement;
import controleur.Controleur;

public class PanelAbonnement extends PanelPrincipal implements ActionListener {
    private JButton bt1Mois = new JButton("1 Mois");
    private JButton bt3Mois = new JButton("3 Mois");
    private JButton bt1An = new JButton("1 An");

    private int idUser; // ID de l'utilisateur connecté

    public PanelAbonnement(int idUser) {
        super("Gestion des Abonnements");
        this.idUser = idUser;

        // Configuration du panel
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(Color.cyan);
        panelBoutons.setBounds(50, 50, 400, 200);
        panelBoutons.setLayout(new GridLayout(3, 1, 10, 10)); // 3 lignes, 1 colonne, espacement de 10

        // Ajout des boutons
        panelBoutons.add(bt1Mois);
        panelBoutons.add(bt3Mois);
        panelBoutons.add(bt1An);

        // Rendre les boutons écoutables
        bt1Mois.addActionListener(this);
        bt3Mois.addActionListener(this);
        bt1An.addActionListener(this);

        // Ajout du panel au JFrame
        this.add(panelBoutons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date dateDebut = new Date(System.currentTimeMillis()); // Date de début = aujourd'hui
        Date dateFin = null;
        int points = 0;

        // Déterminer la date de fin et les points en fonction du bouton cliqué
        if (e.getSource() == bt1Mois) {
            dateFin = calculerDateFin(dateDebut, Calendar.MONTH, 1);
            points = 10; // Exemple : 10 points pour 1 mois
        } else if (e.getSource() == bt3Mois) {
            dateFin = calculerDateFin(dateDebut, Calendar.MONTH, 3);
            points = 30; // Exemple : 30 points pour 3 mois
        } else if (e.getSource() == bt1An) {
            dateFin = calculerDateFin(dateDebut, Calendar.YEAR, 1);
            points = 120; // Exemple : 120 points pour 1 an
        }

        // Créer un nouvel abonnement
        Abonnement unAbonnement = new Abonnement(0, idUser, dateDebut, dateFin, points);

        // Insérer l'abonnement dans la base de données
        Controleur.insertAbonnement(unAbonnement);

        // Afficher un message de confirmation
        JOptionPane.showMessageDialog(this, "Abonnement souscrit avec succès !",
                "Abonnement réussi", JOptionPane.INFORMATION_MESSAGE);
    }

    private Date calculerDateFin(Date dateDebut, int champCalendrier, int quantite) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        calendar.add(champCalendrier, quantite); // Ajouter la durée (1 mois, 3 mois, 1 an)
        return new Date(calendar.getTimeInMillis());
    }
}