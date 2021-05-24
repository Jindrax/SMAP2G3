package sma.grupo3.Retailer.Agents.Warehouse.Data;

import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class WarehouseOrderAuction {
    private final Set<String> bidders;
    private final Set<WarehouseOrderAuctionResponse> bidStack;

    public WarehouseOrderAuction(Set<String> bidders) {
        this.bidders = bidders;
        this.bidStack = new HashSet<>();
    }

    public boolean addBidderResponse(WarehouseOrderAuctionResponse response) {
        this.bidStack.add(response);
        return this.bidders.size() == this.bidStack.size();
    }

    public WarehouseOrderAuctionResponse getAuctionWinner() {
        return this.bidStack.stream().filter(WarehouseOrderAuctionResponse::isFulfillable).min(Comparator.comparingDouble(WarehouseOrderAuctionResponse::getBid)).orElse(null);
    }
}
