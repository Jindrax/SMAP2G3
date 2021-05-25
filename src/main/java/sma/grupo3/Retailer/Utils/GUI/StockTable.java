package sma.grupo3.Retailer.Utils.GUI;

import sma.grupo3.Retailer.SharedDomain.Catalog;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class StockTable extends JTable {
    Map<Catalog, Integer> catalogTableIndex;
    CatalogTableModel tableModel;

    public StockTable() {
        super(new CatalogTableModel());
        this.tableModel = (CatalogTableModel) getModel();
        setDefaultRenderer(String.class, new CatalogCellRenderer());
        setFont(new Font("Serif", Font.PLAIN, 20));
        setRowHeight(getRowHeight() + 10);
        this.tableModel.addColumn("Producto");
        this.tableModel.addColumn("Inventario");
        this.tableModel.addColumn("Variacion");
        getColumnModel().getColumn(0).setPreferredWidth(40);
        getColumnModel().getColumn(1).setPreferredWidth(10);
        getColumnModel().getColumn(2).setPreferredWidth(10);
    }

    public void addStock(List<Catalog> catalogList, Map<Catalog, Integer> stock) {
        this.catalogTableIndex = new Hashtable<>();
        int i = 0;
        for (Catalog catalog : catalogList) {
            this.catalogTableIndex.put(catalog, i);
            Object[] row = new Object[3];
            row[0] = catalog.name;
            row[1] = stock.get(catalog);
            row[2] = 0;
            this.tableModel.addRow(row);
            i++;
        }
        for (int j = 0; j < tableModel.getColumnCount(); j++) {
            setDefaultRenderer(getColumnClass(j), new CatalogCellRenderer());
        }
    }

    public void creditStock(Catalog catalog, int creditedStock, int credit) {
        int index = this.catalogTableIndex.get(catalog);
        this.tableModel.setValueAt(creditedStock, index, 1);
        this.tableModel.setValueAt("- " + credit, index, 2);
        this.tableModel.setFocusRow(index);
    }
}
