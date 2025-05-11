package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import controleur.Promotion;
import controleur.Controleur;
import controleur.Tableau;
import controleur.Livre;

public class PanelPromotion extends PanelPrincipal implements ActionListener, KeyListener {
    private String niveauAdmin;
    // Panel pour les promotions
    private JPanel panelPromotionForm = new JPanel();
    private JTextField txtNomPromotion = new JTextField();
    private JTextField txtDateDebutPromotion = new JTextField();
    private JTextField txtDateFinPromotion = new JTextField();
    private JTextField txtReductionPromotion = new JTextField();
    private JButton btAnnulerPromotion = new JButton("Annuler");
    private JButton btValiderPromotion = new JButton("Valider");
    private JButton btSupprimerPromotion = new JButton("Supprimer");

    // Panel pour l'association Livre-Promotion
    private JPanel panelLivrePromotionForm = new JPanel();
    private JTextField txtNomLivre = new JTextField();
    private JTextField txtNomPromotionLivre = new JTextField();
    private JButton btAnnulerLivrePromotion = new JButton("Annuler");
    private JButton btValiderLivrePromotion = new JButton("Valider");

    // Tableaux et filtres
    private JTable tablePromotions;
    private Tableau tableauPromotions;
    private JLabel lbNbPromotions = new JLabel();

    private JTable tableLivresPromotions;
    private Tableau tableauLivresPromotions;
    private JLabel lbNbLivresPromotions = new JLabel();

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField(7);
    private JButton btFiltrer = new JButton("Filtrer");

    private JPanel panelFiltreLivres = new JPanel();
    private JTextField txtFiltreLivres = new JTextField(7);
    private JButton btFiltrerLivres = new JButton("Filtrer");

    // Données temporaires et état
    private int idPromotionModification = 0;
    private int idLivreModification = 0;

    // Couleur personnalisée pour les formulaires
    private Color couleurFormulaire = new Color(100, 140, 180);

    public PanelPromotion() {
        super("Gestion des Promotions");

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(Controleur.getUserConnecte().getIdUser());
        // Initialisation des panels
        initPromotion();
        initLivrePromotion();
        initTableauPromotions();
        initTableauLivresPromotions();
        initFiltres();

        // Gestion des listeners
        setupListeners();

        // Masquer le bouton de suppression initialement
        btSupprimerPromotion.setVisible(false);
    }

    private void initPromotion() {
        this.panelPromotionForm.setBackground(couleurFormulaire);
        this.panelPromotionForm.setBounds(30, 100, 350, 150);
        this.panelPromotionForm.setLayout(new GridLayout(6, 2));

        this.panelPromotionForm.add(new JLabel("Nom Promotion:"));
        this.panelPromotionForm.add(this.txtNomPromotion);
        this.panelPromotionForm.add(new JLabel("Date Début (YYYY-MM-DD):"));
        this.panelPromotionForm.add(this.txtDateDebutPromotion);
        this.panelPromotionForm.add(new JLabel("Date Fin (YYYY-MM-DD):"));
        this.panelPromotionForm.add(this.txtDateFinPromotion);
        this.panelPromotionForm.add(new JLabel("Réduction (%):"));
        this.panelPromotionForm.add(this.txtReductionPromotion);
        this.panelPromotionForm.add(this.btAnnulerPromotion);
        this.panelPromotionForm.add(this.btValiderPromotion);
        this.panelPromotionForm.add(this.btSupprimerPromotion);

        this.add(this.panelPromotionForm);
    }

    private void initLivrePromotion() {
        this.panelLivrePromotionForm.setBackground(couleurFormulaire);
        this.panelLivrePromotionForm.setBounds(30, 240, 350, 150);
        this.panelLivrePromotionForm.setLayout(new GridLayout(4, 2));

        this.panelLivrePromotionForm.add(new JLabel("Nom Livre:"));
        this.panelLivrePromotionForm.add(this.txtNomLivre);
        this.panelLivrePromotionForm.add(new JLabel("Nom Promotion:"));
        this.panelLivrePromotionForm.add(this.txtNomPromotionLivre);
        this.panelLivrePromotionForm.add(this.btAnnulerLivrePromotion);
        this.panelLivrePromotionForm.add(this.btValiderLivrePromotion);

        this.add(this.panelLivrePromotionForm);
    }

    private void initTableauPromotions() {
        // Tableau des promotions
        String entetesPromotions[] = {"ID Promotion", "Nom Promotion", "Date Début", "Date Fin", "Réduction (%)"};
        this.tableauPromotions = new Tableau(this.obtenirDonneesPromotions(""), entetesPromotions);
        this.tablePromotions = new JTable(this.tableauPromotions);
        JScrollPane uneScrollPromotions = new JScrollPane(this.tablePromotions);
        uneScrollPromotions.setBounds(400, 100, 260, 250);
        this.add(uneScrollPromotions);

        // Gestion de la sélection dans le tableau des promotions
        this.tablePromotions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsPromotionSelectionnee();
            }
        });
    }

    private void initTableauLivresPromotions() {
        // Tableau des livres et leurs promotions
        String entetesLivresPromotions[] = {"ID Livre", "Nom Livre", "Nom Promotion"};
        this.tableauLivresPromotions = new Tableau(this.obtenirDonneesLivresPromotions(""), entetesLivresPromotions);
        this.tableLivresPromotions = new JTable(this.tableauLivresPromotions);
        JScrollPane uneScrollLivresPromotions = new JScrollPane(this.tableLivresPromotions);
        uneScrollLivresPromotions.setBounds(670, 100, 220, 250);
        this.add(uneScrollLivresPromotions);

        // Gestion de la sélection dans le tableau des livres et promotions
        this.tableLivresPromotions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsLivrePromotionSelectionne();
            }
        });
    }

    private void initFiltres() {
        // Filtre pour le tableau des promotions
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

        this.lbNbPromotions.setBounds(400, 360, 400, 20);
        this.add(this.lbNbPromotions);
        this.lbNbPromotions.setText("Nombre de promotions : " + this.tableauPromotions.getRowCount());

        // Filtre pour le tableau des livres et promotions
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

        this.lbNbLivresPromotions.setBounds(630, 360, 400, 20);
        this.add(this.lbNbLivresPromotions);
        this.lbNbLivresPromotions.setText("Nombre de livres : " + this.tableauLivresPromotions.getRowCount());
    }

    private void setupListeners() {
        // Boutons Promotion
        this.btAnnulerPromotion.addActionListener(this);
        this.btValiderPromotion.addActionListener(this);
        this.btSupprimerPromotion.addActionListener(this);

        // Boutons Livre-Promotion
        this.btAnnulerLivrePromotion.addActionListener(this);
        this.btValiderLivrePromotion.addActionListener(this);

        // Filtres
        this.btFiltrer.addActionListener(this);
        this.btFiltrerLivres.addActionListener(this);

        // Listeners pour validation avec Entrée
        this.txtNomPromotion.addKeyListener(this);
        this.txtDateDebutPromotion.addKeyListener(this);
        this.txtDateFinPromotion.addKeyListener(this);
        this.txtReductionPromotion.addKeyListener(this);
        this.txtNomLivre.addKeyListener(this);
        this.txtNomPromotionLivre.addKeyListener(this);
    }

    private void validerPromotion() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des promotions",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomPromotion = txtNomPromotion.getText();
        String dateDebutStr = txtDateDebutPromotion.getText();
        String dateFinStr = txtDateFinPromotion.getText();
        String reductionStr = txtReductionPromotion.getText();

        if (nomPromotion.isEmpty() || dateDebutStr.isEmpty() || dateFinStr.isEmpty() || reductionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Conversion et validation des données
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = sdf.parse(dateDebutStr);
            Date dateFin = sdf.parse(dateFinStr);
            int reduction = Integer.parseInt(reductionStr);

            if (reduction < 0 || reduction > 100) {
                JOptionPane.showMessageDialog(this, "La réduction doit être comprise entre 0 et 100%", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dateFin.before(dateDebut)) {
                JOptionPane.showMessageDialog(this, "La date de fin ne peut pas être antérieure à la date de début", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Promotion promotion = new Promotion(nomPromotion, dateDebut, dateFin, reduction);

            if (idPromotionModification == 0) {
                // Insertion d'une nouvelle promotion
                Controleur.insertPromotion(promotion);
                JOptionPane.showMessageDialog(this, "Promotion ajoutée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mise à jour d'une promotion existante
                promotion.setIdPromotion(idPromotionModification);
                Controleur.updatePromotion(promotion);
                JOptionPane.showMessageDialog(this, "Promotion mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                idPromotionModification = 0; // Réinitialiser après la mise à jour
            }

            // Mise à jour de l'interface
            tableauPromotions.setDonnees(obtenirDonneesPromotions(""));
            tableauLivresPromotions.setDonnees(obtenirDonneesLivresPromotions(""));
            majNbPromotions();
            majNbLivresPromotions();
            viderChampsPromotion();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez le format YYYY-MM-DD", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la validation des données: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerPromotion() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des promotions",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Confirmez-vous la suppression de cette promotion ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            Controleur.deletePromotion(idPromotionModification);
            JOptionPane.showMessageDialog(this, "Promotion supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Mise à jour de l'interface
            tableauPromotions.setDonnees(obtenirDonneesPromotions(""));
            tableauLivresPromotions.setDonnees(obtenirDonneesLivresPromotions(""));
            majNbPromotions();
            majNbLivresPromotions();
            viderChampsPromotion();
        }
    }

    private void validerLivrePromotion() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier les promotions des livres",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomLivre = txtNomLivre.getText();
        if ("Non définie".equals(nomLivre)) {
            nomLivre = null;
        }
        String nomPromotion = txtNomPromotionLivre.getText();

        if (nomLivre == null || nomPromotion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de livre et une promotion", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livre livre = Controleur.selectLivreByNom(nomLivre);
        Promotion promotion = Controleur.selectPromotionByNom(nomPromotion);

        if (livre != null) {
            if (promotion != null) {
                livre.setIdPromotion(promotion.getIdPromotion());
                Controleur.updatePromotionLivre(livre);
            } else {
                livre.setIdPromotion(null);
                Controleur.updatePromotionLivre(livre);
            }
            JOptionPane.showMessageDialog(this, "Promotion du livre mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Mise à jour du tableau après l'association
            tableauLivresPromotions.setDonnees(obtenirDonneesLivresPromotions(""));
            majNbLivresPromotions();
            viderChampsLivrePromotion();
        } else {
            JOptionPane.showMessageDialog(this, "Livre introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherDetailsPromotionSelectionnee() {
        int row = tablePromotions.getSelectedRow();
        if (row >= 0) {
            int idPromotion = (int) tableauPromotions.getValueAt(row, 0); // Colonne ID Promotion à l'index 0
            Promotion promotion = Controleur.selectPromotionById(idPromotion);

            if (promotion != null) {
                txtNomPromotion.setText(promotion.getNomPromotion());
                txtDateDebutPromotion.setText(promotion.getDateDebutPromotion().toString());
                txtDateFinPromotion.setText(promotion.getDateFinPromotion().toString());
                txtReductionPromotion.setText(String.valueOf(promotion.getReductionPromotion()));

                // Préparation de la modification
                idPromotionModification = idPromotion;
                btValiderPromotion.setText("Modifier");
                btSupprimerPromotion.setVisible(true);
            }
        }
    }

    private void afficherDetailsLivrePromotionSelectionne() {
        int row = tableLivresPromotions.getSelectedRow();
        if (row >= 0) {
            int idLivre = (int) tableauLivresPromotions.getValueAt(row, 0); // Colonne ID Livre à l'index 0
            String nomLivre = (String) tableauLivresPromotions.getValueAt(row, 1); // Nom du livre
            String nomPromotion = (String) tableauLivresPromotions.getValueAt(row, 2); // Nom promotion

            // Préremplir le formulaire association livre-promotion
            txtNomLivre.setText(nomLivre);
            txtNomPromotionLivre.setText(nomPromotion);
            idLivreModification = idLivre;
        }
    }

    private void viderChampsPromotion() {
        this.txtNomPromotion.setText("");
        this.txtDateDebutPromotion.setText("");
        this.txtDateFinPromotion.setText("");
        this.txtReductionPromotion.setText("");
        btValiderPromotion.setText("Valider");
        btSupprimerPromotion.setVisible(false);
        idPromotionModification = 0;
    }

    private void viderChampsLivrePromotion() {
        this.txtNomLivre.setText("");
        this.txtNomPromotionLivre.setText("");
        idLivreModification = 0;
    }

    public Object[][] obtenirDonneesPromotions(String filtre) {
        // Récupère les données pour le tableau des promotions
        ArrayList<Promotion> lesPromotions = filtre.isEmpty() ?
                Controleur.selectPromotion() : Controleur.selectLikePromotion(filtre);

        Object matrice[][] = new Object[lesPromotions.size()][5];

        int i = 0;
        for (Promotion unePromotion : lesPromotions) {
            matrice[i][0] = unePromotion.getIdPromotion();
            matrice[i][1] = unePromotion.getNomPromotion();
            matrice[i][2] = unePromotion.getDateDebutPromotion();
            matrice[i][3] = unePromotion.getDateFinPromotion();
            matrice[i][4] = unePromotion.getReductionPromotion();
            i++;
        }
        return matrice;
    }

    public Object[][] obtenirDonneesLivresPromotions(String filtre) {
        // Récupère les données pour le tableau des livres et promotions
        ArrayList<Livre> lesLivres = filtre.isEmpty() ?
                Controleur.selectLivre() : Controleur.selectLikeLivre(filtre);

        Object matrice[][] = new Object[lesLivres.size()][3];

        int i = 0;
        for (Livre unLivre : lesLivres) {
            matrice[i][0] = unLivre.getIdLivre();
            matrice[i][1] = unLivre.getNomLivre();
            int idPromotion = unLivre.getIdPromotion();

            if (idPromotion > 0) {
                Promotion promotion = Controleur.selectPromotionById(idPromotion);
                matrice[i][2] = promotion != null ? promotion.getNomPromotion() : "Non définie";
            } else {
                matrice[i][2] = "Non définie";
            }

            i++;
        }
        return matrice;
    }

    private void majNbPromotions() {
        lbNbPromotions.setText("Nombre de promotions : " + tableauPromotions.getRowCount());
    }

    private void majNbLivresPromotions() {
        lbNbLivresPromotions.setText("Nombre de livres : " + tableauLivresPromotions.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnulerPromotion) {
            viderChampsPromotion();
        } else if (e.getSource() == this.btValiderPromotion) {
            validerPromotion();
        } else if (e.getSource() == this.btSupprimerPromotion) {
            supprimerPromotion();
        } else if (e.getSource() == this.btAnnulerLivrePromotion) {
            viderChampsLivrePromotion();
        } else if (e.getSource() == this.btValiderLivrePromotion) {
            validerLivrePromotion();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauPromotions.setDonnees(this.obtenirDonneesPromotions(filtre));
            this.majNbPromotions();
        } else if (e.getSource() == this.btFiltrerLivres) {
            String filtre = this.txtFiltreLivres.getText();
            this.tableauLivresPromotions.setDonnees(this.obtenirDonneesLivresPromotions(filtre));
            this.majNbLivresPromotions();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == txtNomPromotion || e.getSource() == txtDateDebutPromotion ||
                    e.getSource() == txtDateFinPromotion || e.getSource() == txtReductionPromotion) {
                validerPromotion();
            } else if (e.getSource() == txtNomLivre || e.getSource() == txtNomPromotionLivre) {
                validerLivrePromotion();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}