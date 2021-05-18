package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Customer.Data.CustomerOrderWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderAuctionWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseAuctionBid;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;

public class OnCustomerOrderPlacedWarehouseGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerOrderWrapper order = (CustomerOrderWrapper) eventBESA.getData();
        WarehouseState state = (WarehouseState) this.getAgent().getState();
        if(state.getStock().containsKey(order.getOrder().getOrderProduct())){
            if(state.getStock().get(order.getOrder().getOrderProduct())>=order.getOrder().getOrderQuantity()){
                CustomerOrderAuctionWrapper auction = new CustomerOrderAuctionWrapper(order.getCustomerId(), order.getOrder(), 6);
                auction.registerBid(new WarehouseAuctionBid(this.getAgent().getAid(), order, true, ));
                state.startAuction(, this.getAgent().getAid(), order.getCustomerId());
            }
        }
    }
}
