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
import controleur.Particulier;

public class PanelProfil extends PanelPrincipal implements ActionListener {
    private JTextArea txtInfos = new JTextArea();
    private Particulier unParticulier;
    private JButton btModifier = new JButton("Modifier Profil");

    private JPanel panelForm = new JPanel();
    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JPasswordField txtMdp1 = new JPasswordField();
    private JPasswordField txtMdp2 = new JPasswordField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtSexe = new JTextField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    public PanelProfil() {
        super("Gestion du Profil"); // Appel du constructeur de PanelPrincipal avec un paramètre

        // Installation du panel Form
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(400, 100, 400, 300);
        this.panelForm.setLayout(new GridLayout(8, 2));
        this.panelForm.setVisible(false);

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

        this.panelForm.add(new JLabel("Mot de Passe :"));
        this.panelForm.add(this.txtMdp1);

        this.panelForm.add(new JLabel("Confirmation :"));
        this.panelForm.add(this.txtMdp2);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        // Installation de TextArea
        this.txtInfos.setBounds(50, 100, 300, 240);
        unParticulier = Controleur.getParticulierConnecte(); // Récupérer le particulier connecté

        this.txtInfos.setBackground(Color.cyan);
        this.txtInfos.setEditable(false);

        this.txtInfos.setText(
                "________________INFOS PROFIL _____________\n\n"
                        + " Nom : " + unParticulier.getNomUser() + "\n\n"
                        + " Prénom : " + unParticulier.getPrenomUser() + "\n\n"
                        + " Adresse : " + unParticulier.getAdresseUser() + "\n\n"
                        + " Email : " + unParticulier.getEmailUser() + "\n\n"
                        + " Date de Naissance : " + unParticulier.getDateNaissanceUser() + "\n\n"
                        + " Sexe : " + unParticulier.getSexeUser() + "\n\n"
                        + "__________________________________________"
        );
        this.add(this.txtInfos);

        this.btModifier.setBounds(100, 360, 200, 40);
        this.add(this.btModifier);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btModifier.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btModifier) {
            this.txtNom.setText(unParticulier.getNomUser());
            this.txtPrenom.setText(unParticulier.getPrenomUser());
            this.txtAdresse.setText(unParticulier.getAdresseUser());
            this.txtEmail.setText(unParticulier.getEmailUser());
            this.txtDateNaissance.setText(unParticulier.getDateNaissanceUser().toString());
            this.txtSexe.setText(unParticulier.getSexeUser());

            this.panelForm.setVisible(true);
        } else if (e.getSource() == this.btAnnuler) {
            this.txtNom.setText("");
            this.txtPrenom.setText("");
            this.txtAdresse.setText("");
            this.txtEmail.setText("");
            this.txtDateNaissance.setText("");
            this.txtSexe.setText("");
            this.txtMdp1.setText("");
            this.txtMdp2.setText("");

            this.panelForm.setVisible(false);
        } else if (e.getSource() == this.btValider) {
            // On récupère les champs
            String nom = this.txtNom.getText();
            String prenom = this.txtPrenom.getText();
            String adresse = this.txtAdresse.getText();
            String email = this.txtEmail.getText();
            String dateNaissance = this.txtDateNaissance.getText();
            String sexe = this.txtSexe.getText();
            String mdp1 = new String(this.txtMdp1.getPassword());
            String mdp2 = new String(this.txtMdp2.getPassword());

            // On vérifie si les champs sont remplis
            ArrayList<String> lesChamps = new ArrayList<>();
            lesChamps.add(nom);
            lesChamps.add(prenom);
            lesChamps.add(adresse);
            lesChamps.add(email);
            lesChamps.add(dateNaissance);
            lesChamps.add(sexe);
            lesChamps.add(mdp1);
            lesChamps.add(mdp2);

            if (Controleur.verifDonnees(lesChamps) && mdp1.equals(mdp2)) {
                // On instancie le Particulier
                unParticulier.setNomUser(nom);
                unParticulier.setPrenomUser(prenom);
                unParticulier.setAdresseUser(adresse);
                unParticulier.setEmailUser(email);
                unParticulier.setDateNaissanceUser(java.sql.Date.valueOf(dateNaissance)); // Convertir en Date SQL
                unParticulier.setSexeUser(sexe);
                unParticulier.setMdpUser(mdp1);

                // On modifie dans la BDD
                Controleur.updateParticulier(unParticulier);

                // On met à jour le particulier connecté
                Controleur.setParticulierConnecte(unParticulier);

                // Message de confirmation
                JOptionPane.showMessageDialog(this,
                        "Modification des données réussie", "Modification",
                        JOptionPane.OK_OPTION);

                // Mettre à jour l'affichage des informations
                this.txtInfos.setText(
                        "________________INFOS PROFIL _____________\n\n"
                                + " Nom : " + unParticulier.getNomUser() + "\n\n"
                                + " Prénom : " + unParticulier.getPrenomUser() + "\n\n"
                                + " Adresse : " + unParticulier.getAdresseUser() + "\n\n"
                                + " Email : " + unParticulier.getEmailUser() + "\n\n"
                                + " Date de Naissance : " + unParticulier.getDateNaissanceUser() + "\n\n"
                                + " Sexe : " + unParticulier.getSexeUser() + "\n\n"
                                + "__________________________________________"
                );
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir les champs correctement.",
                        "Modification Profil", JOptionPane.ERROR_MESSAGE);
            }

            // On vide les données
            this.txtNom.setText("");
            this.txtPrenom.setText("");
            this.txtAdresse.setText("");
            this.txtEmail.setText("");
            this.txtDateNaissance.setText("");
            this.txtSexe.setText("");
            this.txtMdp1.setText("");
            this.txtMdp2.setText("");

            // On rend le formulaire invisible
            this.panelForm.setVisible(false);
        }
    }
}