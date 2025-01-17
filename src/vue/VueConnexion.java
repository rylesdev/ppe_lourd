package vue;

import controleur.Controleur;
import controleur.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VueConnexion extends JFrame implements ActionListener {
    private JButton btAnnuler = new JButton("Annuler");
    private JButton btSeConnecter = new JButton("Se Connecter");
    private JTextField txtEmail = new JTextField("b@gmail.com");
    private JPasswordField txtMdp = new JPasswordField("123");

    private JPanel panelForm = new JPanel();

    public VueConnexion() {
        // Changer le titre de la fenetre
        this.setTitle("Application Client Lourd Gestion de Orange 2025");

        // Définir des dimensions de la fenetre $
        this.setBounds(100, 100, 600, 300);

        // Fermer et tuer l'application sur le bouton croix
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Changer la couleur de fond de la fenetre
        this.getContentPane().setBackground(new Color(81, 66, 62));

        // Eliminer le quadrillage par defaut : feuille de style
        this.setLayout(null);

        // Desactiver le redimensionnement de la fenetre
        this.setResizable(false);

        // Installation du logo
        ImageIcon uneImage = new ImageIcon("src/images/logo.png");
        JLabel leLogo = new JLabel(uneImage);
        leLogo.setBounds(20, 20, 250, 250);
        this.add(leLogo);


        // Construction du panel Formulaire
        this.panelForm.setBackground(new Color(81, 66, 62));
        this.panelForm.setLayout(new GridLayout(3,2));
        this.panelForm.setBounds(300, 60, 280, 180);
        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);
        this.panelForm.add(new JLabel ("MDP :"));
        this.panelForm.add(this.txtMdp);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btSeConnecter);
        this.add(this.panelForm);

        // Rendre les boutons cliquables
        this.btAnnuler.addActionListener(this);
        this.btSeConnecter.addActionListener(this);


        // Rendre visible la fenetre
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btAnnuler) {
            this.txtEmail.setText("");
            this.txtMdp.setText("");
        } else if (e.getSource() == this.btSeConnecter) {
            String email = this.txtEmail.getText();
            String mdp = new String (this.txtMdp.getPassword());

            User unUser = Controleur.selectWhereUser(email, mdp);
            if (unUser == null) {
                JOptionPane.showMessageDialog(this, "Veuillez vérifier vos identifiants.");
            } else {
                JOptionPane.showMessageDialog(this, "Bienvenue " + unUser.getNom());
            }
        }
    }
}