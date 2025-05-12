package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import controleur.User;

public class PanelProfil extends PanelPrincipal implements ActionListener {
    private JTextArea txtInfos = new JTextArea();
    private JButton btModifier = new JButton("Modifier Profil");
    private String niveauAdmin;

    private JPanel panelForm = new JPanel();
    private JPanel panelLabels = new JPanel();
    private JPanel panelFields = new JPanel();
    private JPanel panelBoutons = new JPanel();

    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtRole = new JTextField();
    private JPasswordField txtMdp1 = new JPasswordField();
    private JPasswordField txtMdp2 = new JPasswordField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    private JLabel lblLogo = new JLabel();

    public PanelProfil() {
        super("Gestion du Profil");

        configurerPanelForm();
        configurerTxtInfos();
        configurerLogo();
        ajouterBoutonsEtEcouteurs();

        viderAffichage();
        this.setVisible(true);
    }


    private void configurerLogo() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/livre.png"));
        int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
        int tailleLogo = largeurEcran / 8;

        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(tailleLogo, tailleLogo, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);

        lblLogo.setIcon(icon);
        lblLogo.setBounds(700, 30, tailleLogo, tailleLogo);
        lblLogo.setOpaque(false);
        this.add(lblLogo);
    }

    private void viderAffichage() {
        this.txtInfos.setText("");
        this.panelForm.setVisible(false);
    }

    public void chargerProfil() {
        User currentUser = Controleur.getUserConnecte();

        if (currentUser == null) {
            viderAffichage();
            JOptionPane.showMessageDialog(this,
                    "Aucun utilisateur connecté.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.niveauAdmin = Controleur.selectNiveauAdminByIdUser(currentUser.getIdUser());

        afficherInfosUtilisateur(currentUser);
        viderChampsFormulaire();
    }

    private void configurerPanelForm() {
        Color customColor = new Color(100, 140, 180);

        this.panelForm.setBackground(customColor);
        this.panelForm.setBounds(370, 100, 300, 250);
        this.panelForm.setLayout(new BorderLayout());

        JPanel panelLabelsFields = new JPanel();
        panelLabelsFields.setLayout(new GridLayout(5, 2));
        panelLabelsFields.setBackground(customColor);

        panelLabelsFields.add(new JLabel("Email:"));
        panelLabelsFields.add(this.txtEmail);
        panelLabelsFields.add(new JLabel("Adresse:"));
        panelLabelsFields.add(this.txtAdresse);
        panelLabelsFields.add(new JLabel("Rôle:"));
        panelLabelsFields.add(this.txtRole);
        panelLabelsFields.add(new JLabel("Nouveau mot de passe:"));
        panelLabelsFields.add(this.txtMdp1);
        panelLabelsFields.add(new JLabel("Confirmation:"));
        panelLabelsFields.add(this.txtMdp2);

        this.panelForm.add(panelLabelsFields, BorderLayout.CENTER);

        this.panelBoutons.setBackground(customColor);
        this.panelBoutons.setLayout(new GridLayout(1, 2));
        this.panelBoutons.add(this.btAnnuler);
        this.panelBoutons.add(this.btValider);

        this.panelForm.add(this.panelBoutons, BorderLayout.SOUTH);

        this.add(this.panelForm);
        this.panelForm.setVisible(false);
    }

    private JPanel createTextFieldPanel(JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(textField);
        return panel;
    }

    private void configurerTxtInfos() {
        Color customColor = new Color(100, 140, 180);
        this.panelForm.setBackground(customColor);

        this.txtInfos.setBounds(50, 100, 300, 240);
        this.txtInfos.setBackground(customColor);
        this.txtInfos.setEditable(false);
        this.add(this.txtInfos);
    }

    private void ajouterBoutonsEtEcouteurs() {
        this.btModifier.setBounds(50, 360, 200, 40);
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
                        + " Rôle: " + this.niveauAdmin + "\n\n"
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
            this.txtRole.setText(this.niveauAdmin);
            this.txtMdp1.setText("");
            this.txtMdp2.setText("");
            this.panelForm.setVisible(true);
            this.panelBoutons.setVisible(true);
        } else if (e.getSource() == this.btAnnuler) {
            viderChampsFormulaire();
            afficherInfosUtilisateur(currentUser);
            this.panelBoutons.setVisible(false);
        } else if (e.getSource() == this.btValider) {
            if (validerFormulaire(currentUser)) {
                validerModifications(currentUser);
                this.panelBoutons.setVisible(false);
            }
        }
    }

    private boolean validerFormulaire(User currentUser) {
        String email = this.txtEmail.getText().trim();
        String adresse = this.txtAdresse.getText().trim();
        String role = this.txtRole.getText().trim();
        String mdp1 = new String(this.txtMdp1.getPassword());
        String mdp2 = new String(this.txtMdp2.getPassword());

        if (email.isEmpty() || adresse.isEmpty() || role.isEmpty() || mdp1.isEmpty() || mdp2.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tous les champs doivent être remplis.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            JOptionPane.showMessageDialog(this,
                    "L'email doit contenir un '@' et un '.' (ex: exemple@domaine.com)",
                    "Format email invalide", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!mdp1.equals(mdp2)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String[] roles = {"principal", "client", "gestionnaire"};
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
            return false;
        }

        if (Controleur.emailExiste(email) && !email.equals(currentUser.getEmailUser())) {
            JOptionPane.showMessageDialog(this,
                    "Cet email est déjà utilisé par un autre utilisateur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void validerModifications(User originalUser) {
        String email = this.txtEmail.getText().trim();
        String adresse = this.txtAdresse.getText().trim();
        this.niveauAdmin = this.txtRole.getText().trim();
        String mdp1 = new String(this.txtMdp1.getPassword());
        String mdp2 = new String(this.txtMdp2.getPassword());
        String verifMdp = "";
        if (mdp1.equals(mdp2)) {
            verifMdp = Controleur.sha1Hash(mdp1);
        } else {
            verifMdp = null;
        }
        String role = "admin";

        try {
            User modifiedUser = new User(
                    originalUser.getIdUser(),
                    email,
                    verifMdp,
                    adresse,
                    role
            );

            User userAvantUpdate = Controleur.getUserConnecte();

            Controleur.updateAdmin(modifiedUser, this.niveauAdmin);

            if (Controleur.getUserConnecte() == null) {
                Controleur.setUserConnecte(userAvantUpdate);
            }

            Controleur.actualiserUserConnecte();

            if (Controleur.getUserConnecte() == null) {
                Controleur.setUserConnecte(modifiedUser);
            }

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