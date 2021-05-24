package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TransporterCommandAuction extends DataBESA {
    private final Set<String> bidders;
    private final Set<TransporterCommandAuctionResponse> bidStack;

    public TransporterCommandAuction(Set<String> bidders) {
        this.bidders = bidders;
        this.bidStack = new HashSet<>();
    }

    public boolean addBidderResponse(TransporterCommandAuctionResponse response) {
        this.bidStack.add(response);
        return this.bidders.size() == this.bidStack.size();
    }

    public TransporterCommandAuctionResponse getAuctionWinner() {
        return this.bidStack.stream().filter(TransporterCommandAuctionResponse::isFulfillable).min(Comparator.comparingDouble(TransporterCommandAuctionResponse::getBid)).orElse(null);
    }
}
