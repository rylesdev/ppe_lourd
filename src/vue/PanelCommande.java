package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import controleur.Commande;
import controleur.Controleur;
import controleur.Tableau;
import controleur.Livre;
import controleur.LigneCommande;

public class PanelCommande extends PanelPrincipal implements ActionListener, KeyListener {
    private String niveauAdmin;
    private JPanel panelLigneForm = new JPanel();
    private ArrayList<Livre> tousLesLivres;
    private JComboBox<String> comboLivre;
    private JTextField txtQuantiteLivre = new JTextField();
    private JButton btAnnulerLigne = new JButton("Annuler");
    private JButton btValiderLigne = new JButton("Valider");
    private JButton btSupprimerLigne = new JButton("Supprimer Dernière Ligne");
    private JLabel lbNbLignes = new JLabel("Nombre de lignes: 0");

    private JPanel panelCommandeForm = new JPanel();
    private JTextField txtDateCommande = new JTextField();
    private JTextField txtStatutCommande = new JTextField();
    private JTextField txtDateLivraisonCommande = new JTextField();
    private JTextField txtIdUser = new JTextField();
    private JButton btAnnulerCommande = new JButton("Annuler");
    private JButton btValiderCommande = new JButton("Valider Commande");
    private JButton btSupprimerCommande = new JButton("Supprimer");

    private JTable tableCommandes;
    private Tableau tableauCommandes;
    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");
    private JLabel lbNbCommandes = new JLabel();

    private ArrayList<LigneCommande> lignesTemporaires = new ArrayList<>();
    private int idCommandeModification = 0;

    private Color couleurFormulaire = new Color(100, 140, 180);

    public PanelCommande(int idUser) {
        super("Gestion des Commandes");

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(idUser);
        initLigneCommande();
        initCommande(idUser);
        initTableau();
        initFiltres();

        setupListeners();

        btSupprimerCommande.setVisible(false);
    }

    private void initLigneCommande() {
        this.panelLigneForm.setBackground(couleurFormulaire);
        this.panelLigneForm.setBounds(30, 100, 350, 150);
        this.panelLigneForm.setLayout(new GridLayout(6, 2));

        tousLesLivres = Controleur.selectLivre();

        String[] nomsLivres = new String[tousLesLivres.size()];
        for (int i = 0; i < tousLesLivres.size(); i++) {
            nomsLivres[i] = tousLesLivres.get(i).getNomLivre();
        }

        comboLivre = new JComboBox<>(nomsLivres);

        this.panelLigneForm.add(new JLabel("Livre:"));
        this.panelLigneForm.add(this.comboLivre);
        this.panelLigneForm.add(new JLabel("Quantité:"));
        this.panelLigneForm.add(this.txtQuantiteLivre);
        this.panelLigneForm.add(this.btAnnulerLigne);
        this.panelLigneForm.add(this.btValiderLigne);
        this.panelLigneForm.add(this.btSupprimerLigne);
        this.panelLigneForm.add(this.lbNbLignes);

        this.add(this.panelLigneForm);
    }

    private void initCommande(int idUser) {
        this.panelCommandeForm.setBackground(couleurFormulaire);
        this.panelCommandeForm.setBounds(30, 260, 350, 200);
        this.panelCommandeForm.setLayout(new GridLayout(7, 2));

        this.txtIdUser.setText(String.valueOf(idUser));

        this.panelCommandeForm.add(new JLabel("Date (yyyy-mm-dd):"));
        this.panelCommandeForm.add(this.txtDateCommande);
        this.panelCommandeForm.add(new JLabel("Statut:"));
        this.panelCommandeForm.add(this.txtStatutCommande);
        this.panelCommandeForm.add(new JLabel("Date Livraison:"));
        this.panelCommandeForm.add(this.txtDateLivraisonCommande);
        this.panelCommandeForm.add(new JLabel("ID Utilisateur:"));
        this.panelCommandeForm.add(this.txtIdUser);
        this.panelCommandeForm.add(this.btAnnulerCommande);
        this.panelCommandeForm.add(this.btValiderCommande);
        this.panelCommandeForm.add(this.btSupprimerCommande);

        this.add(this.panelCommandeForm);
    }

    private void initTableau() {
        String entetes[] = {"ID Commande", "Date", "Statut", "Livraison", "ID User",
                "ID Ligne", "Nom Livre", "Quantité"};
        this.tableauCommandes = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableCommandes = new JTable(this.tableauCommandes);
        JScrollPane uneScroll = new JScrollPane(this.tableCommandes);
        uneScroll.setBounds(400, 100, 480, 250);
        this.add(uneScroll);

        this.tableCommandes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afficherDetailsCommandeSelectionnee();
            }
        });
    }

    private void initFiltres() {
        this.panelFiltre.setBackground(couleurFormulaire);
        this.panelFiltre.setBounds(410, 60, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);

        this.lbNbCommandes.setBounds(450, 380, 400, 20);
        this.add(this.lbNbCommandes);
        this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());
    }

    private void setupListeners() {
        this.btAnnulerLigne.addActionListener(this);
        this.btValiderLigne.addActionListener(this);
        this.btSupprimerLigne.addActionListener(this);

        this.btAnnulerCommande.addActionListener(this);
        this.btValiderCommande.addActionListener(this);
        this.btSupprimerCommande.addActionListener(this);

        this.btFiltrer.addActionListener(this);

        this.txtQuantiteLivre.addKeyListener(this);
        this.txtDateCommande.addKeyListener(this);
        this.txtStatutCommande.addKeyListener(this);
        this.txtDateLivraisonCommande.addKeyListener(this);
    }

    private void validerLigneCommande() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des lignes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int indexSelectionne = comboLivre.getSelectedIndex();
            if (indexSelectionne < 0) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Livre livreSelectionne = tousLesLivres.get(indexSelectionne);
            int quantite = Integer.parseInt(txtQuantiteLivre.getText());

            if (quantite <= 0) {
                JOptionPane.showMessageDialog(this, "La quantité doit être positive", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LigneCommande ligne = new LigneCommande(0, livreSelectionne.getIdLivre(), quantite);
            lignesTemporaires.add(ligne);

            lbNbLignes.setText("Nombre de lignes: " + lignesTemporaires.size());
            txtQuantiteLivre.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerDerniereLigne() {
        if (!lignesTemporaires.isEmpty()) {
            lignesTemporaires.remove(lignesTemporaires.size() - 1);
            lbNbLignes.setText("Nombre de lignes: " + lignesTemporaires.size());
        } else {
            JOptionPane.showMessageDialog(this, "Aucune ligne à supprimer", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void validerCommande() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent créer des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (lignesTemporaires.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins une ligne de commande", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCommande = sdf.parse(txtDateCommande.getText());
            Date dateLivraison = sdf.parse(txtDateLivraisonCommande.getText());

            int idUser = Integer.parseInt(txtIdUser.getText());

            Commande commande = new Commande(dateCommande, txtStatutCommande.getText(), idUser);
            commande.setDateLivraisonCommande(dateLivraison);

            for (LigneCommande ligne : lignesTemporaires) {
                commande.ajouterLigneCommande(ligne);
            }

            int resultInsertCommande = Controleur.insertCommande(commande);

            tableauCommandes.setDonnees(obtenirDonnees(""));
            majNbCommandes();
            viderChampsCommande();
            lignesTemporaires.clear();
            lbNbLignes.setText("Nombre de lignes: 0");

            if (resultInsertCommande == 2) {
                JOptionPane.showMessageDialog(this, "Erreur: La quantite totale depasse le nombre exemplaires disponibles pour ce livre", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (resultInsertCommande == 3) {
                JOptionPane.showMessageDialog(this, "Erreur: Réessayez", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (resultInsertCommande == 1) {
                JOptionPane.showMessageDialog(this, "Commande créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }


        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide (yyyy-mm-dd)", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID utilisateur invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierCommande() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCommande = sdf.parse(txtDateCommande.getText());
            Date dateLivraison = txtDateLivraisonCommande.getText().isEmpty() ?
                    null : sdf.parse(txtDateLivraisonCommande.getText());

            int idUser = Integer.parseInt(txtIdUser.getText());

            Commande commande = new Commande(
                    idCommandeModification,
                    dateCommande,
                    txtStatutCommande.getText(),
                    dateLivraison,
                    idUser
            );

            Controleur.updateCommande(commande);

            tableauCommandes.setDonnees(obtenirDonnees(""));
            majNbCommandes();
            viderChampsCommande();

            JOptionPane.showMessageDialog(this, "Commande modifiée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide (yyyy-mm-dd)", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID utilisateur invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerCommande() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Confirmez-vous la suppression de cette commande ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            Controleur.deleteCommande(idCommandeModification);

            tableauCommandes.setDonnees(obtenirDonnees(""));
            majNbCommandes();
            viderChampsCommande();

            JOptionPane.showMessageDialog(this, "Commande supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void afficherDetailsCommandeSelectionnee() {
        int row = tableCommandes.getSelectedRow();
        if (row >= 0) {
            int idCommande = (int) tableauCommandes.getValueAt(row, 0);
            Commande commande = Controleur.selectWhereCommande(idCommande);

            if (commande != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                txtDateCommande.setText(sdf.format(commande.getDateCommande()));
                txtStatutCommande.setText(commande.getStatutCommande());
                txtDateLivraisonCommande.setText(commande.getDateLivraisonCommande() != null ?
                        sdf.format(commande.getDateLivraisonCommande()) : "");
                txtIdUser.setText(String.valueOf(commande.getIdUser()));

                idCommandeModification = idCommande;
                btValiderCommande.setText("Modifier");
                btSupprimerCommande.setVisible(true);
            }
        }
    }

    private void viderChampsLigne() {
        this.txtQuantiteLivre.setText("");
    }

    private void viderChampsCommande() {
        this.txtDateCommande.setText("");
        this.txtStatutCommande.setText("");
        this.txtDateLivraisonCommande.setText("");
        btValiderCommande.setText("Valider Commande");
        btSupprimerCommande.setVisible(false);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Commande> lesCommandes = filtre.isEmpty() ?
                Controleur.selectCommande() : Controleur.selectLikeCommande(filtre);

        int totalLignes = 0;
        for (Commande uneCommande : lesCommandes) {
            totalLignes += uneCommande.getLesLignesCommande().size();
        }

        Object matrice[][] = new Object[totalLignes][8];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int i = 0;
        for (Commande uneCommande : lesCommandes) {
            for (LigneCommande uneLigneCommande : uneCommande.getLesLignesCommande()) {
                Livre unLivre = Controleur.selectWhereLivre(uneLigneCommande.getIdLivre());

                matrice[i][0] = uneCommande.getIdCommande();
                matrice[i][1] = sdf.format(uneCommande.getDateCommande());
                matrice[i][2] = uneCommande.getStatutCommande();
                matrice[i][3] = uneCommande.getDateLivraisonCommande() != null ?
                        sdf.format(uneCommande.getDateLivraisonCommande()) : "";
                matrice[i][4] = uneCommande.getIdUser();
                matrice[i][5] = uneLigneCommande.getIdLigneCommande();
                matrice[i][6] = unLivre != null ? unLivre.getNomLivre() : "Inconnu";
                matrice[i][7] = uneLigneCommande.getQuantiteLigneCommande();
                i++;
            }
        }
        return matrice;
    }

    private void majNbCommandes() {
        lbNbCommandes.setText("Nombre de commandes : " + tableauCommandes.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnulerLigne) {
            viderChampsLigne();
        } else if (e.getSource() == this.btValiderLigne) {
            validerLigneCommande();
        } else if (e.getSource() == this.btSupprimerLigne) {
            supprimerDerniereLigne();
        } else if (e.getSource() == this.btAnnulerCommande) {
            viderChampsCommande();
        } else if (e.getSource() == this.btValiderCommande) {
            if (this.btValiderCommande.getText().equals("Valider Commande")) {
                validerCommande();
            } else {
                modifierCommande();
            }
        } else if (e.getSource() == this.btSupprimerCommande) {
            supprimerCommande();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauCommandes.setDonnees(this.obtenirDonnees(filtre));
            this.majNbCommandes();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == txtQuantiteLivre) {
                validerLigneCommande();
            } else if (e.getSource() == txtDateCommande || e.getSource() == txtStatutCommande
                    || e.getSource() == txtDateLivraisonCommande) {
                if (this.btValiderCommande.getText().equals("Modifier")) {
                    modifierCommande();
                } else {
                    validerCommande();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}