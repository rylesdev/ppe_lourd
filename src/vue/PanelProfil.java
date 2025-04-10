package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private User connectedUser;
    private JButton btModifier = new JButton("Modifier Profil");

    private JPanel panelForm = new JPanel();
    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JPasswordField txtMdp1 = new JPasswordField();
    private JPasswordField txtMdp2 = new JPasswordField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    public PanelProfil() {
        super("Gestion du Profil");

        configurerPanelForm();
        configurerTxtInfos();
        ajouterBoutonsEtEcouteurs();

        connectedUser = Controleur.getUserConnecte();

        if (connectedUser != null) {
            afficherInfosUtilisateur(connectedUser);
        } else {
            JOptionPane.showMessageDialog(this, "Aucun utilisateur connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        this.setVisible(true);
    }

    private void configurerPanelForm() {
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(400, 100, 400, 300);
        this.panelForm.setLayout(new GridLayout(4, 2));
        this.panelForm.setVisible(false);

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(new JLabel("Mot de Passe :"));
        this.panelForm.add(this.txtMdp1);

        this.panelForm.add(new JLabel("Confirmation :"));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btModifier) {
            if (connectedUser != null) {
                this.txtEmail.setText(connectedUser.getEmailUser());
                this.txtAdresse.setText(connectedUser.getAdresseUser());
                this.panelForm.setVisible(true);
            }
        } else if (e.getSource() == this.btAnnuler) {
            viderChampsFormulaire();
        } else if (e.getSource() == this.btValider) {
            validerModifications();
        }
    }

    private void viderChampsFormulaire() {
        this.txtEmail.setText("");
        this.txtAdresse.setText("");
        this.txtMdp1.setText("");
        this.txtMdp2.setText("");
        this.panelForm.setVisible(false);
    }

    private void validerModifications() {
        String email = this.txtEmail.getText();
        String adresse = this.txtAdresse.getText();
        String mdp1 = new String(this.txtMdp1.getPassword());
        String mdp2 = new String(this.txtMdp2.getPassword());

        ArrayList<String> lesChamps = new ArrayList<>();
        lesChamps.add(email);
        lesChamps.add(adresse);
        lesChamps.add(mdp1);
        lesChamps.add(mdp2);

        if (Controleur.verifDonnees(lesChamps) && mdp1.equals(mdp2)) {
            mettreAJourUtilisateur(email, adresse, mdp1);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir les champs correctement.",
                    "Modification Profil", JOptionPane.ERROR_MESSAGE);
        }

        viderChampsFormulaire();
    }

    private void mettreAJourUtilisateur(String email, String adresse, String mdp) {
        connectedUser.setEmailUser(email);
        connectedUser.setAdresseUser(adresse);
        connectedUser.setMdpUser(mdp);

        Controleur.updateUser(connectedUser);
        Controleur.setUserConnecte(connectedUser);

        JOptionPane.showMessageDialog(this,
                "Modification des données réussie", "Modification",
                JOptionPane.OK_OPTION);

        afficherInfosUtilisateur(connectedUser);
    }

    private void afficherInfosUtilisateur(User user) {
        if (user == null) {
            System.out.println("Erreur : L'utilisateur est null.");
            return;
        }
        System.out.println("Affichage des informations de l'utilisateur : " + user.getEmailUser());
        this.txtInfos.setText(
                "________________INFOS PROFIL _____________\n\n"
                        + " ID : " + user.getIdUser() + "\n\n"
                        + " Email : " + user.getEmailUser() + "\n\n"
                        + " Adresse : " + user.getAdresseUser() + "\n\n"
                        + "__________________________________________"
        );
    }
}
