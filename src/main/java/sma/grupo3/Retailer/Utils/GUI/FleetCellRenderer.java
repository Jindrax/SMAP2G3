package sma.grupo3.Retailer.Utils.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class FleetCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        FleetTableModel model = (FleetTableModel) table.getModel();
        switch ((String) (model.getValueAt(row, 2))) {
            case "FIXING":
                setBackground(Color.RED);
                break;
            case "WAITING":
                setBackground(Color.YELLOW);
                break;
            case "OPERATIONAL":
                setBackground(Color.WHITE);
                break;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
