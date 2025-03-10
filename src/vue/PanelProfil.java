package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import controleur.Controleur;
import controleur.User;

public class PanelProfil extends PanelPrincipal implements ActionListener, ListSelectionListener {
    private JTextArea txtInfos = new JTextArea();
    private User unUser;
    private JButton btModifier = new JButton("Modifier Profil");

    private JPanel panelForm = new JPanel();
    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JPasswordField txtMdp1 = new JPasswordField();
    private JPasswordField txtMdp2 = new JPasswordField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    private User connectedUser;
    private JList<User> userList;
    private DefaultListModel<User> userListModel;

    public PanelProfil() {
        super("Gestion du Profil");

        configurerPanelForm();

        configurerTxtInfos();

        ajouterBoutonsEtEcouteurs();

        // Récupérer l'utilisateur connecté
        connectedUser = Controleur.getUserConnecte();

        // Activer/désactiver les fonctionnalités en fonction du rôle
        if (connectedUser != null && "admin".equals(connectedUser.getRoleUser())) {
            this.btModifier.setEnabled(true);
            configurerUserList();
        } else {
            this.btModifier.setEnabled(false);
        }

        // Afficher les informations de l'utilisateur connecté ou de tous les utilisateurs
        if (connectedUser != null && "admin".equals(connectedUser.getRoleUser())) {
            afficherTousLesUtilisateurs(); // Afficher tous les utilisateurs pour l'admin
        } else if (connectedUser != null) {
            afficherInfosUtilisateur(connectedUser); // Afficher les infos de l'utilisateur connecté
        }

        // Assurez-vous que le panel principal est visible
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

    private void configurerUserList() {
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.addListSelectionListener(this);

        // Ajouter tous les utilisateurs à la liste
        ArrayList<User> allUsers = Controleur.selectUser();
        for (User user : allUsers) {
            userListModel.addElement(user);
        }

        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBounds(50, 350, 300, 100);
        this.add(scrollPane);
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
            if (unUser != null && "admin".equals(connectedUser.getRoleUser())) {
                this.txtEmail.setText(unUser.getEmailUser());
                this.txtAdresse.setText(unUser.getAdresseUser());
                this.panelForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vous n'avez pas les droits nécessaires pour modifier ce profil.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.btAnnuler) {
            viderChampsFormulaire();
        } else if (e.getSource() == this.btValider) {
            validerModifications();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            unUser = userList.getSelectedValue();
            if (unUser != null) {
                afficherInfosUtilisateur(unUser);
            }
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
        unUser.setEmailUser(email);
        unUser.setAdresseUser(adresse);
        unUser.setMdpUser(mdp);

        Controleur.updateUser(unUser);
        Controleur.setUserConnecte(unUser);

        JOptionPane.showMessageDialog(this,
                "Modification des données réussie", "Modification",
                JOptionPane.OK_OPTION);

        afficherInfosUtilisateur(unUser);
    }

    private void afficherInfosUtilisateur(User user) {
        this.txtInfos.setText(
                "________________INFOS PROFIL _____________\n\n"
                        + " ID : " + user.getIdUser() + "\n\n"
                        + " Email : " + user.getEmailUser() + "\n\n"
                        + " Adresse : " + user.getAdresseUser() + "\n\n"
                        + "__________________________________________"
        );
    }

    private void afficherTousLesUtilisateurs() {
        ArrayList<User> allUsers = Controleur.selectUser();
        StringBuilder sb = new StringBuilder();
        for (User user : allUsers) {
            sb.append("________________INFOS PROFIL _____________\n\n")
                    .append(" ID : ").append(user.getIdUser()).append("\n\n")
                    .append(" Email : ").append(user.getEmailUser()).append("\n\n")
                    .append(" Adresse : ").append(user.getAdresseUser()).append("\n\n")
                    .append("__________________________________________\n\n");
        }
        this.txtInfos.setText(sb.toString());
    }
}