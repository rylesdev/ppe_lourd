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
    private JLabel titreLabel; // Déclaré comme variable de classe pour pouvoir le modifier

    public PanelUser() {
        super("");

        // Configuration du layout principal
        this.setLayout(new BorderLayout());
        Color customColor = new Color(100, 140, 180);
        this.setBackground(customColor);

        // Création du panel titre avec police taille 30
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(customColor); // Appliquer la couleur de fond au panelTitre
        titreLabel = new JLabel("Gestion des Utilisateurs");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titreLabel.setForeground(Color.BLACK); // Définir la couleur du texte en noir
        titreLabel.setBackground(customColor); // Définir le fond du JLabel avec la couleur personnalisée
        titreLabel.setOpaque(true); // Rendre le JLabel opaque pour que le fond soit visible
        panelTitre.add(titreLabel);
        this.add(panelTitre, BorderLayout.NORTH);

        // Configuration des sous-panels
        contentPanel.add(panelParticulier, "Particulier");
        contentPanel.add(panelEntreprise, "Entreprise");
        this.add(contentPanel, BorderLayout.CENTER);

        // Panel boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBoutons.setBackground(customColor);

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
            titreLabel.setText("Gestion des Particuliers");
            titreLabel.setForeground(Color.BLACK); // Définir la couleur du texte en noir
        }
        else if(e.getSource() == btEntreprise) {
            cl.show(contentPanel, "Entreprise");
            titreLabel.setText("Gestion des Entreprises");
            titreLabel.setForeground(Color.BLACK); // Définir la couleur du texte en noir
        }
    }
}