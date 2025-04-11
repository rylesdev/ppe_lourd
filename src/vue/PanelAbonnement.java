package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import controleur.Abonnement;
import controleur.Controleur;
import controleur.Tableau;

public class PanelAbonnement extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();

    private JTextField txtIdAbonnement = new JTextField();
    private JTextField txtIdUser = new JTextField();
    private JTextField txtDateDebutAbonnement = new JTextField();
    private JTextField txtDateFinAbonnement = new JTextField();
    private JTextField txtPointAbonnement = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");

    private JTable tableAbonnements;
    private Tableau tableauAbonnements;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");

    private JLabel lbNbAbonnements = new JLabel();

    public PanelAbonnement(int idUser) {
        super("Gestion des Abonnements");

        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 100, 300, 250);
        this.panelForm.setLayout(new GridLayout(10, 2));
        this.panelForm.add(new JLabel("Id Abonnement :"));
        this.panelForm.add(this.txtIdAbonnement);
        this.panelForm.add(new JLabel("Id User :"));
        this.panelForm.add(this.txtIdUser);
        this.panelForm.add(new JLabel("Date Début :"));
        this.panelForm.add(this.txtDateDebutAbonnement);
        this.panelForm.add(new JLabel("Date Fin :"));
        this.panelForm.add(this.txtDateFinAbonnement);
        this.panelForm.add(new JLabel("Points :"));
        this.panelForm.add(this.txtPointAbonnement);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btSupprimer);

        this.add(this.panelForm);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);

        this.txtIdAbonnement.addKeyListener(this);
        this.txtIdUser.addKeyListener(this);
        this.txtDateDebutAbonnement.addKeyListener(this);
        this.txtDateFinAbonnement.addKeyListener(this);
        this.txtPointAbonnement.addKeyListener(this);

        String entetes[] = {"Id", "Id User", "Date Début", "Date Fin", "Points"};
        this.tableauAbonnements = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableAbonnements = new JTable(this.tableauAbonnements);
        JScrollPane uneScroll = new JScrollPane(this.tableAbonnements);
        uneScroll.setBounds(360, 100, 480, 250);
        this.add(uneScroll);

        this.panelFiltre.setBackground(Color.cyan);
        this.panelFiltre.setBounds(370, 60, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        this.lbNbAbonnements.setBounds(450, 380, 400, 20);
        this.add(this.lbNbAbonnements);
        this.lbNbAbonnements.setText("Nombre d'abonnements : " + this.tableauAbonnements.getRowCount());

        // Affiche les données du formulaire à gauche
        this.tableAbonnements.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableAbonnements.getSelectedRow();
                if (numLigne >= 0) {
                    txtIdAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 0).toString());
                    txtIdUser.setText(tableauAbonnements.getValueAt(numLigne, 1).toString());
                    txtDateDebutAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 2).toString());
                    txtDateFinAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 3).toString());
                    txtPointAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 4).toString());

                    btSupprimer.setVisible(true);
                    btValider.setText("Modifier");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    // Affiche les données du tableau à droite
    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Abonnement> lesAbonnements;
        if (filtre.equals("")) {
            lesAbonnements = Controleur.selectAbonnement();
        } else {
            lesAbonnements = Controleur.selectLikeAbonnement(filtre);
        }
        Object matrice[][] = new Object[lesAbonnements.size()][5];
        int i = 0;
        for (Abonnement unAbonnement : lesAbonnements) {
            matrice[i][0] = unAbonnement.getIdAbonnement();
            matrice[i][1] = unAbonnement.getIdUser();
            matrice[i][2] = unAbonnement.getDateDebutAbonnement();
            matrice[i][3] = unAbonnement.getDateFinAbonnement();
            matrice[i][4] = unAbonnement.getPointAbonnement();
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        this.txtIdAbonnement.setText("");
        this.txtIdUser.setText("");
        this.txtDateDebutAbonnement.setText("");
        this.txtDateFinAbonnement.setText("");
        this.txtPointAbonnement.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauAbonnements.setDonnees(this.obtenirDonnees(filtre));
        } else if (e.getSource() == this.btValider && this.btValider.getText().equals("Valider")) {
            this.traitement();
            this.lbNbAbonnements.setText("Nombre d'abonnements : " + this.tableauAbonnements.getRowCount());
        } else if (e.getSource() == this.btValider && this.btValider.getText().equals("Modifier")) {
            this.modifierAbonnement();
        } else if (e.getSource() == this.btSupprimer) {
            int numLigne = tableAbonnements.getSelectedRow();
            if (numLigne >= 0) {
                int idAbonnement = Integer.parseInt(tableauAbonnements.getValueAt(numLigne, 0).toString());
                Controleur.deleteAbonnement(idAbonnement);
                this.tableauAbonnements.setDonnees(this.obtenirDonnees(""));
                this.lbNbAbonnements.setText("Nombre d'abonnements : " + this.tableauAbonnements.getRowCount());
                JOptionPane.showMessageDialog(this, "Suppression réussie de l'abonnement.",
                        "Suppression Abonnement", JOptionPane.INFORMATION_MESSAGE);
                this.viderChamps();
            }
        }
    }

    private void traitement() {
        try {
            Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateDebutAbonnement.getText());
            Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateFinAbonnement.getText());
            int idUser = Integer.parseInt(this.txtIdUser.getText());
            int points = Integer.parseInt(this.txtPointAbonnement.getText());

            Abonnement unAbonnement = new Abonnement(0, idUser, dateDebut, dateFin, points);
            Controleur.insertAbonnement(unAbonnement);

            // Afficher un message de confirmation
            JOptionPane.showMessageDialog(this, "Insertion réussie de l'abonnement.",
                    "Insertion Abonnement", JOptionPane.INFORMATION_MESSAGE);

            // Mettre à jour le tableau des abonnements
            this.tableauAbonnements.setDonnees(this.obtenirDonnees(""));

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID utilisateur ou points invalide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        this.viderChamps();
    }

    private void modifierAbonnement() {
        try {
            int idAbonnement = Integer.parseInt(this.txtIdAbonnement.getText());
            Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateDebutAbonnement.getText());
            Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateFinAbonnement.getText());
            int idUser = Integer.parseInt(this.txtIdUser.getText());
            int points = Integer.parseInt(this.txtPointAbonnement.getText());

            Abonnement unAbonnement = new Abonnement(idAbonnement, idUser, dateDebut, dateFin, points);
            Controleur.updateAbonnement(unAbonnement);
            this.tableauAbonnements.setDonnees(this.obtenirDonnees(""));
            JOptionPane.showMessageDialog(this, "Modification réussie de l'abonnement.",
                    "Modification Abonnement", JOptionPane.INFORMATION_MESSAGE);
            this.viderChamps();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID invalide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.btValider.getText().equals("Valider")) {
                this.traitement();
            } else {
                this.modifierAbonnement();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
