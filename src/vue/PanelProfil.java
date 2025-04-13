package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controleur.Controleur;
import controleur.User;

public class PanelProfil extends PanelPrincipal implements ActionListener {
    private JTextArea txtInfos = new JTextArea();
    private JButton btModifier = new JButton("Modifier Profil");

    private JPanel panelForm = new JPanel();
    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtRole = new JTextField();
    private JPasswordField txtMdp1 = new JPasswordField();
    private JPasswordField txtMdp2 = new JPasswordField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    public PanelProfil() {
        super("Gestion du Profil");

        // Configuration initiale
        configurerPanelForm();
        configurerTxtInfos();
        ajouterBoutonsEtEcouteurs();

        // Ne pas charger de profil au démarrage
        viderAffichage();
        this.setVisible(true);
    }

    // Nouvelle méthode pour vider l'affichage
    private void viderAffichage() {
        this.txtInfos.setText("");
        this.panelForm.setVisible(false);
    }

    // Méthode publique pour charger le profil
    public void chargerProfil() {
        User currentUser = Controleur.getUserConnecte();
        if (currentUser == null) {
            viderAffichage();
            return;
        }

        afficherInfosUtilisateur(currentUser);
        viderChampsFormulaire();
    }

    private void configurerPanelForm() {
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(400, 100, 400, 300);
        this.panelForm.setLayout(new GridLayout(5, 2));
        this.panelForm.setVisible(false);

        this.panelForm.add(new JLabel("Email:"));
        this.panelForm.add(this.txtEmail);
        this.panelForm.add(new JLabel("Adresse:"));
        this.panelForm.add(this.txtAdresse);
        this.panelForm.add(new JLabel("Rôle:"));
        this.panelForm.add(this.txtRole);
        this.txtRole.setEditable(false);
        this.panelForm.add(new JLabel("Nouveau mot de passe:"));
        this.panelForm.add(this.txtMdp1);
        this.panelForm.add(new JLabel("Confirmation:"));
        this.panelForm.add(this.txtMdp2);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);
    }

    private void configurerTxtInfos() {
        this.txtInfos.setBounds(50, 100, 300, 240);
        this.txtInfos.setBackground(Color.cyan);
        this.txtInfos.setEditable(false);
        this.add(this.txtInfos);
    }

    private void ajouterBoutonsEtEcouteurs() {
        this.btModifier.setBounds(100, 460, 200, 40);
        this.add(this.btModifier);
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btModifier.addActionListener(this);
    }

    private void afficherInfosUtilisateur(User user) {
        if (user == null) {
            viderAffichage();
            return;
        }

        this.txtInfos.setText(
                "________________ INFOS PROFIL ________________\n\n"
                        + " ID: " + user.getIdUser() + "\n\n"
                        + " Email: " + user.getEmailUser() + "\n\n"
                        + " Mot de passe: ********\n\n"
                        + " Adresse: " + user.getAdresseUser() + "\n\n"
                        + " Rôle: " + user.getRoleUser() + "\n\n"
                        + "_____________________________________________"
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User currentUser = Controleur.getUserConnecte();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this,
                    "Session expirée. Veuillez vous reconnecter.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            viderAffichage();
            return;
        }

        if (e.getSource() == this.btModifier) {
            this.txtEmail.setText(currentUser.getEmailUser());
            this.txtAdresse.setText(currentUser.getAdresseUser());
            this.txtRole.setText(currentUser.getRoleUser());
            this.txtMdp1.setText("");
            this.txtMdp2.setText("");
            this.panelForm.setVisible(true);
        }
        else if (e.getSource() == this.btAnnuler) {
            viderChampsFormulaire();
            afficherInfosUtilisateur(currentUser);
        }
        else if (e.getSource() == this.btValider) {
            validerModifications(currentUser);
        }
    }

    private void validerModifications(User originalUser) {
        String email = this.txtEmail.getText().trim();
        String adresse = this.txtAdresse.getText().trim();
        String mdp1 = new String(this.txtMdp1.getPassword());
        String mdp2 = new String(this.txtMdp2.getPassword());

        if (email.isEmpty() || adresse.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Email et adresse sont obligatoires",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!mdp1.isEmpty() && !mdp1.equals(mdp2)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User modifiedUser = new User(
                    originalUser.getIdUser(),
                    email,
                    mdp1.isEmpty() ? originalUser.getMdpUser() : mdp1,
                    adresse,
                    originalUser.getRoleUser()
            );

            Controleur.updateUser(modifiedUser);
            Controleur.actualiserUserConnecte();
            chargerProfil();

            JOptionPane.showMessageDialog(this,
                    "Profil mis à jour avec succès",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour: " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viderChampsFormulaire() {
        this.txtEmail.setText("");
        this.txtAdresse.setText("");
        this.txtRole.setText("");
        this.txtMdp1.setText("");
        this.txtMdp2.setText("");
        this.panelForm.setVisible(false);
    }
}