package controleur;

import javax.swing.table.AbstractTableModel;

public class Tableau extends AbstractTableModel
{
    private Object donnees[][];
    private String entetes [];

    public Tableau(Object[][] donnees, String[] entetes) {
        super();
        this.donnees = donnees;
        this.entetes = entetes;
    }

    @Override
    public int getRowCount() {
        return this.donnees.length; // nb Lignes
    }

    @Override
    public int getColumnCount() {
        return this.entetes.length; // nb Colonnes
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.donnees[rowIndex][columnIndex];
        //retourne la valeur de l'element [ligne][colonne]
    }

    @Override
    public String getColumnName(int column) {
        return this.entetes[column]; //retourne le nom de la colonne
    }
    //

    public void setDonnees(Object[][] donnees) {
        this.donnees = donnees;
        this.fireTableDataChanged(); //actualiser les données
    }

}
