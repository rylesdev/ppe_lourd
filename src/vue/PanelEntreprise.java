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

import controleur.Controleur;
import controleur.Entreprise;
import controleur.Tableau;

public class PanelEntreprise extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();

    private JTextField txtSiret = new JTextField();
    private JTextField txtRaisonSociale = new JTextField();
    private JTextField txtCapitalSocial = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    private JTable tableEntreprise;
    private Tableau tableauEntreprise;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");

    private JButton btSupprimer = new JButton("Supprimer");
    private JLabel lbNbEntreprise = new JLabel();

    public PanelEntreprise() {
        super("Gestion des Entreprises");

        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 100, 300, 200);
        this.panelForm.setLayout(new GridLayout(6, 2));

        this.panelForm.add(new JLabel("SIRET :"));
        this.panelForm.add(this.txtSiret);

        this.panelForm.add(new JLabel("Raison Sociale :"));
        this.panelForm.add(this.txtRaisonSociale);

        this.panelForm.add(new JLabel("Capital Social :"));
        this.panelForm.add(this.txtCapitalSocial);

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);

        String[] entetes = {"Id", "Email", "SIRET", "Raison Sociale", "Capital Social", "Adresse"};
        this.tableauEntreprise = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableEntreprise = new JTable(this.tableauEntreprise);
        JScrollPane uneScroll = new JScrollPane(this.tableEntreprise);
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

        this.btSupprimer.setBounds(80, 340, 140, 30);
        this.add(this.btSupprimer);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);
        this.btSupprimer.setBackground(Color.red);

        this.lbNbEntreprise.setBounds(450, 380, 400, 20);
        this.add(this.lbNbEntreprise);
        this.lbNbEntreprise.setText("Nombre d'entreprises : " + this.tableauEntreprise.getRowCount());

        this.tableEntreprise.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableEntreprise.getSelectedRow();
                if (numLigne >= 0) {
                    txtSiret.setText(tableauEntreprise.getValueAt(numLigne, 2).toString());
                    txtRaisonSociale.setText(tableauEntreprise.getValueAt(numLigne, 3).toString());
                    txtCapitalSocial.setText(tableauEntreprise.getValueAt(numLigne, 4).toString());
                    txtEmail.setText(tableauEntreprise.getValueAt(numLigne, 1).toString());
                    txtAdresse.setText(tableauEntreprise.getValueAt(numLigne, 5).toString());

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
        ArrayList<Entreprise> lesEntreprises;
        if (filtre.isEmpty()) {
            lesEntreprises = Controleur.selectEntreprise();
        } else {
            lesEntreprises = Controleur.selectLikeEntreprise(filtre);
        }
        Object[][] matrice = new Object[lesEntreprises.size()][6];
        int i = 0;
        for (Entreprise e : lesEntreprises) {
            matrice[i][0] = e.getIdUser();
            matrice[i][1] = e.getEmailUser();
            matrice[i][2] = e.getSiretUser();
            matrice[i][3] = e.getRaisonSocialeUser();
            matrice[i][4] = e.getCapitalSocialUser();
            matrice[i][5] = e.getAdresseUser();
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        txtSiret.setText("");
        txtRaisonSociale.setText("");
        txtCapitalSocial.setText("");
        txtEmail.setText("");
        txtAdresse.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            viderChamps();
        } else if (e.getSource() == btFiltrer) {
            tableauEntreprise.setDonnees(obtenirDonnees(txtFiltre.getText()));
            lbNbEntreprise.setText("Nombre d'entreprises : " + tableauEntreprise.getRowCount());
        } else if (e.getSource() == btValider && btValider.getText().equals("Valider")) {
            traitement();
        } else if (e.getSource() == btValider && btValider.getText().equals("Modifier")) {
            modifierEntreprise();
        } else if (e.getSource() == btSupprimer) {
            supprimerEntreprise();
        }
    }

    private void traitement() {
        String siret = txtSiret.getText();
        String raisonSociale = txtRaisonSociale.getText();
        String capitalText = txtCapitalSocial.getText();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();

        ArrayList<String> champs = new ArrayList<>();
        champs.add(siret);
        champs.add(raisonSociale);
        champs.add(capitalText);
        champs.add(email);
        champs.add(adresse);

        if (Controleur.verifDonnees(champs)) {
            try {
                double capital = Double.parseDouble(capitalText);
                Entreprise e = new Entreprise(
                        0, siret, raisonSociale, (float) capital,
                        email, "", adresse, "entreprise"
                );
                Controleur.insertEntreprise(e);
                tableauEntreprise.setDonnees(obtenirDonnees(""));
                JOptionPane.showMessageDialog(this, "Entreprise créée avec succès !");
                viderChamps();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capital Social invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Champs manquants", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierEntreprise() {
        int numLigne = tableEntreprise.getSelectedRow();
        if (numLigne >= 0) {
            int idUser = (int) tableauEntreprise.getValueAt(numLigne, 0);
            String siret = txtSiret.getText();
            String raisonSociale = txtRaisonSociale.getText();
            String capitalText = txtCapitalSocial.getText();
            String email = txtEmail.getText();
            String adresse = txtAdresse.getText();

            try {
                double capital = Double.parseDouble(capitalText);
                Entreprise e = new Entreprise(
                        idUser, siret, raisonSociale, (float) capital,
                        email, "", adresse, "entreprise"
                );
                Controleur.updateEntreprise(e);
                tableauEntreprise.setDonnees(obtenirDonnees(""));
                JOptionPane.showMessageDialog(this, "Modification réussie !");
                viderChamps();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capital Social invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerEntreprise() {
        int numLigne = tableEntreprise.getSelectedRow();
        if (numLigne >= 0) {
            int idUser = (int) tableauEntreprise.getValueAt(numLigne, 0);
            Controleur.deleteEntreprise(idUser);
            tableauEntreprise.setDonnees(obtenirDonnees(""));
            lbNbEntreprise.setText("Nombre d'entreprises : " + tableauEntreprise.getRowCount());
            viderChamps();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) traitement();
    }
    @Override public void keyReleased(KeyEvent e) {}
}