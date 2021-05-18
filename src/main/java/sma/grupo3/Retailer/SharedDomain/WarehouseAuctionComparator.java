package sma.grupo3.Retailer.SharedDomain;

import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseAuctionBid;

import java.util.Comparator;

public class WarehouseAuctionComparator implements Comparator<WarehouseAuctionBid> {
    @Override
    public int compare(WarehouseAuctionBid o1, WarehouseAuctionBid o2) {
        if (o1.isOrderAvailable() && o2.isOrderAvailable()) {
            return (int) (o1.getTimeBid() - o2.getTimeBid());
        } else if (o1.isOrderAvailable()) {
            return 1;
        } else {
            return -1;
        }
    }
}
