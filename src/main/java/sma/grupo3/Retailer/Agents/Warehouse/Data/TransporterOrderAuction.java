package sma.grupo3.Retailer.Agents.Warehouse.Data;

import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TransporterOrderAuction {
    private final Set<String> bidders;
    private final Set<TransporterOrderAuctionResponse> bidStack;

    public TransporterOrderAuction(Set<String> bidders) {
        this.bidders = bidders;
        this.bidStack = new HashSet<>();
    }

    public boolean addBidderResponse(TransporterOrderAuctionResponse response) {
        this.bidStack.add(response);
        return this.bidders.size() == this.bidStack.size();
    }

    public TransporterOrderAuctionResponse getAuctionWinner() {
        return this.bidStack.stream().filter(TransporterOrderAuctionResponse::isFulfillable).min(Comparator.comparingDouble(TransporterOrderAuctionResponse::getBid)).orElse(null);
    }
}
