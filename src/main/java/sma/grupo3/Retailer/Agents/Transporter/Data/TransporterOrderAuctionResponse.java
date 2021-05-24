package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import org.jetbrains.annotations.NotNull;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

import java.util.Objects;


public class TransporterOrderAuctionResponse extends DataBESA implements Comparable {
    private final String auctioneer;
    private final CustomerOrder order;
    private final String bidder;
    private boolean fulfillable;
    private double bid;

    public TransporterOrderAuctionResponse(String auctioneer, CustomerOrder order, String bidder) {
        this.auctioneer = auctioneer;
        this.order = order;
        this.bidder = bidder;
    }

    public CustomerOrder getOrder() {
        return order;
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

    @Override
    public int compareTo(@NotNull Object o) {
        TransporterOrderAuctionResponse response = (TransporterOrderAuctionResponse) o;
        if (this.fulfillable && response.fulfillable) {
            return Double.compare(this.bid, response.bid);
        } else {
            if (this.fulfillable) {
                return -1;
            } else if (response.fulfillable) {
                return 1;
            }
        }
        return 1;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransporterOrderAuctionResponse response = (TransporterOrderAuctionResponse) o;
        return fulfillable == response.fulfillable && Double.compare(response.bid, bid) == 0 && Objects.equals(order, response.order) && Objects.equals(bidder, response.bidder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, bidder, fulfillable, bid);
    }

    @Override
    public String toString() {
        return "TransporterOrderAuctionResponse{" +
                "bidder='" + bidder + '\'' +
                ", fulfillable=" + fulfillable +
                ", bid=" + bid +
                '}';
    }
}
