package vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelUser extends PanelPrincipal implements ActionListener {

    private JButton btParticulier = new JButton("Particulier");
    private JButton btEntreprise = new JButton("Entreprise");
    private JPanel contentPanel = new JPanel(new CardLayout());
    private PanelParticulier panelParticulier = new PanelParticulier();
    private PanelEntreprise panelEntreprise = new PanelEntreprise();

    public PanelUser() {
        super("");

        // Configuration du layout principal
        this.setLayout(new BorderLayout());
        this.setBackground(Color.CYAN);

        // Création du panel titre avec police taille 30
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(Color.CYAN);
        JLabel titreLabel = new JLabel("Gestion des Utilisateurs");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Modification ici
        panelTitre.add(titreLabel);
        this.add(panelTitre, BorderLayout.NORTH);

        // Configuration des sous-panels
        contentPanel.add(panelParticulier, "Particulier");
        contentPanel.add(panelEntreprise, "Entreprise");
        this.add(contentPanel, BorderLayout.CENTER);

        // Panel boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBoutons.setBackground(Color.CYAN);

        btParticulier.setPreferredSize(new Dimension(150, 40));
        btEntreprise.setPreferredSize(new Dimension(150, 40));

        panelBoutons.add(btParticulier);
        panelBoutons.add(btEntreprise);
        this.add(panelBoutons, BorderLayout.SOUTH);

        // Gestion des événements
        btParticulier.addActionListener(this);
        btEntreprise.addActionListener(this);

        ((CardLayout)contentPanel.getLayout()).show(contentPanel, "Particulier");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)contentPanel.getLayout();

        if(e.getSource() == btParticulier) {
            cl.show(contentPanel, "Particulier");
        }
        else if(e.getSource() == btEntreprise) {
            cl.show(contentPanel, "Entreprise");
        }
    }
}