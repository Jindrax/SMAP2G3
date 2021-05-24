package sma.grupo3.Retailer.Agents.Warehouse;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderAuctionWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuction;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.Utils.GUI.CatalogCellRenderer;
import sma.grupo3.Retailer.Utils.GUI.CatalogTableModel;
import sma.grupo3.Retailer.Utils.Randomizer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class WarehouseState extends StateBESA {
    Localities locality;
    Map<Catalog, Integer> stock;
    List<CustomerOrder> customerOrders;
    Map<String, CustomerOrderAuctionWrapper> auctions;
    Map<CustomerOrder, TransporterOrderAuction> transporterAuctions;
    Map<CustomerOrder, WarehouseOrderAuction> warehouseAuctions;
    Map<Catalog, Integer> tableIndex;
    CatalogTableModel tableModel;

    public WarehouseState(Localities locality) {
        this.locality = locality;
        this.stock = new Hashtable<Catalog, Integer>();
        this.tableIndex = new Hashtable<>();
        for (Catalog catalog : Catalog.values()) {
            this.stock.put(catalog, Randomizer.randomInt(20, 50));
        }
        this.customerOrders = new ArrayList<>();
        this.auctions = new Hashtable<>();
        this.transporterAuctions = new Hashtable<>();
        this.warehouseAuctions = new Hashtable<>();
        //1. Create the frame.
        JFrame frame = new JFrame(locality.value);
        //frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        //...create emptyLabel...
        frame.getContentPane().add(new JLabel(locality.value), BorderLayout.NORTH);
        JTable table = new JTable(new CatalogTableModel());
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.setRowHeight(table.getRowHeight() + 10);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tableModel = (CatalogTableModel) table.getModel();
        int i = 0;
        this.tableModel.addColumn("Producto");
        this.tableModel.addColumn("Inventario");
        this.tableModel.addColumn("Variacion");
        for (Catalog catalog : Arrays.stream(Catalog.values()).sorted().collect(Collectors.toList())) {
            this.tableIndex.put(catalog, i);
            Object[] row = new Object[3];
            row[0] = catalog.name;
            row[1] = this.stock.get(catalog);
            row[2] = 0;
            this.tableModel.addRow(row);
            i++;
        }
        for (int j = 0; j < tableModel.getColumnCount(); j++) {
            table.setDefaultRenderer(table.getColumnClass(j), new CatalogCellRenderer());
        }
        frame.getContentPane().add(table, BorderLayout.CENTER);
        //4. Size the frame.
        frame.pack();

        frame.setMinimumSize(new Dimension(500, 250));
        frame.setResizable(false);

        //5. Show it.
        frame.setVisible(true);
    }

    public Map<CustomerOrder, TransporterOrderAuction> getTransporterAuctions() {
        return transporterAuctions;
    }

    public Map<CustomerOrder, WarehouseOrderAuction> getWarehouseAuctions() {
        return warehouseAuctions;
    }

    public void startTransporterAuction(CustomerOrder order, TransporterOrderAuction auction) {
        this.transporterAuctions.put(order, auction);
    }

    public void startWarehouseAuction(CustomerOrder order, WarehouseOrderAuction auction) {
        this.warehouseAuctions.put(order, auction);
    }

    public Map<Catalog, Integer> getStock() {
        return stock;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void startAuction(CustomerOrderAuctionWrapper auction, String warehouseId, String customerId) {
        this.auctions.put(warehouseId + "-" + customerId, auction);
    }

    public void finishTransporterAuction(CustomerOrder order) {
        this.transporterAuctions.remove(order);
    }

    public Localities getLocality() {
        return locality;
    }

    public void stockCredit(CustomerOrder order) {
        int actualStock = this.stock.get(order.getOrderProduct());
        int creditedStock = actualStock - order.getOrderQuantity();
        this.stock.put(order.getOrderProduct(), creditedStock);
        int index = this.tableIndex.get(order.getOrderProduct());
        this.tableModel.setValueAt(creditedStock, index, 1);
        this.tableModel.setValueAt("- " + order.getOrderQuantity(), index, 2);
        this.tableModel.setFocusRow(index);
    }

    public boolean evalOrderCapacity(CustomerOrder order) {
        int actualStock = this.stock.get(order.getOrderProduct());
        return order.getOrderQuantity() <= actualStock;
    }

    public int getStock(Catalog catalog) {
        return this.stock.get(catalog);
    }
}
