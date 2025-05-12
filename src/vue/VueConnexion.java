package vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import controleur.Controleur;
import controleur.Gestion;
import controleur.User;

public class VueConnexion extends JFrame implements ActionListener, KeyListener {
    private JButton btAnnuler = new JButton("Annuler");
    private JButton btSeConnecter = new JButton("Se Connecter");
    private JTextField txtEmail = new JTextField("ryles@gmail.com");
    private JPasswordField txtMdp = new JPasswordField("123");
    private JRadioButton rbAdmin = new JRadioButton("Principal");
    private JRadioButton rbGestionnaire = new JRadioButton("Gestionnaire");
    private ButtonGroup bgRole = new ButtonGroup();

    private JPanel panelForm = new JPanel();

    public VueConnexion() {
        this.setTitle("PPE Client Lourd Ryles");
        this.setBounds(100, 100, 600, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(100, 140, 180));
        this.setLayout(null);
        this.setResizable(false);

        ImageIcon uneImage = new ImageIcon("src/images/livre.png");
        JLabel leLogo = new JLabel(uneImage);
        leLogo.setBounds(20, 20, 250, 250);
        this.add(leLogo);

        this.panelForm.setBackground(new Color(100, 140, 180));
        this.panelForm.setLayout(new GridLayout(3, 2));
        this.panelForm.setBounds(300, 40, 280, 160);

        this.panelForm.add(new JLabel("Email : "));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("MDP : "));
        this.panelForm.add(this.txtMdp);

        this.panelForm.add(new JLabel("Rôle : "));
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.setBackground(new Color(100, 140, 180));
        rolePanel.add(this.rbAdmin);
        rolePanel.add(this.rbGestionnaire);
        this.panelForm.add(rolePanel);

        this.add(this.panelForm);

        this.btAnnuler.setBounds(300, 220, 130, 30);
        this.btSeConnecter.setBounds(450, 220, 130, 30);
        this.add(this.btAnnuler);
        this.add(this.btSeConnecter);

        bgRole.add(rbAdmin);
        bgRole.add(rbGestionnaire);

        this.btAnnuler.addActionListener(this);
        this.btSeConnecter.addActionListener(this);

        this.txtEmail.addKeyListener(this);
        this.txtMdp.addKeyListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.txtEmail.setText("");
            this.txtMdp.setText("");
            this.bgRole.clearSelection();
        } else if (e.getSource() == this.btSeConnecter) {
            this.traitement();
        }
    }

    private void traitement() {
        String email = this.txtEmail.getText();
        String unMdp = new String(this.txtMdp.getPassword());
        String mdp = Controleur.sha1Hash(unMdp);
        String role = this.rbAdmin.isSelected() ? "principal" : "gestionnaire";
        User unUser = Controleur.selectWhereUser(email, mdp, role);
        if (unUser == null) {
            JOptionPane.showMessageDialog(this, "Veuillez vérifier vos identifiants.");
        } else {
            JOptionPane.showMessageDialog(this, "Bienvenue " + unUser.getEmailUser());

            Gestion.rendreVisibleVueConnexion(false);
            Gestion.setUserConnecte(unUser);

            if (Gestion.getVueGenerale() != null) {
                Gestion.getVueGenerale().getPanelProfil().chargerProfil();
            }

            Gestion.creerVueGenerale(true);
        }
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