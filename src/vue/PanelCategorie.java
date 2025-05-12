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
import controleur.Categorie;
import controleur.Controleur;
import controleur.Tableau;
import controleur.Livre;

public class PanelCategorie extends PanelPrincipal implements ActionListener, KeyListener {
    private String niveauAdmin;
    private JPanel panelCategorieForm = new JPanel();
    private JTextField txtNomCategorie = new JTextField();
    private JButton btAnnulerCategorie = new JButton("Annuler");
    private JButton btValiderCategorie = new JButton("Valider");
    private JButton btSupprimerCategorie = new JButton("Supprimer");

    private JPanel panelLivreCategorieForm = new JPanel();
    private JTextField txtNomLivre = new JTextField();
    private JTextField txtNomCategorieLivre = new JTextField();
    private JButton btAnnulerLivreCategorie = new JButton("Annuler");
    private JButton btValiderLivreCategorie = new JButton("Valider");

    private JTable tableCategories;
    private Tableau tableauCategories;
    private JLabel lbNbCategories = new JLabel();

    private JTable tableLivresCategories;
    private Tableau tableauLivresCategories;
    private JLabel lbNbLivresCategories = new JLabel();

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField(7);
    private JButton btFiltrer = new JButton("Filtrer");

    private JPanel panelFiltreLivres = new JPanel();
    private JTextField txtFiltreLivres = new JTextField(7);
    private JButton btFiltrerLivres = new JButton("Filtrer");

    private int idCategorieModification = 0;
    private int idLivreModification = 0;

    private Color couleurFormulaire = new Color(100, 140, 180);

    public PanelCategorie() {
        super("Gestion des Catégories");

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(Controleur.getUserConnecte().getIdUser());
        initCategorie();
        initLivreCategorie();
        initTableauCategories();
        initTableauLivresCategories();
        initFiltres();

        setupListeners();

        btSupprimerCategorie.setVisible(false);
    }

    private void initCategorie() {
        this.panelCategorieForm.setBackground(couleurFormulaire);
        this.panelCategorieForm.setBounds(30, 100, 350, 150);
        this.panelCategorieForm.setLayout(new GridLayout(4, 2));

        this.panelCategorieForm.add(new JLabel("Nom Catégorie:"));
        this.panelCategorieForm.add(this.txtNomCategorie);
        this.panelCategorieForm.add(this.btAnnulerCategorie);
        this.panelCategorieForm.add(this.btValiderCategorie);
        this.panelCategorieForm.add(this.btSupprimerCategorie);

        this.add(this.panelCategorieForm);
    }

    private void initLivreCategorie() {
        this.panelLivreCategorieForm.setBackground(couleurFormulaire);
        this.panelLivreCategorieForm.setBounds(30, 240, 350, 150);
        this.panelLivreCategorieForm.setLayout(new GridLayout(4, 2));

        this.panelLivreCategorieForm.add(new JLabel("Nom Livre:"));
        this.panelLivreCategorieForm.add(this.txtNomLivre);
        this.panelLivreCategorieForm.add(new JLabel("Nom Catégorie:"));
        this.panelLivreCategorieForm.add(this.txtNomCategorieLivre);
        this.panelLivreCategorieForm.add(this.btAnnulerLivreCategorie);
        this.panelLivreCategorieForm.add(this.btValiderLivreCategorie);

        this.add(this.panelLivreCategorieForm);
    }

    private void initTableauCategories() {
        String entetesCategories[] = {"ID Categorie", "Nom Categorie"};
        this.tableauCategories = new Tableau(this.obtenirDonneesCategories(""), entetesCategories);
        this.tableCategories = new JTable(this.tableauCategories);
        JScrollPane uneScrollCategories = new JScrollPane(this.tableCategories);
        uneScrollCategories.setBounds(400, 100, 220, 250);
        this.add(uneScrollCategories);

        this.tableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsCategorieSelectionnee();
            }
        });
    }

    private void initTableauLivresCategories() {
        String entetesLivresCat[] = {"ID Livre", "Nom Livre", "Nom Catégorie"};
        this.tableauLivresCategories = new Tableau(this.obtenirDonneesLivresCategories(""), entetesLivresCat);
        this.tableLivresCategories = new JTable(this.tableauLivresCategories);
        JScrollPane uneScrollLivresCat = new JScrollPane(this.tableLivresCategories);
        uneScrollLivresCat.setBounds(630, 100, 260, 250);
        this.add(uneScrollLivresCat);

        this.tableLivresCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsLivreCategorieSelectionne();
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

        this.lbNbCategories.setBounds(400, 360, 400, 20);
        this.add(this.lbNbCategories);
        this.lbNbCategories.setText("Nombre de catégories : " + this.tableauCategories.getRowCount());

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

        this.lbNbLivresCategories.setBounds(630, 360, 400, 20);
        this.add(this.lbNbLivresCategories);
        this.lbNbLivresCategories.setText("Nombre de livres : " + this.tableauLivresCategories.getRowCount());
    }

    private void setupListeners() {
        this.btAnnulerCategorie.addActionListener(this);
        this.btValiderCategorie.addActionListener(this);
        this.btSupprimerCategorie.addActionListener(this);

        this.btAnnulerLivreCategorie.addActionListener(this);
        this.btValiderLivreCategorie.addActionListener(this);

        this.btFiltrer.addActionListener(this);
        this.btFiltrerLivres.addActionListener(this);

        this.txtNomCategorie.addKeyListener(this);
        this.txtNomLivre.addKeyListener(this);
        this.txtNomCategorieLivre.addKeyListener(this);
    }

    private void validerCategorie() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des catégories",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomCategorie = txtNomCategorie.getText();
        if (nomCategorie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de catégorie", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Categorie categorie = new Categorie(nomCategorie);

        if (idCategorieModification == 0) {
            Controleur.insertCategorie(categorie);
        } else {
            categorie.setIdCategorie(idCategorieModification);
            Controleur.updateCategorie(categorie);
            idCategorieModification = 0;
        }

        tableauCategories.setDonnees(obtenirDonneesCategories(""));
        tableauLivresCategories.setDonnees(obtenirDonneesLivresCategories(""));
        majNbCategories();
        majNbLivresCategories();
        viderChampsCategorie();
    }

    private void supprimerCategorie() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des catégories",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Confirmez-vous la suppression de cette catégorie ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            Controleur.deleteCategorie(idCategorieModification);

            tableauCategories.setDonnees(obtenirDonneesCategories(""));
            tableauLivresCategories.setDonnees(obtenirDonneesLivresCategories(""));
            majNbCategories();
            majNbLivresCategories();
            viderChampsCategorie();
        }
    }

    private void validerLivreCategorie() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier les catégories des livres",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomLivre = txtNomLivre.getText();
        String nomCategorie = txtNomCategorieLivre.getText();

        if (nomLivre.isEmpty() || nomCategorie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de livre et une catégorie", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livre livre = Controleur.selectLivreByNom(nomLivre);
        Categorie categorie = Controleur.selectCategorieByNom(nomCategorie);

        if (livre != null && categorie != null) {
            livre.setIdCategorie(categorie.getIdCategorie());
            Controleur.updateCategorieLivre(livre);
            JOptionPane.showMessageDialog(this, "Catégorie du livre mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

            tableauLivresCategories.setDonnees(obtenirDonneesLivresCategories(""));
            majNbLivresCategories();
            viderChampsLivreCategorie();
        } else {
            JOptionPane.showMessageDialog(this, "Livre ou catégorie introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherDetailsCategorieSelectionnee() {
        int row = tableCategories.getSelectedRow();
        if (row >= 0) {
            int idCategorie = (int) tableauCategories.getValueAt(row, 0);
            Categorie categorie = Controleur.selectCategorieById(idCategorie);

            if (categorie != null) {
                txtNomCategorie.setText(categorie.getNomCategorie());

                idCategorieModification = idCategorie;
                btValiderCategorie.setText("Modifier");
                btSupprimerCategorie.setVisible(true);
            }
        }
    }

    private void afficherDetailsLivreCategorieSelectionne() {
        int row = tableLivresCategories.getSelectedRow();
        if (row >= 0) {
            int idLivre = (int) tableauLivresCategories.getValueAt(row, 0);
            String nomLivre = (String) tableauLivresCategories.getValueAt(row, 1);
            String nomCategorie = (String) tableauLivresCategories.getValueAt(row, 2);

            txtNomLivre.setText(nomLivre);
            txtNomCategorieLivre.setText(nomCategorie);
            idLivreModification = idLivre;
        }
    }

    private void viderChampsCategorie() {
        this.txtNomCategorie.setText("");
        btValiderCategorie.setText("Valider");
        btSupprimerCategorie.setVisible(false);
        idCategorieModification = 0;
    }

    private void viderChampsLivreCategorie() {
        this.txtNomLivre.setText("");
        this.txtNomCategorieLivre.setText("");
        idLivreModification = 0;
    }

    public Object[][] obtenirDonneesCategories(String filtre) {
        ArrayList<Categorie> lesCategories = filtre.isEmpty() ?
                Controleur.selectCategorie() : Controleur.selectLikeCategorie(filtre);

        Object matrice[][] = new Object[lesCategories.size()][2];

        int i = 0;
        for (Categorie uneCategorie : lesCategories) {
            matrice[i][0] = uneCategorie.getIdCategorie();
            matrice[i][1] = uneCategorie.getNomCategorie();
            i++;
        }
        return matrice;
    }

    public Object[][] obtenirDonneesLivresCategories(String filtre) {
        ArrayList<Livre> lesLivres = filtre.isEmpty() ?
                Controleur.selectLivre() : Controleur.selectLikeLivre(filtre);

        Object matrice[][] = new Object[lesLivres.size()][3];

        int i = 0;
        for (Livre unLivre : lesLivres) {
            matrice[i][0] = unLivre.getIdLivre();
            matrice[i][1] = unLivre.getNomLivre();
            int idCategorie = unLivre.getIdCategorie();

            if (idCategorie > 0) {
                Categorie categorie = Controleur.selectCategorieById(idCategorie);
                matrice[i][2] = categorie != null ? categorie.getNomCategorie() : "Non définie";
            } else {
                matrice[i][2] = "Non définie";
            }

            i++;
        }
        return matrice;
    }

    private void majNbCategories() {
        lbNbCategories.setText("Nombre de catégories : " + tableauCategories.getRowCount());
    }

    private void majNbLivresCategories() {
        lbNbLivresCategories.setText("Nombre de livres : " + tableauLivresCategories.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnulerCategorie) {
            viderChampsCategorie();
        } else if (e.getSource() == this.btValiderCategorie) {
            validerCategorie();
        } else if (e.getSource() == this.btSupprimerCategorie) {
            supprimerCategorie();
        } else if (e.getSource() == this.btAnnulerLivreCategorie) {
            viderChampsLivreCategorie();
        } else if (e.getSource() == this.btValiderLivreCategorie) {
            validerLivreCategorie();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauCategories.setDonnees(this.obtenirDonneesCategories(filtre));
            this.majNbCategories();
        } else if (e.getSource() == this.btFiltrerLivres) {
            String filtre = this.txtFiltreLivres.getText();
            this.tableauLivresCategories.setDonnees(this.obtenirDonneesLivresCategories(filtre));
            this.majNbLivresCategories();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == txtNomCategorie) {
                validerCategorie();
            } else if (e.getSource() == txtNomLivre || e.getSource() == txtNomCategorieLivre) {
                validerLivreCategorie();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}