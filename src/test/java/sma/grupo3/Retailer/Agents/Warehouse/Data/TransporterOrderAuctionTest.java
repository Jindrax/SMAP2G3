package sma.grupo3.Retailer.Agents.Warehouse.Data;

import org.junit.jupiter.api.Test;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;

import java.util.HashSet;
import java.util.Set;

class TransporterOrderAuctionTest {

    @Test
    void testSorting() {
        Set<String> bidders = new HashSet<String>() {{
            add("Jairo");
            add("Monica");
            add("Juan");
        }};
        TransporterOrderAuction auction = new TransporterOrderAuction(bidders);
        auction.addBidderResponse(generateResponse("Jairo", true, 1000));
        auction.addBidderResponse(generateResponse("Monica", false, 2000));
        auction.addBidderResponse(generateResponse("Juan", true, 1250));
        auction.addBidderResponse(generateResponse("Sergio", false, 750));
        System.out.println(auction.getAuctionWinner());
    }

    TransporterOrderAuctionResponse generateResponse(String bidder, boolean fulfill, double bid) {
        TransporterOrderAuctionResponse response = new TransporterOrderAuctionResponse("SDFGDFHH", StandardServices.WAREHOUSE, null, bidder);
        response.setFulfillable(fulfill);
        response.setBid(bid);
        return response;
    }

}