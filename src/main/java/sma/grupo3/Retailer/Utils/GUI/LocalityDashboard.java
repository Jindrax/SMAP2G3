package sma.grupo3.Retailer.Utils.GUI;

import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.TransporterStateEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LocalityDashboard extends JFrame {

    StockTable stockTable;
    JTable fleetTable;
    Map<String, Integer> fleetTableIndex;
    FleetTableModel fleetModel;


    public LocalityDashboard(String title) throws HeadlessException {
        super(title);
        this.fleetTableIndex = new Hashtable<>();
        GridLayout layout = new GridLayout(2, 1);
        setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.stockTable = new StockTable();
        add(stockTable);
        this.fleetTable = new JTable(new FleetTableModel());
        this.fleetModel = (FleetTableModel) fleetTable.getModel();
        fleetTable.setFont(new Font("Serif", Font.PLAIN, 20));
        fleetTable.setRowHeight(fleetTable.getRowHeight() + 10);
        stockTable.setDefaultRenderer(String.class, new FleetCellRenderer());
        this.fleetModel.addColumn("Camion");
        this.fleetModel.addColumn("Localidad");
        this.fleetModel.addColumn("Estado");
        this.fleetTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        this.fleetTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        this.fleetTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        add(fleetTable);
        setResizable(false);
        pack();
        setVisible(true);
    }

    public void addStock(List<Catalog> catalogList, Map<Catalog, Integer> stock) {
        this.stockTable.addStock(catalogList, stock);
        pack();
    }

    public void creditStock(Catalog catalog, int creditedStock, int credit) {
        this.stockTable.creditStock(catalog, creditedStock, credit);
    }

    public void addToFleet(String alias, Localities baseLocality) {
        this.fleetTableIndex.put(alias, this.fleetTableIndex.size());
        Object[] row = new Object[3];
        row[0] = alias;
        row[1] = baseLocality.value;
        row[2] = TransporterStateEnum.OPERATIONAL.name();
        this.fleetModel.addRow(row);
        for (int j = 0; j < this.fleetModel.getColumnCount(); j++) {
            this.fleetTable.setDefaultRenderer(this.fleetTable.getColumnClass(j), new FleetCellRenderer());
        }
        pack();
    }

    public void updateFleetMemberLocality(String alias, Localities locality) {
        Integer index = this.fleetTableIndex.get(alias);
        if (index != null) {
            if (this.fleetModel != null) {
                this.fleetModel.setValueAt(locality.value, index, 1);
                this.fleetModel.updateRow(index);
            }
        }
    }

    public void updateFleetMemberState(String alias, TransporterStateEnum state) {
        Integer index = this.fleetTableIndex.get(alias);
        if (index != null) {
            if (this.fleetModel != null) {
                this.fleetModel.setValueAt(state.name(), index, 2);
                this.fleetModel.updateRow(index);
            }
        }
    }
}
