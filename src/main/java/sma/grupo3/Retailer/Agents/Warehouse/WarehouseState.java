package sma.grupo3.Retailer.Agents.Warehouse;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderAuctionWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuction;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.Utils.GUI.LocalityDashboard;

import java.util.*;
import java.util.stream.Collectors;

public class WarehouseState extends StateBESA {
    Localities locality;
    Map<Catalog, Integer> stock;
    List<CustomerOrder> customerOrders;
    Map<String, CustomerOrderAuctionWrapper> auctions;
    Map<CustomerOrder, TransporterOrderAuction> transporterAuctions;
    Map<CustomerOrder, WarehouseOrderAuction> warehouseAuctions;
    LocalityDashboard dashboard;

    public WarehouseState(Localities locality, LocalityDashboard dashboard, Map<Catalog, Integer> stock) {
        this.locality = locality;
        this.stock = stock;
        this.customerOrders = new ArrayList<>();
        this.auctions = new Hashtable<>();
        this.transporterAuctions = new Hashtable<>();
        this.warehouseAuctions = new Hashtable<>();
        this.dashboard = dashboard;
        this.dashboard.addStock(Arrays.stream(Catalog.values()).sorted().collect(Collectors.toList()), this.stock);

    }

    public WarehouseState(Localities locality, LocalityDashboard dashboard) {
        this.locality = locality;
        this.stock = new Hashtable<>();
        this.customerOrders = new ArrayList<>();
        this.auctions = new Hashtable<>();
        this.transporterAuctions = new Hashtable<>();
        this.warehouseAuctions = new Hashtable<>();
        this.dashboard = dashboard;
        this.dashboard.addStock(Arrays.stream(Catalog.values()).sorted().collect(Collectors.toList()), this.stock);

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
        this.dashboard.creditStock(order.getOrderProduct(), creditedStock, order.getOrderQuantity());
    }

    public boolean evalOrderCapacity(CustomerOrder order) {
        int actualStock = this.stock.get(order.getOrderProduct());
        return order.getOrderQuantity() <= actualStock;
    }

    public int getStock(Catalog catalog) {
        return this.stock.get(catalog);
    }
}
