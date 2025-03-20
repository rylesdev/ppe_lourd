package vue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controleur.Commande;
import controleur.Livre;
import controleur.Controleur;
import controleur.Tableau;

public class PanelCommande extends PanelPrincipal implements ActionListener {
    private JTable tableCommandes;
    private Tableau tableauCommandes;

    private JButton btAnnulerCommande = new JButton("Annuler la commande");

    public PanelCommande(int idUser) {
        super("Gestion des Commandes");

        String entetes[] = {"Id Commande", "Date Commande", "Statut", "Date Livraison", "Id User"};
        this.tableauCommandes = new Tableau(this.obtenirDonnees(idUser), entetes);
        this.tableCommandes = new JTable(this.tableauCommandes);
        JScrollPane uneScroll = new JScrollPane(this.tableCommandes);
        uneScroll.setBounds(50, 50, 700, 300);
        this.add(uneScroll);

        this.btAnnulerCommande.setBounds(300, 370, 200, 30);
        this.add(this.btAnnulerCommande);
        this.btAnnulerCommande.addActionListener(this);

        this.tableCommandes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int ligne = tableCommandes.getSelectedRow();
                if (ligne >= 0) {
                    btAnnulerCommande.setEnabled(true);
                }
            }
        });
    }

    public Object[][] obtenirDonnees(int idUser) {
        ArrayList<Commande> lesCommandes = Controleur.selectCommande(idUser);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnulerCommande) {
            int ligne = this.tableCommandes.getSelectedRow();
            if (ligne >= 0) {
                int idCommande = (int) this.tableauCommandes.getValueAt(ligne, 0);
                int choix = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment annuler cette commande ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
                    Controleur.deleteCommande(idCommande);
                    this.tableauCommandes.setDonnees(this.obtenirDonnees(idCommande));
                    JOptionPane.showMessageDialog(this, "Commande annulée avec succès.",
                            "Commande annulée", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une commande à annuler.",
                        "Aucune commande sélectionnée", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}