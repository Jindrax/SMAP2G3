package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.SharedDomain.Annotations.WarehouseGuard;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

@WarehouseGuard
public class OnWarehouseOrderAuctionWarehouseGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        try {
            WarehouseOrderAuctionResponse response = (WarehouseOrderAuctionResponse) eventBESA.getData();
            WarehouseState state = (WarehouseState) this.getAgent().getState();
            CustomerOrder order = response.getOrder();
            boolean fulfillable = state.evalOrderCapacity(order);
            double bid = ConnectionMap.getTimeFromTo(state.getLocality(), order.getCustomerLocality()) - state.getStock(order.getOrderProduct());
            response.setFulfillable(fulfillable);
            response.setBid(bid);
            EventBESA responseEvent = new EventBESA(OnWarehouseOrderAuctionResponseWarehouseGuard.class.getName(), response);
            this.getAgent().getAdmLocal().getHandlerByAid(response.getAuctioneer()).sendEvent(responseEvent);
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}
