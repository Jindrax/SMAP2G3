package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.Agents.Warehouse.Behavior.OnTransporterOrderAuctionResponseWarehouseGuard;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.SharedDomain.Annotations.TransporterGuard;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;


@TransporterGuard
public class OnTransporterOrderAuctionTransporterGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        try {
            TransporterOrderAuctionResponse response = (TransporterOrderAuctionResponse) eventBESA.getData();
            TransporterState state = (TransporterState) getAgent().getState();
            double currentLoadWeight = state.getCommandList().stream().mapToDouble(prospect -> prospect.getOrder().getOrderQuantity() * prospect.getOrder().getOrderProduct().weight).reduce(Double::sum).orElse(0);
            CustomerOrder order = response.getOrder();
            double orderWeight = order.getOrderQuantity() * order.getOrderProduct().weight;
            double fullWeight = currentLoadWeight + orderWeight;
            boolean fulfillable = fullWeight <= state.getMaxWeightCapacity();
            double bid = ConnectionMap.getTimeFromTo(state.getCurrentLocality(), order.getCustomerLocality()) + fullWeight;
            response.setFulfillable(fulfillable);
            response.setBid(bid);
            EventBESA responseEvent = new EventBESA(OnTransporterOrderAuctionResponseWarehouseGuard.class.getName(), response);
            getAgent().getAdmLocal().getHandlerByAid(response.getAuctioneer()).sendEvent(responseEvent);
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}
