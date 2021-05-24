package sma.grupo3.Retailer.Utils.GUI;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CatalogTableModel extends DefaultTableModel {

    int focusedRow;

    public CatalogTableModel() {
        super();
        this.focusedRow = -1;
    }

    public void setFocusRow(int row) {
        this.focusedRow = row;
        fireTableRowsUpdated(0, getRowCount());
    }

    public Color getRowColour(int row) {
        if (this.focusedRow == row) {
            return Color.RED;
        } else {
            return Color.WHITE;
        }
    }

}
