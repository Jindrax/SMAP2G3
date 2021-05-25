package sma.grupo3.Retailer.Utils.GUI;

import javax.swing.table.DefaultTableModel;

public class FleetTableModel extends DefaultTableModel {

    public FleetTableModel() {
        super();
    }

    public void updateRow(int row) {
        fireTableRowsUpdated(row, row);
    }

}
