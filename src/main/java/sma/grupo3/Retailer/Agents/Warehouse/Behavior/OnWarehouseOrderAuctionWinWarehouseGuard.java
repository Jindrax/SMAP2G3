package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Exceptions.NoTransporterAvailable;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.SharedDomain.Annotations.WarehouseGuard;

@WarehouseGuard
public class OnWarehouseOrderAuctionWinWarehouseGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        WarehouseOrderAuctionResponse response = (WarehouseOrderAuctionResponse) eventBESA.getData();
        try {
            ((WarehouseAgent) getAgent()).startTransporterAuction(response.getOrder());
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        } catch (NoTransporterAvailable noTransporterAvailable) {
            ((WarehouseAgent) getAgent()).cancelOrder(response.getOrder());
        }
    }
}
