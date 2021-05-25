package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnCommandFromWarehouseTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterCommandFromWarehouse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.SharedDomain.Annotations.WarehouseGuard;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;
import sma.grupo3.Retailer.SharedDomain.TransportCommandType;

@WarehouseGuard
public class OnTransporterOrderAuctionResponseWarehouseGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        WarehouseAgent agent = (WarehouseAgent) this.getAgent();
        WarehouseState state = (WarehouseState) agent.getState();
        TransporterOrderAuctionResponse response = (TransporterOrderAuctionResponse) eventBESA.getData();
        CustomerOrder order = response.getOrder();
        TransporterOrderAuction auction = state.getTransporterAuctions().get(response.getOrder());
        if (auction.addBidderResponse(response)) {
            TransporterOrderAuctionResponse winner = auction.getAuctionWinner();
            try {
                AgHandlerBESA transporterHandler = agent.getAdmLocal().getHandlerByAid(winner.getBidder());
                TransportCommand command = new TransportCommand(state.getLocality(), order, TransportCommandType.PICKUP);
                TransporterCommandFromWarehouse commandWrapper = new TransporterCommandFromWarehouse(agent.getAid(), command);
                EventBESA commandToTransporter = new EventBESA(OnCommandFromWarehouseTransporterGuard.class.getName(), commandWrapper);
                transporterHandler.sendEvent(commandToTransporter);
                state.finishTransporterAuction(order);
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }
}
