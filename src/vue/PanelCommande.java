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
import controleur.Commande;
import controleur.Controleur;
import controleur.Tableau;
import controleur.Livre;
import controleur.LigneCommande;

public class PanelCommande extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();
    private JTextField txtDateCommande = new JTextField();
    private JTextField txtStatutCommande = new JTextField();
    private JTextField txtDateLivraisonCommande = new JTextField();
    private JTextField txtIdUser = new JTextField();
    private JTextField txtNomLivre = new JTextField();
    private JTextField txtQuantiteLivre = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");

    private JTable tableCommandes;
    private Tableau tableauCommandes;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");

    private JLabel lbNbCommandes = new JLabel();

    public PanelCommande(int idUser) {
        super("Gestion des Commandes");

        // Initialisation de l'interface
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 100, 300, 300);
        this.panelForm.setLayout(new GridLayout(11, 2));

        this.txtIdUser.setText(String.valueOf(idUser));

        // Ajout des composants dans l'ordre demandé
        this.panelForm.add(new JLabel("Date Commande:"));
        this.panelForm.add(this.txtDateCommande);
        this.panelForm.add(new JLabel("Statut Commande:"));
        this.panelForm.add(this.txtStatutCommande);
        this.panelForm.add(new JLabel("Date Livraison Commande:"));
        this.panelForm.add(this.txtDateLivraisonCommande);
        this.panelForm.add(new JLabel("ID Utilisateur:"));
        this.panelForm.add(this.txtIdUser);
        this.panelForm.add(new JLabel("Nom Livre:"));
        this.panelForm.add(this.txtNomLivre);
        this.panelForm.add(new JLabel("Quantité Livre:"));
        this.panelForm.add(this.txtQuantiteLivre);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btSupprimer);

        this.add(this.panelForm);

        // Initialisation des listeners
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);

        this.txtDateCommande.addKeyListener(this);
        this.txtStatutCommande.addKeyListener(this);
        this.txtDateLivraisonCommande.addKeyListener(this);
        this.txtIdUser.addKeyListener(this);
        this.txtNomLivre.addKeyListener(this);
        this.txtQuantiteLivre.addKeyListener(this);

        // Initialisation du tableau avec les nouvelles colonnes
        String entetes[] = {"ID Commande", "Date Commande", "Statut Commande",
                "Date Livraison Commande", "ID User", "ID Ligne Commande",
                "Nom Livre", "Quantité Livre"};
        this.tableauCommandes = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableCommandes = new JTable(this.tableauCommandes);
        JScrollPane uneScroll = new JScrollPane(this.tableCommandes);
        uneScroll.setBounds(360, 100, 600, 250); // Largeur augmentée pour les nouvelles colonnes
        this.add(uneScroll);

        // Initialisation du filtre
        this.panelFiltre.setBackground(Color.cyan);
        this.panelFiltre.setBounds(370, 60, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        this.lbNbCommandes.setBounds(450, 380, 400, 20);
        this.add(this.lbNbCommandes);
        this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());

        // Gestion de la sélection dans le tableau
        this.tableCommandes.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                afficherDetailsCommandeSelectionnee();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }

    private void afficherDetailsCommandeSelectionnee() {
        int numLigne = tableCommandes.getSelectedRow();
        if (numLigne >= 0) {
            Commande uneCommande = Controleur.selectWhereCommande((int)tableauCommandes.getValueAt(numLigne, 0));
            if (uneCommande != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                txtDateCommande.setText(sdf.format(uneCommande.getDateCommande()));
                txtStatutCommande.setText(uneCommande.getStatutCommande());
                txtDateLivraisonCommande.setText(uneCommande.getDateLivraisonCommande() != null ?
                        sdf.format(uneCommande.getDateLivraisonCommande()) : "");
                txtIdUser.setText(String.valueOf(uneCommande.getIdUser()));

                // Récupération des infos du livre
                int idLigneCommande = (int)tableauCommandes.getValueAt(numLigne, 5);
                LigneCommande uneLigneCommande = Controleur.selectWhereLigneCommande(idLigneCommande);
                if (uneLigneCommande != null) {
                    Livre unLivre = Controleur.selectWhereLivre(uneLigneCommande.getIdLivre());
                    txtNomLivre.setText(unLivre.getNomLivre());
                    txtQuantiteLivre.setText(String.valueOf(uneLigneCommande.getQuantiteLigneCommande()));
                }

                btSupprimer.setVisible(true);
                btValider.setText("Modifier");
            }
        }
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Commande> lesCommandes = filtre.isEmpty() ?
                Controleur.selectCommande() : Controleur.selectLikeCommande(filtre);

        // Compter le nombre total de lignes (commandes + lignes de commande)
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

    public void viderChamps() {
        this.txtDateCommande.setText("");
        this.txtStatutCommande.setText("");
        this.txtDateLivraisonCommande.setText("");
        this.txtIdUser.setText("");
        this.txtNomLivre.setText("");
        this.txtQuantiteLivre.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    private void insererCommande() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Parsing des dates
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCommande = sdf.parse(txtDateCommande.getText());
            Date dateLivraison = txtDateLivraisonCommande.getText().isEmpty() ?
                    null : sdf.parse(txtDateLivraisonCommande.getText());

            // Vérification du livre
            int idLivre = Controleur.selectIdLivre(txtNomLivre.getText());
            if (idLivre == 0) {
                JOptionPane.showMessageDialog(this, "Livre non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérification de la quantité
            int quantite = Integer.parseInt(txtQuantiteLivre.getText());
            if (quantite <= 0) {
                JOptionPane.showMessageDialog(this, "La quantité doit être positive", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Création de la commande
            Commande uneCommande = new Commande(
                    dateCommande,
                    txtStatutCommande.getText(),
                    Integer.parseInt(txtIdUser.getText())
            );
            uneCommande.setDateLivraisonCommande(dateLivraison);

            // Création et ajout de la ligne de commande
            LigneCommande uneLigneCommande = new LigneCommande(
                    0, // ID sera généré par la base de données
                    uneCommande.getIdCommande(),
                    idLivre,
                    quantite
            );
            uneCommande.ajouterLigneCommande(uneLigneCommande);

            // Insertion en base de données
            Controleur.insertCommande(uneCommande);

            // Mise à jour de l'interface
            tableauCommandes.setDonnees(obtenirDonnees(""));
            majNbCommandes();
            viderChamps();
            JOptionPane.showMessageDialog(this, "Commande créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide (yyyy-mm-dd)", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La quantité doit être un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierCommande() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int row = tableCommandes.getSelectedRow();
        if (row >= 0) {
            try {
                // Récupération de l'ID de la commande existante
                int idCommande = (int) tableauCommandes.getValueAt(row, 0);
                int idLigneCommande = (int) tableauCommandes.getValueAt(row, 5);

                // Parsing des dates
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateCommande = sdf.parse(txtDateCommande.getText());
                Date dateLivraison = txtDateLivraisonCommande.getText().isEmpty() ?
                        null : sdf.parse(txtDateLivraisonCommande.getText());

                // Vérification du livre
                int idLivre = Controleur.selectIdLivre(txtNomLivre.getText());
                if (idLivre == 0) {
                    JOptionPane.showMessageDialog(this, "Livre non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérification de la quantité
                int quantite = Integer.parseInt(txtQuantiteLivre.getText());
                if (quantite <= 0) {
                    JOptionPane.showMessageDialog(this, "La quantité doit être positive", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Création de la commande mise à jour
                Commande uneCommande = new Commande(
                        idCommande,
                        dateCommande,
                        txtStatutCommande.getText(),
                        dateLivraison,
                        Integer.parseInt(txtIdUser.getText())
                );

                // Création de la ligne de commande mise à jour
                LigneCommande ligneModifiee = new LigneCommande(
                        idLigneCommande,
                        idCommande,
                        idLivre,
                        quantite
                );
                uneCommande.ajouterLigneCommande(ligneModifiee);

                // Mise à jour en base de données
                Controleur.updateCommande(uneCommande);

                // Mise à jour de l'interface
                tableauCommandes.setDonnees(obtenirDonnees(""));
                viderChamps();
                JOptionPane.showMessageDialog(this, "Commande modifiée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Format de date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La quantité doit être un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerCommande() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int row = tableCommandes.getSelectedRow();
        if (row >= 0) {
            int idCommande = (int) tableauCommandes.getValueAt(row, 0);

            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Confirmez-vous la suppression de cette commande ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                // Suppression en base de données
                Controleur.deleteCommande(idCommande);

                // Mise à jour de l'interface
                tableauCommandes.setDonnees(obtenirDonnees(""));
                majNbCommandes();
                viderChamps();
                JOptionPane.showMessageDialog(
                        this,
                        "Commande supprimée avec succès",
                        "Suppression réussie",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    private void majNbCommandes() {
        lbNbCommandes.setText("Nombre de commandes : " + tableauCommandes.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauCommandes.setDonnees(this.obtenirDonnees(filtre));
            this.majNbCommandes();
        } else if (e.getSource() == this.btValider) {
            if (this.btValider.getText().equals("Valider")) {
                insererCommande();
            } else {
                modifierCommande();
            }
        } else if (e.getSource() == this.btSupprimer) {
            supprimerCommande();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.btValider.getText().equals("Valider")) {
                insererCommande();
            } else {
                modifierCommande();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}