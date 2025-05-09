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

        // Initialisation de l'interface
        Color customColor = new Color(100, 140, 180);
        this.setBackground(customColor);

        this.panelForm.setBounds(30, 100, 300, 250);
        this.panelForm.setLayout(new GridLayout(10, 2));
        this.panelForm.setBackground(customColor); // Définir la couleur de fond du formulaire

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

        // Initialisation des listeners
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);

        this.txtIdUser.addKeyListener(this);
        this.txtDateDebutAbonnement.addKeyListener(this);
        this.txtDateFinAbonnement.addKeyListener(this);
        this.txtPointAbonnement.addKeyListener(this);

        // Initialisation du tableau
        String entetes[] = {"Id", "Id User", "Date Début Abonnement", "Date Fin Abonnement", "Points"};
        this.tableauAbonnements = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableAbonnements = new JTable(this.tableauAbonnements);

        // Définir la couleur de fond du tableau sur blanc
        this.tableAbonnements.setBackground(Color.WHITE);

        // Définir la couleur de fond des cellules du tableau sur blanc
        this.tableAbonnements.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                return c;
            }
        });

        JScrollPane uneScroll = new JScrollPane(this.tableAbonnements);
        uneScroll.setBounds(360, 100, 480, 250);
        uneScroll.getViewport().setBackground(customColor); // Définir la couleur de fond pour le JScrollPane
        this.add(uneScroll);

        // Initialisation du filtre
        this.panelFiltre.setBackground(customColor);
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

        // Gestion de la sélection dans le tableau
        this.tableAbonnements.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                afficherDetailsAbonnementSelectionne();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }

    private void afficherDetailsAbonnementSelectionne() {
        int numLigne = tableAbonnements.getSelectedRow();
        if (numLigne >= 0) {
            txtIdUser.setText(tableauAbonnements.getValueAt(numLigne, 1).toString());
            txtDateDebutAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 2).toString());
            txtDateFinAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 3).toString());
            txtPointAbonnement.setText(tableauAbonnements.getValueAt(numLigne, 4).toString());

            btSupprimer.setVisible(true);
            btValider.setText("Modifier");
        }
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Abonnement> lesAbonnements = filtre.isEmpty() ?
                Controleur.selectAbonnement() : Controleur.selectLikeAbonnement(filtre);

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
        this.txtIdUser.setText("");
        this.txtDateDebutAbonnement.setText("");
        this.txtDateFinAbonnement.setText("");
        this.txtPointAbonnement.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    private void insererAbonnement() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des abonnements",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Validation des dates
            Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateDebutAbonnement.getText());
            Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateFinAbonnement.getText());

            // Validation que la date de fin est après la date de début
            if (dateFin.before(dateDebut)) {
                JOptionPane.showMessageDialog(this,
                        "La date de fin doit être postérieure à la date de début",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idUser = Integer.parseInt(this.txtIdUser.getText());
            int points = Integer.parseInt(this.txtPointAbonnement.getText());

            // Validation des points
            if (points < 0) {
                JOptionPane.showMessageDialog(this,
                        "Le nombre de points doit être positif",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Abonnement unAbonnement = new Abonnement(0, idUser, dateDebut, dateFin, points);
            Controleur.insertAbonnement(unAbonnement);

            // Formatage des dates pour l'affichage
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String debutStr = dateFormat.format(dateDebut);
            String finStr = dateFormat.format(dateFin);

            JOptionPane.showMessageDialog(this,
                    "Abonnement créé avec succès !\n" +
                            "Période : " + debutStr + " au " + finStr + "\n" +
                            "Points : " + points,
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

            this.tableauAbonnements.setDonnees(this.obtenirDonnees(""));
            this.lbNbAbonnements.setText("Nombre d'abonnements : " + this.tableauAbonnements.getRowCount());
            this.viderChamps();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Format de date invalide (utilisez yyyy-MM-dd)",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID utilisateur ou points invalides",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierAbonnement() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des abonnements",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableAbonnements.getSelectedRow();
        if (numLigne >= 0) {
            try {
                int idAbonnement = Integer.parseInt(tableauAbonnements.getValueAt(numLigne, 0).toString());
                Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateDebutAbonnement.getText());
                Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateFinAbonnement.getText());

                // Validation que la date de fin est après la date de début
                if (dateFin.before(dateDebut)) {
                    JOptionPane.showMessageDialog(this,
                            "La date de fin doit être postérieure à la date de début",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idUser = Integer.parseInt(this.txtIdUser.getText());
                int points = Integer.parseInt(this.txtPointAbonnement.getText());

                // Validation des points
                if (points < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Le nombre de points doit être positif",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Abonnement unAbonnement = new Abonnement(idAbonnement, idUser, dateDebut, dateFin, points);
                Controleur.updateAbonnement(unAbonnement);

                JOptionPane.showMessageDialog(this,
                        "Modification de l'abonnement réussie",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);

                this.tableauAbonnements.setDonnees(this.obtenirDonnees(""));
                this.viderChamps();

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Format de date invalide (utilisez yyyy-MM-dd)",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "ID invalide",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerAbonnement() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des abonnements",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableAbonnements.getSelectedRow();
        if (numLigne >= 0) {
            int idAbonnement = Integer.parseInt(tableauAbonnements.getValueAt(numLigne, 0).toString());
            int reponse = JOptionPane.showConfirmDialog(this,
                    "Confirmez-vous la suppression de cet abonnement ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (reponse == JOptionPane.YES_OPTION) {
                Controleur.deleteAbonnement(idAbonnement);
                this.tableauAbonnements.setDonnees(this.obtenirDonnees(""));
                this.lbNbAbonnements.setText("Nombre d'abonnements : " + this.tableauAbonnements.getRowCount());
                JOptionPane.showMessageDialog(this,
                        "Suppression de l'abonnement réussie",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.viderChamps();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauAbonnements.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbAbonnements.setText("Nombre d'abonnements : " + this.tableauAbonnements.getRowCount());
        } else if (e.getSource() == this.btValider) {
            if (this.btValider.getText().equals("Valider")) {
                insererAbonnement();
            } else {
                modifierAbonnement();
            }
        } else if (e.getSource() == this.btSupprimer) {
            supprimerAbonnement();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.btValider.getText().equals("Valider")) {
                insererAbonnement();
            } else {
                modifierAbonnement();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}