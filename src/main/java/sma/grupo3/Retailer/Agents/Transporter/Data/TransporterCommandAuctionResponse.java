package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

public class TransporterCommandAuctionResponse extends DataBESA {
    private final String auctioneer;
    private final StandardServices auctioneerService;
    private final TransportCommand transportCommand;
    private final String bidder;
    private boolean fulfillable;
    private double bid;

    public TransporterCommandAuctionResponse(String auctioneer, StandardServices auctioneerService, TransportCommand transportCommand, String bidder) {
        this.auctioneer = auctioneer;
        this.auctioneerService = auctioneerService;
        this.transportCommand = transportCommand;
        this.bidder = bidder;
    }

    public TransportCommand getTransportCommand() {
        return transportCommand;
    }

    public String getBidder() {
        return bidder;
    }

    public boolean isFulfillable() {
        return fulfillable;
    }

    public double getBid() {
        return bid;
    }

    public void setFulfillable(boolean fulfillable) {
        this.fulfillable = fulfillable;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public String getAuctioneer() {
        return auctioneer;
    }

    public StandardServices getAuctioneerService() {
        return auctioneerService;
    }

}
