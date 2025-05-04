package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import controleur.User;

public class PanelProfil extends PanelPrincipal implements ActionListener {
    private JTextArea txtInfos = new JTextArea();
    private JButton btModifier = new JButton("Modifier Profil");

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

        afficherInfosUtilisateur(currentUser);
        viderChampsFormulaire();
    }


    private void configurerPanelForm() {
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(400, 100, 350, 250);
        this.panelForm.setLayout(new BorderLayout()); // Utiliser BorderLayout pour plus de flexibilité

        // Panel pour les labels et champs
        JPanel panelLabelsFields = new JPanel();
        panelLabelsFields.setLayout(new GridLayout(5, 2));
        panelLabelsFields.setBackground(Color.cyan);

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

        // Panel pour les boutons
        this.panelBoutons.setBackground(Color.cyan);
        this.panelBoutons.setLayout(new GridLayout(1, 2));
        this.panelBoutons.add(this.btAnnuler);
        this.panelBoutons.add(this.btValider);

        this.panelForm.add(this.panelBoutons, BorderLayout.SOUTH); // Ajouter les boutons en bas

        this.add(this.panelForm);
        this.panelForm.setVisible(false);
    }

    private JPanel createTextFieldPanel(JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Ajoute des marges autour du JTextField
        panel.add(textField);
        return panel;
    }

    private void configurerTxtInfos() {
        this.txtInfos.setBounds(50, 100, 300, 240); // Positionnement décalé pour laisser de la place
        this.txtInfos.setBackground(Color.cyan);
        this.txtInfos.setEditable(false);
        this.add(this.txtInfos);
    }

    private void ajouterBoutonsEtEcouteurs() {
        this.btModifier.setBounds(50, 360, 200, 40); // Positionnement à gauche
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
            this.panelBoutons.setVisible(true); // Assurez-vous que cette ligne est présente
        } else if (e.getSource() == this.btAnnuler) {
            viderChampsFormulaire();
            afficherInfosUtilisateur(currentUser);
            this.panelBoutons.setVisible(false); // Optionnel : masquer les boutons après annulation
        } else if (e.getSource() == this.btValider) {
            if (validerFormulaire(currentUser)) {
                validerModifications(currentUser);
                this.panelBoutons.setVisible(false); // Optionnel : masquer après validation
            }
        }
    }


    private boolean validerFormulaire(User currentUser) {
        String email = this.txtEmail.getText().trim();
        String adresse = this.txtAdresse.getText().trim();
        String role = this.txtRole.getText().trim();
        String mdp1 = new String(this.txtMdp1.getPassword());
        String mdp2 = new String(this.txtMdp2.getPassword());

        // Vérification des champs vides
        if (email.isEmpty() || adresse.isEmpty() || role.isEmpty() || mdp1.isEmpty() || mdp2.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tous les champs doivent être remplis.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérification du format de l'email
        if (!email.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            JOptionPane.showMessageDialog(this,
                    "L'email doit contenir un '@' et un '.' (ex: exemple@domaine.com)",
                    "Format email invalide", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérification de la correspondance des mots de passe
        if (!mdp1.equals(mdp2)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérification du rôle utilisateur
        String[] roles = {"admin", "client", "gestionnaire"}; // Exemple de rôles possibles
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
        String mdp1 = new String(this.txtMdp1.getPassword());

        try {
            User modifiedUser = new User(
                    originalUser.getIdUser(),
                    email,
                    mdp1.isEmpty() ? originalUser.getMdpUser() : Controleur.sha1Hash(mdp1),
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