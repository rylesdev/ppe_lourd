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

public class PanelCommande extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();

    private JTextField txtDateCommande = new JTextField();
    private JTextField txtStatutCommande = new JTextField();
    private JTextField txtDateLivraisonCommande = new JTextField();
    private JTextField txtIdUser = new JTextField();

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
        this.panelForm.setBounds(30, 100, 300, 250);
        this.panelForm.setLayout(new GridLayout(10, 2));

        this.panelForm.add(new JLabel("Date Commande :"));
        this.panelForm.add(this.txtDateCommande);
        this.panelForm.add(new JLabel("Statut :"));
        this.panelForm.add(this.txtStatutCommande);
        this.panelForm.add(new JLabel("Date Livraison :"));
        this.panelForm.add(this.txtDateLivraisonCommande);
        this.panelForm.add(new JLabel("Id User :"));
        this.panelForm.add(this.txtIdUser);
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

        // Initialisation du tableau
        String entetes[] = {"Id", "Date Commande", "Statut", "Date Livraison", "Id User"};
        this.tableauCommandes = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableCommandes = new JTable(this.tableauCommandes);
        JScrollPane uneScroll = new JScrollPane(this.tableCommandes);
        uneScroll.setBounds(360, 100, 480, 250);
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
            txtDateCommande.setText(tableauCommandes.getValueAt(numLigne, 1).toString());
            txtStatutCommande.setText(tableauCommandes.getValueAt(numLigne, 2).toString());
            txtDateLivraisonCommande.setText(tableauCommandes.getValueAt(numLigne, 3).toString());
            txtIdUser.setText(tableauCommandes.getValueAt(numLigne, 4).toString());

            btSupprimer.setVisible(true);
            btValider.setText("Modifier");
        }
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Commande> lesCommandes = filtre.isEmpty() ?
                Controleur.selectCommande() : Controleur.selectLikeCommande(filtre);

        Object matrice[][] = new Object[lesCommandes.size()][5];
        int i = 0;
        for (Commande uneCommande : lesCommandes) {
            matrice[i][0] = uneCommande.getIdCommande();
            matrice[i][1] = uneCommande.getDateCommande();
            matrice[i][2] = uneCommande.getStatutCommande();
            matrice[i][3] = uneCommande.getDateLivraisonCommande();
            matrice[i][4] = uneCommande.getIdUser();
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        this.txtDateCommande.setText("");
        this.txtStatutCommande.setText("");
        this.txtDateLivraisonCommande.setText("");
        this.txtIdUser.setText("");
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
            Date dateCommande = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateCommande.getText());
            Date dateLivraisonCommande = txtDateLivraisonCommande.getText().isEmpty() ?
                    null : new SimpleDateFormat("yyyy-MM-dd").parse(txtDateLivraisonCommande.getText());
            int idUser = Integer.parseInt(txtIdUser.getText());

            if (txtStatutCommande.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le statut est obligatoire",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Commande uneCommande = new Commande(dateCommande, txtStatutCommande.getText(),
                    dateLivraisonCommande, idUser);
            Controleur.insertCommande(uneCommande);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCommandeStr = dateFormat.format(dateCommande);
            String dateLivraisonStr = dateLivraisonCommande != null ?
                    dateFormat.format(dateLivraisonCommande) : "Non définie";

            JOptionPane.showMessageDialog(this,
                    "Commande créée avec succès !\n" +
                            "Date commande: " + dateCommandeStr + "\n" +
                            "Date livraison: " + dateLivraisonStr,
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

            this.tableauCommandes.setDonnees(this.obtenirDonnees(""));
            this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());
            this.viderChamps();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide (yyyy-MM-dd)",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID utilisateur invalide",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierCommande() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des commandes",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableCommandes.getSelectedRow();
        if (numLigne >= 0) {
            try {
                int idCommande = Integer.parseInt(tableauCommandes.getValueAt(numLigne, 0).toString());
                Date dateCommande = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateCommande.getText());
                Date dateLivraisonCommande = txtDateLivraisonCommande.getText().isEmpty() ?
                        null : new SimpleDateFormat("yyyy-MM-dd").parse(txtDateLivraisonCommande.getText());
                int idUser = Integer.parseInt(txtIdUser.getText());

                if (txtStatutCommande.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Le statut est obligatoire",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Commande uneCommande = new Commande(idCommande, dateCommande,
                        txtStatutCommande.getText(), dateLivraisonCommande, idUser);
                Controleur.updateCommande(uneCommande);

                JOptionPane.showMessageDialog(this, "Modification réussie de la commande",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);

                this.tableauCommandes.setDonnees(this.obtenirDonnees(""));
                this.viderChamps();

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Format de date invalide (yyyy-MM-dd)",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID invalide",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
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

        int numLigne = tableCommandes.getSelectedRow();
        if (numLigne >= 0) {
            int idCommande = Integer.parseInt(tableauCommandes.getValueAt(numLigne, 0).toString());
            int reponse = JOptionPane.showConfirmDialog(this,
                    "Confirmez-vous la suppression de cette commande ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (reponse == JOptionPane.YES_OPTION) {
                Controleur.deleteCommande(idCommande);
                this.tableauCommandes.setDonnees(this.obtenirDonnees(""));
                this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());
                JOptionPane.showMessageDialog(this, "Suppression réussie de la commande",
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
            this.tableauCommandes.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());
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