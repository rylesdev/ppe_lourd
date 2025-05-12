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
    private String niveauAdmin;
    private JPanel panelForm = new JPanel();

    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtRole = new JTextField();
    private JTextField txtSiret = new JTextField();
    private JTextField txtRaisonSociale = new JTextField();
    private JTextField txtCapitalSocial = new JTextField();

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
        super("");

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(Controleur.getUserConnecte().getIdUser());
        Color customColor = new Color(100, 140, 180);
        this.panelForm.setBackground(customColor);
        this.panelForm.setBounds(30, 40, 300, 250);
        this.panelForm.setLayout(new GridLayout(8, 2));

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(new JLabel("Rôle :"));
        this.panelForm.add(this.txtRole);

        this.panelForm.add(new JLabel("SIRET :"));
        this.panelForm.add(this.txtSiret);

        this.panelForm.add(new JLabel("Raison Sociale :"));
        this.panelForm.add(this.txtRaisonSociale);

        this.panelForm.add(new JLabel("Capital Social :"));
        this.panelForm.add(this.txtCapitalSocial);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);

        this.txtEmail.addKeyListener(this);
        this.txtAdresse.addKeyListener(this);
        this.txtRole.addKeyListener(this);
        this.txtSiret.addKeyListener(this);
        this.txtRaisonSociale.addKeyListener(this);
        this.txtCapitalSocial.addKeyListener(this);

        String[] entetes = {"Id", "Email", "Adresse", "Rôle", "SIRET", "Raison Sociale", "Capital Social"};
        this.tableauEntreprise = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableEntreprise = new JTable(this.tableauEntreprise);
        JScrollPane uneScroll = new JScrollPane(this.tableEntreprise);
        uneScroll.setBounds(360, 40, 480, 250);
        this.add(uneScroll);

        this.panelFiltre.setBackground(customColor);
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

        this.lbNbEntreprise.setBounds(450, 320, 400, 20);
        this.add(this.lbNbEntreprise);
        this.lbNbEntreprise.setText("Nombre d'entreprises : " + this.tableauEntreprise.getRowCount());

        this.tableEntreprise.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableEntreprise.getSelectedRow();
                if (numLigne >= 0) {
                    txtEmail.setText(tableauEntreprise.getValueAt(numLigne, 1).toString());
                    txtAdresse.setText(tableauEntreprise.getValueAt(numLigne, 2).toString());
                    txtRole.setText(tableauEntreprise.getValueAt(numLigne, 3).toString());
                    txtSiret.setText(tableauEntreprise.getValueAt(numLigne, 4).toString());
                    txtRaisonSociale.setText(tableauEntreprise.getValueAt(numLigne, 5).toString());
                    txtCapitalSocial.setText(tableauEntreprise.getValueAt(numLigne, 6).toString());
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
        Object[][] matrice = new Object[lesEntreprises.size()][7];
        int i = 0;
        for (Entreprise e : lesEntreprises) {
            matrice[i][0] = e.getIdUser();
            matrice[i][1] = e.getEmailUser();
            matrice[i][2] = e.getAdresseUser();
            matrice[i][3] = e.getRoleUser();
            matrice[i][4] = e.getSiretUser();
            matrice[i][5] = e.getRaisonSocialeUser();
            matrice[i][6] = e.getCapitalSocialUser();
            i++;
        }
        return matrice;
    }

    public void viderChamps() {
        this.txtEmail.setText("");
        this.txtAdresse.setText("");
        this.txtRole.setText("");
        this.txtSiret.setText("");
        this.txtRaisonSociale.setText("");
        this.txtCapitalSocial.setText("");
        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    private void insererEntreprise() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent ajouter des entreprises",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String email = this.txtEmail.getText();
        String adresse = this.txtAdresse.getText();
        String role = this.txtRole.getText();
        String siret = this.txtSiret.getText();
        String raisonSociale = this.txtRaisonSociale.getText();
        String capitalSocialText = this.txtCapitalSocial.getText();

        if (!email.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            JOptionPane.showMessageDialog(this,
                    "L'email doit contenir un '@' et un '.' (ex: exemple@domaine.com)",
                    "Format email invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!siret.matches("^\\d{14}$")) {
            JOptionPane.showMessageDialog(this,
                    "Le SIRET doit contenir exactement 14 chiffres.",
                    "Format SIRET invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Controleur.emailExiste(email)) {
            JOptionPane.showMessageDialog(this,
                    "Cet email est déjà utilisé par un autre utilisateur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] roles = {"admin", "particulier", "entreprise"};
        boolean roleValide = false;
        for (String r : roles) {
            if (r.equals(role)) {
                roleValide = true;
                break;
            }
        }
        if (!roleValide) {
            JOptionPane.showMessageDialog(this,
                    "Le rôle spécifié n'est pas valide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<String> lesChamps = new ArrayList<>();
        lesChamps.add(email);
        lesChamps.add(adresse);
        lesChamps.add(role);
        lesChamps.add(siret);
        lesChamps.add(raisonSociale);
        lesChamps.add(capitalSocialText);

        if (Controleur.verifDonnees(lesChamps)) {
            try {
                float capitalSocial = Float.parseFloat(capitalSocialText);
                Entreprise uneEntreprise = new Entreprise(
                        0, email, "", adresse, role, siret, raisonSociale, capitalSocial
                );
                String resultat = Controleur.insertEntreprise(uneEntreprise);
                if (resultat.startsWith("OK:")) {
                    String mdpGenere = resultat.substring(3);
                    this.tableauEntreprise.setDonnees(this.obtenirDonnees(""));
                    JOptionPane.showMessageDialog(this,
                            "Entreprise créée avec succès !\nMot de passe temporaire: " + mdpGenere,
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    this.viderChamps();
                } else {
                    JOptionPane.showMessageDialog(this, resultat,
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Format du capital social invalide",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierEntreprise() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent modifier des entreprises",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableEntreprise.getSelectedRow();
        if (numLigne >= 0) {
            int idUser = Integer.parseInt(tableauEntreprise.getValueAt(numLigne, 0).toString());
            String email = this.txtEmail.getText();
            String mdp = "d43affcc277ee52980fc4ecea523730f28d6405b";
            String adresse = this.txtAdresse.getText();
            String role = this.txtRole.getText();
            String siret = this.txtSiret.getText();
            String raisonSociale = this.txtRaisonSociale.getText();
            String capitalSocialText = this.txtCapitalSocial.getText();

            ArrayList<String> lesChamps = new ArrayList<>();
            lesChamps.add(email);
            lesChamps.add(mdp);
            lesChamps.add(adresse);
            lesChamps.add(role);
            lesChamps.add(siret);
            lesChamps.add(raisonSociale);
            lesChamps.add(capitalSocialText);

            if (Controleur.verifDonnees(lesChamps)) {
                try {
                    float capitalSocial = Float.parseFloat(capitalSocialText);
                    Entreprise uneEntreprise = new Entreprise(
                            idUser, email, mdp, adresse, role, siret, raisonSociale, capitalSocial
                    );
                    Controleur.updateEntreprise(uneEntreprise);
                    this.tableauEntreprise.setDonnees(this.obtenirDonnees(""));
                    JOptionPane.showMessageDialog(this, "Modification réussie !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    this.viderChamps();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Format du capital social invalide",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerEntreprise() {
        if (!this.niveauAdmin.equals("principal")) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé : Seuls les administrateurs peuvent supprimer des entreprises",
                    "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numLigne = tableEntreprise.getSelectedRow();
        if (numLigne >= 0) {
            int idUser = Integer.parseInt(tableauEntreprise.getValueAt(numLigne, 0).toString());
            int reponse = JOptionPane.showConfirmDialog(this,
                    "Confirmez-vous la suppression de cette entreprise ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (reponse == JOptionPane.YES_OPTION) {
                Controleur.deleteEntreprise(idUser);
                this.tableauEntreprise.setDonnees(this.obtenirDonnees(""));
                this.lbNbEntreprise.setText("Nombre d'entreprises : " + this.tableauEntreprise.getRowCount());
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
            this.tableauEntreprise.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbEntreprise.setText("Nombre d'entreprises : " + this.tableauEntreprise.getRowCount());
        } else if (e.getSource() == this.btValider) {
            if (this.btValider.getText().equals("Valider")) {
                insererEntreprise();
            } else if (this.btValider.getText().equals("Modifier")) {
                modifierEntreprise();
            }
        } else if (e.getSource() == this.btSupprimer) {
            supprimerEntreprise();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.btValider.getText().equals("Valider")) {
                insererEntreprise();
            } else if (this.btValider.getText().equals("Modifier")) {
                modifierEntreprise();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}