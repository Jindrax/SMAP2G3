package sma.grupo3.Retailer.Agents.Warehouse;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderAuctionWrapper;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.Utils.Randomizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class WarehouseState extends StateBESA {
    Map<Catalog, Integer> stock;
    List<CustomerOrder> customerOrders;
    Map<String, CustomerOrderAuctionWrapper> auctions;

    public WarehouseState() {
        this.stock = new Hashtable<Catalog, Integer>();
        for (Catalog catalog : Catalog.values()) {
            this.stock.put(catalog, Randomizer.randomInt(100, 500));
        }
        this.customerOrders = new ArrayList<>();
        this.auctions = new Hashtable<>();
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
}
