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

    private JTextField txtIdCommande = new JTextField();
    private JTextField txtDateCommande = new JTextField();
    private JTextField txtStatutCommande = new JTextField();
    private JTextField txtDateLivraisonCommande = new JTextField();
    private JTextField txtIdUser = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Annuler");

    private JTable tableCommandes;
    private Tableau tableauCommandes;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");

    private JLabel lbNbCommandes = new JLabel();

    public PanelCommande(int idUser) {
        super("Gestion des Commandes");

        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 100, 300, 250);
        this.panelForm.setLayout(new GridLayout(10, 2));
        this.panelForm.add(new JLabel("Id Commande :"));
        this.panelForm.add(this.txtIdCommande);
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

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);

        this.txtIdCommande.addKeyListener(this);
        this.txtDateCommande.addKeyListener(this);
        this.txtStatutCommande.addKeyListener(this);
        this.txtDateLivraisonCommande.addKeyListener(this);
        this.txtIdUser.addKeyListener(this);

        String entetes[] = {"Id", "Date Commande", "Statut", "Date Livraison", "Id User"};
        this.tableauCommandes = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableCommandes = new JTable(this.tableauCommandes);
        JScrollPane uneScroll = new JScrollPane(this.tableCommandes);
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

        this.lbNbCommandes.setBounds(450, 380, 400, 20);
        this.add(this.lbNbCommandes);
        this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());

        // Affiche les données du formulaire à gauche
        this.tableCommandes.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableCommandes.getSelectedRow();
                if (numLigne >= 0) {
                    txtIdCommande.setText(tableauCommandes.getValueAt(numLigne, 0).toString());
                    txtDateCommande.setText(tableauCommandes.getValueAt(numLigne, 1).toString());
                    txtStatutCommande.setText(tableauCommandes.getValueAt(numLigne, 2).toString());
                    txtDateLivraisonCommande.setText(tableauCommandes.getValueAt(numLigne, 3).toString());
                    txtIdUser.setText(tableauCommandes.getValueAt(numLigne, 4).toString());

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
        ArrayList<Commande> lesCommandes;
        if (filtre.equals("")) {
            lesCommandes = Controleur.selectCommande();
        } else {
            lesCommandes = Controleur.selectLikeCommande(filtre);
        }
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
        this.txtIdCommande.setText("");
        this.txtDateCommande.setText("");
        this.txtStatutCommande.setText("");
        this.txtDateLivraisonCommande.setText("");
        this.txtIdUser.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauCommandes.setDonnees(this.obtenirDonnees(filtre));
        } else if (e.getSource() == this.btValider && this.btValider.getText().equals("Valider")) {
            this.traitement();
            this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());
        } else if (e.getSource() == this.btValider && this.btValider.getText().equals("Modifier")) {
            int numLigne = tableCommandes.getSelectedRow();
            if (numLigne >= 0) {
                this.modifierCommande();
            }
        } else if (e.getSource() == this.btSupprimer) {
            int numLigne = tableCommandes.getSelectedRow();
            if (numLigne >= 0) {
                int idCommande = Integer.parseInt(tableauCommandes.getValueAt(numLigne, 0).toString());
                Controleur.deleteCommande(idCommande);
                this.tableauCommandes.setDonnees(this.obtenirDonnees(""));
                this.lbNbCommandes.setText("Nombre de commandes : " + this.tableauCommandes.getRowCount());
                JOptionPane.showMessageDialog(this, "Suppression réussie de la commande.",
                        "Suppression Commande", JOptionPane.INFORMATION_MESSAGE);
                this.viderChamps();
            }
        }
    }

    private void traitement() {
        try {
            Date dateCommande = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateCommande.getText());
            Date dateLivraisonCommande = null;
            if (!txtDateLivraisonCommande.getText().isEmpty()) {
                dateLivraisonCommande = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateLivraisonCommande.getText());
            }
            int idUser = Integer.parseInt(txtIdUser.getText());

            Commande uneCommande = new Commande(dateCommande, txtStatutCommande.getText(), dateLivraisonCommande, idUser);
            Controleur.insertCommande(uneCommande);

            // Convertir les dates en chaînes de caractères pour l'affichage ou la journalisation
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCommandeStr = dateFormat.format(dateCommande);
            String dateLivraisonCommandeStr = (dateLivraisonCommande != null) ? dateFormat.format(dateLivraisonCommande) : "N/A";

            // Afficher un message de confirmation
            JOptionPane.showMessageDialog(this, "Insertion réussie de la commande.\nDate Commande: " + dateCommandeStr +
                    "\nDate Livraison: " + dateLivraisonCommandeStr, "Insertion Commande", JOptionPane.INFORMATION_MESSAGE);

            // Mettre à jour le tableau des commandes
            this.tableauCommandes.setDonnees(this.obtenirDonnees(""));

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID utilisateur invalide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        this.viderChamps();
    }


    private void modifierCommande() {
        try {
            int idCommande = Integer.parseInt(txtIdCommande.getText());
            Date dateCommande = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateCommande.getText());
            Date dateLivraisonCommande = txtDateLivraisonCommande.getText().isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(txtDateLivraisonCommande.getText());
            int idUser = Integer.parseInt(txtIdUser.getText());

            Commande uneCommande = new Commande(idCommande, dateCommande, txtStatutCommande.getText(), dateLivraisonCommande, idUser);
            Controleur.updateCommande(uneCommande);
            this.tableauCommandes.setDonnees(this.obtenirDonnees(""));
            JOptionPane.showMessageDialog(this, "Modification réussie de la commande.",
                    "Modification Commande", JOptionPane.INFORMATION_MESSAGE);
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
                this.modifierCommande();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}