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

import controleur.Particulier;
import controleur.Controleur;
import controleur.Tableau;

public class PanelParticulier extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
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
        super("Gestion des Particuliers");

        // Placement du panel formulaire
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 100, 300, 200);
        this.panelForm.setLayout(new GridLayout(7, 2)); // 7 lignes pour 6 champs + boutons
        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);

        this.panelForm.add(new JLabel("Prénom :"));
        this.panelForm.add(this.txtPrenom);

        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Date de Naissance :"));
        this.panelForm.add(this.txtDateNaissance);

        this.panelForm.add(new JLabel("Sexe :"));
        this.panelForm.add(this.txtSexe);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        // Rendre les boutons écoutables
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);

        // Rendre les champs écoutables
        this.txtNom.addKeyListener(this);
        this.txtPrenom.addKeyListener(this);
        this.txtAdresse.addKeyListener(this);
        this.txtEmail.addKeyListener(this);
        this.txtDateNaissance.addKeyListener(this);
        this.txtSexe.addKeyListener(this);

        // Installation de la JTable
        String entetes[] = {"Id", "Nom", "Prénom", "Adresse", "Email", "Date de Naissance", "Sexe"};
        this.tableauParticulier = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableParticulier = new JTable(this.tableauParticulier);
        JScrollPane uneScroll = new JScrollPane(this.tableParticulier);
        uneScroll.setBounds(360, 100, 480, 250);
        this.add(uneScroll);

        // Installation du panel filtre
        this.panelFiltre.setBackground(Color.cyan);
        this.panelFiltre.setBounds(370, 60, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        // Installation du bouton Supprimer
        this.btSupprimer.setBounds(80, 340, 140, 30);
        this.add(this.btSupprimer);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);
        this.btSupprimer.setBackground(Color.red);

        // Installation du compteur
        this.lbNbParticulier.setBounds(450, 380, 400, 20);
        this.add(this.lbNbParticulier);
        this.lbNbParticulier.setText("Nombre de particuliers : " + this.tableauParticulier.getRowCount());

        // Rendre la JTable écoutable sur le clic de la souris
        this.tableParticulier.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableParticulier.getSelectedRow();
                if (numLigne >= 0) {
                    txtNom.setText(tableauParticulier.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(tableauParticulier.getValueAt(numLigne, 2).toString());
                    txtAdresse.setText(tableauParticulier.getValueAt(numLigne, 3).toString());
                    txtEmail.setText(tableauParticulier.getValueAt(numLigne, 4).toString());
                    txtDateNaissance.setText(tableauParticulier.getValueAt(numLigne, 5).toString());
                    txtSexe.setText(tableauParticulier.getValueAt(numLigne, 6).toString());

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

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Particulier> lesParticuliers;
        if (filtre.equals("")) {
            lesParticuliers = Controleur.selectParticulier();
        } else {
            lesParticuliers = Controleur.selectLikeParticulier(filtre);
        }
        Object matrice[][] = new Object[lesParticuliers.size()][7];
        int i = 0;
        for (Particulier unParticulier : lesParticuliers) {
            matrice[i][0] = unParticulier.getIdUser();
            matrice[i][1] = unParticulier.getNomUser();
            matrice[i][2] = unParticulier.getPrenomUser();
            matrice[i][3] = unParticulier.getAdresseUser();
            matrice[i][4] = unParticulier.getEmailUser();
            matrice[i][5] = unParticulier.getDateNaissanceUser();
            matrice[i][6] = unParticulier.getSexeUser();
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtAdresse.setText("");
        this.txtEmail.setText("");
        this.txtDateNaissance.setText("");
        this.txtSexe.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    private Date convertirEnDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Adaptez le format à votre besoin
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.",
                    "Erreur de date", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.tableauParticulier.setDonnees(this.obtenirDonnees(filtre));
        } else if (e.getSource() == this.btValider && this.btValider.getText().equals("Valider")) {
            this.traitement();
            this.lbNbParticulier.setText("Nombre de particuliers : " + this.tableauParticulier.getRowCount());
        } else if (e.getSource() == this.btValider && this.btValider.getText().equals("Modifier")) {
            int numLigne = tableParticulier.getSelectedRow();
            if (numLigne >= 0) {
                int idUser = Integer.parseInt(tableauParticulier.getValueAt(numLigne, 0).toString());
                String nom = this.txtNom.getText();
                String prenom = this.txtPrenom.getText();
                String adresse = this.txtAdresse.getText();
                String email = this.txtEmail.getText();
                String dateNaissance = this.txtDateNaissance.getText();
                String sexe = this.txtSexe.getText();

                ArrayList<String> lesChamps = new ArrayList<>();
                lesChamps.add(nom);
                lesChamps.add(prenom);
                lesChamps.add(adresse);
                lesChamps.add(email);
                lesChamps.add(dateNaissance);
                lesChamps.add(sexe);

                if (Controleur.verifDonnees(lesChamps)) {
                    Date dateNaissanceDate = convertirEnDate(dateNaissance);
                    if (dateNaissanceDate != null) {
                        Particulier unParticulier = new Particulier(idUser, nom, prenom, dateNaissanceDate, sexe, email, "", adresse, "");
                        Controleur.updateParticulier(unParticulier);
                        this.tableauParticulier.setDonnees(this.obtenirDonnees(""));
                        JOptionPane.showMessageDialog(this, "Modification réussie du particulier.",
                                "Modification Particulier", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur de format de date.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                this.viderChamps();
            }
        } else if (e.getSource() == this.btSupprimer) {
            int numLigne = tableParticulier.getSelectedRow();
            if (numLigne >= 0) {
                int idUser = Integer.parseInt(tableauParticulier.getValueAt(numLigne, 0).toString());
                Controleur.deleteParticulier(idUser);
                this.tableauParticulier.setDonnees(this.obtenirDonnees(""));
                this.lbNbParticulier.setText("Nombre de particuliers : " + this.tableauParticulier.getRowCount());
                JOptionPane.showMessageDialog(this, "Suppression réussie du particulier.",
                        "Suppression Particulier", JOptionPane.INFORMATION_MESSAGE);
                this.viderChamps();
            }
        }
    }

    private void traitement() {
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String adresse = this.txtAdresse.getText();
        String email = this.txtEmail.getText();
        String dateNaissance = this.txtDateNaissance.getText();
        String sexe = this.txtSexe.getText();

        ArrayList<String> lesChamps = new ArrayList<>();
        lesChamps.add(nom);
        lesChamps.add(prenom);
        lesChamps.add(adresse);
        lesChamps.add(email);
        lesChamps.add(dateNaissance);
        lesChamps.add(sexe);

        if (Controleur.verifDonnees(lesChamps)) {
            Date dateNaissanceDate = convertirEnDate(dateNaissance);
            if (dateNaissanceDate != null) {
                Particulier unParticulier = new Particulier(0, nom, prenom, dateNaissanceDate, sexe, email, "", adresse, "");
                Controleur.insertParticulier(unParticulier);
                JOptionPane.showMessageDialog(this, "Insertion réussie du particulier.",
                        "Insertion Particulier", JOptionPane.INFORMATION_MESSAGE);
                this.tableauParticulier.setDonnees(this.obtenirDonnees(""));
            } else {
                JOptionPane.showMessageDialog(this, "Erreur de format de date.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        this.viderChamps();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.traitement();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}