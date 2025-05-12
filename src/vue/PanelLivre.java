package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import controleur.Livre;
import controleur.Controleur;
import controleur.Tableau;

public class PanelLivre extends PanelPrincipal implements ActionListener, KeyListener {
    private String niveauAdmin;
    private JPanel panelForm = new JPanel();
    private JTextField txtNom = new JTextField();
    private JTextField txtAuteur = new JTextField();
    private JTextField txtImage = new JTextField();
    private JTextField txtExemplaire = new JTextField();
    private JTextField txtPrix = new JTextField();
    private JTextField txtCategorie = new JTextField();
    private JTextField txtMaisonEdition = new JTextField();
    private JTextField txtPromotion = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");

    private JTable tableLivres;
    private Tableau tableauLivres;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");

    private JLabel lbNbLivres = new JLabel();

    public PanelLivre() {
        super("Gestion des Livres");

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(Controleur.getUserConnecte().getIdUser());
        Color customColor = new Color(100, 140, 180);
        this.setBackground(customColor);

        this.panelForm.setBounds(30, 100, 300, 300);
        this.panelForm.setLayout(new GridLayout(11, 2));
        this.panelForm.setBackground(customColor);

        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);
        this.panelForm.add(new JLabel("Auteur :"));
        this.panelForm.add(this.txtAuteur);
        this.panelForm.add(new JLabel("Image :"));
        this.panelForm.add(this.txtImage);
        this.panelForm.add(new JLabel("Exemplaire :"));
        this.panelForm.add(this.txtExemplaire);
        this.panelForm.add(new JLabel("Prix :"));
        this.panelForm.add(this.txtPrix);
        this.panelForm.add(new JLabel("Catégorie :"));
        this.panelForm.add(this.txtCategorie);
        this.panelForm.add(new JLabel("Maison d'édition :"));
        this.panelForm.add(this.txtMaisonEdition);
        this.panelForm.add(new JLabel("Promotion :"));
        this.panelForm.add(this.txtPromotion);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btSupprimer);

        this.add(this.panelForm);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);

        this.txtNom.addKeyListener(this);
        this.txtAuteur.addKeyListener(this);
        this.txtImage.addKeyListener(this);
        this.txtExemplaire.addKeyListener(this);
        this.txtPrix.addKeyListener(this);
        this.txtCategorie.addKeyListener(this);
        this.txtMaisonEdition.addKeyListener(this);
        this.txtPromotion.addKeyListener(this);

        String entetes[] = {"Id", "Nom", "Auteur", "Image", "Exemplaire", "Prix", "Catégorie", "Maison d'Édition", "Promotion"};
        this.tableauLivres = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableLivres = new JTable(this.tableauLivres);
        JScrollPane uneScroll = new JScrollPane(this.tableLivres);
        uneScroll.setBounds(360, 100, 480, 250);
        uneScroll.getViewport().setBackground(customColor);
        this.add(uneScroll);

        this.panelFiltre.setBackground(customColor);
        this.panelFiltre.setBounds(370, 60, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        this.lbNbLivres.setBounds(450, 380, 400, 20);
        this.add(this.lbNbLivres);
        this.lbNbLivres.setText("Nombre de livres : " + this.tableauLivres.getRowCount());

        this.tableLivres.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                afficherDetailsLivreSelectionne();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }

    private void afficherDetailsLivreSelectionne() {
        int numLigne = tableLivres.getSelectedRow();
        if (numLigne >= 0) {
            txtNom.setText(tableauLivres.getValueAt(numLigne, 1).toString());
            txtAuteur.setText(tableauLivres.getValueAt(numLigne, 2).toString());
            txtImage.setText(tableauLivres.getValueAt(numLigne, 3).toString());
            txtExemplaire.setText(tableauLivres.getValueAt(numLigne, 4).toString());
            txtPrix.setText(tableauLivres.getValueAt(numLigne, 5).toString());
            txtCategorie.setText(tableauLivres.getValueAt(numLigne, 6).toString());
            txtMaisonEdition.setText(tableauLivres.getValueAt(numLigne, 7).toString());
            Object promotionValue = tableauLivres.getValueAt(numLigne, 8);
            txtPromotion.setText(promotionValue == null || promotionValue.toString().trim().isEmpty() ?
                    "Non définie" : promotionValue.toString());

            btSupprimer.setVisible(true);
            btValider.setText("Modifier");
        }
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Livre> lesLivres = filtre.isEmpty() ?
                Controleur.selectLivre() : Controleur.selectLikeLivre(filtre);

        Object matrice[][] = new Object[lesLivres.size()][9];
        int i = 0;
        for (Livre unLivre : lesLivres) {
            matrice[i][0] = unLivre.getIdLivre();
            matrice[i][1] = unLivre.getNomLivre();
            matrice[i][2] = unLivre.getAuteurLivre();
            matrice[i][3] = unLivre.getImageLivre();
            matrice[i][4] = unLivre.getExemplaireLivre();
            matrice[i][5] = unLivre.getPrixLivre();
            matrice[i][6] = Controleur.selectNomCategorie(unLivre.getIdCategorie());
            matrice[i][7] = Controleur.selectNomMaisonEdition(unLivre.getIdMaisonEdition());
            matrice[i][8] = Controleur.selectNomPromotion(unLivre.getIdPromotion());
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        this.txtNom.setText("");
        this.txtAuteur.setText("");
        this.txtImage.setText("");
        this.txtExemplaire.setText("");
        this.txtPrix.setText("");
        this.txtCategorie.setText("");
        this.txtMaisonEdition.setText("");
        this.txtPromotion.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    private boolean verifierDonneesExistantes() {
        String categorie = this.txtCategorie.getText();
        String maisonEdition = this.txtMaisonEdition.getText();
        String promotion = this.txtPromotion.getText();

        int idCategorie = Controleur.selectIdCategorie(categorie);
        if (idCategorie <= 0) {
            JOptionPane.showMessageDialog(this,
                    "La catégorie '" + categorie + "' n'existe pas dans la base de données.",
                    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int idMaisonEdition = Controleur.selectIdMaisonEdition(maisonEdition);
        if (idMaisonEdition <= 0) {
            JOptionPane.showMessageDialog(this,
                    "La maison d'édition '" + maisonEdition + "' n'existe pas dans la base de données.",
                    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!promotion.equals("Non définie") && !promotion.isEmpty()) {
            int idPromotion = Controleur.selectIdPromotion(promotion);
            if (idPromotion <= 0) {
                JOptionPane.showMessageDialog(this,
                        "La promotion '" + promotion + "' n'existe pas dans la base de données.",
                        "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private void insererLivre() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des livres",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nom = this.txtNom.getText();
        String auteur = this.txtAuteur.getText();
        String image = this.txtImage.getText();
        String exemplaireStr = this.txtExemplaire.getText();
        String prixStr = this.txtPrix.getText();
        String categorie = this.txtCategorie.getText();
        String maisonEdition = this.txtMaisonEdition.getText();
        String promotion = this.txtPromotion.getText();

        if (!exemplaireStr.matches("\\d+") || !prixStr.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer des valeurs numériques valides pour l'exemplaire et le prix.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!verifierDonneesExistantes()) {
            return;
        }

        int exemplaire = Integer.parseInt(exemplaireStr);
        float prix = Float.parseFloat(prixStr);
        int idCategorie = Controleur.selectIdCategorie(categorie);
        int idMaisonEdition = Controleur.selectIdMaisonEdition(maisonEdition);
        Integer idPromotion = promotion.equals("Non définie") || promotion.isEmpty() ?
                null : Controleur.selectIdPromotion(promotion);

        ArrayList<String> lesChamps = new ArrayList<>();
        lesChamps.add(nom);
        lesChamps.add(auteur);
        lesChamps.add(image);
        lesChamps.add(String.valueOf(exemplaire));
        lesChamps.add(String.valueOf(prix));
        lesChamps.add(String.valueOf(idCategorie));
        lesChamps.add(String.valueOf(idMaisonEdition));
        lesChamps.add(String.valueOf(idPromotion));

        if (Controleur.verifDonnees(lesChamps)) {
            Livre unLivre = new Livre(0, nom, auteur, image, exemplaire, prix,
                    idCategorie, idMaisonEdition, idPromotion);
            Controleur.insertLivre(unLivre);
            JOptionPane.showMessageDialog(this, "Insertion réussie du livre.",
                    "Insertion Livre", JOptionPane.INFORMATION_MESSAGE);
            this.tableauLivres.setDonnees(this.obtenirDonnees(""));
            this.lbNbLivres.setText("Nombre de livres : " + this.tableauLivres.getRowCount());
            this.viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierLivre() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des livres",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableLivres.getSelectedRow();
        if (numLigne >= 0) {
            int idLivre = Integer.parseInt(tableauLivres.getValueAt(numLigne, 0).toString());
            String nomLivre = this.txtNom.getText();
            String auteurLivre = this.txtAuteur.getText();
            String imageLivre = this.txtImage.getText();
            String exemplaireStr = this.txtExemplaire.getText();
            String prixStr = this.txtPrix.getText();
            String categorie = this.txtCategorie.getText();
            String maisonEdition = this.txtMaisonEdition.getText();
            String promotion = this.txtPromotion.getText();

            if (!exemplaireStr.matches("\\d+") || !prixStr.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer des valeurs numériques valides pour l'exemplaire et le prix.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!verifierDonneesExistantes()) {
                return;
            }

            int exemplaireLivre = Integer.parseInt(exemplaireStr);
            float prixLivre = Float.parseFloat(prixStr);
            int idCategorie = Controleur.selectIdCategorie(categorie);
            int idMaisonEdition = Controleur.selectIdMaisonEdition(maisonEdition);
            Integer idPromotion = Controleur.selectIdPromotion(promotion);

            ArrayList<String> lesChamps = new ArrayList<>();
            lesChamps.add(nomLivre);
            lesChamps.add(auteurLivre);
            lesChamps.add(imageLivre);
            lesChamps.add(String.valueOf(exemplaireLivre));
            lesChamps.add(String.valueOf(prixLivre));
            lesChamps.add(String.valueOf(idCategorie));
            lesChamps.add(String.valueOf(idMaisonEdition));
            lesChamps.add(String.valueOf(idPromotion));

            if (Controleur.verifDonnees(lesChamps)) {
                Livre unLivre = new Livre(idLivre, nomLivre, auteurLivre, imageLivre,
                        exemplaireLivre, prixLivre, idCategorie,
                        idMaisonEdition, idPromotion);
                Controleur.updateLivre(unLivre);
                this.tableauLivres.setDonnees(this.obtenirDonnees(""));
                JOptionPane.showMessageDialog(this, "Modification réussie du livre.",
                        "Modification Livre", JOptionPane.INFORMATION_MESSAGE);
                this.viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerLivre() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des livres",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableLivres.getSelectedRow();
        if (numLigne >= 0) {
            int idLivre = Integer.parseInt(tableauLivres.getValueAt(numLigne, 0).toString());
            int reponse = JOptionPane.showConfirmDialog(this,
                    "Confirmez-vous la suppression de ce livre ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (reponse == JOptionPane.YES_OPTION) {
                Controleur.deleteLivre(idLivre);
                this.tableauLivres.setDonnees(this.obtenirDonnees(""));
                this.lbNbLivres.setText("Nombre de livres : " + this.tableauLivres.getRowCount());
                JOptionPane.showMessageDialog(this, "Suppression réussie du livre.",
                        "Suppression Livre", JOptionPane.INFORMATION_MESSAGE);
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
            this.tableauLivres.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbLivres.setText("Nombre de livres : " + this.tableauLivres.getRowCount());
        } else if (e.getSource() == this.btValider) {
            if (this.btValider.getText().equals("Valider")) {
                insererLivre();
            } else {
                modifierLivre();
            }
        } else if (e.getSource() == this.btSupprimer) {
            supprimerLivre();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.btValider.getText().equals("Valider")) {
                insererLivre();
            } else {
                modifierLivre();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
