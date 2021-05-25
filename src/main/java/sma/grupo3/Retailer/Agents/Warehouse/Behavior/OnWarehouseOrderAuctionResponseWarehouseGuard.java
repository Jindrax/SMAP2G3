package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.SharedDomain.Annotations.WarehouseGuard;

@WarehouseGuard
public class OnWarehouseOrderAuctionResponseWarehouseGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        WarehouseAgent agent = (WarehouseAgent) this.getAgent();
        WarehouseState state = (WarehouseState) agent.getState();
        WarehouseOrderAuctionResponse response = (WarehouseOrderAuctionResponse) eventBESA.getData();
        WarehouseOrderAuction auction = state.getWarehouseAuctions().get(response.getOrder());
        if (auction.addBidderResponse(response)) {
            WarehouseOrderAuctionResponse winner = auction.getAuctionWinner();
            if (winner == null) {
                ((WarehouseAgent) getAgent()).cancelOrder(response.getOrder());
            } else {
                try {
                    AgHandlerBESA winnerAgent = getAgent().getAdmLocal().getHandlerByAid(winner.getBidder());
                    EventBESA winnerEvent = new EventBESA(OnWarehouseOrderAuctionWinWarehouseGuard.class.getName(), winner);
                    winnerAgent.sendEvent(winnerEvent);
                } catch (ExceptionBESA exceptionBESA) {
                    exceptionBESA.printStackTrace();
                }
            }
        }
    }
}
