package sma.grupo3.Retailer.Utils.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CatalogCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        CatalogTableModel model = (CatalogTableModel) table.getModel();
        setBackground(model.getRowColour(row));
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
