package vue;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class PanelPrincipal extends JPanel {
    private JLabel lbTitre = new JLabel();

    public PanelPrincipal(String titre) {
        this.setBackground(Color.cyan);
        this.setBounds(50, 80, 900, 440);

        this.lbTitre.setText(titre);
        this.lbTitre.setBounds(350, 20, 400, 30);
        Font unePolice = new Font("Arial", Font.BOLD, 25);
        this.lbTitre.setFont(unePolice);

        this.add(this.lbTitre);

        this.setLayout(null);

        this.setVisible(false);
    }
}





