package sma.grupo3.Retailer.Agents.Warehouse.Data;

import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.WarehouseAuctionComparator;

import java.util.PriorityQueue;

public class CustomerOrderAuctionWrapper {
    String customerId;
    CustomerOrder order;
    int maxSeats;
    PriorityQueue<WarehouseAuctionBid> auctionBids;

    public CustomerOrderAuctionWrapper(String customerId, CustomerOrder order, int maxSeats) {
        this.customerId = customerId;
        this.order = order;
        this.maxSeats = maxSeats;
        this.auctionBids = new PriorityQueue<WarehouseAuctionBid>(new WarehouseAuctionComparator());
    }

    public WarehouseAuctionBid registerBid(WarehouseAuctionBid bid) {
        this.auctionBids.add(bid);
        this.maxSeats--;
        if (maxSeats == 0) {
            return this.auctionBids.peek();
        }
        return null;
    }
}
