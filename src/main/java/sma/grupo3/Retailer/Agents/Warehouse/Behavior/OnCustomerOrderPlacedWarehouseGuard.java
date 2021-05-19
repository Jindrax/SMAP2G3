package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import sma.grupo3.Retailer.Agents.Customer.Behavior.OnWareHouseResponseCustomerGuard;
import sma.grupo3.Retailer.Agents.Customer.Data.CustomerOrderWrapper;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnCommandFromWarehouseTransporterGuard;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderAuctionWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderWarehouseResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterCommandFromWarehouse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseAuctionBid;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;
import sma.grupo3.Retailer.Utils.Configuration;

import java.util.Optional;

public class OnCustomerOrderPlacedWarehouseGuard extends GuardBESA {

    private static final boolean cooperativeBehavior = Configuration.getBoolean("COOPERATIVE_BEHAVIOR");

    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        WarehouseAgent agent = (WarehouseAgent) this.getAgent();
        CustomerOrderWrapper order = (CustomerOrderWrapper) eventBESA.getData();
        WarehouseState state = (WarehouseState) this.getAgent().getState();
        try {
            AgHandlerBESA customerHandler = agent.getAdmLocal().getHandlerByAid(order.getCustomerId());
//            ReportBESA.info(String.format("%s me llega un pedido de %s", this.getAgent().getAlias(), customerHandler.getAlias()));
            if (cooperativeBehavior) {
                cooperativeExecution(agent, customerHandler, order, state);
            } else {
                nonCooperativeExecution(agent, customerHandler, order, state);
            }
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }

    public boolean isWarehouseCapable(CustomerOrderWrapper order, WarehouseState state) {
        if (state.getStock().containsKey(order.getOrder().getOrderProduct())) {
            return state.getStock().get(order.getOrder().getOrderProduct()) >= order.getOrder().getOrderQuantity();
        }
        return false;
    }

    public void nonCooperativeExecution(WarehouseAgent agent, AgHandlerBESA customerHandler, CustomerOrderWrapper order, WarehouseState state) throws ExceptionBESA {
        if (isWarehouseCapable(order, state)) {
            Optional<String> availableTransport = Services.getLocalityServiceProviders(Services.getThisLocality(), StandardServices.TRANSPORTER.value).stream().findFirst();
            if (availableTransport.isPresent()) {
                AgHandlerBESA transporterHandler = agent.getAdmLocal().getHandlerByAid(availableTransport.get());
                ReportBESA.info(String.format("Asignado el camion %s para el pedido de %s: %s %d unidades. Envio desde %s hacia %s",
                        transporterHandler.getAlias(),
                        customerHandler.getAlias(),
                        order.getOrder().getOrderProduct(),
                        order.getOrder().getOrderQuantity(),
                        Services.getThisLocality().value,
                        order.getOrder().getCustomerLocality().value
                ));
                TransportCommand command = new TransportCommand(Services.getThisLocality(), order.getOrder(), true);
                TransporterCommandFromWarehouse commandWrapper = new TransporterCommandFromWarehouse(agent.getAid(), command);
                EventBESA commandToTransporter = new EventBESA(OnCommandFromWarehouseTransporterGuard.class.getName(), commandWrapper);
                transporterHandler.sendEvent(commandToTransporter);
            } else {
                Optional<String> globalTransport = Services.getGlobalServiceProviders(StandardServices.TRANSPORTER.value).stream().findFirst();
                if (globalTransport.isPresent()) {
                    AgHandlerBESA transporterHandler = agent.getAdmLocal().getHandlerByAid(globalTransport.get());
                    ReportBESA.info(String.format("Asignado el camion %s para el pedido de %s: %s %d unidades. Envio desde %s hacia %s",
                            transporterHandler.getAlias(),
                            customerHandler.getAlias(),
                            order.getOrder().getOrderProduct(),
                            order.getOrder().getOrderQuantity(),
                            Services.getThisLocality().value,
                            order.getOrder().getCustomerLocality().value
                    ));
                    TransportCommand command = new TransportCommand(Services.getThisLocality(), order.getOrder(), true);
                    TransporterCommandFromWarehouse commandWrapper = new TransporterCommandFromWarehouse(agent.getAid(), command);
                    EventBESA commandToTransporter = new EventBESA(OnCommandFromWarehouseTransporterGuard.class.getName(), commandWrapper);
                    transporterHandler.sendEvent(commandToTransporter);
                }
            }
        } else {
            customerHandler.sendEvent(new EventBESA(
                    OnWareHouseResponseCustomerGuard.class.getName(),
                    new CustomerOrderWarehouseResponse(agent.getAid(), order.getOrder(), true)
            ));
        }
    }

    public void cooperativeExecution(WarehouseAgent agent, AgHandlerBESA customerHandler, CustomerOrderWrapper order, WarehouseState state) {
        if (isWarehouseCapable(order, state)) {
            CustomerOrderAuctionWrapper auction = new CustomerOrderAuctionWrapper(order.getCustomerId(), order.getOrder(), 6);
            auction.registerBid(new WarehouseAuctionBid(this.getAgent().getAid(), order, true, ConnectionMap.getTimeFromTo(Services.getThisLocality(), order.getOrder().getCustomerLocality())));
            state.startAuction(auction, this.getAgent().getAid(), order.getCustomerId());
        } else {

        }
    }
}
