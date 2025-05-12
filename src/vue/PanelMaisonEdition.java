package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import controleur.MaisonEdition;
import controleur.Controleur;
import controleur.Tableau;
import controleur.Livre;

public class PanelMaisonEdition extends PanelPrincipal implements ActionListener, KeyListener {
    private String niveauAdmin;
    private JPanel panelMaisonEditionForm = new JPanel();
    private JTextField txtNomMaisonEdition = new JTextField();
    private JButton btAnnulerMaisonEdition = new JButton("Annuler");
    private JButton btValiderMaisonEdition = new JButton("Valider");
    private JButton btSupprimerMaisonEdition = new JButton("Supprimer");

    private JPanel panelLivreMaisonEditionForm = new JPanel();
    private JTextField txtNomLivre = new JTextField();
    private JTextField txtNomMaisonEditionLivre = new JTextField();
    private JButton btAnnulerLivreMaisonEdition = new JButton("Annuler");
    private JButton btValiderLivreMaisonEdition = new JButton("Valider");

    private JTable tableMaisonEditions;
    private Tableau tableauMaisonEditions;
    private JLabel lbNbMaisonEditions = new JLabel();

    private JTable tableLivresMaisonEditions;
    private Tableau tableauLivresMaisonEditions;
    private JLabel lbNbLivresMaisonEditions = new JLabel();

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField(7);
    private JButton btFiltrer = new JButton("Filtrer");

    private JPanel panelFiltreLivres = new JPanel();
    private JTextField txtFiltreLivres = new JTextField(7);
    private JButton btFiltrerLivres = new JButton("Filtrer");

    private int idMaisonEditionModification = 0;
    private int idLivreModification = 0;

    private Color couleurFormulaire = new Color(100, 140, 180);

    public PanelMaisonEdition() {
        super("Gestion des Maisons d'Edition");

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(Controleur.getUserConnecte().getIdUser());
        initMaisonEdition();
        initLivreMaisonEdition();
        initTableauMaisonEditions();
        initTableauLivresMaisonEditions();
        initFiltres();

        setupListeners();

        btSupprimerMaisonEdition.setVisible(false);
    }

    private void initMaisonEdition() {
        this.panelMaisonEditionForm.setBackground(couleurFormulaire);
        this.panelMaisonEditionForm.setBounds(30, 100, 350, 150);
        this.panelMaisonEditionForm.setLayout(new GridLayout(4, 2));

        this.panelMaisonEditionForm.add(new JLabel("Nom Maison d'Edition:"));
        this.panelMaisonEditionForm.add(this.txtNomMaisonEdition);
        this.panelMaisonEditionForm.add(this.btAnnulerMaisonEdition);
        this.panelMaisonEditionForm.add(this.btValiderMaisonEdition);
        this.panelMaisonEditionForm.add(this.btSupprimerMaisonEdition);

        this.add(this.panelMaisonEditionForm);
    }

    private void initLivreMaisonEdition() {
        this.panelLivreMaisonEditionForm.setBackground(couleurFormulaire);
        this.panelLivreMaisonEditionForm.setBounds(30, 240, 350, 150);
        this.panelLivreMaisonEditionForm.setLayout(new GridLayout(4, 2));

        this.panelLivreMaisonEditionForm.add(new JLabel("Nom Livre:"));
        this.panelLivreMaisonEditionForm.add(this.txtNomLivre);
        this.panelLivreMaisonEditionForm.add(new JLabel("Nom Maison d'Edition:"));
        this.panelLivreMaisonEditionForm.add(this.txtNomMaisonEditionLivre);
        this.panelLivreMaisonEditionForm.add(this.btAnnulerLivreMaisonEdition);
        this.panelLivreMaisonEditionForm.add(this.btValiderLivreMaisonEdition);

        this.add(this.panelLivreMaisonEditionForm);
    }

    private void initTableauMaisonEditions() {
        String entetesMaisonEditions[] = {"ID Maison d'Edition", "Nom Maison d'Edition"};
        this.tableauMaisonEditions = new Tableau(this.obtenirDonneesMaisonEditions(""), entetesMaisonEditions);
        this.tableMaisonEditions = new JTable(this.tableauMaisonEditions);
        JScrollPane uneScrollMaisonEditions = new JScrollPane(this.tableMaisonEditions);
        uneScrollMaisonEditions.setBounds(400, 100, 220, 250);
        this.add(uneScrollMaisonEditions);

        this.tableMaisonEditions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsMaisonEditionSelectionnee();
            }
        });
    }

    private void initTableauLivresMaisonEditions() {
        String entetesLivresMaisonEd[] = {"ID Livre", "Nom Livre", "Nom Maison d'Edition"};
        this.tableauLivresMaisonEditions = new Tableau(this.obtenirDonneesLivresMaisonEditions(""), entetesLivresMaisonEd);
        this.tableLivresMaisonEditions = new JTable(this.tableauLivresMaisonEditions);
        JScrollPane uneScrollLivresMaisonEd = new JScrollPane(this.tableLivresMaisonEditions);
        uneScrollLivresMaisonEd.setBounds(630, 100, 260, 250);
        this.add(uneScrollLivresMaisonEd);

        this.tableLivresMaisonEditions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsLivreMaisonEditionSelectionne();
            }
        });
    }

    private void initFiltres() {
        this.panelFiltre.setBackground(couleurFormulaire);
        this.panelFiltre.setBounds(400, 60, 320, 30);
        this.panelFiltre.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelFiltre = new JLabel("Filtrer :");
        labelFiltre.setPreferredSize(new Dimension(50, 20));
        this.panelFiltre.add(labelFiltre);
        this.txtFiltre.setPreferredSize(new Dimension(75, 20));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);

        this.lbNbMaisonEditions.setBounds(400, 360, 400, 20);
        this.add(this.lbNbMaisonEditions);
        this.lbNbMaisonEditions.setText("Nombre de maisons d'édition : " + this.tableauMaisonEditions.getRowCount());

        this.panelFiltreLivres.setBackground(couleurFormulaire);
        this.panelFiltreLivres.setBounds(650, 60, 320, 30);
        this.panelFiltreLivres.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelFiltreLivres = new JLabel("Filtrer :");
        labelFiltreLivres.setPreferredSize(new Dimension(50, 20));
        this.panelFiltreLivres.add(labelFiltreLivres);
        this.txtFiltreLivres.setPreferredSize(new Dimension(75, 20));
        this.panelFiltreLivres.add(this.txtFiltreLivres);
        this.panelFiltreLivres.add(this.btFiltrerLivres);
        this.add(this.panelFiltreLivres);

        this.lbNbLivresMaisonEditions.setBounds(630, 360, 400, 20);
        this.add(this.lbNbLivresMaisonEditions);
        this.lbNbLivresMaisonEditions.setText("Nombre de livres : " + this.tableauLivresMaisonEditions.getRowCount());
    }

    private void setupListeners() {
        this.btAnnulerMaisonEdition.addActionListener(this);
        this.btValiderMaisonEdition.addActionListener(this);
        this.btSupprimerMaisonEdition.addActionListener(this);

        this.btAnnulerLivreMaisonEdition.addActionListener(this);
        this.btValiderLivreMaisonEdition.addActionListener(this);

        this.btFiltrer.addActionListener(this);
        this.btFiltrerLivres.addActionListener(this);

        this.txtNomMaisonEdition.addKeyListener(this);
        this.txtNomLivre.addKeyListener(this);
        this.txtNomMaisonEditionLivre.addKeyListener(this);
    }

    private void validerMaisonEdition() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des maisons d'édition",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomMaisonEdition = txtNomMaisonEdition.getText();
        if (nomMaisonEdition.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de maison d'édition", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MaisonEdition maisonEdition = new MaisonEdition(nomMaisonEdition);

        if (idMaisonEditionModification == 0) {
            Controleur.insertMaisonEdition(maisonEdition);
        } else {
            maisonEdition.setIdMaisonEdition(idMaisonEditionModification);
            Controleur.updateMaisonEdition(maisonEdition);
            idMaisonEditionModification = 0;
        }

        tableauMaisonEditions.setDonnees(obtenirDonneesMaisonEditions(""));
        tableauLivresMaisonEditions.setDonnees(obtenirDonneesLivresMaisonEditions(""));
        majNbMaisonEditions();
        majNbLivresMaisonEditions();
        viderChampsMaisonEdition();
    }

    private void supprimerMaisonEdition() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des maisons d'édition",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Confirmez-vous la suppression de cette maison d'édition ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            Controleur.deleteMaisonEdition(idMaisonEditionModification);

            tableauMaisonEditions.setDonnees(obtenirDonneesMaisonEditions(""));
            tableauLivresMaisonEditions.setDonnees(obtenirDonneesLivresMaisonEditions(""));
            majNbMaisonEditions();
            majNbLivresMaisonEditions();
            viderChampsMaisonEdition();
        }
    }

    private void validerLivreMaisonEdition() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier les maisons d'édition des livres",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomLivre = txtNomLivre.getText();
        String nomMaisonEdition = txtNomMaisonEditionLivre.getText();

        if (nomLivre.isEmpty() || nomMaisonEdition.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de livre et une maison d'édition", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livre livre = Controleur.selectLivreByNom(nomLivre);
        MaisonEdition maisonEdition = Controleur.selectMaisonEditionByNom(nomMaisonEdition);

        if (livre != null && maisonEdition != null) {
            livre.setIdMaisonEdition(maisonEdition.getIdMaisonEdition());
            Controleur.updateMaisonEditionLivre(livre);
            JOptionPane.showMessageDialog(this, "Maison d'édition du livre mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

            tableauLivresMaisonEditions.setDonnees(obtenirDonneesLivresMaisonEditions(""));
            majNbLivresMaisonEditions();
            viderChampsLivreMaisonEdition();
        } else {
            JOptionPane.showMessageDialog(this, "Livre ou maison d'édition introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherDetailsMaisonEditionSelectionnee() {
        int row = tableMaisonEditions.getSelectedRow();
        if (row >= 0) {
            int idMaisonEdition = (int) tableauMaisonEditions.getValueAt(row, 0);
            MaisonEdition maisonEdition = Controleur.selectMaisonEditionById(idMaisonEdition);

            if (maisonEdition != null) {
                txtNomMaisonEdition.setText(maisonEdition.getNomMaisonEdition());

                idMaisonEditionModification = idMaisonEdition;
                btValiderMaisonEdition.setText("Modifier");
                btSupprimerMaisonEdition.setVisible(true);
            }
        }
    }

    private void afficherDetailsLivreMaisonEditionSelectionne() {
        int row = tableLivresMaisonEditions.getSelectedRow();
        if (row >= 0) {
            int idLivre = (int) tableauLivresMaisonEditions.getValueAt(row, 0);
            String nomLivre = (String) tableauLivresMaisonEditions.getValueAt(row, 1);
            String nomMaisonEdition = (String) tableauLivresMaisonEditions.getValueAt(row, 2);

            txtNomLivre.setText(nomLivre);
            txtNomMaisonEditionLivre.setText(nomMaisonEdition);
            idLivreModification = idLivre;
        }
    }

    private void viderChampsMaisonEdition() {
        this.txtNomMaisonEdition.setText("");
        btValiderMaisonEdition.setText("Valider");
        btSupprimerMaisonEdition.setVisible(false);
        idMaisonEditionModification = 0;
    }

    private void viderChampsLivreMaisonEdition() {
        this.txtNomLivre.setText("");
        this.txtNomMaisonEditionLivre.setText("");
        idLivreModification = 0;
    }

    public Object[][] obtenirDonneesMaisonEditions(String filtre) {
        ArrayList<MaisonEdition> lesMaisonEditions = filtre.isEmpty() ?
                Controleur.selectMaisonEdition() : Controleur.selectLikeMaisonEdition(filtre);

        Object matrice[][] = new Object[lesMaisonEditions.size()][2];

        int i = 0;
        for (MaisonEdition uneMaisonEdition : lesMaisonEditions) {
            matrice[i][0] = uneMaisonEdition.getIdMaisonEdition();
            matrice[i][1] = uneMaisonEdition.getNomMaisonEdition();
            i++;
        }
        return matrice;
    }

    public Object[][] obtenirDonneesLivresMaisonEditions(String filtre) {
        ArrayList<Livre> lesLivres = filtre.isEmpty() ?
                Controleur.selectLivre() : Controleur.selectLikeLivre(filtre);

        Object matrice[][] = new Object[lesLivres.size()][3];

        int i = 0;
        for (Livre unLivre : lesLivres) {
            matrice[i][0] = unLivre.getIdLivre();
            matrice[i][1] = unLivre.getNomLivre();
            int idMaisonEdition = unLivre.getIdMaisonEdition();

            if (idMaisonEdition > 0) {
                MaisonEdition maisonEdition = Controleur.selectMaisonEditionById(idMaisonEdition);
                matrice[i][2] = maisonEdition != null ? maisonEdition.getNomMaisonEdition() : "Non définie";
            } else {
                matrice[i][2] = "Non définie";
            }

            i++;
        }
        return matrice;
    }

    private void majNbMaisonEditions() {
        lbNbMaisonEditions.setText("Nombre de maisons d'édition : " + tableauMaisonEditions.getRowCount());
    }

    private void majNbLivresMaisonEditions() {
        lbNbLivresMaisonEditions.setText("Nombre de livres : " + tableauLivresMaisonEditions.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnulerMaisonEdition) {
            viderChampsMaisonEdition();
        } else if (e.getSource() == this.btValiderMaisonEdition) {
            validerMaisonEdition();
        } else if (e.getSource() == this.btSupprimerMaisonEdition) {
            supprimerMaisonEdition();
        } else if (e.getSource() == this.btAnnulerLivreMaisonEdition) {
            viderChampsLivreMaisonEdition();
        } else if (e.getSource() == this.btValiderLivreMaisonEdition) {
            validerLivreMaisonEdition();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauMaisonEditions.setDonnees(this.obtenirDonneesMaisonEditions(filtre));
            this.majNbMaisonEditions();
        } else if (e.getSource() == this.btFiltrerLivres) {
            String filtre = this.txtFiltreLivres.getText();
            this.tableauLivresMaisonEditions.setDonnees(this.obtenirDonneesLivresMaisonEditions(filtre));
            this.majNbLivresMaisonEditions();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == txtNomMaisonEdition) {
                validerMaisonEdition();
            } else if (e.getSource() == txtNomLivre || e.getSource() == txtNomMaisonEditionLivre) {
                validerLivreMaisonEdition();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}