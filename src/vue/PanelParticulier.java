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

import javax.swing.*;

import controleur.Particulier;
import controleur.Controleur;
import controleur.Tableau;

public class PanelParticulier extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();

    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtRole = new JTextField();
    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtSexe = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    private JTable tableParticulier;
    private Tableau tableauParticulier;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");

    private JButton btSupprimer = new JButton("Supprimer");

    private JLabel lbNbParticulier = new JLabel();

    public PanelParticulier() {
        super("");

        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 40, 300, 250);
        this.panelForm.setLayout(new GridLayout(9, 2));

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(new JLabel("Rôle :"));
        this.panelForm.add(this.txtRole);

        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);

        this.panelForm.add(new JLabel("Prénom :"));
        this.panelForm.add(this.txtPrenom);

        this.panelForm.add(new JLabel("Date de Naissance :"));
        this.panelForm.add(this.txtDateNaissance);

        this.panelForm.add(new JLabel("Sexe :"));
        this.panelForm.add(this.txtSexe);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);

        this.txtEmail.addKeyListener(this);
        this.txtAdresse.addKeyListener(this);
        this.txtRole.addKeyListener(this);
        this.txtNom.addKeyListener(this);
        this.txtPrenom.addKeyListener(this);
        this.txtDateNaissance.addKeyListener(this);
        this.txtSexe.addKeyListener(this);

        String entetes[] = {"Id", "Email", "Adresse", "Rôle", "Nom", "Prénom", "Date de Naissance", "Sexe"};
        this.tableauParticulier = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableParticulier = new JTable(this.tableauParticulier);
        JScrollPane uneScroll = new JScrollPane(this.tableParticulier);
        uneScroll.setBounds(360, 40, 480, 250);
        this.add(uneScroll);

        this.panelFiltre.setBackground(Color.cyan);
        this.panelFiltre.setBounds(370, 0, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        this.btSupprimer.setBounds(80, 280, 140, 30);
        this.add(this.btSupprimer);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);
        this.btSupprimer.setBackground(Color.red);

        this.lbNbParticulier.setBounds(450, 320, 400, 20);
        this.add(this.lbNbParticulier);
        this.lbNbParticulier.setText("Nombre de particuliers : " + this.tableauParticulier.getRowCount());

        this.tableParticulier.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableParticulier.getSelectedRow();
                if (numLigne >= 0) {
                    txtEmail.setText(tableauParticulier.getValueAt(numLigne, 1).toString());
                    txtAdresse.setText(tableauParticulier.getValueAt(numLigne, 2).toString());
                    txtRole.setText(tableauParticulier.getValueAt(numLigne, 3).toString());
                    txtNom.setText(tableauParticulier.getValueAt(numLigne, 4).toString());
                    txtPrenom.setText(tableauParticulier.getValueAt(numLigne, 5).toString());
                    txtDateNaissance.setText(tableauParticulier.getValueAt(numLigne, 6).toString());
                    txtSexe.setText(tableauParticulier.getValueAt(numLigne, 7).toString());
                    btValider.setText("Modifier");
                    btSupprimer.setVisible(true);
                }
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Particulier> lesParticuliers;
        if (filtre.equals("")) {
            lesParticuliers = Controleur.selectParticulier();
        } else {
            lesParticuliers = Controleur.selectLikeParticulier(filtre);
        }
        Object matrice[][] = new Object[lesParticuliers.size()][8];
        int i = 0;
        for (Particulier unParticulier : lesParticuliers) {
            matrice[i][0] = unParticulier.getIdUser();
            matrice[i][1] = unParticulier.getEmailUser();
            matrice[i][2] = unParticulier.getAdresseUser();
            matrice[i][3] = unParticulier.getRoleUser();
            matrice[i][4] = unParticulier.getNomUser();
            matrice[i][5] = unParticulier.getPrenomUser();
            matrice[i][6] = unParticulier.getDateNaissanceUser();
            matrice[i][7] = unParticulier.getSexeUser();
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        this.txtEmail.setText("");
        this.txtAdresse.setText("");
        this.txtRole.setText("");
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtDateNaissance.setText("");
        this.txtSexe.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    private Date convertirEnDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.",
                    "Erreur de date", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void insererParticulier() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des particuliers",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String email = this.txtEmail.getText();
        String mdp = "d43affcc277ee52980fc4ecea523730f28d6405b";
        String adresse = this.txtAdresse.getText();
        String role = this.txtRole.getText();
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String dateNaissance = this.txtDateNaissance.getText();
        String sexe = this.txtSexe.getText();

        ArrayList<String> lesChamps = new ArrayList<>();
        lesChamps.add(email);
        lesChamps.add(mdp);
        lesChamps.add(adresse);
        lesChamps.add(role);
        lesChamps.add(nom);
        lesChamps.add(prenom);
        lesChamps.add(dateNaissance);
        lesChamps.add(sexe);

        if (Controleur.verifDonnees(lesChamps)) {
            Date dateNaissanceDate = convertirEnDate(dateNaissance);
            if (dateNaissanceDate != null) {
                Particulier unParticulier = new Particulier(0, email, mdp, adresse, role, nom, prenom, dateNaissanceDate, sexe);
                Controleur.insertParticulier(unParticulier);
                this.tableauParticulier.setDonnees(this.obtenirDonnees(""));
                JOptionPane.showMessageDialog(this, "Particulier ajouté avec succès",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur de format de date",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierParticulier() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des particuliers",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableParticulier.getSelectedRow();
        if (numLigne >= 0) {
            int idUser = Integer.parseInt(tableauParticulier.getValueAt(numLigne, 0).toString());
            String email = this.txtEmail.getText();
            String mdp = "d43affcc277ee52980fc4ecea523730f28d6405b"; // Mot de passe inchangé
            String adresse = this.txtAdresse.getText();
            String role = this.txtRole.getText();
            String nom = this.txtNom.getText();
            String prenom = this.txtPrenom.getText();
            String dateNaissance = this.txtDateNaissance.getText();
            String sexe = this.txtSexe.getText();

            ArrayList<String> lesChamps = new ArrayList<>();
            lesChamps.add(email);
            lesChamps.add(mdp);
            lesChamps.add(adresse);
            lesChamps.add(role);
            lesChamps.add(nom);
            lesChamps.add(prenom);
            lesChamps.add(dateNaissance);
            lesChamps.add(sexe);

            if (Controleur.verifDonnees(lesChamps)) {
                Date dateNaissanceDate = convertirEnDate(dateNaissance);
                if (dateNaissanceDate != null) {
                    Particulier unParticulier = new Particulier(idUser, email, mdp, adresse, role, nom, prenom, dateNaissanceDate, sexe);
                    Controleur.updateParticulier(unParticulier);
                    this.tableauParticulier.setDonnees(this.obtenirDonnees(""));
                    JOptionPane.showMessageDialog(this, "Modification réussie",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    this.viderChamps();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur de format de date",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerParticulier() {
        if (!Controleur.getRoleUserConnecte().equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des particuliers",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableParticulier.getSelectedRow();
        if (numLigne >= 0) {
            int idUser = Integer.parseInt(tableauParticulier.getValueAt(numLigne, 0).toString());
            int reponse = JOptionPane.showConfirmDialog(this,
                    "Confirmez-vous la suppression de ce particulier ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (reponse == JOptionPane.YES_OPTION) {
                Controleur.deleteParticulier(idUser);
                this.tableauParticulier.setDonnees(this.obtenirDonnees(""));
                this.lbNbParticulier.setText("Nombre de particuliers : " + this.tableauParticulier.getRowCount());
                JOptionPane.showMessageDialog(this, "Suppression réussie",
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
            this.tableauParticulier.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbParticulier.setText("Nombre de particuliers : " + this.tableauParticulier.getRowCount());
        } else if (e.getSource() == this.btValider) {
            if (this.btValider.getText().equals("Valider")) {
                insererParticulier();
            } else if (this.btValider.getText().equals("Modifier")) {
                modifierParticulier();
            }
        } else if (e.getSource() == this.btSupprimer) {
            supprimerParticulier();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.btValider.getText().equals("Valider")) {
                insererParticulier();
            } else if (this.btValider.getText().equals("Modifier")) {
                modifierParticulier();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}