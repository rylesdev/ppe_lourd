package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import controleur.Livre;
import controleur.Commande;
import controleur.Controleur;
import controleur.Tableau;

public class PanelLivre extends PanelPrincipal implements ActionListener, MouseListener {
    private JTable tableLivres;
    private Tableau tableauLivres;
    private JButton btAjouterCommande = new JButton("Ajouter à la commande");

    public PanelLivre() {
        super("Sélection des Livres");

        // Installation de la JTable pour afficher les livres
        String entetes[] = {"Id", "Titre", "Auteur", "Catégorie", "Prix"};
        this.tableauLivres = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableLivres = new JTable(this.tableauLivres);
        JScrollPane uneScroll = new JScrollPane(this.tableLivres);
        uneScroll.setBounds(50, 50, 700, 300);
        this.add(uneScroll);

        // Installation du bouton "Ajouter à la commande"
        this.btAjouterCommande.setBounds(300, 370, 200, 30);
        this.add(this.btAjouterCommande);
        this.btAjouterCommande.addActionListener(this);

        // Rendre la JTable écoutable sur le clic de la souris
        this.tableLivres.addMouseListener(this);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Livre> lesLivres;
        if (filtre.equals("")) {
            lesLivres = Controleur.selectLivre();
        } else {
            lesLivres = Controleur.selectLikeLivre(filtre);
        }
        Object matrice[][] = new Object[lesLivres.size()][5];
        int i = 0;
        for (Livre unLivre : lesLivres) {
            matrice[i][0] = unLivre.getIdLivre();
            matrice[i][1] = unLivre.getNomLivre();
            matrice[i][2] = unLivre.getAuteurLivre();
            matrice[i][3] = unLivre.getNomCategorie(); // Utilisez le nouvel attribut
            matrice[i][4] = unLivre.getPrixLivre();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAjouterCommande) {
            int[] lignesSelectionnees = this.tableLivres.getSelectedRows();
            if (lignesSelectionnees.length == 0) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins un livre.",
                        "Aucun livre sélectionné", JOptionPane.WARNING_MESSAGE);
            } else {
                int idUser = 1; // Remplacez par l'ID de l'utilisateur connecté

                for (int ligne : lignesSelectionnees) {
                    int idLivre = (int) this.tableauLivres.getValueAt(ligne, 0);
                    Date dateCommande = new Date(System.currentTimeMillis());
                    String statutCommande = "en attente";
                    Date dateLivraisonCommande = null;

                    Commande uneCommande = new Commande(0, dateCommande, statutCommande, dateLivraisonCommande, idUser);
                    Controleur.insertCommande(uneCommande);
                }

                // Afficher un message de confirmation
                JOptionPane.showMessageDialog(this, "Les livres sélectionnés ont été ajoutés à la commande.",
                        "Commande créée", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Vous pouvez ajouter ici le comportement souhaité lors du clic sur la table
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
